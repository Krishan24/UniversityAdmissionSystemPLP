package com.cg.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.cg.entity.Program;
import com.cg.entity.Schedule;

@Service
public class AdminServiceImpl implements AdminService {

	Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	private RestTemplate temp;

	@Override
	public ResponseEntity<List<Program>> getAllProgramsOffered() {
		String url = "http://program-service/program/getall";
		List<Program> result = temp.getForObject(url, List.class);
		logger.info(result.toString());
		if (result.size() > 0) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Program> getProgramById(int id) {
		String url = "http://program-service/program/getbyid/" + id;
		logger.info(temp.getForEntity(url, Program.class).toString());
		return temp.getForEntity(url, Program.class);
	}

	@Override
	public ResponseEntity<Program> addProgram(Program program) {
		String url = "http://program-service/program/addprogram";
		ResponseEntity<Program> response = temp.postForEntity(url, program, Program.class);
		System.out.println(response);
		return response;
	}

	@Override
	public ResponseEntity<Program> updateProgram(Program program) {
		String url = "http://program-service/program/updateprogram";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Program> httpEntity = new HttpEntity<Program>(program, headers);
		ResponseEntity<Program> responseEntity = temp.exchange(url, HttpMethod.PUT, httpEntity, Program.class);
		System.out.println(responseEntity);//
		return responseEntity;

	}

	@Override
	public ResponseEntity<Program> deleteProgramById(int id) {
		String url = "http://program-service/program/delete/id/" + id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", id);
		ResponseEntity<Program> responseEntity = temp.exchange(url, HttpMethod.DELETE, null, Program.class);
		System.out.println(responseEntity);
		return responseEntity;
	}

	@Override
	public ResponseEntity<List<Schedule>> getAllScheduledPrograms() {
		String url = "http://scheduled-program-service/schedule/getall";
		List<Schedule> result = temp.getForObject(url, List.class);
		logger.info(result.toString());
		if (result.size() > 0) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Schedule> getScheduledProgramById(int id) {
		String url = "http://scheduled-program-service/schedule/getbyid/" + id;
		logger.info(temp.getForEntity(url, Schedule.class).toString());
		return temp.getForEntity(url, Schedule.class);
	}

	@Override
	public ResponseEntity<Schedule> addScheduledProgram(Schedule schedule) {
		String url = "http://scheduled-program-service/schedule/addschedule";
		ResponseEntity<Schedule> response = temp.postForEntity(url, schedule, Schedule.class);
		System.out.println(response);
		return response;
	}

	@Override
	public ResponseEntity<Schedule> updateScheduledProgram(Schedule schedule) {
		String url = "http://scheduled-program-service/schedule/updateschedule";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Schedule> httpEntity = new HttpEntity<Schedule>(schedule, headers);
		ResponseEntity<Schedule> responseEntity = temp.exchange(url, HttpMethod.PUT, httpEntity, Schedule.class);
		System.out.println(responseEntity);
		return responseEntity;
	}

	@Override
	public ResponseEntity<Schedule> deleteScheduledProgramsById(int id) {
		String url = "http://scheduled-program-service/schedule/delete/id/" + id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", id);
		ResponseEntity<Schedule> responseEntity = temp.exchange(url, HttpMethod.DELETE, null, Schedule.class);
		System.out.println(responseEntity);
		return responseEntity;
	}

	@Override
	public ResponseEntity<List<Application>> getAllApplicationsReportBySchedId(int schedId) {
		String url = "http://application-service/application/getall";
		List<Application> result = temp.getForObject(url, List.class);
		result = result.stream().filter((a) -> Integer.parseInt(a.getScheduledProgramId()) == schedId)
				.collect(Collectors.toList());
		logger.info(result.toString());
		if (result.size() > 0) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
	}

}
