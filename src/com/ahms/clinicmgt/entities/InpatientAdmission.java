package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class InpatientAdmission implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String invoiceNo,  hospitalNo, patientSurname, patientOthernames, dob, gender, bloodGroup,  emrStatus, fullname;
	String admissionDate, department, admittingDoctor, admissionTime;

	String contactRelation, contactPhone;
	String bedCode, bedDetails, admissionStatus;
	int admBedDays;
	
	
	 public int getAdmBedDays() {
		return admBedDays;
	}
	 

	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


	public void setAdmBedDays(int admBedDays) {
		this.admBedDays = admBedDays;
	}

	double bedCharge;

	public InpatientAdmission() {      }

	public String getInvoiceNo() {
		return invoiceNo;
	}

 	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	} 


	public String getHospitalNo() {
		return hospitalNo;
	}

	public void setHospitalNo(String hospitalNo) {
		this.hospitalNo = hospitalNo;
	}

	public String getPatientSurname() {
		return patientSurname;
	}

	public void setPatientSurname(String patientSurname) {
		this.patientSurname = patientSurname;
	}

	public String getPatientOthernames() {
		return patientOthernames;
	}

	public void setPatientOthernames(String patientOthernames) {
		this.patientOthernames = patientOthernames;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloogGroup) {
		this.bloodGroup = bloogGroup;
	}

	public String getEmrStatus() {
		return emrStatus;
	}

	public void setEmrStatus(String emrStatus) {
		this.emrStatus = emrStatus;
	}

	
	public String getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}

	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAdmittingDoctor() {
		return admittingDoctor;
	}

	public void setAdmittingDoctor(String admittingDoctor) {
		this.admittingDoctor = admittingDoctor;
	}

	

	public String getContactRelation() {
		return contactRelation;
	}

	public void setContactRelation(String contactRelation) {
		this.contactRelation = contactRelation;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getBedCode() {
		return bedCode;
	}

	public void setBedCode(String bedCode) {
		this.bedCode = bedCode;
	}

	public String getBedDetails() {
		return bedDetails;
	}

	public void setBedDetails(String bedDetails) {
		this.bedDetails = bedDetails;
	}

	public String getAdmissionStatus() {
		return admissionStatus;
	}

	public void setAdmissionStatus(String admissionStatus) {
		this.admissionStatus = admissionStatus;
	}

	public double getBedCharge() {
		return bedCharge;
	}

	public void setBedCharge(double bedCharge) {
		this.bedCharge = bedCharge;
	}
	
	public String getAdmissionTime() {
		return admissionTime;
	}

	public void setAdmissionTime(String admissionTime) {
		this.admissionTime = admissionTime;
	}

}
