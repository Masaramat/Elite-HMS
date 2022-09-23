package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class Symptom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String categoryCode, categoryDesc, symptomCode, symptomDesc;

	public Symptom() {	}

	public Symptom(String categoryCode, String categoryDesc, String symptomCode, String symptomDesc) {
		super();
		this.categoryCode = categoryCode;
		this.categoryDesc = categoryDesc;
		this.symptomCode = symptomCode;
		this.symptomDesc = symptomDesc;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public String getSymptomCode() {
		return symptomCode;
	}

	public void setSymptomCode(String symptomCode) {
		this.symptomCode = symptomCode;
	}

	public String getSymptomDesc() {
		return symptomDesc;
	}

	public void setSymptomDesc(String symptomDesc) {
		this.symptomDesc = symptomDesc;
	}
	
	

}
