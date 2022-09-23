package com.ahms.clinicmgt.entities;

import java.io.Serializable;

public class SystemReviewNote implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String invoiceNo, system, reviewVotes;

	public SystemReviewNote() {
		// TODO Auto-generated constructor stub
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getReviewVotes() {
		return reviewVotes;
	}

	public void setReviewVotes(String reviewVotes) {
		this.reviewVotes = reviewVotes;
	}

	public SystemReviewNote(String invoiceNo, String system, String reviewVotes) {
		super();
		this.invoiceNo = invoiceNo;
		this.system = system;
		this.reviewVotes = reviewVotes;
	}
	
	

}
