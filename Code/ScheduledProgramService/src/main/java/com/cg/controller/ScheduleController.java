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

import com.cg.entity.Schedule;
import com.cg.exception.ScheduleAlreadyExistsException;
import com.cg.exception.ScheduleNotFoundException;
import com.cg.service.ScheduledProgramService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/schedule/")
public class ScheduleController {

	Logger logger = LoggerFactory.getLogger(ScheduleController.class);
	
	@Autowired
	private ScheduledProgramService service;
	
	@GetMapping("/getall")
	public List<Schedule> getAllSchedule() {
		return service.getAllSchedules();
	}

	@GetMapping("/getbyid/{id}")
	@HystrixCommand(fallbackMethod = "ScheduleNotFoundByIdErrorHandler")
	public ResponseEntity<Schedule> getScheduleById(@PathVariable int id) throws ScheduleNotFoundException {
		Schedule p = service.getScheduleById(id);
		logger.info("Schedule found in database : "+p);
		return ResponseEntity.status(HttpStatus.SC_OK).body(p);
	}

	@PostMapping("/addschedule")
	@HystrixCommand(fallbackMethod = "ScheduleAdditionErrorHandler")
	public ResponseEntity<Schedule> addSchedule(@RequestBody Schedule Schedule) throws ScheduleAlreadyExistsException {
		Schedule p = service.addSchedule(Schedule);
		logger.info("Schedule added to database");
		return ResponseEntity.status(HttpStatus.SC_OK).body(p);
	}

	@DeleteMapping("/delete/id/{id}")
	@HystrixCommand(fallbackMethod = "ScheduleNotFoundByIdErrorHandler")
	public ResponseEntity<Schedule> deleteSchedule(@PathVariable int id) throws ScheduleNotFoundException {
		Schedule p = service.deleteScheduleById(id);
		logger.info("Schedule deleted from database : "+p);
		return ResponseEntity.status(HttpStatus.SC_OK).body(p);
	}

	@PutMapping("/updateschedule")
	@HystrixCommand(fallbackMethod = "ScheduleUpdateErrorHandler")
	public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule Schedule) throws ScheduleNotFoundException {
		Schedule p = service.updateScheduleById(Schedule);
		logger.info("Schedule updated in database : "+p);
		return ResponseEntity.status(HttpStatus.SC_OK).body(p);
	}
	
	//add Schedule error handler
	public ResponseEntity<Schedule> ScheduleAdditionErrorHandler(Schedule Schedule) {
		logger.info("cannot add - Schedule entered is invalid");
		return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(null);
	}

	//update Schedule error handler
	public ResponseEntity<Schedule> ScheduleUpdateErrorHandler(Schedule Schedule) {
		logger.info("cannot update - Schedule entered is invalid : " + Schedule.getId());
		return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
	}

	//not found Schedule error handler
	public ResponseEntity<Schedule> ScheduleNotFoundByIdErrorHandler(int id) {
		logger.info("Schedule with id not present in database : " + id);
		return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
	}
}
