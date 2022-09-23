package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class NursingNote implements Serializable{
	String invoiceNo, note, nurse, date,time, status;
	int serialNo;

	public NursingNote() {
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNurse() {
		return nurse;
	}

	public void setNurse(String nurse) {
		this.nurse = nurse;
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

	public NursingNote(String invoiceNo, String note, String nurse, String date, String time) {
		super();
		this.invoiceNo = invoiceNo;
		this.note = note;
		this.nurse = nurse;
		this.date = date;
		this.time = time;
	}
	
	

}
