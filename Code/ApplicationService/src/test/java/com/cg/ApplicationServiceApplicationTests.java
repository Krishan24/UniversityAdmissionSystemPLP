package com.cg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cg.entity.Application;
import com.cg.exception.ApplicationAlreadyExistsException;
import com.cg.exception.ApplicationNotFoundException;
import com.cg.repository.ApplicationRepository;
import com.cg.service.ApplicationService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationServiceApplicationTests {

	@MockBean
	private ApplicationRepository repo;

	@Autowired
	private ApplicationService service;

	Application a;
	List<Application> list;

	@Test
	public void contextLoads() {
	}

	@Before
	public void setUp() throws Exception {
		a = new Application(101, "Siddharth", LocalDate.of(1997, 03, 21), "B.Tech", 50, "To be a Developer",
				"siddharth1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		list = new ArrayList<Application>();
		list.add(a);
	}

	@Test
	public void testGetAllApplications_Success() {
		Mockito.when(repo.findAll()).thenReturn(list);
		assertEquals(list, service.getAllApplications());
	}

	@Test
	public void testGetAllApplicationsForWithZeroValuesPresent_Failure() {
		Mockito.when(repo.findAll()).thenReturn(new ArrayList<>());
		assertNotEquals(list, service.getAllApplications());
	}

	@Test
	public void testGetApplicationByIdFound_Success() throws ApplicationNotFoundException {
		int id = 101;
		Optional<Application> opt = Optional.of(a);
		Mockito.when(repo.findById(id)).thenReturn(opt);
		assertEquals(a, service.getApplicationById(id));
	}

	@Test(expected = ApplicationNotFoundException.class)
	public void testGetApplicationByIdNotFound_Failure() throws ApplicationNotFoundException {
		int id = 101;
		service.getApplicationById(id);
	}

	@Test(expected = ApplicationNotFoundException.class)
	public void testUpdateApplicationByIdNotFound_Failure() throws ApplicationNotFoundException {
		a = new Application(101, "Siddharth", LocalDate.of(1997, 03, 21), "B.Tech", 50, "To be a Developer",
				"siddharth1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		Mockito.when(repo.existsById(a.getApplicationId())).thenReturn(false);
		service.updateApplicationById(a);
	}

	@Test
	public void testUpdateApplicationById_Success() throws ApplicationNotFoundException {
		a = new Application(101, "Sonu", LocalDate.of(1997, 03, 21), "B.Tech", 50, "To be a Developer",
				"sonu1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		Mockito.when(repo.existsById(a.getApplicationId())).thenReturn(true);
		Mockito.when(repo.save(a)).thenReturn(a);
		assertEquals(a, service.updateApplicationById(a));
	}

	@Test
	public void testAddApplication_Success() throws ApplicationAlreadyExistsException {
		a = new Application(102, "Shailesh", LocalDate.of(1997, 03, 21), "B.Tech", 50, "To be a Developer",
				"shaileshh1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		Mockito.when(repo.existsById(a.getApplicationId())).thenReturn(false);
		Mockito.when(repo.save(a)).thenReturn(a);
		assertEquals(a, service.addApplication(a));
	}

	@Test(expected = ApplicationAlreadyExistsException.class)
	public void testAddApplicationForAlreadyExists_Failure() throws ApplicationAlreadyExistsException {
		a = new Application(102, "Shailesh", LocalDate.of(1997, 03, 21), "B.Tech", 50, "To be a Developer",
				"shaileshh1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		Mockito.when(repo.existsById(a.getApplicationId())).thenReturn(true);
		service.addApplication(a);
	}

	@Test
	public void testAddApplicationForValidation_Failure() throws ApplicationAlreadyExistsException {
		a = new Application(101, "S", LocalDate.of(2010, 03, 21), "B.Tech", 110, "To be a Developer",
				"shaileshh1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		Mockito.when(repo.existsById(a.getApplicationId())).thenReturn(false);
		assertEquals(null, service.addApplication(a));
	}

	@Test(expected = ApplicationNotFoundException.class)
	public void testDeleteApplicationForNotFound_Failure() throws ApplicationNotFoundException {
		int id = 101;
		/*
		 * a = new Application(101, "Shailesh", LocalDate.of(1997, 03, 21), "B.Tech",
		 * 50, "To be a Developer", "shaileshh1234@gmail.com", "A101", "Pending",
		 * LocalDate.of(2020, 06, 16));
		 */
		Mockito.when(repo.existsById(id)).thenReturn(false);
		service.deleteApplicationById(id);
	}

	@Test
	public void testDeleteApplication_Success() throws ApplicationNotFoundException {
		int id = 101;
		a = new Application(101, "Siddharth", LocalDate.of(1997, 03, 21), "B.Tech", 50, "To be a Developer",
				"siddharth1234@gmail.com", "A101", "Pending", LocalDate.of(2020, 06, 16));
		Optional<Application> opt = Optional.of(a);
		Mockito.when(repo.findById(id)).thenReturn(opt);
		Mockito.when(repo.existsById(id)).thenReturn(true);
		assertEquals(a.getApplicationId(), service.deleteApplicationById(id).getApplicationId());
	}

}
