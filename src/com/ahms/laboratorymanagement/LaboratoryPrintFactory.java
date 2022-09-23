package com.ahms.laboratorymanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import com.ahms.api.LabManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;

import com.ahms.labmgt.entities.ResultLine;

public class LaboratoryPrintFactory {
	
	LabManagementInterface lmi = InterfaceGenerator.getLabManagementInterface();

	public LaboratoryPrintFactory() {	}
	
	
	public ArrayList<ResultLine> getInvoiceLabResults(String invoice_no){
		ArrayList<ResultLine> pbd = null;
		try {
			 pbd = lmi.getInvoiceResult(invoice_no);					
						
		} catch (RemoteException e) {	e.printStackTrace();	}		
				
		return pbd;
	}

}
