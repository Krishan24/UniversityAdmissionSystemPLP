package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

}
