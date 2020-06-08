package com.cg.exception;

public class ProgramAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 4856045176449549769L;

	@Override
	public String getMessage() {
		return "program is already present";
	}
}
