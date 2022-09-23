package com.ahms.hmgt.entities;

import java.io.Serializable;

public class FamilyCard implements Serializable {
	
	String familyNo, familyName, date, regOfficer;
	double regFee;

	public double getRegFee() {
		return regFee;
	}

	public void setRegFee(double regFee) {
		this.regFee = regFee;
	}

	public FamilyCard() {
		// TODO Auto-generated constructor stub
	}

	public FamilyCard(String familyNo, String familyName, String date, String regOfficer, double regFee) {
		super();
		this.familyNo = familyNo;
		this.familyName = familyName;
		this.date = date;
		this.regOfficer = regOfficer;
		this.regFee = regFee;
		
	}

	public String getFamilyNo() {
		return familyNo;
	}

	public void setFamilyNo(String familyNo) {
		this.familyNo = familyNo;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRegOfficer() {
		return regOfficer;
	}

	public void setRegOfficer(String regOfficer) {
		this.regOfficer = regOfficer;
	}

	
	

}
