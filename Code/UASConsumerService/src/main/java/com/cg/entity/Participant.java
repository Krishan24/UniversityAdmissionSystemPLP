package com.cg.entity;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class Participant {
	
	@NotNull
	private String rollNo;

	@Email
	private String emailId;
	@Id
	private int applicationId;
	@NotNull
	private String scheduledProgramId;
	
	public Participant() {
		
	}

	public Participant(String rollNo, String emailId, int applicationId, String scheduledProgramId) {
		super();
		this.rollNo = rollNo;
		this.emailId = emailId;
		this.applicationId = applicationId;
		this.scheduledProgramId = scheduledProgramId;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public String getScheduledProgramId() {
		return scheduledProgramId;
	}

	public void setScheduledProgramId(String scheduledProgramId) {
		this.scheduledProgramId = scheduledProgramId;
	}

	@Override
	public String toString() {
		return "Participant [rollNo=" + rollNo + ", emailId=" + emailId + ", applicationId=" + applicationId
				+ ", scheduledProgramId=" + scheduledProgramId + "]";
	}


}
