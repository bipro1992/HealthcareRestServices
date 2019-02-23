package hcportal.employer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import hcportal.employer.entity.Employer;
import hcportal.employer.repository.EmployerRepository;





/**
 * @author biprajeet
 * 
 * This is service class for employer rest service.
 *
 */
@Service
public class EmployerService {

	private static Logger log = LoggerFactory.getLogger(EmployerService.class);

	
	
	@Autowired
	private EmployerRepository repository;

	@Cacheable("addEmployer")
	public Employer addEmployer(Employer employer) {
		return this.repository.save(employer);
	}

	@Cacheable("getEmployerById")
	public Employer getEmployerById(int employerId) {
		try {
			return this.repository.findById(employerId).get();

		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}

	}

	@Cacheable("updateEmployer")
	public Employer updateEmployer(int employerId, Employer employer) {
		try {
			Employer employerNew = this.repository.findById(employerId).get();

			if (employer != null) {
				employerNew.setEmployerName(employer.getEmployerName());
				employerNew.setElectionAmount(employer.getElectionAmount());
				return this.repository.save(employerNew);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;

		}

		return null;
	}

}
