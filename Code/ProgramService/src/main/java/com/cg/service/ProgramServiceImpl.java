package com.cg.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.Program;
import com.cg.exception.ProgramAlreadyExistsException;
import com.cg.exception.ProgramNotFoundException;
import com.cg.repo.ProgramRepository;

@Service
public class ProgramServiceImpl implements ProgramService {

	Logger logger = LoggerFactory.getLogger(ProgramServiceImpl.class);

	@Autowired
	private ProgramRepository repo;

	@Override
	public List<Program> getAllPrograms() {
		return repo.findAll();
	}

	@Override
	public Program getProgramById(int id) throws ProgramNotFoundException {
		return repo.findById(id).orElseThrow(() -> new ProgramNotFoundException());
	}

	@Override
	public Program addProgram(Program program) throws ProgramAlreadyExistsException {
		if (repo.existsById(program.getId()))
			throw new ProgramAlreadyExistsException();
		else
			return repo.save(program);
	}

	@Override
	public Program updateProgramById(Program program) throws ProgramNotFoundException {
		if (!repo.existsById(program.getId()))
			throw new ProgramNotFoundException();
		else
			return repo.save(program);
	}

	@Override
	public Program deleteProgramById(int id) throws ProgramNotFoundException {
		if (repo.existsById(id)) {
			Program p = repo.findById(id).orElse(null);
			Program result = new Program(p.getId(), p.getName(), p.getDescription(), p.getEligibility(),
					p.getDuration(), p.getCertificate());
			repo.deleteById(id);
			return result;
		} else {
			throw new ProgramNotFoundException();
		}
	}

}
