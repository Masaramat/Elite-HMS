package com.ahms.hmgt.entities;

import java.io.Serializable;

public class Appointment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String hospitalNno, surname, othernames, date, time, state;
	String docId, doctorName, saveDate;

	public Appointment() {	}

	public Appointment(String hospitalNno, String surname, String othernames, String date, String time, String state,
			String docId, String doctorName, String saveDate) {
		super();
		this.hospitalNno = hospitalNno;
		this.surname = surname;
		this.othernames = othernames;
		this.date = date;
		this.time = time;
		this.state = state;
		this.docId = docId;
		this.doctorName = doctorName;
		this.saveDate = saveDate;
	}

	public String getHospitalNno() {
		return hospitalNno;
	}

	public void setHospitalNno(String hospitalNno) {
		this.hospitalNno = hospitalNno;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getOthernames() {
		return othernames;
	}

	public void setOthernames(String othernames) {
		this.othernames = othernames;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(String saveDate) {
		this.saveDate = saveDate;
	}
	
	
	
	

}
