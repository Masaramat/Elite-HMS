package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class DiseeaseIndex implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String invoiceNo, caseNo, diseaseCode, diseaseCondition, diagnosisType;

	public DiseeaseIndex() {
		// TODO Auto-generated constructor stub
	}

	public DiseeaseIndex(String invoiceNo, String caseNo, String diseaseCode, String diseaseCondition,
			String diagnosisType) {
		super();
		this.invoiceNo = invoiceNo;
		this.caseNo = caseNo;
		this.diseaseCode = diseaseCode;
		this.diseaseCondition = diseaseCondition;
		this.diagnosisType = diagnosisType;
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

	public String getDiseaseCode() {
		return diseaseCode;
	}

	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}

	public String getDiseaseCondition() {
		return diseaseCondition;
	}

	public void setDiseaseCondition(String diseaseCondition) {
		this.diseaseCondition = diseaseCondition;
	}

	public String getDiagnosisType() {
		return diagnosisType;
	}

	public void setDiagnosisType(String diagnosisType) {
		this.diagnosisType = diagnosisType;
	}
	
	

}
