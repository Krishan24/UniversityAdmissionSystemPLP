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
import com.cg.entity.Program;
import com.cg.entity.Schedule;
import com.cg.service.AdminService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/admin")
public class AdminController {

	Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;
	

	@GetMapping("getAppsBySchedId")
	public ResponseEntity<List<Application>> getReportOfApps(@PathVariable int schedId){
		return adminService.getAllApplicationsReportBySchedId(schedId);
	}
	
	@GetMapping("/getallprograms")
	public ResponseEntity<List<Program>> getAllPrograms() {
		return adminService.getAllProgramsOffered();
	}

	@GetMapping("/getprogrambyid/{id}")
	@HystrixCommand(fallbackMethod = "ProgramNotFoundByIdErrorHandler")
	public ResponseEntity<Program> getProgramById(@PathVariable int id) {
		return adminService.getProgramById(id);
	}

	@PostMapping("/addprogram")
	@HystrixCommand(fallbackMethod = "ProgramAdditionErrorHandler")
	public ResponseEntity<Program> addNewProgram(@RequestBody Program program) {
		return adminService.addProgram(program);
	}

	@DeleteMapping("/deleteprogrambyid/{id}")
	@HystrixCommand(fallbackMethod = "ProgramNotFoundByIdErrorHandler")
	public ResponseEntity<Program> deleteProgramById(@PathVariable int id) {
		return adminService.deleteProgramById(id);
	}

	@PutMapping("/updateprogram")
	@HystrixCommand(fallbackMethod = "ProgramUpdateErrorHandler")
	public ResponseEntity<Program> updateProgram(@RequestBody Program program) {
		return adminService.updateProgram(program);
	}

	// add program error handler
	public ResponseEntity<Program> ProgramAdditionErrorHandler(Program program) {
		logger.info("cannot add - program entered by admin is invalid");
		return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(null);
	}

	// update program error handler
	public ResponseEntity<Program> ProgramUpdateErrorHandler(Program program) {
		logger.info("cannot update - program entered by admin is invalid : " + program.getId());
		return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
	}

	// not found program error handler
	public ResponseEntity<Program> ProgramNotFoundByIdErrorHandler(int id) {
		logger.info("program with id not present in database : " + id);
		return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
	}

	// scheduled program controls

	@GetMapping("/getallschedules")
	public ResponseEntity<List<Schedule>> getAllSchedules() {
		return adminService.getAllScheduledPrograms();
	}

	@GetMapping("/getschedulebyid/{id}")
	@HystrixCommand(fallbackMethod = "ScheduleNotFoundByIdErrorHandler")
	public ResponseEntity<Schedule> getScheduleById(@PathVariable int id) {
		return adminService.getScheduledProgramById(id);
	}

	@PostMapping("/addschedule")
	@HystrixCommand(fallbackMethod = "ScheduleAdditionErrorHandler")
	public ResponseEntity<Schedule> addNewSchedule(@RequestBody Schedule schedule) {
		return adminService.addScheduledProgram(schedule);
	}

	@DeleteMapping("/deleteschedulebyid/{id}")
	@HystrixCommand(fallbackMethod = "ScheduleNotFoundByIdErrorHandler")
	public ResponseEntity<Schedule> deleteScheduleById(@PathVariable int id) {
		return adminService.deleteScheduledProgramsById(id);
	}

	@PutMapping("/updateschedule")
	@HystrixCommand(fallbackMethod = "ScheduleUpdateErrorHandler")
	public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule schedule) {
		return adminService.updateScheduledProgram(schedule);
	}

	// add schedule error handler
	public ResponseEntity<Schedule> ScheduleAdditionErrorHandler(Schedule schedule) {
		logger.info("cannot add - Schedule entered by admin is invalid");
		return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(null);
	}

	// update schedule error handler
	public ResponseEntity<Schedule> ScheduleUpdateErrorHandler(Schedule schedule) {
		logger.info("cannot update - Schedule entered by admin is invalid : " + schedule.getId());
		return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
	}

	// not found schedule error handler
	public ResponseEntity<Schedule> ScheduleNotFoundByIdErrorHandler(int id) {
		logger.info("schedule with id not present in database : " + id);
		return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
	}

}

