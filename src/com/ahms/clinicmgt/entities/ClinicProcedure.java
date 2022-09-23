package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class ClinicProcedure implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id, procedure, cptCode;

	public ClinicProcedure() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	public String getCptCode() {
		return cptCode;
	}

	public void setCptCode(String cptCode) {
		this.cptCode = cptCode;
	}

	public ClinicProcedure(String id, String procedure, String cptCode) {
		super();
		this.id = id;
		this.procedure = procedure;
		this.cptCode = cptCode;
	}
	
	

}
