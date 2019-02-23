package hcportal.claimDetail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hcportal.claimDetail.entity.ClaimDetail;
import hcportal.claimDetail.service.ClaimDetailService;

@RestController
public class ClaimDetailController {

	@Autowired
	private ClaimDetailService claimDetailService;

	/*@RequestMapping(method = RequestMethod.POST, value = "/claimDetail", consumes = "application/json")
	public ClaimDetail addClaimDetail(@RequestBody ClaimDetail claimDetail) {
		return this.claimDetailService.addClaimDetail(claimDetail);
	}*/

	@RequestMapping(method = RequestMethod.GET, value = "/claimDetail/{id}", produces = "application/json")
	public ClaimDetail getClaimDetailById(@PathVariable("id") int claimDetailId) {
		return this.claimDetailService.getClaimDetailById(claimDetailId);
	}

	
	@RequestMapping(method = RequestMethod.GET, value = "/claimDetail/employee/{id}", produces = "application/json")
	public List<ClaimDetail> getClaimDetailsByEmployeeId(@PathVariable("id") int employeeId) {
		return this.claimDetailService.getClaimDetailsByEmployeeId(employeeId);
	}

	/*@RequestMapping(method = RequestMethod.PUT, value = "/claimDetail/{id}", consumes = "application/json", produces = "application/json")
	public ClaimDetail updateClaimDetail(@PathVariable("id") int claimDetailId, @RequestBody ClaimDetail claimDetail) {
		return this.claimDetailService.updateClaimDetail(claimDetailId, claimDetail);
	}*/

}
