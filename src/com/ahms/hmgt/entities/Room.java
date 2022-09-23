package com.ahms.hmgt.entities;

import java.io.Serializable;

public class Room implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String roomId, roomName, roomDescription, wardId, wardName;

	public Room() {
		// TODO Auto-generated constructor stub
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getWardId() {
		return wardId;
	}

	public void setWardId(String wardId) {
		this.wardId = wardId;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public String getWardName() {
		return wardName;
	}

	public void setWardName(String wardName) {
		this.wardName = wardName;
	}

	public Room(String roomId, String roomName, String roomDescription, String wardId, String wardName) {
		super();
		this.roomId = roomId;
		this.roomName = roomName;
		this.roomDescription = roomDescription;
		this.wardId = wardId;
		this.wardName = wardName;
	}

	
	

}
