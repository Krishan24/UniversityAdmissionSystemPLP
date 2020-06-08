package com.cg.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
public class Program {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Size(min = 2, message = "Name should have atleast 2 characters")
	private String name;
	
	@NotNull
	@Size(min = 2, message = "description should have atleast 2 characters")
	private String description;
	
	@NotNull
	@Size(min = 2, message = "eligibility should have atleast 2 characters")
	private String eligibility;
	
	@NotNull
	@Positive
	private int duration;
	
	@NotNull
	@Size(min = 2, message = "certificate should have atleast 2 characters")
	private String certificate;

	public Program() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Program(int id, String name, String description, String eligibility, int duration, String certificate) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.eligibility = eligibility;
		this.duration = duration;
		this.certificate = certificate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEligibility() {
		return eligibility;
	}

	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	@Override
	public String toString() {
		return "Program [id=" + id + ", name=" + name + ", description=" + description + ", eligibility=" + eligibility
				+ ", duration=" + duration + ", certificate=" + certificate + "]";
	}

}
