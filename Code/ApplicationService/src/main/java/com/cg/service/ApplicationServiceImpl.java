package com.cg.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.Application;
import com.cg.exception.ApplicationAlreadyExistsException;
import com.cg.exception.ApplicationNotFoundException;
import com.cg.repository.ApplicationRepository;

@Service
public class ApplicationServiceImpl implements ApplicationService {
	
	Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

	@Autowired
	private ApplicationRepository repo;

	@Override
	public List<Application> getAllApplications() {
		return repo.findAll();
	}

	@Override
	public Application getApplicationById(int id) throws ApplicationNotFoundException {
			return repo.findById(id).orElseThrow(() -> new ApplicationNotFoundException());
	}

	@Override
	public Application addApplication(Application application) throws ApplicationAlreadyExistsException {
		logger.info(application.toString());
		if (repo.existsById(application.getApplicationId()))
			throw new ApplicationAlreadyExistsException();
		else
			return repo.save(application);
	}

	@Override
	public Application updateApplicationById(Application application) throws ApplicationNotFoundException {
		if (!repo.existsById(application.getApplicationId()))
			throw new ApplicationNotFoundException();
		else
			return repo.save(application);
	}

	@Override
	public Application deleteApplicationById(int id) throws ApplicationNotFoundException {
		if (repo.existsById(id)) {
			Application s = repo.findById(id).orElse(null);
			Application result = new Application(s.getApplicationId(), s.getFullName(), s.getDateOfBirth(), 
					s.getHighestQualification(), s.getMarksObtained(), s.getGoals(), 
					s.getEmailId(), s.getScheduledProgramId(), s.getStatus(), s.getDateOfInterview());
			repo.deleteById(id);
			return result;
		}else {
			throw new ApplicationNotFoundException();
		}
	}
}
