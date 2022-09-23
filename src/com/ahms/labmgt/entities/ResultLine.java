package com.ahms.labmgt.entities;

import java.io.Serializable;

public class ResultLine implements Serializable{
	private String invoice_no, test_id, test_name, parameter_code, test_parameter, result, unit, normal_range;
	
	public String getTest_name() {
		return test_name;
	}

	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}

	public ResultLine() {
		// TODO Auto-generated constructor stub
	}

	public ResultLine(String invoice_no, String test_id, String parameter_code, String test_parameter, String result,
			String unit, String normal_range) {
		super();
		this.invoice_no = invoice_no;
		this.test_id = test_id;
		this.parameter_code = parameter_code;
		this.test_parameter = test_parameter;
		this.result = result;
		this.unit = unit;
		this.normal_range = normal_range;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getTest_id() {
		return test_id;
	}

	public void setTest_id(String test_id) {
		this.test_id = test_id;
	}

	public String getParameter_code() {
		return parameter_code;
	}

	public void setParameter_code(String parameter_code) {
		this.parameter_code = parameter_code;
	}

	public String getTest_parameter() {
		return test_parameter;
	}

	public void setTest_parameter(String test_parameter) {
		this.test_parameter = test_parameter;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getNormal_range() {
		return normal_range;
	}

	public void setNormal_range(String normal_range) {
		this.normal_range = normal_range;
	}
	
	
	

}
