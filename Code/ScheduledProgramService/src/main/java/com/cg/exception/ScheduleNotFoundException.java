package com.cg.exception;

public class ScheduleNotFoundException extends Exception {

	private static final long serialVersionUID = -5034839811552161017L;

	@Override
	public String getMessage() {
		return "program schedule not found";
	}
}
