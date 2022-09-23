package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class PrescriptionOrderItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String invoiceNumber, medicineCode, medicineName,  orderState, medicineForm;
	int serialNo, dose, frequency, duration, dispenseQauntity; 
	
	public PrescriptionOrderItem() {
		// TODO Auto-generated constructor stub
	}
	

	public String getMedicineForm() {
		return medicineForm;
	}


	public void setMedicineForm(String medicineForm) {
		this.medicineForm = medicineForm;
	}


	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getMedicineCode() {
		return medicineCode;
	}

	public void setMedicineCode(String medicineCode) {
		this.medicineCode = medicineCode;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public int getDose() {
		return dose;
	}

	public void setDose(int dose) {
		this.dose = dose;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDispenseQauntity() {
		return dispenseQauntity;
	}

	public void setDispenseQauntity(int dispenseQauntity) {
		this.dispenseQauntity = dispenseQauntity;
	}

		
	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public PrescriptionOrderItem(String invoiceNumber, String medicineCode, String medicineName, String orderState,
			int dose, int frequency, int duration, int dispenseQauntity) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.medicineCode = medicineCode;
		this.medicineName = medicineName;
		this.orderState = orderState;
		this.dose = dose;
		this.frequency = frequency;
		this.duration = duration;
		this.dispenseQauntity = dispenseQauntity;
	}

	
	
}
