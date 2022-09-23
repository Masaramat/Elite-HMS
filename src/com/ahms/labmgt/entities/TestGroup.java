package com.ahms.labmgt.entities;

import java.io.Serializable;

public class TestGroup implements Serializable{
	String group_id, group_code, group_name;	
	
	public TestGroup() {}

	public TestGroup(String group_id, String group_code, String group_name) {
		super();
		this.group_id = group_id;
		this.group_code = group_code;
		this.group_name = group_name;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	
	

}
