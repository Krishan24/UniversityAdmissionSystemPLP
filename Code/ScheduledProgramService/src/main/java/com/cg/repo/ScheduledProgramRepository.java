package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Schedule;

public interface ScheduledProgramRepository extends JpaRepository<Schedule, Integer>{

}
