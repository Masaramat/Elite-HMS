package com.ahms.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import com.ahms.hmgt.entities.Appointment;
import com.ahms.hmgt.entities.Bed;
import com.ahms.hmgt.entities.Deposit;
import com.ahms.hmgt.entities.Doctor;
import com.ahms.hmgt.entities.FamilyCard;
import com.ahms.hmgt.entities.HospitalCharge;
import com.ahms.hmgt.entities.IPInvoice;
import com.ahms.hmgt.entities.InventoryDepartment;
import com.ahms.hmgt.entities.LGA;
import com.ahms.hmgt.entities.OPInvoice;
import com.ahms.hmgt.entities.PatientBiodata;
import com.ahms.hmgt.entities.Product;
import com.ahms.hmgt.entities.Room;
import com.ahms.hmgt.entities.State;
import com.ahms.hmgt.entities.UserCard;
import com.ahms.hmgt.entities.Ward;


public interface HospitalManagementInterface extends Remote{
	
	//login 
	public UserCard getUserCard(UserCard uc) throws RemoteException;
	
	//change password 
	public void changePassword(String new_password, UserCard user) throws RemoteException;
	
	//hospital administration
	public ArrayList<InventoryDepartment> getInventoryDepartments() throws RemoteException;
	
	public Product saveProduct(Product p) throws RemoteException;
	
	public void createUser(UserCard ucd) throws RemoteException;
	
	public void saveDoctor(Doctor doctor) throws RemoteException;
	
	public void updateDoctor(Doctor doctor) throws RemoteException;
	
	public ArrayList<Doctor> getAllDoctors(String status) throws RemoteException;
	
	public ArrayList<UserCard> getAllUsers() throws RemoteException;
	
	public void updateUser(UserCard ucd) throws RemoteException;
	
	//methods belong to the patient registration class
	public ArrayList<State> getAllStates() throws RemoteException;
	
	public ArrayList<LGA> getLGAs(String state_id) throws RemoteException;
	
	public PatientBiodata saveNewRegistration(PatientBiodata pat) throws RemoteException;
	
	public void updatePatientBiodata(PatientBiodata pat) throws RemoteException;
	
	public ArrayList<PatientBiodata> getPatient(String searchText, String searchBy) throws RemoteException;
	
	public ArrayList<PatientBiodata> getPatient(String family_no) throws RemoteException;
	
	public ArrayList<PatientBiodata> getPatient(Date date1, Date date2) throws RemoteException;
	
	public PatientBiodata getPatientBiodata(String file_no) throws RemoteException;

	
	//these metthods belong to the OPD Appointtment class
	public String getPatientName(String hosp_no) throws RemoteException;
	
	public ArrayList<Doctor> getAllDoctors() throws RemoteException;

	public void saveAppointment(String hosp_no, String doc_id, java.sql.Date date, String time, String status) throws RemoteException;
	
	public ArrayList<Appointment> searchAppointment(java.sql.Date date1, java.sql.Date date2) throws RemoteException;
	
	public ArrayList<Appointment> searchAppointment(String search_criterion, String search_text) throws RemoteException;
	
	
	//these methods are for the ward management class
	public String createWard(String ward_desc) throws RemoteException;
	
	public void updateWard(String ward_id, String ward_desc) throws RemoteException;
	
	public ArrayList<Ward> getAllWards() throws RemoteException;
	
	public String createRoom(String ward_id, String room_name, String room_desc) throws RemoteException;
	
	public void updateRoom(String room_id, String ward_id, String room_name, String room_desc) throws RemoteException;
	
	public ArrayList<Room> getAllRooms(String ward_id) throws RemoteException;
	
	public ArrayList<Room> getAllRooms() throws RemoteException;
	
	public String createBed(String room_id, Bed bed) throws RemoteException;
	
	public void updateBed(Bed bed) throws RemoteException;
	
	public ArrayList<Bed> getAllBeds() throws RemoteException;
	
	
	// methods for opd invoice
	public ArrayList<OPInvoice> getOPDinvoice(String status, Date date1, Date date2) throws RemoteException;
	
	public ArrayList<OPInvoice> getOPDinvoice(String status) throws RemoteException;
	
	public OPInvoice getOneOPDinvoice(String invoice_no) throws RemoteException;
	
	public ArrayList<IPInvoice> getIPDInvoice(String status, Date date1, Date date2) throws RemoteException;
	
	public ArrayList<IPInvoice> getIPDInvoice(String status) throws RemoteException;
	
	public IPInvoice getOneIPDInvoice(String invoice_no) throws RemoteException;
	
	
	// method to save deposit
	public void saveDeposit(String inovice_no, String paymode, String bank, double amount) throws RemoteException;
	
	public void getDeposits(String invoice_no) throws RemoteException;
	
	//method to save hospital charges	
	public void saveHospitalCharge(String chargeDesc, double amount) throws RemoteException;	
	
	public ArrayList<HospitalCharge> getAllCharges() throws RemoteException;
	
	public void updateHospitalCharge(String chargeDesc, double amount) throws RemoteException;

	
	//method to create family card
	public FamilyCard createFamilyCard(FamilyCard fam) throws RemoteException;
	
	public ArrayList<FamilyCard> getAllFamilyCards() throws RemoteException;
	
	public ArrayList<FamilyCard> getFamilyCards(String search_criterion, String search_text) throws RemoteException;
	
	public void updateFamilyCard(FamilyCard fam) throws RemoteException;
	
	//method to load deposit
	public ArrayList<Deposit> getAllDeposits(String invoice_no) throws RemoteException;
	
	public String databaseBackup(String filepath) throws RemoteException;
	
	
	
	
	
	
}
