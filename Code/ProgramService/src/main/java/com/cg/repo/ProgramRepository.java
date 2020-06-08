package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Program;

public interface ProgramRepository extends JpaRepository<Program, Integer> {

}
