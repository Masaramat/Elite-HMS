package com.ahms.hmgt.entities;

import java.io.Serializable;

public class Ward implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String wardId, wardName;
	
	public Ward() {
		// TODO Auto-generated constructor stub
	}

	public String getWardId() {
		return wardId;
	}

	public void setWardId(String wardId) {
		this.wardId = wardId;
	}

	public String getWardName() {
		return wardName;
	}

	public void setWardName(String wardName) {
		this.wardName = wardName;
	}

	public Ward(String wardId, String wardName) {
		super();
		this.wardId = wardId;
		this.wardName = wardName;
	}
	
	

}
