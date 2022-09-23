package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class DiseaseCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String groupId, groupCode, groupDesc;  
	
	public DiseaseCategory() {}

	public DiseaseCategory(String groupId, String groupCode, String groupDesc) {
		super();
		this.groupId = groupId;
		this.groupCode = groupCode;
		this.groupDesc = groupDesc;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	
	

}
