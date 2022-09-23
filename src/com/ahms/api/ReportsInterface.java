package com.ahms.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import com.ahms.clinicmgt.entities.BookedProcedure;
import com.ahms.clinicmgt.entities.InpatientAdmission;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.hmgt.entities.FamilyCard;
import com.ahms.hmgt.entities.GeneralDeposit;
import com.ahms.hmgt.entities.PatientBiodata;
import com.ahms.labmgt.entities.TestOrderItem;
import com.ahms.pharmarcymgt.entities.MedicinePurchaseItem;
import com.ahms.pharmarcymgt.entities.MedicineWithdrawalItem;



public interface ReportsInterface extends Remote{
	
	public ArrayList<TestOrderItem> getLaboratoryReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate) throws RemoteException;
	
	public ArrayList<BookedProcedure> getProcedureReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate) throws RemoteException;
	
	public ArrayList<PatientVisit> getOutpatientVisitReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate) throws RemoteException;
	
	public ArrayList<InpatientAdmission> getInpatientAdmissionReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate) throws RemoteException;
	
	public ArrayList<MedicinePurchaseItem> getPharmacyPurchaseReport(Date fromDate, Date todateDate) throws RemoteException;
	
	public ArrayList<MedicineWithdrawalItem> getPharmacyWithdrawalReport(Date fromDate, Date todateDate) throws RemoteException;
	
	public ArrayList<FamilyCard> getFamilyRegistrationReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate) throws RemoteException;
	
	public ArrayList<GeneralDeposit> getGeneralDepositReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate) throws RemoteException;
	
	public ArrayList<PatientBiodata> getPatientRegistrationReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate) throws RemoteException;
}
