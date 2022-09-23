package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class OPDPrescription implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String invoiceNo, caseNo, drugCode, drugName, type, dosage, regimen, duration, comment;

	public OPDPrescription() {
		// TODO Auto-generated constructor stub
	}

	public OPDPrescription(String invoiceNo, String caseNo, String drugCode, String type, String dosage, String regimen,
			String duration, String comment) {
		super();
		this.invoiceNo = invoiceNo;
		this.caseNo = caseNo;
		this.drugCode = drugCode;
		this.type = type;
		this.dosage = dosage;
		this.regimen = regimen;
		this.duration = duration;
		this.comment = comment;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getRegimen() {
		return regimen;
	}

	public void setRegimen(String regimen) {
		this.regimen = regimen;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	
	
	

}
