package com.ahms.hmgt.entities;

import java.io.Serializable;

public class IPInvoice implements Serializable {
	String invoiceNo, hospitalNo, surname, othernames, gender;
	String admissionNo, admissionDate, bedDetails, bedCharge, noOfDays, admissionStatus, admissionDues;
	String clinicDues, laboratoryDues, pharmacyDues, procedureDues;
	String totalDiscount, totalDeposit, totalRefund, totalReceivable, netReceivable, totalDue;

	private static final long serialVersionUID = 9158352550798499392L;

	public IPInvoice() {	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getAdmissionNo() {
		return admissionNo;
	}

	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}

	public String getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}

	public String getHospitalNo() {
		return hospitalNo;
	}

	public void setHospitalNo(String hospitalNo) {
		this.hospitalNo = hospitalNo;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAdmissionStatus() {
		return admissionStatus;
	}

	public void setAdmissionStatus(String admissionStatus) {
		this.admissionStatus = admissionStatus;
	}

	public String getBedDetails() {
		return bedDetails;
	}

	public void setBedDetails(String bedDetails) {
		this.bedDetails = bedDetails;
	}

	public String getBedCharge() {
		return bedCharge;
	}

	public void setBedCharge(String bedCharge) {
		this.bedCharge = bedCharge;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getAdmissionDues() {
		return admissionDues;
	}

	public void setAdmissionDues(String admissionDues) {
		this.admissionDues = admissionDues;
	}

	public String getClinicDues() {
		return clinicDues;
	}

	public void setClinicDues(String clinicDues) {
		this.clinicDues = clinicDues;
	}

	public String getLaboratoryDues() {
		return laboratoryDues;
	}

	public void setLaboratoryDues(String laboratoryDues) {
		this.laboratoryDues = laboratoryDues;
	}

	public String getPharmacyDues() {
		return pharmacyDues;
	}

	public void setPharmacyDues(String pharmacyDues) {
		this.pharmacyDues = pharmacyDues;
	}

	public String getProcedureDues() {
		return procedureDues;
	}

	public void setProcedureDues(String procedureDues) {
		this.procedureDues = procedureDues;
	}

	public String getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(String totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public String getTotalDeposit() {
		return totalDeposit;
	}

	public void setTotalDeposit(String totalDeposit) {
		this.totalDeposit = totalDeposit;
	}

	public String getTotalRefund() {
		return totalRefund;
	}

	public void setTotalRefund(String totalRefund) {
		this.totalRefund = totalRefund;
	}

	public String getTotalReceivable() {
		return totalReceivable;
	}

	public void setTotalReceivable(String totalReceivable) {
		this.totalReceivable = totalReceivable;
	}

	public String getNetReceivable() {
		return netReceivable;
	}

	public void setNetReceivable(String netReceivable) {
		this.netReceivable = netReceivable;
	}

	public String getTotalDue() {
		return totalDue;
	}

	public void setTotalDue(String totalDue) {
		this.totalDue = totalDue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
