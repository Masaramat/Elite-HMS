package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class BookedProcedure implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String sno, invoiceNo, procedure, anesthesia, theatre, date, time, remarks, bookedBy, cptCode, confirmDate, confirmTime, procedureState;
	double procedureCost, anesthesiaCost;

	public BookedProcedure() {
		// TODO Auto-generated constructor stub
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	public String getAnesthesia() {
		return anesthesia;
	}

	public void setAnesthesia(String anesthesia) {
		this.anesthesia = anesthesia;
	}

	public String getTheatre() {
		return theatre;
	}

	public void setTheatre(String theatre) {
		this.theatre = theatre;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBookedBy() {
		return bookedBy;
	}

	public void setBookedBy(String bookedBy) {
		this.bookedBy = bookedBy;
	}

	public String getCptCode() {
		return cptCode;
	}

	public void setCptCode(String cptCode) {
		this.cptCode = cptCode;
	}

	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}

	public double getProcedureCost() {
		return procedureCost;
	}

	public void setProcedureCost(double procedureCost) {
		this.procedureCost = procedureCost;
	}

	public double getAnesthesiaCost() {
		return anesthesiaCost;
	}

	public void setAnesthesiaCost(double anesthesiaCost) {
		this.anesthesiaCost = anesthesiaCost;
	}

	public String getProcedureState() {
		return procedureState;
	}

	public void setProcedureState(String procedureState) {
		this.procedureState = procedureState;
	}
	
	

}
