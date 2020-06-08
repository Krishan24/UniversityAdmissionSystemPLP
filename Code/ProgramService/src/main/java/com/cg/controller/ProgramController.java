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

import com.cg.entity.Program;
import com.cg.exception.ProgramAlreadyExistsException;
import com.cg.exception.ProgramNotFoundException;
import com.cg.service.ProgramService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/program")
public class ProgramController {

	Logger logger = LoggerFactory.getLogger(ProgramController.class);

	@Autowired
	private ProgramService service;

	@GetMapping("/getall")
	public List<Program> getAllProgram() {
		return service.getAllPrograms();
	}

	@GetMapping("/getbyid/{id}")
	@HystrixCommand(fallbackMethod = "ProgramNotFoundByIdErrorHandler")
	public ResponseEntity<Program> getProgramById(@PathVariable int id) throws ProgramNotFoundException {
		Program p = service.getProgramById(id);
		logger.info("program found in database : "+p);
		return ResponseEntity.status(HttpStatus.SC_OK).body(p);
	}

	@PostMapping("/addprogram")
	@HystrixCommand(fallbackMethod = "ProgramAdditionErrorHandler")
	public ResponseEntity<Program> addProgram(@RequestBody Program program) throws ProgramAlreadyExistsException {
		Program p = service.addProgram(program);
		logger.info("program added to database");
		return ResponseEntity.status(HttpStatus.SC_OK).body(p);
	}

	@DeleteMapping("/delete/id/{id}")
	@HystrixCommand(fallbackMethod = "ProgramNotFoundByIdErrorHandler")
	public ResponseEntity<Program> deleteProgram(@PathVariable int id) throws ProgramNotFoundException {
		Program p = service.deleteProgramById(id);
		logger.info("program deleted from database : "+p);
		return ResponseEntity.status(HttpStatus.SC_OK).body(p);
	}

	@PutMapping("/updateprogram")
	@HystrixCommand(fallbackMethod = "ProgramUpdateErrorHandler")
	public ResponseEntity<Program> updateProgram(@RequestBody Program program) throws ProgramNotFoundException {
		Program p = service.updateProgramById(program);
		logger.info("program updated in database : "+p);
		return ResponseEntity.status(HttpStatus.SC_OK).body(p);
	}

	//add program error handler
	public ResponseEntity<Program> ProgramAdditionErrorHandler(Program program) {
		logger.info("cannot add - program entered is invalid");
		return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(null);
	}

	//update program error handler
	public ResponseEntity<Program> ProgramUpdateErrorHandler(Program program) {
		logger.info("cannot update - program entered is invalid : " + program.getId());
		return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
	}

	//not found program error handler
	public ResponseEntity<Program> ProgramNotFoundByIdErrorHandler(int id) {
		logger.info("program with id not present in database : " + id);
		return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
	}
}
