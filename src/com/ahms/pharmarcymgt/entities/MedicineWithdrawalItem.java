package com.ahms.pharmarcymgt.entities;

import java.io.Serializable;

public class MedicineWithdrawalItem implements Serializable{

	String medicineCode, drugName, date;
	int quantity;
	public String getDate() {
		return date;
	}	

	public void setDate(String date) {
		this.date = date;
	}

	
	
	public String getMedicineCode() {
		return medicineCode;
	}

	public void setMedicineCode(String medicineCode) {
		this.medicineCode = medicineCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public MedicineWithdrawalItem() {
		// TODO Auto-generated constructor stub
	}

	public MedicineWithdrawalItem(String medicineCode, String drugName, String date, int quantity) {
		super();
		this.medicineCode = medicineCode;
		this.drugName = drugName;
		this.date = date;
		this.quantity = quantity;
	}

	
	
	

}
