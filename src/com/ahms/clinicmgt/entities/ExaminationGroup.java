package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class ExaminationGroup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String examGroupCode, examGroupDesc, examSectionCode;

	public ExaminationGroup() {
		// TODO Auto-generated constructor stub
	}

	public ExaminationGroup(String examGroupCode, String examGroupDesc, String examSectionCode) {
		super();
		this.examGroupCode = examGroupCode;
		this.examGroupDesc = examGroupDesc;
		this.examSectionCode = examSectionCode;
	}

	public String getExamGroupCode() {
		return examGroupCode;
	}

	public void setExamGroupCode(String examGroupCode) {
		this.examGroupCode = examGroupCode;
	}

	public String getExamGroupDesc() {
		return examGroupDesc;
	}

	public void setExamGroupDesc(String examGroupDesc) {
		this.examGroupDesc = examGroupDesc;
	}

	public String getExamSectionCode() {
		return examSectionCode;
	}

	public void setExamSectionCode(String examSectionCode) {
		this.examSectionCode = examSectionCode;
	}
	
	

}
