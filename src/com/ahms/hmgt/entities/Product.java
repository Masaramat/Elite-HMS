package com.ahms.hmgt.entities;

public class Product {
	String productCode, productName, productDescription;

	public Product(String productCode, String productName, String productDescription) {
		super();
		this.productCode = productCode;
		this.productName = productName;
		this.productDescription = productDescription;
	}

	public Product() {
		// TODO Auto-generated constructor stub
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	

}
