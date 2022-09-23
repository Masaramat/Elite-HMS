package com.ahms.hmgt.entities;

import java.io.Serializable;

public class PatientBiodata implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String hospital_no, surname, firstname, othernames, regDate, dob;
	String gender, bloodGroup, genotype, maritalStatus;
	String nationality, state, lga, occupation, religion;
	String phoneMobile, phoneHome, fax, email, address1, address2;
	String nok_surname, nok_othernames, nok_address, nok_relationship, nok_mobile, familyNo, registrationOfficer;
	double charge;
	
	public PatientBiodata() {	}
	
	public String getFamilyNo() {
		return familyNo;
	}


	public void setFamilyNo(String familyNo) {
		this.familyNo = familyNo;
	}


	public String getRegistrationOfficer() {
		return registrationOfficer;
	}

	public void setRegistrationOfficer(String registrationOfficer) {
		this.registrationOfficer = registrationOfficer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getHospital_no() {
		return hospital_no;
	}


	public void setHospital_no(String hospital_no) {
		this.hospital_no = hospital_no;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getOthernames() {
		return othernames;
	}


	public void setOthernames(String othernames) {
		this.othernames = othernames;
	}


	public String getRegDate() {
		return regDate;
	}


	public void setRegDate(String regDate) {
		this.regDate = regDate;
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


	public String getMaritalStatus() {
		return maritalStatus;
	}


	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getLga() {
		return lga;
	}


	public void setLga(String lga) {
		this.lga = lga;
	}


	public String getOccupation() {
		return occupation;
	}


	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}


	public String getReligion() {
		return religion;
	}


	public void setReligion(String religion) {
		this.religion = religion;
	}


	public String getPhoneMobile() {
		return phoneMobile;
	}


	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}


	public String getPhoneHome() {
		return phoneHome;
	}


	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}


	public String getFax() {
		return fax;
	}


	public void setFax(String fax) {
		this.fax = fax;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAddress1() {
		return address1;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public String getAddress2() {
		return address2;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getNok_surname() {
		return nok_surname;
	}


	public void setNok_surname(String nok_surname) {
		this.nok_surname = nok_surname;
	}


	public String getNok_othernames() {
		return nok_othernames;
	}


	public void setNok_othernames(String nok_othernames) {
		this.nok_othernames = nok_othernames;
	}


	public String getNok_address() {
		return nok_address;
	}


	public void setNok_address(String nok_address) {
		this.nok_address = nok_address;
	}


	public String getNok_relationship() {
		return nok_relationship;
	}


	public void setNok_relationship(String nok_relationship) {
		this.nok_relationship = nok_relationship;
	}


	public String getNok_mobile() {
		return nok_mobile;
	}


	public void setNok_mobile(String nok_mobile) {
		this.nok_mobile = nok_mobile;
	}


	public double getCharge() {
		return charge;
	}


	public void setCharge(double charge) {
		this.charge = charge;
	}
	
	
	
	

}
