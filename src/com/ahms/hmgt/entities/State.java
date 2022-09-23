package com.ahms.hmgt.entities;

import java.io.Serializable;

public class State implements Serializable {

	String id, name, capital;
	
	private static final long serialVersionUID = 1L;

	public State() {
		// TODO Auto-generated constructor stub
	}

	public State(String id, String name, String capital) {
		super();
		this.id = id;
		this.name = name;
		this.capital = capital;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
