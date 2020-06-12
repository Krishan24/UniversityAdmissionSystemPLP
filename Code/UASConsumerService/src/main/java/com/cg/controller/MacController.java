package com.cg.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.cg.entity.Participant;
import com.cg.service.MacServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/mac")
public class MacController {
	
	Logger logger = LoggerFactory.getLogger(MacController.class);
	
	@Autowired
	private MacServiceImpl macService;
	
	//http:localhost:8086/mac/getallparticipants
	@GetMapping("/getallparticipants")
	public ResponseEntity<List<Participant>> getAllParticipants()
	{
		return macService.getAllParticipants();
	}
	
	@GetMapping("/getparticipantbyid/{id}")
	@HystrixCommand(fallbackMethod = "participantNotFoundErrorHandler")
	public ResponseEntity<Participant> getParticipantByApplicationId(@PathVariable("id") int id)
	{
		return macService.getParticipantById(id);
	}
	@GetMapping("/getparticipantbyrollno/{rollno}")
	@HystrixCommand(fallbackMethod = "participantNotFoundByRollNoErrorHandler")
	public ResponseEntity<Participant> getParticipantByRollNo(@PathVariable("rollno") String rollNo)
	{
		return macService.getParticipantByRollNo(rollNo);
	}
	
	@PostMapping("/addparticipant")
	@HystrixCommand(fallbackMethod = "participantAdditionErrorHandler")
	public ResponseEntity<Participant> addNewParticipant(@RequestBody Participant participant)
	{
		return macService.addParticipant(participant);
	}
	@PutMapping("/updateparticipant")
	@HystrixCommand(fallbackMethod = "participantUpdateErrorHandler")
	public ResponseEntity<Participant> updateParticipantById(@PathVariable("id") int id, @RequestBody Participant participant)
	{
		return macService.updateParticipant(participant);
	}

	@DeleteMapping("/deleteparticipantById/{id}")
	@HystrixCommand(fallbackMethod = "participantNotFoundDeletionErrorHandler")
	public ResponseEntity<Participant> deleteParticipantById(@PathVariable("id") int id)
	{
		return macService.deleteParticipantById(id);
	}
	@GetMapping("/getallapplicants")
	public ResponseEntity<List<Application>> getAllApplicants()
	{
		return macService.getAllApplicants();
	}
	
	// participant not found error handler
	public Participant participantNotFoundErrorHandler(int pid)
	{
		logger.info("Participant with this id is not present in database :"+pid);
		return new Participant("0",null,0,"00");
	}
	
	//participant deletion error handler
	public ResponseEntity<Participant> participantNotFoundDeletionErrorHandler(int pid)
	{
		logger.info("Can't delete. Participant with this id is not present in database :"+pid);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	//participant not found error handler
	public Participant participantNotFoundByRollNoErrorHandler(String rollNo)
	{
		logger.info("Participant with this roll no. is not present : "+rollNo);
		return new Participant("0",null,0,"00");
	}
	
	//addition error handler
	public ResponseEntity<Participant> participantAdditionErrorHandler(Participant participant)
	{
		logger.info("cannot add participant. Particpant may already exist.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	//update error handler
	public ResponseEntity<Participant> participantUpdateErrorHandler(Participant participant)
	{
		logger.info("Participant with application id "+participant.getApplicationId()+" not found to update");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}
