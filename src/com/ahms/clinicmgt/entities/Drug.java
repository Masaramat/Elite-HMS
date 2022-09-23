package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class Drug implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String drugCode, drugName;
	
	public Drug() {
		// TODO Auto-generated constructor stub
	}

	public Drug(String drugCode, String drugName) {
		super();
		this.drugCode = drugCode;
		this.drugName = drugName;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	
	
}
