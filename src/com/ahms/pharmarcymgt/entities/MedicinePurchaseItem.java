package com.ahms.pharmarcymgt.entities;

import java.io.Serializable;

public class MedicinePurchaseItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String medicineCode, medicimeName, date, vendor, staff;
	double medicinePrice;
	int unit_price, qty;
	
	
	public MedicinePurchaseItem() {
		// TODO Auto-generated constructor stub
	}
	
	public double getMedicinePrice() {
		return medicinePrice;
	}

	public void setMedicinePrice(double medicinePrice) {
		this.medicinePrice = medicinePrice;
	}

	public String getMedicineCode() {
		return medicineCode;
	}
	public void setMedicineCode(String medicineCode) {
		this.medicineCode = medicineCode;
	}
	public String getMedicimeName() {
		return medicimeName;
	}
	public void setMedicimeName(String medicimeName) {
		this.medicimeName = medicimeName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getStaff() {
		return staff;
	}
	public void setStaff(String staff) {
		this.staff = staff;
	}
	public int getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(int unit_price) {
		this.unit_price = unit_price;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public MedicinePurchaseItem(String medicineCode, String medicimeName, String date, String vendor, String staff,
			int unit_price, int qty) {
		super();
		this.medicineCode = medicineCode;
		this.medicimeName = medicimeName;
		this.date = date;
		this.vendor = vendor;
		this.staff = staff;
		this.unit_price = unit_price;
		this.qty = qty;
	}
	
	

}
