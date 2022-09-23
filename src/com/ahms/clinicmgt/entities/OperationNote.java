package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class OperationNote implements Serializable{
	String invoiceNo, CPTCode, procedureName, findings, procedure, drain, detailsOfSpecimen, histologySent, remarks;
	String surgeon, asstSurgeon, anaesthetist, scrubNurse, preOpDiagnosis, postOpDiagnosis, postOpTreatment;
	

	
	public String getSurgeon() {
		return surgeon;
	}
	
	
	public void setSurgeon(String surgeon) {
		this.surgeon = surgeon;
	}

	public String getAsstSurgeon() {
		return asstSurgeon;
	}

	public void setAsstSurgeon(String asstSurgeon) {
		this.asstSurgeon = asstSurgeon;
	}

	public String getAnaesthetist() {
		return anaesthetist;
	}

	public void setAnaesthetist(String anaesthetist) {
		this.anaesthetist = anaesthetist;
	}

	public String getScrubNurse() {
		return scrubNurse;
	}

	public void setScrubNurse(String scrubNurse) {
		this.scrubNurse = scrubNurse;
	}

	public String getPreOpDiagnosis() {
		return preOpDiagnosis;
	}

	public void setPreOpDiagnosis(String preOpDiagnosis) {
		this.preOpDiagnosis = preOpDiagnosis;
	}

	public String getPostOpDiagnosis() {
		return postOpDiagnosis;
	}

	public void setPostOpDiagnosis(String postOpDiagnosis) {
		this.postOpDiagnosis = postOpDiagnosis;
	}

	public String getPostOpTreatment() {
		return postOpTreatment;
	}

	public void setPostOpTreatment(String postOpTreatment) {
		this.postOpTreatment = postOpTreatment;
	}

	private static final long serialVersionUID = 1L;

	public OperationNote() {
		// TODO Auto-generated constructor stub
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getCPTCode() {
		return CPTCode;
	}

	public void setCPTCode(String cPTCode) {
		CPTCode = cPTCode;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getFindings() {
		return findings;
	}

	public void setFindings(String findings) {
		this.findings = findings;
	}

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	public String getDrain() {
		return drain;
	}

	public void setDrain(String drain) {
		this.drain = drain;
	}

	public String getDetailsOfSpecimen() {
		return detailsOfSpecimen;
	}

	public void setDetailsOfSpecimen(String detailsOfSpecimen) {
		this.detailsOfSpecimen = detailsOfSpecimen;
	}

	public String getHistologySent() {
		return histologySent;
	}

	public void setHistologySent(String histologySent) {
		this.histologySent = histologySent;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
