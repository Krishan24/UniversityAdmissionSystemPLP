package com.cg.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cg.entity.Application;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

	@Autowired
	private RestTemplate temp;

	@Override
	public ResponseEntity<List<Application>> getAllApplications() {
		String url = "http://application-service:8081/application/getall";
		List<Application> result = temp.getForObject(url, List.class);
		logger.info(result.toString());
		if (result.size() > 0) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
	}
	

	@Override
	public ResponseEntity<Application> getApplicationById(int id) {
		String url = "http://application-service:8081/application/getbyid/" + id;
		logger.info(temp.getForEntity(url, Application.class).toString());
		return temp.getForEntity(url, Application.class);
	}

	@Override
	public ResponseEntity<Application> addApplication(Application application) {
		String url = "http://application-service:8081/application/addapplication";
		ResponseEntity<Application> response = temp.postForEntity(url, application, Application.class);
		System.out.println(response);
		return response;
	}

	@Override
	public ResponseEntity<Application> updateApplication(Application application) {
		String url = "http://application-service:8081/application/updateapplication";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Application> httpEntity = new HttpEntity<Application>(application, headers);
		ResponseEntity<Application> responseEntity = temp.exchange(url, HttpMethod.PUT, httpEntity, Application.class);
		System.out.println(responseEntity);//
		return responseEntity;
	}

	@Override
	public ResponseEntity<Application> deleteApplicationById(int id) {
		String url = "http://application-service:8081/application/delete/id/" + id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", id);
		ResponseEntity<Application> responseEntity = temp.exchange(url, HttpMethod.DELETE, null, Application.class);
		System.out.println(responseEntity);
		return responseEntity;
	}
}
