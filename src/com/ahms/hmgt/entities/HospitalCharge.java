package com.ahms.hmgt.entities;

import java.io.Serializable;

public class HospitalCharge implements Serializable{
	int sno;
	String chargeName;
	double amount;

	public HospitalCharge() {
		// TODO Auto-generated constructor stub
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public HospitalCharge(int sno, String chargeName, double amount) {
		super();
		this.sno = sno;
		this.chargeName = chargeName;
		this.amount = amount;
	}

	
	
	
	
}
