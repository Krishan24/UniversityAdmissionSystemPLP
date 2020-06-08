package com.cg.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cg.entity.Application;

public interface ApplicationService {

	public ResponseEntity<List<Application>> getAllApplications();

	public ResponseEntity<Application> getApplicationById(int id);

	public ResponseEntity<Application> addApplication(Application application);

	public ResponseEntity<Application> updateApplication(Application application);

	public ResponseEntity<Application> deleteApplicationById(int id);
}
