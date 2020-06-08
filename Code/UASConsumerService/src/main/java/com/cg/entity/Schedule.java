package com.cg.entity;

import java.time.LocalDate;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class Schedule {

	private int id;

	@NotNull
	@Size(min = 2, message = "Name should have atleast 2 characters")
	private String programName;

	@NotNull
	@Size(min = 2, message = "location should have atleast 2 characters")
	private String location;

	@Future
	private LocalDate startdate;

	@Future
	private LocalDate enddate;

	@Positive
	private int sessionsPerWeek;

	@AssertTrue(message = "start date field should be lesser than end date field")
	private boolean isValid() {
		if (startdate == null || enddate == null)
			return false;
		if (startdate.isBefore(enddate)) {
			return true;
		} else {
			return false;
		}
	}

	public Schedule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Schedule(int id, String programName, String location, LocalDate startdate, LocalDate enddate,
			int sessionsPerWeek) {
		super();
		this.id = id;
		this.programName = programName;
		this.location = location;
		this.startdate = startdate;
		this.enddate = enddate;
		this.sessionsPerWeek = sessionsPerWeek;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getStartdate() {
		return startdate;
	}

	public void setStartdate(LocalDate startdate) {
		this.startdate = startdate;
	}

	public LocalDate getEnddate() {
		return enddate;
	}

	public void setEnddate(LocalDate enddate) {
		this.enddate = enddate;
	}

	public int getSessionsPerWeek() {
		return sessionsPerWeek;
	}

	public void setSessionsPerWeek(int sessionsPerWeek) {
		this.sessionsPerWeek = sessionsPerWeek;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", programName=" + programName + ", location=" + location + ", startdate="
				+ startdate + ", enddate=" + enddate + ", sessionsPerWeek=" + sessionsPerWeek + "]";
	}

}
