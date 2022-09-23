package com.ahms.pharmarcymgt.entities;

import java.io.Serializable;

public class PrescriptionInvoice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String invoiceNo, patientSurname, patientOthernames, invoiceDate, invoiceStatus;
	int noOfItems;

	public PrescriptionInvoice() {
		// TODO Auto-generated constructor stub
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getPatientSurname() {
		return patientSurname;
	}

	public void setPatientSurname(String patientSurname) {
		this.patientSurname = patientSurname;
	}

	public String getPatientOthernames() {
		return patientOthernames;
	}

	public void setPatientOthernames(String patientOthernames) {
		this.patientOthernames = patientOthernames;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public int getNoOfItems() {
		return noOfItems;
	}

	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}
	
	
	

}
