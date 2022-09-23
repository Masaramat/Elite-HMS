package com.ahms.labmgt.entities;

import java.io.Serializable;

public class LabInvoiceSummary implements Serializable{
	String invoice_no, invoice_date, fullname, gender, no_of_tests;
	
	public LabInvoiceSummary() {}

	public LabInvoiceSummary(String invoice_no, String invoice_date, String fullname, String gender, String no_of_tests) {
		super();
		this.invoice_no = invoice_no;
		this.invoice_date = invoice_date;
		this.fullname = fullname;
		this.gender = gender;
		this.no_of_tests = no_of_tests;
	}

	
	
	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getInvoice_date() {
		return invoice_date;
	}

	public void setInvoice_date(String invoice_date) {
		this.invoice_date = invoice_date;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNo_of_tests() {
		return no_of_tests;
	}

	public void setNo_of_tests(String no_of_tests) {
		this.no_of_tests = no_of_tests;
	}
	
	
	
	

}
