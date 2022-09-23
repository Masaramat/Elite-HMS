package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class ProgressNote implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String invoice_no, date, time, note, physician, noteStatus;
	int sno;
	
	public ProgressNote() {}

	public ProgressNote(int sno, String invoice_no, String date, String time, String note, String physician, String noteStatus) {
		super();
		this.sno = sno;
		this.invoice_no = invoice_no;
		this.date = date;
		this.time = time;
		this.note = note;
		this.physician = physician;
		this.noteStatus = noteStatus;
	}	
		
	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getNoteStatus() {
		return noteStatus;
	}

	public void setNoteStatus(String noteStatus) {
		this.noteStatus = noteStatus;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPhysician() {
		return physician;
	}

	public void setPhysician(String physician) {
		this.physician = physician;
	}
	
	

}
