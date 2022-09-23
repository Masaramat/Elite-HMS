package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class ExaminationSection implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String examSectionCode, examSectionDesc;

	public ExaminationSection() {
		// TODO Auto-generated constructor stub
	}

	public ExaminationSection(String examSectionCode, String examSectionDesc) {
		super();
		this.examSectionCode = examSectionCode;
		this.examSectionDesc = examSectionDesc;
	}

	public String getExamSectionCode() {
		return examSectionCode;
	}

	public void setExamSectionCode(String examSectionCode) {
		this.examSectionCode = examSectionCode;
	}

	public String getExamSectionDesc() {
		return examSectionDesc;
	}

	public void setExamSectionDesc(String examSectionDesc) {
		this.examSectionDesc = examSectionDesc;
	}

	
	

}
