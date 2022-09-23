package com.ahms.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.labmgt.entities.LabRequest;
import com.ahms.labmgt.entities.ResultLine;
import com.ahms.labmgt.entities.TestGroup;
import com.ahms.labmgt.entities.TestItem;
import com.ahms.labmgt.entities.TestOrderItem;
import com.ahms.labmgt.entities.TestParameter;


public interface LabManagementInterface extends Remote {
	//testgroup entry
	public String createTestGroup(String groupname) throws RemoteException;
	
	public void saveTestGroup(String groupid, String groupcode, String groupname ) throws RemoteException;
	
	public ArrayList<TestGroup> getTestGroups() throws RemoteException;
	
	public String leftPad(String str, int xy) throws RemoteException;
	
	//test name entry
	public String createTest(TestItem test) throws RemoteException;
	
	public void saveTest(TestItem test) throws RemoteException;
	
	public ArrayList<TestItem> getTestItems() throws RemoteException;
	
	//test configuration
	public ArrayList<TestItem> getTestItems(String grpId) throws RemoteException;
	
	public String createTestParameter(String test_id)throws RemoteException;
	
	public void saveParameters(String testPar, String unit, String range, String param) throws RemoteException;
	
	public ArrayList<TestParameter> getAllParameters(String testId) throws RemoteException;
	
	//test order entry
	public PatientVisit getPatientVisitView(String invoice_no) throws RemoteException;
	
	public void saveTestOrder(String invoice, String test_id, double price) throws RemoteException;

	//test confirmation
	public ArrayList<TestOrderItem> getOrderItems(String invoice_no) throws RemoteException;
	
	public ArrayList<TestOrderItem> getOrderItems(String invoice_no, String status) throws RemoteException;
	
	public void updateOrderStatus(String invoice_no, ArrayList<String> list) throws RemoteException;
	
	public ArrayList<String> getParameters(String test_id) throws RemoteException;

	public ArrayList<TestOrderItem> getConfirmedTest(String invoice_no) throws RemoteException;
	
	//result posting
	public ArrayList<ResultLine> getTestResult(String inv, String test) throws RemoteException;
	
	public void updateResults(ArrayList<String> resultlist, String test_id, String invoice_no) throws RemoteException;
	
	//result delivery
	public ArrayList<TestOrderItem> getPreparedResult(String invoice_no) throws RemoteException;
	
	public void updateToDelivery(String invoice_no, ArrayList<String> tests_list) throws RemoteException;
	
	public ArrayList<TestOrderItem> getDeliveredResult(String invoice_no) throws RemoteException;
	
	public ArrayList<ResultLine> getInvoiceResult(String invoice_no) throws RemoteException;
	
	//function to load all pending lab requests
	public ArrayList<LabRequest> getPendingRequest() throws RemoteException;
	
	public ArrayList<LabRequest> getConfirmedRequests() throws RemoteException;
	
	public ArrayList<LabRequest> getDeliveredRequest() throws RemoteException;
	
	public ArrayList<LabRequest> getLabRequestSummary(String status) throws RemoteException;
	
	public ArrayList<LabRequest> getLabRequestSummary(String search_by, String search_text) throws RemoteException;
	
	public ArrayList<LabRequest> getLabRequestSummary(java.sql.Date from_date, java.sql.Date to_date) throws RemoteException;
	
	
	
	
	
	
	
}
