package com.cg.exception;

public class ScheduleAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -8738222115540559430L;

	@Override
	public String getMessage() {
		return "program schedule already exists";
	}
}
