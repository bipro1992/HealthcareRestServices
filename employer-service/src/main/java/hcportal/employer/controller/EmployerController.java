package hcportal.employer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hcportal.employer.entity.Employer;
import hcportal.employer.service.EmployerService;

@RestController
public class EmployerController {

	@Autowired
	private EmployerService service;

	@RequestMapping(method = RequestMethod.POST, value = "/employer", consumes = "application/json")
	public Employer addEmployer(@RequestBody Employer employer) {
		return this.service.addEmployer(employer);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/employer/{id}", produces = "application/json")
	public Employer getEmployerById(@PathVariable("id") int employerId) {
		return this.service.getEmployerById(employerId);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/employer/{id}", produces = "application/json", consumes = "application/json")
	public Employer updateEmployer(@PathVariable("id") int employerId, @RequestBody Employer employer) {
		return this.service.updateEmployer(employerId, employer);
	}

}
