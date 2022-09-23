package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class ExaminationDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String examDetailCode, examDetailDesc, examGroupCode;

	public ExaminationDetail() {
		// TODO Auto-generated constructor stub
	}

	public ExaminationDetail(String examDetailCode, String examDetailDesc, String examGroupCode) {
		super();
		this.examDetailCode = examDetailCode;
		this.examDetailDesc = examDetailDesc;
		this.examGroupCode = examGroupCode;
	}

	public String getExamDetailCode() {
		return examDetailCode;
	}

	public void setExamDetailCode(String examDetailCode) {
		this.examDetailCode = examDetailCode;
	}

	public String getExamDetailDesc() {
		return examDetailDesc;
	}

	public void setExamDetailDesc(String examDetailDesc) {
		this.examDetailDesc = examDetailDesc;
	}

	public String getExamGroupCode() {
		return examGroupCode;
	}

	public void setExamGroupCode(String examGroupCode) {
		this.examGroupCode = examGroupCode;
	}

	
	
}
