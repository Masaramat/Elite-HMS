package com.ahms.labmgt.entities;

import java.io.Serializable;

public class TestItem implements Serializable{

	String testId, testCode, testName, testType, testTitle, testGroupCode;
	double price, discount, vat;
	
	public TestItem() {	}

	public TestItem(String testId, String testCode, String testName, String testType, String testTitle,
			String testGroupCode, double price, double discount, double vat) {
		super();
		this.testId = testId;
		this.testCode = testCode;
		this.testName = testName;
		this.testType = testType;
		this.testTitle = testTitle;
		this.testGroupCode = testGroupCode;
		this.price = price;
		this.discount = discount;
		this.vat = vat;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getTestCode() {
		return testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getTestTitle() {
		return testTitle;
	}

	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}

	public String getTestGroupCode() {
		return testGroupCode;
	}

	public void setTestGroupCode(String testGroupCode) {
		this.testGroupCode = testGroupCode;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}
	
	
	
	

}
