package com.ahms.pharmarcymgt.entities;

import java.io.Serializable;

public class MedicineStockItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String medicineCode, drugName, medicineForm, salesUnit, countState;
	int availableQty;
	
	
	
	public MedicineStockItem() {
		// TODO Auto-generated constructor stub
	}

	public MedicineStockItem(String medicineCode, String drugName, String medicineForm, String salesUnit,
			String countState, int availableQty) {
		super();
		this.medicineCode = medicineCode;
		this.drugName = drugName;
		this.medicineForm = medicineForm;
		this.salesUnit = salesUnit;
		this.countState = countState;
		this.availableQty = availableQty;
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

	public String getMedicineForm() {
		return medicineForm;
	}

	public void setMedicineForm(String medicineForm) {
		this.medicineForm = medicineForm;
	}

	public String getSalesUnit() {
		return salesUnit;
	}

	public void setSalesUnit(String salesUnit) {
		this.salesUnit = salesUnit;
	}

	public String getCountState() {
		return countState;
	}

	public void setCountState(String countState) {
		this.countState = countState;
	}

	public int getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(int availableQty) {
		this.availableQty = availableQty;
	}
	
	

}
