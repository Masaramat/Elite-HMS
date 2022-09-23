package com.ahms.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import com.ahms.clinicmgt.entities.PrescriptionOrderItem;
import com.ahms.hmgt.entities.Vendor;
import com.ahms.pharmarcymgt.entities.Medicine;
import com.ahms.pharmarcymgt.entities.MedicinePurchaseItem;
import com.ahms.pharmarcymgt.entities.MedicineStockItem;
import com.ahms.pharmarcymgt.entities.MedicineWithdrawalItem;
import com.ahms.pharmarcymgt.entities.PrescriptionInvoice;


public interface PharmacyManagementInterface extends Remote {
	
	public Medicine saveNewMedicine(Medicine med) throws RemoteException;
	
	public ArrayList<Medicine> getAllMedicines() throws RemoteException;
	
	public String leftPad(String str, int xy) throws RemoteException;
	
	public ArrayList<Vendor> getAllVendors() throws RemoteException;
	
	public void saveMedicinePurchase(MedicinePurchaseItem purchase_item) throws RemoteException;
	
	public ArrayList<PrescriptionInvoice> getPrescriptionOrders(String status) throws RemoteException;
	
	public void updatePrescriptionStatus(String invoice_no, ArrayList<Integer> intlist, Double amount) throws RemoteException;
	
	public ArrayList<PrescriptionOrderItem> getDispensedPrescriptions(String invoice_no) throws RemoteException;
	
	public ArrayList<PrescriptionOrderItem> getPrescriptions(String invoice_no) throws RemoteException;
	
	public void saveMedicineWithdrawal(MedicineWithdrawalItem item) throws RemoteException;
	
	public void updateMedicne(Medicine med) throws RemoteException;
	
	public ArrayList<MedicineStockItem> getCurrentStockItems() throws RemoteException;
	
	public ArrayList<MedicineStockItem> getCurrentStockItems(String search) throws RemoteException;
	

}
