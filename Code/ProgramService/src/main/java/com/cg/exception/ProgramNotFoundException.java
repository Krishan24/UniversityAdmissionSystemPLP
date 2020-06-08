package com.cg.exception;

public class ProgramNotFoundException extends Exception {

	private static final long serialVersionUID = 6430118707796281939L;

	@Override
	public String getMessage() {
		return "program not found";
	}
}
