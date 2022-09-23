package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class SymptomCategory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String categoryCode, categoryDescription;

	public SymptomCategory() {
		// TODO Auto-generated constructor stub
	}

	public SymptomCategory(String categoryCode, String categoryDescription) {
		super();
		this.categoryCode = categoryCode;
		this.categoryDescription = categoryDescription;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	
	

}
