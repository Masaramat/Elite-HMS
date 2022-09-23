package com.ahms.hmgt.entities;

import java.io.Serializable;

public class UserCard implements Serializable {
	
	String fullNames, username, password, role, status;

	public UserCard() {
		// TODO Auto-generated constructor stub
	}

	public String getFullNames() {
		return fullNames;
	}

	public void setFullNames(String fullNames) {
		this.fullNames = fullNames;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserCard(String fullNames, String username, String password, String role, String status) {
		super();
		this.fullNames = fullNames;
		this.username = username;
		this.password = password;
		this.role = role;
		this.status = status;
	}
	
	

}
