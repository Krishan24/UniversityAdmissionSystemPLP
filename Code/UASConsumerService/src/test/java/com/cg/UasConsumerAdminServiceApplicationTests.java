package com.cg;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
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

import com.cg.entity.Program;
import com.cg.entity.Schedule;
import com.cg.service.AdminService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UasConsumerAdminServiceApplicationTests {

	@MockBean
	private RestTemplate template;

	@Autowired
	private AdminService service;

	Schedule s;
	Program p;
	List<Schedule> scheduledProgramsList;
	List<Program> offeredProgramsList;

	@Test
	public void contextLoads() {

	}

	@Test
	public void testAdminForGetAllPrograms_Success() {
		p = new Program(1, "name", "description", "eligibility", 1, "certificate");
		offeredProgramsList = new ArrayList<Program>();
		offeredProgramsList.add(p);
		Mockito.when(template.getForObject("http://program-service/program/getall", List.class))
				.thenReturn(offeredProgramsList);
		assertEquals(HttpStatus.OK, service.getAllProgramsOffered().getStatusCode());
	}

	@Test
	public void testAdminForGetAllProgramsBadRequest_Failure() {
		offeredProgramsList = new ArrayList<Program>();
		Mockito.when(template.getForObject("http://program-service/program/getall", List.class))
				.thenReturn(offeredProgramsList);
		assertEquals(HttpStatus.NOT_FOUND, service.getAllProgramsOffered().getStatusCode());
	}

	@Test
	public void testAdminForGetProgramById_Success() {
		int id = 1;
		p = new Program(1, "name", "description", "eligibility", 1, "certificate");
		Mockito.when(template.getForEntity("http://program-service/program/getbyid/" + id, Program.class))
				.thenReturn(new ResponseEntity<Program>(p, HttpStatus.OK));
		assertEquals(p, service.getProgramById(id).getBody());
	}

	@Test
	public void testAdminForGetProgramByIdNotFound_Failure() {
		int id = 1;
		Mockito.when(template.getForEntity("http://program-service/program/getbyid/" + id, Program.class))
				.thenReturn(new ResponseEntity<Program>(HttpStatus.NOT_FOUND));
		assertEquals(HttpStatus.NOT_FOUND, service.getProgramById(id).getStatusCode());
	}

	@Test
	public void testAdminForAddProgram_Success() {
		p = new Program(1, "name", "description", "eligibility", 1, "certificate");
		Mockito.when(template.postForEntity("http://program-service/program/addprogram", p, Program.class))
				.thenReturn(new ResponseEntity<Program>(p, HttpStatus.OK));
		assertEquals(p, service.addProgram(p).getBody());
	}

	@Test
	public void testAdminForAddProgramAlreadyExists_Failure() {
		p = new Program(1, "name", "description", "eligibility", 1, "certificate");
		Mockito.when(template.postForEntity("http://program-service/program/addprogram", p, Program.class))
				.thenReturn(new ResponseEntity<Program>(HttpStatus.BAD_REQUEST));
		assertEquals(HttpStatus.BAD_REQUEST, service.addProgram(p).getStatusCode());
	}

	@Test
	public void testAdminForAddProgramInvalid_Failure() {
		p = new Program(1, "w", "r", "o", 1, "ng");
		Mockito.when(template.postForEntity("http://program-service/program/addprogram", p, Program.class))
				.thenReturn(new ResponseEntity<Program>(HttpStatus.BAD_REQUEST));
		assertEquals(HttpStatus.BAD_REQUEST, service.addProgram(p).getStatusCode());
	}

	@Test
	public void testAdminForUpdateProgram_Success() {
		p = new Program(1, "name", "description", "eligibility", 1, "certificate");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Program> httpEntity = new HttpEntity<Program>(p, headers);
		Mockito.when(template.exchange("http://program-service/program/updateprogram", HttpMethod.PUT, httpEntity,
				Program.class)).thenReturn(new ResponseEntity<Program>(p, HttpStatus.OK));
		assertEquals(p, service.updateProgram(p).getBody());
	}

	@Test
	public void testAdminForUpdateProgramNotFound_Failure() {
		p = new Program(1, "name", "description", "eligibility", 1, "certificate");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Program> httpEntity = new HttpEntity<Program>(p, headers);
		Mockito.when(template.exchange("http://program-service/program/updateprogram", HttpMethod.PUT, httpEntity,
				Program.class)).thenReturn(new ResponseEntity<Program>(HttpStatus.NOT_FOUND));
		assertEquals(HttpStatus.NOT_FOUND, service.updateProgram(p).getStatusCode());
	}

	@Test
	public void testAdminForUpdateProgramInvalid_Failure() {
		p = new Program(1, "w", "r", "o", 1, "ng");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Program> httpEntity = new HttpEntity<Program>(p, headers);
		Mockito.when(template.exchange("http://program-service/program/updateprogram", HttpMethod.PUT, httpEntity,
				Program.class)).thenReturn(new ResponseEntity<Program>(HttpStatus.BAD_REQUEST));
		assertEquals(HttpStatus.BAD_REQUEST, service.updateProgram(p).getStatusCode());
	}

	@Test
	public void testAdminForDeleteProgram_Success() {
		int id = 1;
		p = new Program(1, "name", "description", "eligibility", 1, "certificate");
		Mockito.when(template.exchange("http://program-service/program/delete/id/" + id, HttpMethod.DELETE, null,
				Program.class)).thenReturn(new ResponseEntity<Program>(p, HttpStatus.OK));
		assertEquals(p, service.deleteProgramById(id).getBody());
	}

	@Test
	public void testAdminForDeleteProgramNotFound_Failure() {
		int id = 1;
		Mockito.when(template.exchange("http://program-service/program/delete/id/" + id, HttpMethod.DELETE, null,
				Program.class)).thenReturn(new ResponseEntity<Program>(HttpStatus.NOT_FOUND));
		assertEquals(HttpStatus.NOT_FOUND, service.deleteProgramById(id).getStatusCode());
	}

	@Test
	public void testAdminForGetAllSchedules_Success() {
		s = new Schedule(1, "B.E.", "Mumbai", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		scheduledProgramsList = new ArrayList<Schedule>();
		scheduledProgramsList.add(s);
		Mockito.when(template.getForObject("http://scheduled-program-service/schedule/getall", List.class))
				.thenReturn(scheduledProgramsList);
		assertEquals(HttpStatus.OK, service.getAllScheduledPrograms().getStatusCode());
	}

	@Test
	public void testAdminForGetAllSchedulesBadRequest_Failure() {
		scheduledProgramsList = new ArrayList<Schedule>();
		Mockito.when(template.getForObject("http://scheduled-program-service/schedule/getall", List.class))
				.thenReturn(scheduledProgramsList);
		assertEquals(HttpStatus.NOT_FOUND, service.getAllScheduledPrograms().getStatusCode());
	}

	@Test
	public void testAdminForGetScheduleById_Success() {
		int id = 1;
		s = new Schedule(1, "B.E.", "Mumbai", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		Mockito.when(template.getForEntity("http://scheduled-program-service/schedule/getbyid/" + id, Schedule.class))
				.thenReturn(new ResponseEntity<Schedule>(s, HttpStatus.OK));
		assertEquals(s, service.getScheduledProgramById(id).getBody());
	}

	@Test
	public void testAdminForGetScheduleByIdNotFound_Failure() {
		int id = 1;
		Mockito.when(template.getForEntity("http://scheduled-program-service/schedule/getbyid/" + id, Schedule.class))
				.thenReturn(new ResponseEntity<Schedule>(HttpStatus.NOT_FOUND));
		assertEquals(HttpStatus.NOT_FOUND, service.getScheduledProgramById(id).getStatusCode());
	}

	@Test
	public void testAdminForAddSchedule_Success() {
		s = new Schedule(1, "B.E.", "Mumbai", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		Mockito.when(template.postForEntity("http://scheduled-program-service/schedule/addschedule", s, Schedule.class))
				.thenReturn(new ResponseEntity<Schedule>(s, HttpStatus.OK));
		assertEquals(s, service.addScheduledProgram(s).getBody());
	}

	@Test
	public void testAdminForAddScheduleAlreadyExists_Failure() {
		s = new Schedule(1, "B.E.", "Mumbai", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		Mockito.when(template.postForEntity("http://scheduled-program-service/schedule/addschedule", s, Schedule.class))
				.thenReturn(new ResponseEntity<Schedule>(HttpStatus.BAD_REQUEST));
		assertEquals(HttpStatus.BAD_REQUEST, service.addScheduledProgram(s).getStatusCode());
	}

	@Test
	public void testAdminForAddScheduleInvalid_Failure() {
		s = new Schedule(1, "B", "M", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		Mockito.when(template.postForEntity("http://scheduled-program-service/schedule/addschedule", s, Schedule.class))
				.thenReturn(new ResponseEntity<Schedule>(HttpStatus.BAD_REQUEST));
		assertEquals(HttpStatus.BAD_REQUEST, service.addScheduledProgram(s).getStatusCode());
	}

	@Test
	public void testAdminForUpdateSchedule_Success() {
		s = new Schedule(1, "B.E.", "Mumbai", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Schedule> httpEntity = new HttpEntity<Schedule>(s, headers);
		Mockito.when(template.exchange("http://scheduled-program-service/schedule/updateschedule", HttpMethod.PUT,
				httpEntity, Schedule.class)).thenReturn(new ResponseEntity<Schedule>(s, HttpStatus.OK));
		assertEquals(s, service.updateScheduledProgram(s).getBody());
	}

	@Test
	public void testAdminForUpdateScheduleNotFound_Failure() {
		s = new Schedule(1, "B.E.", "Mumbai", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Schedule> httpEntity = new HttpEntity<Schedule>(s, headers);
		Mockito.when(template.exchange("http://scheduled-program-service/schedule/updateschedule", HttpMethod.PUT,
				httpEntity, Schedule.class)).thenReturn(new ResponseEntity<Schedule>(HttpStatus.NOT_FOUND));
		assertEquals(HttpStatus.NOT_FOUND, service.updateScheduledProgram(s).getStatusCode());
	}

	@Test
	public void testAdminForUpdateScheduleInvalid_Failure() {
		s = new Schedule(1, "B", "M", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Schedule> httpEntity = new HttpEntity<Schedule>(s, headers);
		Mockito.when(template.exchange("http://scheduled-program-service/schedule/updateschedule", HttpMethod.PUT,
				httpEntity, Schedule.class)).thenReturn(new ResponseEntity<Schedule>(HttpStatus.BAD_REQUEST));
		assertEquals(HttpStatus.BAD_REQUEST, service.updateScheduledProgram(s).getStatusCode());
	}

	@Test
	public void testAdminForDeleteSchedule_Success() {
		int id = 1;
		s = new Schedule(1, "B.E.", "Mumbai", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		Mockito.when(template.exchange("http://scheduled-program-service/schedule/delete/id/" + id, HttpMethod.DELETE,
				null, Schedule.class)).thenReturn(new ResponseEntity<Schedule>(s, HttpStatus.OK));
		assertEquals(s, service.deleteScheduledProgramsById(id).getBody());
	}

	@Test
	public void testAdminForDeleteScheduleNotFound_Failure() {
		int id = 1;
		Mockito.when(template.exchange("http://scheduled-program-service/schedule/delete/id/" + id, HttpMethod.DELETE,
				null, Schedule.class)).thenReturn(new ResponseEntity<Schedule>(HttpStatus.NOT_FOUND));
		assertEquals(HttpStatus.NOT_FOUND, service.deleteScheduledProgramsById(id).getStatusCode());
	}

}
