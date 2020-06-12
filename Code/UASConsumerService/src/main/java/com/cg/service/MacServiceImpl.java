package com.cg.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cg.entity.Application;
import com.cg.entity.Participant;

@Service
public class MacServiceImpl implements MacService {
	
	Logger logger = LoggerFactory.getLogger(MacServiceImpl.class);

	@Autowired
	private RestTemplate template;

	@Override
	public ResponseEntity<List<Participant>> getAllParticipants() {
		
		List<Participant> participants = template.getForObject("http://participant-service/participant/getallparticipants", List.class);
		logger.info(participants.toString());
		if (participants.size() > 0) {
			return new ResponseEntity<>(participants, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(participants, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Participant> getParticipantById(int id) {
		
		String url = "http://participant-service/participant/getparticipantbyapplicationid/" + id;
		logger.info(template.getForEntity(url, Participant.class).toString());
		return template.getForEntity(url, Participant.class);
	}

	@Override
	public ResponseEntity<Participant> getParticipantByRollNo(String rollNo) {
		String url = "http://participant-service/participant/getparticipantbyrollno/" + rollNo;
		logger.info(template.getForEntity(url, Participant.class).toString());
		return template.getForEntity(url, Participant.class);
	}

	@Override
	public ResponseEntity<Participant> addParticipant(Participant participant) {
		String url = "http://participant-service/participant/addnewparticipant";
		ResponseEntity<Participant> response = template.postForEntity(url, participant, Participant.class);
		System.out.println(response);
		return response;
	}

	@Override
	public ResponseEntity<Participant> updateParticipant(Participant participant) {
		
		String url = "http://participant-service/updateparticipant/";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Participant> httpEntity = new HttpEntity<Participant>(participant, headers);
		ResponseEntity<Participant> responseEntity = template.exchange(url, HttpMethod.PUT, httpEntity, Participant.class);
		System.out.println(responseEntity);//
		return responseEntity;
	}

	@Override
	public ResponseEntity<Participant> deleteParticipantById(int id) {
		String url = "http://participant-service/participant/deleteparticipantbyid/"+id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", id);
		ResponseEntity<Participant> responseEntity = template.exchange(url, HttpMethod.DELETE, null, Participant.class);
		return responseEntity;
	}

	@Override
	public ResponseEntity<List<Application>> getAllApplicants() {
		
		String url = "http://application-service/application/getall";
		List<Application> applicants = template.getForObject(url, List.class);
		logger.info(applicants.toString());
		if (applicants.size() > 0) {
			return new ResponseEntity<>(applicants, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(applicants, HttpStatus.NOT_FOUND);
		}
	}


}
