package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class InpatientObservation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String sno, invoiceNo, admissionNo, date, time;
	int bp_systole, by_diastole,  pulse, respiration;
	double temperature;

	public InpatientObservation() {
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

	public String getAdmissionNo() {
		return admissionNo;
	}

	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
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

	public int getBp_systole() {
		return bp_systole;
	}

	public void setBp_systole(int bp_systole) {
		this.bp_systole = bp_systole;
	}

	public int getBy_diastole() {
		return by_diastole;
	}

	public void setBy_diastole(int by_diastole) {
		this.by_diastole = by_diastole;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public int getPulse() {
		return pulse;
	}

	public void setPulse(int pulse) {
		this.pulse = pulse;
	}

	public int getRespiration() {
		return respiration;
	}

	public void setRespiration(int respiration) {
		this.respiration = respiration;
	}
	
	

}
