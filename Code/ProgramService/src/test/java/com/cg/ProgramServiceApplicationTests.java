package com.cg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

import com.cg.entity.Program;
import com.cg.exception.ProgramAlreadyExistsException;
import com.cg.exception.ProgramNotFoundException;
import com.cg.repo.ProgramRepository;
import com.cg.service.ProgramService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProgramServiceApplicationTests {

	@MockBean
	private ProgramRepository repo;

	@Autowired
	private ProgramService service;

	Program p;
	List<Program> list;

	@Test
	public void contextLoads() {
	}

	@Before
	public void setUp() throws Exception {
		p = new Program(1, "name", "description", "eligibility", 1, "certificate");
		list = new ArrayList<Program>();
		list.add(p);
	}

	@Test
	public void testGetAllPrograms_Success() {
		Mockito.when(repo.findAll()).thenReturn(list);
		assertEquals(list, service.getAllPrograms());
	}

	@Test
	public void testGetAllProgramsForWithZeroValuesPresent_Failure() {
		Mockito.when(repo.findAll()).thenReturn(new ArrayList<>());
		assertNotEquals(list, service.getAllPrograms());
	}

	@Test
	public void testGetProgramByIdFound_Success() throws ProgramNotFoundException {
		int id = 1;
		Optional<Program> opt = Optional.of(p);
		Mockito.when(repo.findById(id)).thenReturn(opt);
		assertEquals(p, service.getProgramById(id));
	}

	@Test(expected = ProgramNotFoundException.class)
	public void testGetProgramByIdNotFound_Failure() throws ProgramNotFoundException {
		int id = 1;
		service.getProgramById(id);
	}

	@Test(expected = ProgramNotFoundException.class)
	public void testUpdateProgramByIdNotFound_Failure() throws ProgramNotFoundException {
		p = new Program(1, "name", "description", "eligibility", 1, "certificate");
		Mockito.when(repo.existsById(p.getId())).thenReturn(false);
		service.updateProgramById(p);
	}

	@Test
	public void testUpdateProgramById_Success() throws ProgramNotFoundException {
		p = new Program(1, "new name", "description", "eligibility", 1, "certificate");
		Mockito.when(repo.existsById(p.getId())).thenReturn(true);
		Mockito.when(repo.save(p)).thenReturn(p);
		assertEquals(p, service.updateProgramById(p));
	}

	@Test
	public void testAddProgram_Success() throws ProgramAlreadyExistsException {
		p = new Program(1, "new program name", "description", "eligibility", 1, "certificate");
		Mockito.when(repo.existsById(p.getId())).thenReturn(false);
		Mockito.when(repo.save(p)).thenReturn(p);
		assertEquals(p, service.addProgram(p));
	}

	@Test(expected = ProgramAlreadyExistsException.class)
	public void testAddProgramForAlreadyExists_Failure() throws ProgramAlreadyExistsException {
		p = new Program(1, "new program name", "description", "eligibility", 1, "certificate");
		Mockito.when(repo.existsById(p.getId())).thenReturn(true);
		service.addProgram(p);
	}

	@Test
	public void testAddProgramForValidation_Failure() throws ProgramAlreadyExistsException {
		p = new Program(1, "w", "r", "o", 0, "ng data");
		Mockito.when(repo.existsById(p.getId())).thenReturn(false);
		assertEquals(null, service.addProgram(p));
	}
	
	@Test(expected = ProgramNotFoundException.class)
	public void testDeleteProgramForNotFound_Failure() throws ProgramNotFoundException {
		int id =1;
		//p = new Program(1, " name", "description", "eligibility", 1, "certificate");
		Mockito.when(repo.existsById(id)).thenReturn(false);
		service.deleteProgramById(id);
	}
	
	@Test
	public void testDeleteProgram_Success() throws ProgramNotFoundException {
		int id =1;
		p = new Program(1, " name", "description", "eligibility", 1, "certificate");
		Optional<Program> opt = Optional.of(p);
		Mockito.when(repo.findById(id)).thenReturn(opt);
		Mockito.when(repo.existsById(id)).thenReturn(true);
		assertEquals(p.getId(), service.deleteProgramById(id).getId());
	}

}
