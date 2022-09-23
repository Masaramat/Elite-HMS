package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class DiseaseCondition implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String conditionId, conditionCode, conditionDesc, subgroupId;

	public DiseaseCondition() {
		// TODO Auto-generated constructor stub
	}

	public DiseaseCondition(String conditionId, String conditionCode, String conditionDesc, String subgroupId) {
		super();
		this.conditionId = conditionId;
		this.conditionCode = conditionCode;
		this.conditionDesc = conditionDesc;
		this.subgroupId = subgroupId;
	}

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public String getConditionCode() {
		return conditionCode;
	}

	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}

	public String getConditionDesc() {
		return conditionDesc;
	}

	public void setConditionDesc(String conditionDesc) {
		this.conditionDesc = conditionDesc;
	}

	public String getSubgroupId() {
		return subgroupId;
	}

	public void setSubgroupId(String subgroupId) {
		this.subgroupId = subgroupId;
	}
	
	

}
