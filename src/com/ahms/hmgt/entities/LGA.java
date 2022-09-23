package com.ahms.hmgt.entities;

import java.io.Serializable;

public class LGA implements Serializable{

	private static final long serialVersionUID = 1L;
	String sno, stateId, name;
	
	
	public LGA(String sno, String stateId, String name) {
		super();
		this.sno = sno;
		this.stateId = stateId;
		this.name = name;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public LGA() {
		super();
	}
	
	

}
