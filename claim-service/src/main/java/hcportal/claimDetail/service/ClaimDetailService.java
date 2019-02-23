package hcportal.claimDetail.service;

import java.io.FileInputStream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import hcportal.claimDetail.dto.Employee;
import hcportal.claimDetail.entity.ClaimDetail;
import hcportal.claimDetail.repository.ClaimDetailRepository;

/**
 * @author biprajeet 
 * 
 * This is claim detail service class providing rest services like finding claimdetail by its ID
 * or finding list of claims for given employee Id
 * 
 * This service class is demonstrating fallback functionality of Hystrix i.e circuit breaker and caching .
 *
 */
@Service
public class ClaimDetailService {

	private static Logger log = LoggerFactory.getLogger(ClaimDetailService.class);
	private static String restUrl = "employee/";

	@Autowired
	private ClaimDetailRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Cacheable("claimDetailById")
	public ClaimDetail getClaimDetailById(int claimDetail) {
		try {
			return this.repository.findById(claimDetail).get();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	
	

	@HystrixCommand(fallbackMethod = "getClaimDetailsByEmployeeIdFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000") })

	public List<ClaimDetail> getClaimDetailsByEmployeeId(int employeeId) {

		Properties prop = new Properties();
		InputStream input = null;
		List<ClaimDetail> claimDetails = new ArrayList<ClaimDetail>();

		try {

			input = new FileInputStream("..\\hcportal.claimDetail\\src\\main\\resources\\config.properties");
			prop.load(input);
			String baseUrl = prop.getProperty("employeehost");
			String url = baseUrl + ClaimDetailService.restUrl + employeeId;

			Employee employee = this.restTemplate.getForObject(url, Employee.class);

			if (employee != null) {
				// System.out.println(employee.getFirstName());
				claimDetails = this.repository.findByEmployeeId(employeeId);
				return claimDetails;
			}
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			log.error(e.getMessage());
			return null;
		}

		return null;

	}

	/*
	 * // @HystrixCommand(fallbackMethod = "addClaimDetailFallback")
	 * // @Cacheable("addClaimDetail")
	 * 
	 * public ClaimDetail addClaimDetail(ClaimDetail claimDetail) {
	 * 
	 * String url = "http://localhost:8085/employee/" +
	 * claimDetail.getEmployeeId();
	 * 
	 * Employee employee = this.restTemplate.getForObject(url, Employee.class);
	 * 
	 * if (employee != null) { ClaimDetail claimDetailNew =
	 * this.repository.save(claimDetail); return claimDetailNew; }
	 * 
	 * return null; }
	 */

	/*
	 * @Cacheable("updateClaimDetail") public ClaimDetail updateClaimDetail(int
	 * claimDetailId, ClaimDetail claimDetail) { ClaimDetail claimDetailNew =
	 * this.repository.findById(claimDetailId).get(); if (claimDetailNew !=
	 * null) {
	 * claimDetailNew.setRequestedAmount(claimDetail.getRequestedAmount());
	 * claimDetailNew.setDeniedAmount(claimDetail.getDeniedAmount()); return
	 * this.repository.save(claimDetailNew); }
	 * 
	 * return null; }
	 */

	public List<ClaimDetail> getClaimDetailsByEmployeeIdFallback(int employeeId) {
		log.info("inside getClaimDetailsByEmployeeIdFallback");
		List<ClaimDetail> claimDetails = new ArrayList<ClaimDetail>();
		return claimDetails;
	}
}
