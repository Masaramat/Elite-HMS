package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class VitalSignEntry implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String invoiceNumber, date, time;
	int height, weight, bpSystole, bpDiastole, pulseRate, respirationRate, serialNo;
	double bmi, temperature;

	public VitalSignEntry() {
		// TODO Auto-generated constructor stub
	}
	

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getBpSystole() {
		return bpSystole;
	}

	public void setBpSystole(int bpSystole) {
		this.bpSystole = bpSystole;
	}

	public int getBpDiastole() {
		return bpDiastole;
	}

	public void setBpDiastole(int bpDiastole) {
		this.bpDiastole = bpDiastole;
	}

	public int getPulseRate() {
		return pulseRate;
	}

	public void setPulseRate(int pulseRate) {
		this.pulseRate = pulseRate;
	}

	public int getRespirationRate() {
		return respirationRate;
	}

	public void setRespirationRate(int respirationRate) {
		this.respirationRate = respirationRate;
	}

	public double getBmi() {
		return bmi;
	}

	public void setBmi(double bmi) {
		this.bmi = bmi;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	
	
}
