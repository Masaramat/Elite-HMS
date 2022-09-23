package com.ahms.hmgt.entities;

import java.io.Serializable;

public class Vendor implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String vendorCode, vendorName, company, address, phone, email;

	public Vendor() {	}

	public Vendor(String vendorCode, String vendorName, String company, String address, String phone, String email) {
		super();
		this.vendorCode = vendorCode;
		this.vendorName = vendorName;
		this.company = company;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
}
