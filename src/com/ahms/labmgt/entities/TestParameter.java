package com.ahms.labmgt.entities;

import java.io.Serializable;

public class TestParameter implements Serializable{
	
	String paramCode, paramDesc, unit, normalRange, testId;

	public TestParameter() {	}

	public TestParameter(String paramCode, String paramDesc, String unit, String normalRange, String testId) {
		super();
		this.paramCode = paramCode;
		this.paramDesc = paramDesc;
		this.unit = unit;
		this.normalRange = normalRange;
		this.testId = testId;
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getNormalRange() {
		return normalRange;
	}

	public void setNormalRange(String normalRange) {
		this.normalRange = normalRange;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	
	
	
}
