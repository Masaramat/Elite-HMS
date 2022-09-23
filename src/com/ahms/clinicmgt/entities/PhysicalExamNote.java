package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class PhysicalExamNote implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String invoiceNumber, examSection, examNote;

	public PhysicalExamNote() {
		// TODO Auto-generated constructor stub
	}

	public PhysicalExamNote(String invoiceNumber, String examSection, String examNote) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.examSection = examSection;
		this.examNote = examNote;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getExamSection() {
		return examSection;
	}

	public void setExamSection(String examSection) {
		this.examSection = examSection;
	}

	public String getExamNote() {
		return examNote;
	}

	public void setExamNote(String examNote) {
		this.examNote = examNote;
	}
	
	

}
