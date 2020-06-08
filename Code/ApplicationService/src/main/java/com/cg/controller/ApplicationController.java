package com.cg.controller;

import java.util.List;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.Application;
import com.cg.exception.ApplicationAlreadyExistsException;
import com.cg.exception.ApplicationNotFoundException;
import com.cg.service.ApplicationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/application/")
public class ApplicationController {

	Logger logger = LoggerFactory.getLogger(ApplicationController.class);
	
	@Autowired
	private ApplicationService service;
	
	@GetMapping("/getall")
	public List<Application> getAllApplications() {
		return service.getAllApplications();
	}
	
	@GetMapping("/getbyid/{id}")
	@HystrixCommand(fallbackMethod = "ApplicationNotFoundByIdErrorHandler")
	public ResponseEntity<Application> getApplicationById(@PathVariable int id) throws ApplicationNotFoundException {
		Application a = service.getApplicationById(id);
		logger.info("Application found in database : "+a);
		return ResponseEntity.status(HttpStatus.SC_OK).body(a);
	}
	
	@PostMapping("/addapplication")
	@HystrixCommand(fallbackMethod = "ApplicationAdditionErrorHandler")
	public ResponseEntity<Application> addApplication(@RequestBody Application Application) throws ApplicationAlreadyExistsException {
		Application a = service.addApplication(Application);
		logger.info("Application added to database");
		return ResponseEntity.status(HttpStatus.SC_OK).body(a);
	}
	
	@DeleteMapping("/delete/id/{id}")
	@HystrixCommand(fallbackMethod = "ApplicationNotFoundByIdErrorHandler")
	public ResponseEntity<Application> deleteApplication(@PathVariable int id) throws ApplicationNotFoundException {
		Application a = service.deleteApplicationById(id);
		logger.info("Application deleted from database : "+a);
		return ResponseEntity.status(HttpStatus.SC_OK).body(a);
	}
	
	@PutMapping("/updateapplication")
	@HystrixCommand(fallbackMethod = "ApplicationUpdateErrorHandler")
	public ResponseEntity<Application> updateApplication(@RequestBody Application Application) throws ApplicationNotFoundException {
		Application a = service.updateApplicationById(Application);
		logger.info("Application updated in database : "+a);
		return ResponseEntity.status(HttpStatus.SC_OK).body(a);
	}
	
	//add Application error handler
		public ResponseEntity<Application> ApplicationAdditionErrorHandler(Application Application) {
			logger.info("cannot add - Application entered is invalid");
			return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(null);
		}

		//update Application error handler
		public ResponseEntity<Application> ApplicationUpdateErrorHandler(Application Application) {
			logger.info("cannot update - Application entered is invalid : " + Application.getApplicationId());
			return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
		}

		//not found Application error handler
		public ResponseEntity<Application> ApplicationNotFoundByIdErrorHandler(int id) {
			logger.info("Application with id not present in database : " + id);
			return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
		}
}
