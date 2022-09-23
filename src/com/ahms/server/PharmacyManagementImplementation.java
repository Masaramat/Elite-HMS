package com.ahms.server;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.ahms.api.PharmacyManagementInterface;
import com.ahms.clinicmgt.entities.PrescriptionOrderItem;
import com.ahms.hmgt.entities.Vendor;
import com.ahms.pharmarcymgt.entities.Medicine;
import com.ahms.pharmarcymgt.entities.MedicinePurchaseItem;
import com.ahms.pharmarcymgt.entities.MedicineStockItem;
import com.ahms.pharmarcymgt.entities.MedicineWithdrawalItem;
import com.ahms.pharmarcymgt.entities.PrescriptionInvoice;



public class PharmacyManagementImplementation implements PharmacyManagementInterface {
	
	private Connection conn;

	public PharmacyManagementImplementation() {	}

	@Override
	public Medicine saveNewMedicine(Medicine med) throws RemoteException {
		int countvalue = 0;
		String medicineCode = "";
		
		try{
			conn =  DBConnection.getConnection();
			
			//query to get the latest case
			Statement s2 = conn.createStatement();
			ResultSet rss = s2.executeQuery("select value from counter where id= "+ 16);
			while(rss.next()){
				countvalue =  rss.getInt("value");
				medicineCode = "DR"+leftPad("" + countvalue, 4); 				
			
			String query = "INSERT into pharmacy_medicines(medicine_code, medicine_description, medicine_form, purchase_unit, "
					+ " counting_state) VALUES(?,?,?,?,?)";
			PreparedStatement pms = conn.prepareStatement(query);
			
			pms.setString(1, medicineCode);			
			pms.setString(2, med.getMedicineDescription());
			pms.setString(3, med.getMedicineForm());
			pms.setString(4, med.getPurchaseUnit());			
			pms.setString(5, med.getCountingState());
			
			pms.execute();
			med.setMedicineCode(medicineCode);
									
			}
			
			String query2 = "update counter set value = ? where id = ?";
			PreparedStatement pms2 = conn.prepareStatement(query2);
			pms2.setInt(1, countvalue+1);
			pms2.setInt(2, 16);
			pms2.executeUpdate();
			
			conn.close();
			
		}catch(Exception ex){ ex.printStackTrace(); }
		
		return med;
	}

	@Override
	public ArrayList<Medicine> getAllMedicines() throws RemoteException {
		ArrayList<Medicine> medlist = new ArrayList<Medicine>();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM pharmacy_medicines";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				Medicine meds = new Medicine();
				meds.setMedicineCode(rs.getString(2));
				meds.setDrugName("");
				meds.setMedicineDescription(rs.getString(3));
				meds.setMedicineForm(rs.getString(4));
				meds.setPurchaseUnit(rs.getString(5));				
				meds.setCountingState(rs.getString(6));
				
				medlist.add(meds);
				
			}
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		
		return medlist;
	}

	@Override
	public String leftPad(String str, int xy) throws RemoteException {
		String emptystr = "";
		int diff = xy - str.length();
		
		for(int i=0; i<diff; i++){
		emptystr = emptystr.concat("0");
		}
		
		return emptystr+str;
	}

	
	@Override	
	public ArrayList<Vendor> getAllVendors() throws RemoteException {
		ArrayList<Vendor> vendorlist = new ArrayList<Vendor>();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM vendors";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				Vendor vendor = new Vendor();
				vendor.setVendorName(rs.getString(3));
				vendor.setVendorCode(rs.getString(2));
				vendor.setCompany(rs.getString(4));
				
				vendorlist.add(vendor);
			}
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		return vendorlist;
	}

	
	@Override
	public void saveMedicinePurchase(MedicinePurchaseItem purchase_item) throws RemoteException {
		try{
				conn = DBConnection.getConnection();
				String query = "INSERT INTO pharmacy_medicine_purchase(medicine_code, quantity, price, date, vendor_code, purchase_staff)VALUES(?,?,?, CURRENT_DATE, ?, ?)"; 
				PreparedStatement ps = conn.prepareStatement(query);
				
					ps.setString(1, purchase_item.getMedicineCode());
					ps.setInt(2, purchase_item.getQty());
					ps.setDouble(3, purchase_item.getUnit_price());
					ps.setString(4, purchase_item.getVendor());
					ps.setString(5, purchase_item.getStaff());
														
					ps.execute();				
			
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		
	}

	@Override
	public ArrayList<PrescriptionInvoice> getPrescriptionOrders(String status) throws RemoteException {
		ArrayList<PrescriptionInvoice> list = new ArrayList<PrescriptionInvoice>();
		try{		
			
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM prescription_summary_view "
					+ "where order_state ='"+status+"' ";				
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				PrescriptionInvoice po = new PrescriptionInvoice();
				po.setInvoiceNo(rs.getString(1));
				po.setPatientSurname(rs.getString(2));
				po.setPatientOthernames(rs.getString(3));				
				po.setNoOfItems(rs.getInt(6));
				po.setInvoiceStatus(rs.getString(7));
				
				list.add(po);
				
			}
		}catch(Exception ex){ ex.printStackTrace();}
		
		return list;
	}

	
	@Override
	public void updatePrescriptionStatus(String invoice_no, ArrayList<Integer> intlist, Double amount) throws RemoteException {
		String status = "dispensed";
		
		try{
			conn = DBConnection.getConnection();
			String query = "UPDATE prescription_order_items "
					+ "SET order_state = ? "
					+ "WHERE invoice_no = ? AND sno = ?";
			for(int i=0; i<intlist.size(); i++){
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setString(1, status);
				ps.setString(2, invoice_no);
				ps.setInt(3, intlist.get(i));
				
				ps.executeUpdate();
				
			}
			
			String query2 = "INSERT INTO pharmacy_sales(invoice_no, amount, date, time) VALUES(?,?, curdate(), curtime())";
			PreparedStatement pst = conn.prepareStatement(query2);
			pst.setString(1, invoice_no);
			pst.setDouble(2, amount);
			
			pst.execute();		
			
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		
	}

	
	@Override
	public ArrayList<PrescriptionOrderItem> getDispensedPrescriptions(String invoice_no) throws RemoteException {
		ArrayList<PrescriptionOrderItem> list = new ArrayList<PrescriptionOrderItem>();
		try{
			conn = DBConnection.getConnection();
			String query = "select * from prescription_order_items where invoice_no = '" +invoice_no+"' and order_state ='dispensed' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				PrescriptionOrderItem pres = new PrescriptionOrderItem();
				pres.setSerialNo(rs.getInt(1));
				pres.setInvoiceNumber(rs.getString(2));
				pres.setMedicineCode(rs.getString(3));
				pres.setMedicineName(rs.getString(4));
				pres.setDose(rs.getInt(5));
				pres.setFrequency(rs.getInt(6));
				pres.setDuration(rs.getInt(7));
				pres.setDispenseQauntity(rs.getInt(8));
				pres.setOrderState(rs.getString(9));
				
				list.add(pres);
				
			}
			
			
		}catch(Exception ex){ex.printStackTrace();}
		
		return list;
	}

	
	@Override
	public ArrayList<PrescriptionOrderItem> getPrescriptions(String invoice_no) throws RemoteException {
		ArrayList<PrescriptionOrderItem> list = new ArrayList<PrescriptionOrderItem>();
		try{
			conn = DBConnection.getConnection();
			String query = "select * from prescription_order_items where invoice_no = '" +invoice_no+"' and order_state ='pending' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				PrescriptionOrderItem pres = new PrescriptionOrderItem();
				pres.setSerialNo(rs.getInt(1));
				pres.setInvoiceNumber(rs.getString(2));
				pres.setMedicineCode(rs.getString(3));
				pres.setMedicineName(rs.getString(4));
				pres.setDose(rs.getInt(5));
				pres.setFrequency(rs.getInt(6));
				pres.setDuration(rs.getInt(7));
				pres.setDispenseQauntity(rs.getInt(8));
				pres.setOrderState(rs.getString(9));
				
				list.add(pres);
				
			}			
			conn.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return list;
	}

	@Override
	public void saveMedicineWithdrawal(MedicineWithdrawalItem item) throws RemoteException {
		try{
			
				conn = DBConnection.getConnection();
				String query = "INSERT INTO pharmarcy_medicine_withdrawal(medicine_code, quantity, date)VALUES(?,?, CURRENT_DATE)"; 
				PreparedStatement ps = conn.prepareStatement(query);
				
					ps.setString(1, item.getMedicineCode());
					ps.setInt(2, item.getQuantity());
					
					ps.execute();				
			
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		
	}

	@Override
	public void updateMedicne(Medicine med) throws RemoteException {

		try{
			conn = DBConnection.getConnection();
			String query = "UPDATE pharmacy_medicines "
					+ "SET medicine_description = ?, medicine_form = ?, counting_state = ? "
					+ "WHERE medicine_code = ? ";
			
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setString(1, med.getMedicineDescription());
				ps.setString(2, med.getMedicineForm());
				ps.setString(3, med.getCountingState());				
				ps.setString(4, med.getMedicineCode());				
				
				ps.executeUpdate();				
			
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		
	}

	
	@Override
	public ArrayList<MedicineStockItem> getCurrentStockItems() throws RemoteException {
		ArrayList<MedicineStockItem> medlist = new ArrayList<MedicineStockItem>();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM pharmacy_stock_view";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				MedicineStockItem meds = new MedicineStockItem();
				meds.setMedicineCode(rs.getString(1));
				meds.setDrugName(rs.getString(2));
				meds.setAvailableQty(rs.getInt(9));	
				meds.setMedicineForm(rs.getString(3));
				
				medlist.add(meds);
				
			}
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		
		return medlist;
	}

	@Override
	public ArrayList<MedicineStockItem> getCurrentStockItems(String search) throws RemoteException {
		ArrayList<MedicineStockItem> medlist = new ArrayList<MedicineStockItem>();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM pharmacy_stock_view where medicine_description like '%" +search+"%' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				MedicineStockItem meds = new MedicineStockItem();
				meds.setMedicineCode(rs.getString(1));
				meds.setDrugName(rs.getString(2));
				meds.setAvailableQty(rs.getInt(9));	
				meds.setMedicineForm(rs.getString(3));		
				
				medlist.add(meds);
				
			}
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		
		return medlist;
	}

	
	
	
}
