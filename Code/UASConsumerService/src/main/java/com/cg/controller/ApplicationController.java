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
import com.cg.service.ApplicationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/application")
public class ApplicationController {

	Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private ApplicationService service;

	@GetMapping("/getall")
	public ResponseEntity<List<Application>> getAllApplications() {
		return service.getAllApplications();
	}

	@GetMapping("/getapplicationbyid/{id}")
	@HystrixCommand(fallbackMethod = "ApplicationNotFoundByIdErrorHandler")
	public ResponseEntity<Application> getApplicationById(@PathVariable int id) {
		return service.getApplicationById(id);
	}

	@PostMapping("/addapplication")
	@HystrixCommand(fallbackMethod = "ApplicationAdditionErrorHandler")
	public ResponseEntity<Application> addNewApplication(@RequestBody Application application) {
		return service.addApplication(application);
	}

	@DeleteMapping("/mac/deleteapplicationbyid/{id}")
	@HystrixCommand(fallbackMethod = "ApplicationNotFoundByIdErrorHandler")
	public ResponseEntity<Application> deleteApplicationById(@PathVariable int id) {
		return service.deleteApplicationById(id);
	}

	@PutMapping("/mac/updateapplication")
	@HystrixCommand(fallbackMethod = "ApplicationUpdateErrorHandler")
	public ResponseEntity<Application> updateApplication(@RequestBody Application application) {
		return service.updateApplication(application);
	}

	// add Application error handler
	public ResponseEntity<Application> ApplicationAdditionErrorHandler(Application application) {
		logger.info("cannot add - application entered by applicant is invalid");
		return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(null);
	}

	// update Application error handler
	public ResponseEntity<Application> ApplicationUpdateErrorHandler(Application application) {
		logger.info("cannot update - application entered by applicant is invalid : " + application.getApplicationId());
		return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
	}

	// not found Application error handler
	public ResponseEntity<Application> ApplicationNotFoundByIdErrorHandler(int id) {
		logger.info("Application with id not present in database : " + id);
		return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
	}
}
