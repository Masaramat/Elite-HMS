package com.ahms.hmgt.entities;

import java.io.Serializable;

public class Bed implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String bedNo, bedDetails, wardName, status, roomId;
	double bedCharge;

	public Bed() {	}

	public Bed(String bedNo, String bedDetails,  String wardName, String status, double bedCharge) {
		super();
		this.bedNo = bedNo;
		this.bedDetails = bedDetails;		
		this.wardName = wardName;		
		this.bedCharge = bedCharge;
		this.status = status;
	}

	public String getStatus(){
		return this.status;
	}
	
	public void setStatus(String status){
		this.status=status;
	}
	public String getBedNo() {
		return bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	public String getBedDetails() {
		return bedDetails;
	}

	public void setBedDetails(String bedDetails) {
		this.bedDetails = bedDetails;
	}

	
	public String getWardName() {
		return wardName;
	}

	public void setWardName(String wardName) {
		this.wardName = wardName;
	}

	
	public double getBedCharge() {
		return bedCharge;
	}

	public void setBedCharge(double bedCharge) {
		this.bedCharge = bedCharge;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	

}
