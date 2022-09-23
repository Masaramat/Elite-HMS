package com.ahms.hmgt.entities;

import java.io.Serializable;

/**
 * @author Longji
 *
 */
public class Doctor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String doctorId, doctorName, qualifications, specialty, status;
		
	public Doctor() {}
	
	
	public Doctor(String doctorId, String doctorName, String deptCode, String department, String licenceNo,
			String qualifications, String specialties) {
		super();
		this.doctorId = doctorId;
		this.doctorName = doctorName;	
		this.qualifications = qualifications;
		this.specialty = specialties;
		this.status = status;
	}



	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	
	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}

	public String getSpecialty() {
		return specialty;
	}


	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

}
