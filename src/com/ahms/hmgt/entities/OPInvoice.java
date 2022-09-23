package com.ahms.hmgt.entities;

import java.io.Serializable;

public class OPInvoice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4955993471840976262L;
	String invoiceNo, invoiceDate, visitStatus, hospitalNo, surname, othernames, gender;
	String clinicDue, pharmacyDue, laboratoryDue, procedureDue;
	String totalDeposit, totalDiscount, totalRefund, totalReceivable, netReceivable, totalDue;

	public OPInvoice() {
		// TODO Auto-generated constructor stub
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getVisitStatus() {
		return visitStatus;
	}

	public void setVisitStatus(String visitStatus) {
		this.visitStatus = visitStatus;
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

	public String getClinicDue() {
		return clinicDue;
	}

	public void setClinicDue(String clinicDue) {
		this.clinicDue = clinicDue;
	}

	public String getPharmacyDue() {
		return pharmacyDue;
	}

	public void setPharmacyDue(String pharmacyDue) {
		this.pharmacyDue = pharmacyDue;
	}

	public String getLaboratoryDue() {
		return laboratoryDue;
	}

	public void setLaboratoryDue(String laboratoryDue) {
		this.laboratoryDue = laboratoryDue;
	}

	public String getProcedureDue() {
		return procedureDue;
	}

	public void setProcedureDue(String procedureDue) {
		this.procedureDue = procedureDue;
	}

	public String getTotalDeposit() {
		return totalDeposit;
	}

	public void setTotalDeposit(String totalDeposit) {
		this.totalDeposit = totalDeposit;
	}

	public String getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(String totalDiscount) {
		this.totalDiscount = totalDiscount;
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
	
	

}
