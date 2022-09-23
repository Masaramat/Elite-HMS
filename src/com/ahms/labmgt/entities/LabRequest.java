package com.ahms.labmgt.entities;

import java.io.Serializable;

public class LabRequest implements Serializable {
	
	String invoiceNo, surname, othernames;
	int noOfTests;

	public LabRequest() {
		// TODO Auto-generated constructor stub
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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

	public int getNoOfTests() {
		return noOfTests;
	}

	public void setNoOfTests(int noOfTests) {
		this.noOfTests = noOfTests;
	}

	public LabRequest(String invoiceNo, String surname, String othernames, int noOfTests) {
		super();
		this.invoiceNo = invoiceNo;
		this.surname = surname;
		this.othernames = othernames;
		this.noOfTests = noOfTests;
	}
	
	

}
