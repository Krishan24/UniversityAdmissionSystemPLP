package com.cg.entity;

import java.util.ArrayList;
import java.util.List;

public class ProgramList {
	private List<Program> programList;

	public ProgramList() {
		programList = new ArrayList<>();
	}

	public ProgramList(List<Program> programList) {
		super();
		this.programList = programList;
	}

	public List<Program> getProgramList() {
		return programList;
	}

	@Override
	public String toString() {
		return "ProgramList [programList=" + programList + "]";
	}

	public void setProgramList(List<Program> programList) {
		this.programList = programList;
	}

}
