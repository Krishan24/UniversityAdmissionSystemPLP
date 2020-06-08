package com.cg.exception;

public class ApplicationNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Application not found";
	}

}
