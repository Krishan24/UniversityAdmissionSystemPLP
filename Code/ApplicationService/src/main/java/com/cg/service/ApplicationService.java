package com.cg.service;

import java.util.List;

import com.cg.entity.Application;
import com.cg.exception.ApplicationAlreadyExistsException;
import com.cg.exception.ApplicationNotFoundException;

public interface ApplicationService {
	
	public List<Application> getAllApplications();

	public Application getApplicationById(int id) throws ApplicationNotFoundException;

	public Application addApplication(Application application) throws ApplicationAlreadyExistsException;

	public Application updateApplicationById(Application application) throws ApplicationNotFoundException;

	public Application deleteApplicationById(int id) throws ApplicationNotFoundException;

}
