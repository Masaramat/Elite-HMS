package com.ahms.labmgt.entities;

import java.io.Serializable;

public class TestOrderItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String invoice_no, test_id, status, date, test_name;
	private double price, vat, discount; 
	
	public TestOrderItem() {}

	public TestOrderItem(String invoice_no, String test_id, String status, String date, String test_name, double price,
			double vat, double discount) {
		super();
		this.invoice_no = invoice_no;
		this.test_id = test_id;
		this.status = status;
		this.date = date;
		this.test_name = test_name;
		this.price = price;
		this.vat = vat;
		this.discount = discount;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getTest_id() {
		return test_id;
	}

	public void setTest_id(String test_id) {
		this.test_id = test_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTest_name() {
		return test_name;
	}

	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	
	
	
	
	
	
	

}
