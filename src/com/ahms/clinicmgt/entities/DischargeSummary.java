package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class DischargeSummary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String dischargeMOde, dischargeNote, date, time, physician;
	

	public DischargeSummary() {
		// TODO Auto-generated constructor stub
	}


	public String getDischargeMOde() {
		return dischargeMOde;
	}


	public void setDischargeMOde(String dischargeMOde) {
		this.dischargeMOde = dischargeMOde;
	}


	public String getDischargeNote() {
		return dischargeNote;
	}


	public void setDischargeNote(String dischargeNote) {
		this.dischargeNote = dischargeNote;
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


	public String getPhysician() {
		return physician;
	}


	public void setPhysician(String physician) {
		this.physician = physician;
	}


	public DischargeSummary(String dischargeMOde, String dischargeNote, String date, String time, String physician) {
		super();
		this.dischargeMOde = dischargeMOde;
		this.dischargeNote = dischargeNote;
		this.date = date;
		this.time = time;
		this.physician = physician;
	}

	
	
}
