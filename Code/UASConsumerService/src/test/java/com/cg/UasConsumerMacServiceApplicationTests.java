package com.cg;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.cg.entity.Participant;
import com.cg.service.MacService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UasConsumerMacServiceApplicationTests {
	
	@MockBean
	RestTemplate template;
	
	@Autowired
	MacService macService;
	
	Participant participant;
	List<Participant> participantList;
	
	@Test
	public void contextLoads() {

	}
	
	@Test
	public void testMacForGetAllParticipantsSuccess()
	{
		participant = new Participant("C106","rohit@mail.com",106,"A106");
		participantList = new ArrayList<Participant>();
		participantList.add(participant);
		Mockito.when(template.getForObject("http://participant-service/participant/getallparticipants", List.class))
		.thenReturn(participantList);
		assertEquals(HttpStatus.OK, macService.getAllParticipants().getStatusCode());
	}
	
	@Test
	public void testMacForGetAllParticipantsBadRequestFailure()
	{
		participantList = new ArrayList<Participant>();
		Mockito.when(template.getForObject("http://participant-service/participant/getallparticipants", List.class))
		.thenReturn(participantList);
		assertEquals(HttpStatus.NOT_FOUND, macService.getAllParticipants().getStatusCode());
	}
	
	@Test
	public void testMacForGetParticipantByApplicationIdForSuccess()
	{
		int pid = 106;
		participant = new Participant("C106","rohit@mail.com",106,"A106");
		Mockito.when(template.getForEntity("http://participant-service/participant/getparticipantbyapplicationid/"+ pid, Participant.class))
		.thenReturn(new ResponseEntity<Participant>(participant,HttpStatus.OK));
		assertEquals(participant, macService.getParticipantById(pid).getBody());
	}
	@Test
	public void testMacForGetParticipantByApplicationIdForFailure()
	{
		int pid = 106;
		Mockito.when(template.getForEntity("http://participant-service/participant/getparticipantbyapplicationid/"+ pid, Participant.class))
		.thenReturn(new ResponseEntity<Participant>(HttpStatus.NOT_FOUND));
		assertEquals(HttpStatus.NOT_FOUND, macService.getParticipantById(pid).getStatusCode());
	}
	@Test
	public void testMacForGetParticipantByRollNoForSuccess()
	{
		String rollNo = "C106";
		participant = new Participant("C106","rohit@mail.com",106,"A106");
		Mockito.when(template.getForEntity("http://participant-service/participant/getparticipantbyrollno/"+rollNo, Participant.class))
		.thenReturn(new ResponseEntity<Participant>(participant,HttpStatus.OK));
		assertEquals(participant, macService.getParticipantByRollNo(rollNo).getBody());
	}
	@Test
	public void testMacForgetParticipantByRollNoForFailure()
	{
		String rollNo="C106";
		Mockito.when(template.getForEntity("http://participant-service/participant/getparticipantbyrollno/"+rollNo, Participant.class))
		.thenReturn(new ResponseEntity<Participant>(HttpStatus.NOT_FOUND));
		assertEquals(HttpStatus.NOT_FOUND, macService.getParticipantByRollNo(rollNo).getStatusCode());
	}
	
	@Test
	public void testMacForAddParticipantForSuccess()
	{
		participant = new Participant("C106","rohit@mail.com",106,"A106");
		Mockito.when(template.postForEntity("http://participant-service/participant/addnewparticipant", participant, Participant.class))
		.thenReturn(new ResponseEntity<Participant>(participant, HttpStatus.OK));
		assertEquals(participant, macService.addParticipant(participant).getBody());
	}
	
	@Test
	public void testMacForAddParticipantAlreadyExistsFailure()
	{
		participant = new Participant("C106","rohit@mail.com",106,"A106");
		Mockito.when(template.postForEntity("http://participant-service/participant/addnewparticipant", participant, Participant.class))
		.thenReturn(new ResponseEntity<Participant>(HttpStatus.BAD_REQUEST));
		assertEquals(HttpStatus.BAD_REQUEST, macService.addParticipant(participant).getStatusCode());
	}
	
	@Test
	public void testMacForAddParticipantFailure()
	{
		participant = new Participant("C","rohit@mail.com",106,"A1");
		Mockito.when(template.postForEntity("http://participant-service/participant/addnewparticipant", participant, Participant.class))
		.thenReturn(new ResponseEntity<Participant>(HttpStatus.BAD_REQUEST));
		assertEquals(HttpStatus.BAD_REQUEST, macService.addParticipant(participant).getStatusCode());
	}
	
	@Test
	public void testMacForUpdateParticipantSuccess()
	{
		participant = new Participant("C106","rohit@mail.com",106,"A106");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Participant> httpEntity = new HttpEntity<Participant>(participant, headers);
		Mockito.when(template.exchange("http://participant-service/updateparticipant/",HttpMethod.PUT, httpEntity, Participant.class))
		.thenReturn(new ResponseEntity<Participant>(participant,HttpStatus.OK));
		assertEquals(participant,macService.updateParticipant(participant).getBody());
	}
	
	@Test
	public void testMacForUpdateParticipantFailure()
	{
		participant = new Participant("C","rohit@mail.com",106,"A1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Participant> httpEntity = new HttpEntity<Participant>(participant, headers);
		Mockito.when(template.exchange("http://participant-service/updateparticipant/",HttpMethod.PUT, httpEntity, Participant.class))
		.thenReturn(new ResponseEntity<Participant>(HttpStatus.NOT_FOUND));
		assertEquals(HttpStatus.NOT_FOUND,macService.updateParticipant(participant).getStatusCode());
	}
	
	@Test
	public void testMacForDeleteParticipantByApplicationIdSuccess()
	{
		int pid = 106;
		participant = new Participant("C106","rohit@mail.com",106,"A106");
		Mockito.when(template.exchange("http://participant-service/participant/deleteparticipantbyid/"+pid,
				HttpMethod.DELETE, null, Participant.class))
		.thenReturn(new ResponseEntity<Participant>(participant,HttpStatus.OK));
		assertEquals(participant, macService.deleteParticipantById(pid).getBody());
	}
	
	@Test
	public void testMacForDeleteParticipantByApplicationIdFailure()
	{
		int pid =106;
		Mockito.when(template.exchange("http://participant-service/participant/deleteparticipantbyid/" + pid,
		HttpMethod.DELETE,null, Participant.class)).thenReturn(new ResponseEntity<Participant>(HttpStatus.NOT_FOUND));
		assertEquals(HttpStatus.NOT_FOUND, macService.deleteParticipantById(pid).getStatusCode());
		
	}

}
