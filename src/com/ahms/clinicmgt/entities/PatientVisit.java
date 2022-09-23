package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class PatientVisit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String invoiceNo, caseNo, hospitalNo, surname, othernames, dob, gender, bloodGroup, genotype, phone, fullname;
	String clinic, doctor, date, time, type, status, emrSratus;
	
	public PatientVisit() {	}
	
	public PatientVisit(String invoiceNo, String caseNo, String hospitalNo, String surname, String othernames,
			String dob, String gender, String bloodGroup, String genotype, String phone, String clinic, String doctor,
			String date, String time, String type, String status) {
		super();
		this.invoiceNo = invoiceNo;
		this.caseNo = caseNo;
		this.hospitalNo = hospitalNo;
		this.surname = surname;
		this.othernames = othernames;
		this.dob = dob;
		this.gender = gender;
		this.bloodGroup = bloodGroup;
		this.genotype = genotype;
		this.phone = phone;
		this.clinic = clinic;
		this.doctor = doctor;
		this.date = date;
		this.time = time;
		this.type = type;
		this.status = status;
		this.fullname = fullname;
	}

	

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
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

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getGenotype() {
		return genotype;
	}

	public void setGenotype(String genotype) {
		this.genotype = genotype;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getClinic() {
		return clinic;
	}

	public void setClinic(String clinic) {
		this.clinic = clinic;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getEmrSratus() {
		return emrSratus;
	}

	public void setEmrSratus(String emrSratus) {
		this.emrSratus = emrSratus;
	}

}
