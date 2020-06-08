package com.cg.exception;

public class ApplicationAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Application already exists";
	}

}
