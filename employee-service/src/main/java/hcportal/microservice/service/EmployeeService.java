package hcportal.microservice.service;

import java.io.FileInputStream;
import java.io.InputStream;
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

import hcportal.microservice.dto.Employer;
import hcportal.microservice.entity.Employee;
import hcportal.microservice.repository.EmployeeRespository;

@Service
public class EmployeeService {

	private static Logger log = LoggerFactory.getLogger(EmployeeService.class);

	// Rest Url path to access Employer Rest service
	private static String restUrl = "employer/";
	@Autowired
	private EmployeeRespository respository;

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	// using JPA to find employee object by employee's ID

	@Cacheable("EmployeeId")
	public Employee getEmployeeById(int employeeId) {
		try {
			return this.respository.findById(employeeId).get();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/*
	 * @Cacheable("Employee")
	 * 
	 * public Employee addEmployee(Employee employee) {
	 * 
	 * Employee ee = this.respository.save(employee); return ee; }
	 */

	/*
	 * @Cacheable("EmployeeUpdate") public Employee updateEmployee(int
	 * employeeId, Employee employee) { Employee employeeNew =
	 * this.respository.findById(employeeId).get();
	 * 
	 * if (employeeNew != null) {
	 * employeeNew.setFirstName(employee.getFirstName());
	 * employeeNew.setLastName(employee.getLastName());
	 * employeeNew.setAddress1(employee.getAddress1());
	 * employeeNew.setAddress2(employee.getAddress2());
	 * 
	 * return this.respository.save(employeeNew); }
	 * 
	 * return null;
	 * 
	 * }
	 */

	/**
	 * @param employeeId
	 * @return
	 * 
	 * 		Below method is demonstrating fallback functionality of Hystrix
	 *         i.e circuit breaker option. Employee Id is provided as input to
	 *         obtain employer object , whic contains details about employers
	 *         provided election for all members under it
	 * 
	 * 
	 */
	@HystrixCommand(fallbackMethod = "getElectionAmountFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000") })
	public Employer getElectionAmount(int employeeId) {

		Employer employer = null;
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("..\\hcportal.microservice\\src\\main\\resources\\config.properties");

			prop.load(input);
			String baseUrl = prop.getProperty("employerhost");

			Employee employee = this.respository.findById(employeeId).get();

			if (employee != null) {
				String url = baseUrl + EmployeeService.restUrl + employee.getEmployerId();
				employer = restTemplate.getForObject(url, Employer.class);
			}
		} catch (Exception e) {

			log.error(e.getMessage());
			return null;

		}

		return employer;

	}

	public Employer getElectionAmountFallback(int employeeId) {
		log.info("inside getElectionAmountFallback");
		return null;

	}

	/*
	 * public Employee addEmployeeFallback(Employee employee) { return null;
	 * 
	 * }
	 */

}
