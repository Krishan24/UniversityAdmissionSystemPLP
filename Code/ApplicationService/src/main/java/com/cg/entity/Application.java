package com.cg.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer applicationId;
	@NotNull
	@Size(min = 8, message = "Name should be of atleast 8 characters")
	private String fullName;
	@Past
	private LocalDate dateOfBirth;
	@NotNull
	@Size(min = 2, message = "Atleast 2 characters required")
	private String highestQualification;
	@NotNull
	private int marksObtained;
	@NotNull
	private String goals;
	@Email
	private String emailId;
	@NotNull
	private String scheduledProgramId;
	@NotNull
	private String status;
	@Future
	private LocalDate dateOfInterview;
	
	public Application() {
		
	}
	
	public Application(Integer applicationId, String fullName, LocalDate dateOfBirth, String highestQualification,
			int marksObtained, String goals, String emailId, String scheduledProgramId, String status,
			LocalDate dateOfInterview) {
		super();
		this.applicationId = applicationId;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.highestQualification = highestQualification;
		this.marksObtained = marksObtained;
		this.goals = goals;
		this.emailId = emailId;
		this.scheduledProgramId = scheduledProgramId;
		this.status = status;
		this.dateOfInterview = dateOfInterview;
	}
	
	public Integer getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getHighestQualification() {
		return highestQualification;
	}
	public void setHighestQualification(String highestQualification) {
		this.highestQualification = highestQualification;
	}
	public int getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(int marksObtained) {
		this.marksObtained = marksObtained;
	}
	public String getGoals() {
		return goals;
	}
	public void setGoals(String goals) {
		this.goals = goals;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getScheduledProgramId() {
		return scheduledProgramId;
	}
	public void setScheduledProgramId(String scheduledProgramId) {
		this.scheduledProgramId = scheduledProgramId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getDateOfInterview() {
		return dateOfInterview;
	}
	public void setDateOfInterview(LocalDate dateOfInterview) {
		this.dateOfInterview = dateOfInterview;
	}
	
	@Override
	public String toString() {
		return "Application [applicationId=" + applicationId + ", fullName=" + fullName + ", dateOfBirth=" + dateOfBirth
				+ ", highestQualification=" + highestQualification + ", marksObtained=" + marksObtained + ", goals="
				+ goals + ", emailId=" + emailId + ", scheduledProgramId=" + scheduledProgramId + ", status=" + status
				+ ", dateOfInterview=" + dateOfInterview + "]";
	}
}

