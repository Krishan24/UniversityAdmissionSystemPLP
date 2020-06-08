package com.cg.service;

import java.util.List;

import com.cg.entity.Schedule;
import com.cg.exception.ScheduleAlreadyExistsException;
import com.cg.exception.ScheduleNotFoundException;

public interface ScheduledProgramService {
	
	public List<Schedule> getAllSchedules();

	public Schedule getScheduleById(int id) throws ScheduleNotFoundException;

	public Schedule addSchedule(Schedule schedule) throws ScheduleAlreadyExistsException;

	public Schedule updateScheduleById(Schedule schedule) throws ScheduleNotFoundException;

	public Schedule deleteScheduleById(int id) throws ScheduleNotFoundException;
}
