package com.cg.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cg.entity.Application;
import com.cg.entity.Participant;

public interface MacService {
	
	public ResponseEntity<List<Participant>> getAllParticipants();
	
	public ResponseEntity<Participant> getParticipantById(int id);
	
	public ResponseEntity<Participant> getParticipantByRollNo(String rollNo);
	
	public ResponseEntity<Participant> addParticipant(Participant participant);
	
	public ResponseEntity<Participant> updateParticipant(Participant participant);
	
	public ResponseEntity<Participant> deleteParticipantById(int id);
	
	public ResponseEntity<List<Application>> getAllApplicants();
	
	//public ResponseEntity<Application> updateStatus(String status);

}
