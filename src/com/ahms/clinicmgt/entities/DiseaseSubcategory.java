package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class DiseaseSubcategory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String subgroupId, subgroupCode, subgroupDesc, groupId;

	public DiseaseSubcategory() {
		// TODO Auto-generated constructor stub
	}

	public DiseaseSubcategory(String subgroupId, String subgroupCode, String subgroupDesc, String groupId) {
		super();
		this.subgroupId = subgroupId;
		this.subgroupCode = subgroupCode;
		this.subgroupDesc = subgroupDesc;
		this.groupId = groupId;
	}

	public String getSubgroupId() {
		return subgroupId;
	}

	public void setSubgroupId(String subgroupId) {
		this.subgroupId = subgroupId;
	}

	public String getSubgroupCode() {
		return subgroupCode;
	}

	public void setSubgroupCode(String subgroupCode) {
		this.subgroupCode = subgroupCode;
	}

	public String getSubgroupDesc() {
		return subgroupDesc;
	}

	public void setSubgroupDesc(String subgroupDesc) {
		this.subgroupDesc = subgroupDesc;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	
	

}
