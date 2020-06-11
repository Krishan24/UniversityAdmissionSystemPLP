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

import com.cg.entity.Schedule;
import com.cg.exception.ScheduleAlreadyExistsException;
import com.cg.exception.ScheduleNotFoundException;
import com.cg.repo.ScheduledProgramRepository;
import com.cg.service.ScheduledProgramService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ScheduledProgramServiceApplicationTests {

	@MockBean
	private ScheduledProgramRepository repo;

	@Autowired
	private ScheduledProgramService service;

	Schedule s;
	List<Schedule> list;

	@Before
	public void setUp() throws Exception {
		s = new Schedule(1, "B.E.", "Mumbai", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		list = new ArrayList<Schedule>();
		list.add(s);
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllSchedules_Success() {
		Mockito.when(repo.findAll()).thenReturn(list);
		assertEquals(list, service.getAllSchedules());
	}

	@Test
	public void testGetAllSchedulesForWithZeroValuesPresent_Failure() {
		Mockito.when(repo.findAll()).thenReturn(new ArrayList<>());
		assertNotEquals(list, service.getAllSchedules());
	}

	@Test
	public void testGetScheduleByIdFound_Success() throws ScheduleNotFoundException {
		int id = 1;
		Optional<Schedule> opt = Optional.of(s);
		Mockito.when(repo.findById(id)).thenReturn(opt);
		assertEquals(s, service.getScheduleById(id));
	}

	@Test(expected = ScheduleNotFoundException.class)
	public void testGetScheduleByIdNotFound_Failure() throws ScheduleNotFoundException {
		int id = 1;
		service.getScheduleById(id);
	}

	@Test(expected = ScheduleNotFoundException.class)
	public void testUpdateScheduleByIdNotFound_Failure() throws ScheduleNotFoundException {
		s = new Schedule(1, "M.E.", "Mumbai", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		Mockito.when(repo.existsById(s.getId())).thenReturn(false);
		service.updateScheduleById(s);
	}

	@Test
	public void testUpdateScheduleById_Success() throws ScheduleNotFoundException {
		s = new Schedule(1, "M.E.", "Mumbai", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		Mockito.when(repo.existsById(s.getId())).thenReturn(true);
		Mockito.when(repo.save(s)).thenReturn(s);
		assertEquals(s, service.updateScheduleById(s));
	}

	@Test(expected = ScheduleNotFoundException.class)
	public void testDeleteScheduleForNotFound_Failure() throws ScheduleNotFoundException {
		int id = 1;
		Mockito.when(repo.existsById(id)).thenReturn(false);
		service.deleteScheduleById(id);
	}

	@Test
	public void testAddSchedule_Success() throws ScheduleAlreadyExistsException {
		s = new Schedule(1, "M.E.", "Mumbai", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		Mockito.when(repo.existsById(s.getId())).thenReturn(false);
		Mockito.when(repo.save(s)).thenReturn(s);
		assertEquals(s, service.addSchedule(s));
	}

	@Test(expected = ScheduleAlreadyExistsException.class)
	public void testAddScheduleForAlreadyExists_Failure() throws ScheduleAlreadyExistsException {
		s = new Schedule(1, "M.E.", "Mumbai", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		Mockito.when(repo.existsById(s.getId())).thenReturn(true);
		service.addSchedule(s);
	}

	@Test
	public void testAddScheduleForValidation_Failure() throws ScheduleAlreadyExistsException {
		s = new Schedule(1, "M", "Mumbai", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		Mockito.when(repo.existsById(s.getId())).thenReturn(false);
		assertEquals(null, service.addSchedule(s));
	}

	@Test
	public void testDeleteSchedule_Success() throws ScheduleNotFoundException {
		int id = 1;
		s = new Schedule(1, "M.E.", "Mumbai", LocalDate.now(), LocalDate.of(2024, 02, 21), 5);
		Optional<Schedule> opt = Optional.of(s);
		Mockito.when(repo.findById(id)).thenReturn(opt);
		Mockito.when(repo.existsById(id)).thenReturn(true);
		assertEquals(s.getId(), service.deleteScheduleById(id).getId());
	}

}
