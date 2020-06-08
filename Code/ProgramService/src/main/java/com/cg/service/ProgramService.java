package com.cg.service;

import java.util.List;

import com.cg.entity.Program;
import com.cg.exception.ProgramAlreadyExistsException;
import com.cg.exception.ProgramNotFoundException;

public interface ProgramService {

	public List<Program> getAllPrograms();

	public Program getProgramById(int id) throws ProgramNotFoundException;

	public Program addProgram(Program program) throws ProgramAlreadyExistsException;

	public Program updateProgramById(Program program) throws ProgramNotFoundException;

	public Program deleteProgramById(int id) throws ProgramNotFoundException;

}
