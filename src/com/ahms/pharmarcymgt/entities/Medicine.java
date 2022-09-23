package com.ahms.pharmarcymgt.entities;

import java.io.Serializable;

public class Medicine implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String medicineCode, drugName, medicineDescription, medicineForm, purchaseUnit, salesUnit, countingState;
	int psRatio;

	public Medicine() {}

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

	public String getMedicineDescription() {
		return medicineDescription;
	}

	public void setMedicineDescription(String medicineDescription) {
		this.medicineDescription = medicineDescription;
	}

	public String getMedicineForm() {
		return medicineForm;
	}

	public void setMedicineForm(String medicineForm) {
		this.medicineForm = medicineForm;
	}

	public String getPurchaseUnit() {
		return purchaseUnit;
	}

	public void setPurchaseUnit(String purchaseUnit) {
		this.purchaseUnit = purchaseUnit;
	}

	public String getSalesUnit() {
		return salesUnit;
	}

	public void setSalesUnit(String salesUnit) {
		this.salesUnit = salesUnit;
	}

	public String getCountingState() {
		return countingState;
	}

	public void setCountingState(String countingState) {
		this.countingState = countingState;
	}

	public int getPsRatio() {
		return psRatio;
	}

	public void setPsRatio(int psRatio) {
		this.psRatio = psRatio;
	}
	
	

}
