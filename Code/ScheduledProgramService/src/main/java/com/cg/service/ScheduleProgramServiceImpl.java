package com.cg.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.Schedule;
import com.cg.exception.ScheduleAlreadyExistsException;
import com.cg.exception.ScheduleNotFoundException;
import com.cg.repo.ScheduledProgramRepository;

@Service
public class ScheduleProgramServiceImpl implements ScheduledProgramService {

	Logger logger = LoggerFactory.getLogger(ScheduleProgramServiceImpl.class);

	@Autowired
	private ScheduledProgramRepository repo;

	@Override
	public List<Schedule> getAllSchedules() {
		return repo.findAll();
	}

	@Override
	public Schedule getScheduleById(int id) throws ScheduleNotFoundException {
		return repo.findById(id).orElseThrow(() -> new ScheduleNotFoundException());
	}

	@Override
	public Schedule addSchedule(Schedule schedule) throws ScheduleAlreadyExistsException {
		if (repo.existsById(schedule.getId()))
			throw new ScheduleAlreadyExistsException();
		else
			return repo.save(schedule);
	}

	@Override
	public Schedule updateScheduleById(Schedule schedule) throws ScheduleNotFoundException {
		if (!repo.existsById(schedule.getId())) {
			logger.info("schedule not found - schedule service");
			throw new ScheduleNotFoundException();
		}
		else
			return repo.save(schedule);
	}

	@Override
	public Schedule deleteScheduleById(int id) throws ScheduleNotFoundException {
		if (repo.existsById(id)) {
			Schedule s = repo.findById(id).orElse(null);
			Schedule result = new Schedule(s.getId(), s.getProgramName(), s.getLocation(), s.getStartdate(),
					s.getEnddate(), s.getSessionsPerWeek());
			repo.deleteById(id);
			return result;
		}else {
			throw new ScheduleNotFoundException();
		}
	}

}
