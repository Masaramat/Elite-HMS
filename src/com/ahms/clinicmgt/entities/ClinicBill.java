package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class ClinicBill implements Serializable {

	String sno, invoiceNo, date, time, serviceDescription;
	double amount;
	private static final long serialVersionUID = 1L;

	public ClinicBill() {
		// TODO Auto-generated constructor stub
	}

	public ClinicBill(String sno, String invoiceNo, String date, String time, String serviceDescription,
			double amount) {
		super();
		this.sno = sno;
		this.invoiceNo = invoiceNo;
		this.date = date;
		this.time = time;
		this.serviceDescription = serviceDescription;
		this.amount = amount;
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

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
