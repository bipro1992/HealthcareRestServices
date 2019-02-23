package hcportal.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;





/**
 * @author biprajeet
 *
 *This is main class of Employee Rest Spring boot app
 *
 */


@EnableJpaRepositories(basePackages="hcportal.microservice.repository")
@EnableCircuitBreaker
@EnableCaching
@SpringBootApplication
public class EmployeeRest {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(EmployeeRest.class, args);

	}

}
