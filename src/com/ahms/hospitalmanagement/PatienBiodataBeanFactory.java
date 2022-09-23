package com.ahms.hospitalmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.hmgt.entities.IPInvoice;
import com.ahms.hmgt.entities.OPInvoice;
import com.ahms.hmgt.entities.PatientBiodata;



public class PatienBiodataBeanFactory {
	final HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();

	public PatienBiodataBeanFactory() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<PatientBiodata> getPatientBiodata(String hospital_no){
		ArrayList<PatientBiodata> pblist = new ArrayList<PatientBiodata>();	
		try {
			PatientBiodata pbd = hm_interface.getPatientBiodata(hospital_no);					
			pblist.add(pbd);
			
		} catch (RemoteException e) {	e.printStackTrace();	}		
				
		return pblist;
	}
		
	public ArrayList<OPInvoice> getOPDInvoice(String invoice_no){
		ArrayList<OPInvoice> pblist = new ArrayList<OPInvoice>();	
		try {
			OPInvoice opiv = hm_interface.getOneOPDinvoice(invoice_no);
			pblist.add(opiv);				
			
		} catch (Exception e) {	e.printStackTrace();	}			
		
		return pblist;
	}
	
	public ArrayList<IPInvoice> getIPDInvoice(String invoice_no){
		ArrayList<IPInvoice> pblist = new ArrayList<IPInvoice>();	
		try {
			IPInvoice opiv = hm_interface.getOneIPDInvoice(invoice_no);
			pblist.add(opiv);	
			
		} catch(Exception e) {		e.printStackTrace();		}		
				
		return pblist;
	}
	
	
}
