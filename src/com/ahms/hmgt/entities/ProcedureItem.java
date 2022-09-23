package com.ahms.hmgt.entities;

import java.io.Serializable;

public class ProcedureItem implements Serializable {
	String procedureCode, procedureDesc, cptCode;

	public ProcedureItem() {
		// TODO Auto-generated constructor stub
	}

	public String getProcedureCode() {
		return procedureCode;
	}

	public void setProcedureCode(String procedureCode) {
		this.procedureCode = procedureCode;
	}

	public String getProcedureDesc() {
		return procedureDesc;
	}

	public void setProcedureDesc(String procedureDesc) {
		this.procedureDesc = procedureDesc;
	}

	public String getCptCode() {
		return cptCode;
	}

	public void setCptCode(String cptCode) {
		this.cptCode = cptCode;
	}

	public ProcedureItem(String procedureCode, String procedureDesc, String cptCode) {
		super();
		this.procedureCode = procedureCode;
		this.procedureDesc = procedureDesc;
		this.cptCode = cptCode;
	}

	
	
	
	
}
