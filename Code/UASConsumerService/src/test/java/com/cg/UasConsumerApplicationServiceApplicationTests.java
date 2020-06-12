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

import com.cg.entity.Application;
import com.cg.service.ApplicationService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UasConsumerApplicationServiceApplicationTests {

	@MockBean
	private RestTemplate template;

	@Autowired
	private ApplicationService service;

	Application a;
	List<Application> applicationsList;

	@Test
	public void contextLoads() {

	}

	@Test
	public void testApplicationForGetAllApplications_Success() {
		a = new Application(101, "Siddharth", LocalDate.of(1997, 03, 21), "B.Tech", 50, "To be a Developer",
				"siddharth1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		applicationsList = new ArrayList<Application>();
		applicationsList.add(a);
		Mockito.when(template.getForObject("http://application-service/application/getall", List.class))
				.thenReturn(applicationsList);
		assertEquals(HttpStatus.OK, service.getAllApplications().getStatusCode());
	}

	@Test
	public void testApplicationForGetAllApplicationsBadRequest_Failure() {
		applicationsList = new ArrayList<Application>();
		Mockito.when(template.getForObject("http://application-service/application/getall", List.class))
				.thenReturn(applicationsList);
		assertEquals(HttpStatus.NOT_FOUND, service.getAllApplications().getStatusCode());
	}

	@Test
	public void testAdminForGetApplicationById_Success() {
		int id = 101;
		a = new Application(101, "Siddharth", LocalDate.of(1997, 03, 21), "B.Tech", 50, "To be a Developer",
				"siddharth1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		Mockito.when(template.getForEntity("http://application-service/application/getbyid/" + id, Application.class))
				.thenReturn(new ResponseEntity<Application>(a, HttpStatus.OK));
		assertEquals(a, service.getApplicationById(id).getBody());
	}

	@Test
	public void testApplicationForGetApplicationByIdNotFound_Failure() {
		int id = 101;
		Mockito.when(template.getForEntity("http://application-service/application/getbyid/" + id, Application.class))
				.thenReturn(new ResponseEntity<Application>(HttpStatus.NOT_FOUND));
		assertEquals(HttpStatus.NOT_FOUND, service.getApplicationById(id).getStatusCode());
	}

	@Test
	public void testApplicationForAddApplication_Success() {
		a = new Application(102, "Shailesh", LocalDate.of(1997, 03, 21), "B.Tech", 50, "To be a Developer",
				"shaileshh1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		Mockito.when(
				template.postForEntity("http://application-service/application/addapplication", a, Application.class))
				.thenReturn(new ResponseEntity<Application>(a, HttpStatus.OK));
		assertEquals(a, service.addApplication(a).getBody());
	}

	@Test
	public void testApplicationForAddApplicationAlreadyExists_Failure() {
		a = new Application(102, "Shailesh", LocalDate.of(1997, 03, 21), "B.Tech", 50, "To be a Developer",
				"shaileshh1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		Mockito.when(
				template.postForEntity("http://application-service/application/addapplication", a, Application.class))
				.thenReturn(new ResponseEntity<Application>(HttpStatus.BAD_REQUEST));
		assertEquals(HttpStatus.BAD_REQUEST, service.addApplication(a).getStatusCode());
	}

	@Test
	public void testApplicationForAddApplicationInvalid_Failure() {
		a = new Application(101, "S", LocalDate.of(2010, 03, 21), "B.Tech", 110, "To be a Developer",
				"shaileshh1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		Mockito.when(
				template.postForEntity("http://application-service/application/addapplication", a, Application.class))
				.thenReturn(new ResponseEntity<Application>(HttpStatus.BAD_REQUEST));
		assertEquals(HttpStatus.BAD_REQUEST, service.addApplication(a).getStatusCode());
	}

	@Test
	public void testApplicationForUpdateApplication_Success() {
		a = new Application(101, "Sonu", LocalDate.of(1997, 03, 21), "B.Tech", 50, "To be a Developer",
				"sonu1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Application> httpEntity = new HttpEntity<Application>(a, headers);
		Mockito.when(template.exchange("http://application-service/application/updateapplication", HttpMethod.PUT,
				httpEntity, Application.class)).thenReturn(new ResponseEntity<Application>(a, HttpStatus.OK));
		assertEquals(a, service.updateApplication(a).getBody());
	}

	@Test
	public void testApplicationForUpdateApplicationNotFound_Failure() {
		a = new Application(101, "Siddharth", LocalDate.of(1997, 03, 21), "B.Tech", 50, "To be a Developer",
				"siddharth1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Application> httpEntity = new HttpEntity<Application>(a, headers);
		Mockito.when(template.exchange("http://application-service/application/updateapplication", HttpMethod.PUT,
				httpEntity, Application.class)).thenReturn(new ResponseEntity<Application>(HttpStatus.NOT_FOUND));
		assertEquals(HttpStatus.NOT_FOUND, service.updateApplication(a).getStatusCode());
	}

	@Test
	public void testApplicationForUpdateApplicationInvalid_Failure() {
		a = new Application(101, "S", LocalDate.of(2010, 03, 21), "B.Tech", 110, "To be a Developer",
				"shaileshh1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Application> httpEntity = new HttpEntity<Application>(a, headers);
		Mockito.when(template.exchange("http://application-service/application/updateapplication", HttpMethod.PUT,
				httpEntity, Application.class)).thenReturn(new ResponseEntity<Application>(HttpStatus.BAD_REQUEST));
		assertEquals(HttpStatus.BAD_REQUEST, service.updateApplication(a).getStatusCode());
	}

	@Test
	public void testApplicationForDeleteApplication_Success() {
		int id = 101;
		a = new Application(101, "Sonu", LocalDate.of(1997, 03, 21), "B.Tech", 50, "To be a Developer",
				"sonu1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		Mockito.when(template.exchange("http://application-service/application/delete/id/" + id, HttpMethod.DELETE,
				null, Application.class)).thenReturn(new ResponseEntity<Application>(a, HttpStatus.OK));
		assertEquals(a, service.deleteApplicationById(id).getBody());
	}

	@Test
	public void testApplicationForDeleteApplicationNotFound_Failure() {
		int id = 101;
		Mockito.when(template.exchange("http://application-service/application/delete/id/" + id, HttpMethod.DELETE,
				null, Application.class)).thenReturn(new ResponseEntity<Application>(HttpStatus.NOT_FOUND));
		assertEquals(HttpStatus.NOT_FOUND, service.deleteApplicationById(id).getStatusCode());
	}
}
