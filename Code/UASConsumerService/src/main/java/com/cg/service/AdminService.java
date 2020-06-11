package com.cg.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cg.entity.Application;
import com.cg.entity.Program;
import com.cg.entity.Schedule;

public interface AdminService {

	public ResponseEntity<List<Program>> getAllProgramsOffered();

	public ResponseEntity<Program> getProgramById(int id);

	public ResponseEntity<Program> addProgram(Program program);

	public ResponseEntity<Program> updateProgram(Program program);

	public ResponseEntity<Program> deleteProgramById(int id);

	public ResponseEntity<List<Schedule>> getAllScheduledPrograms();

	public ResponseEntity<Schedule> getScheduledProgramById(int id);

	public ResponseEntity<Schedule> addScheduledProgram(Schedule schedule);

	public ResponseEntity<Schedule> updateScheduledProgram(Schedule schedule);

	public ResponseEntity<Schedule> deleteScheduledProgramsById(int id);

	public ResponseEntity<List<Application>> getAllApplicationsReportBySchedId(int schedId);

}
