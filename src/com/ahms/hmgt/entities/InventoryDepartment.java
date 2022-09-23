package com.ahms.hmgt.entities;

import java.io.Serializable;

public class InventoryDepartment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String deptStoreCode, deptStoreName;

	public InventoryDepartment() {	}
	
	public InventoryDepartment(String deptStoreCode, String deptStoreName) {
		super();
		this.deptStoreCode = deptStoreCode;
		this.deptStoreName = deptStoreName;
	}

	public String getDeptStoreCode() {
		return deptStoreCode;
	}

	public void setDeptStoreCode(String deptStoreCode) {
		this.deptStoreCode = deptStoreCode;
	}

	public String getDeptStoreName() {
		return deptStoreName;
	}

	public void setDeptStoreName(String deptStoreName) {
		this.deptStoreName = deptStoreName;
	}
	
	
}
