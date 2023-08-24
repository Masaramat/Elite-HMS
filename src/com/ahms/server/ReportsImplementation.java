package com.ahms.server;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.ahms.api.ReportsInterface;
import com.ahms.clinicmgt.entities.BookedProcedure;
import com.ahms.clinicmgt.entities.InpatientAdmission;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.hmgt.entities.FamilyCard;
import com.ahms.hmgt.entities.GeneralDeposit;
import com.ahms.hmgt.entities.PatientBiodata;
import com.ahms.labmgt.entities.TestOrderItem;
import com.ahms.pharmarcymgt.entities.MedicinePurchaseItem;
import com.ahms.pharmarcymgt.entities.MedicineWithdrawalItem;

public class ReportsImplementation implements ReportsInterface {

	
	public ArrayList<TestOrderItem> getLaboratoryReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate){
		ArrayList<TestOrderItem> list = new ArrayList<TestOrderItem>();
		try {
			
			Connection connection = DBConnection.getConnection();
			String query = "";
			
				if(column_list.size()==0) {
				query = "SELECT * FROM test_order_view "
						+ "WHERE test_order_date between '"+fromDate+"' AND '"+todateDate+"' ";
				}else if (column_list.size() == 1) {
					query = "SELECT * FROM test_order_view "
							+ "WHERE (test_order_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND "+column_list.get(0) +" = '"+data_list.get(0)+"' ";
				}else if (column_list.size() == 2) {
					query = "SELECT * FROM test_order_view "
							+ "WHERE (test_order_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') ";
				}else if (column_list.size() == 3) {
					query = "SELECT * FROM test_order_view "
							+ "WHERE (test_order_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') "
									+ "AND ("+column_list.get(2) +" = '"+data_list.get(2)+"') ";
				}
			
				
			Statement statement = connection.createStatement();
			ResultSet rs= statement.executeQuery(query);
			
			while(rs.next()) {
				Locale locale = new Locale("en", "NG");
				NumberFormat nf = NumberFormat.getCurrencyInstance(locale);	 
				
				TestOrderItem item = new TestOrderItem();
				
				item.setInvoice_no(rs.getString(1));
				item.setTest_name(rs.getString(11));
				item.setStatus(rs.getString(12));
				item.setDate(rs.getDate(13).toString());
				item.setPrice(rs.getDouble(14));
				
				list.add(item);
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return list;
	}
	
	public ArrayList<BookedProcedure> getProcedureReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate){
		ArrayList<BookedProcedure> list = new ArrayList<BookedProcedure>();
		try {
			
			Connection connection = DBConnection.getConnection();
			String query = "";
			
				if(column_list.size()==0) {
				query = "SELECT * FROM procedure_booking "
						+ "WHERE date between '"+fromDate+"' AND '"+todateDate+"' ";
				}else if (column_list.size() == 1) {
					query = "SELECT * FROM procedure_booking "
							+ "WHERE (date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND "+column_list.get(0) +" = '"+data_list.get(0)+"' ";
				}else if (column_list.size() == 2) {
					query = "SELECT * FROM procedure_booking "
							+ "WHERE (date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') ";
				}else if (column_list.size() == 3) {
					query = "SELECT * FROM procedure_booking "
							+ "WHERE (date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') "
									+ "AND ("+column_list.get(2) +" = '"+data_list.get(2)+"') ";
				}
			
				
			Statement statement = connection.createStatement();
			ResultSet rs= statement.executeQuery(query);
			
			while(rs.next()) {
				
				
				BookedProcedure item = new BookedProcedure();
				item.setInvoiceNo(rs.getString(2));
				item.setProcedure(rs.getString(3));
				item.setTheatre(rs.getString(6));
				item.setDate(rs.getDate(7).toString());
				item.setBookedBy(rs.getString(11));
				item.setProcedureState(rs.getString(15));
				item.setProcedureCost(rs.getDouble(9));
				item.setAnesthesiaCost(rs.getDouble(10));
				
				list.add(item);
				
				
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return list;
	}


	@Override
	public ArrayList<PatientVisit> getOutpatientVisitReport(ArrayList<String> column_list,
			ArrayList<String> data_list, Date fromDate, Date todateDate) throws RemoteException {
		ArrayList<PatientVisit> visit_list = new ArrayList<PatientVisit>();
		try {
			
			Connection connection = DBConnection.getConnection();
			String query = "";
			
				if(column_list.size()==0) {
				query = "SELECT * FROM patient_visit_view "
						+ "WHERE (visit_date between '"+fromDate+"' AND '"+todateDate+"') "
						+ "AND clinic = 'opd' ";
				}else if (column_list.size() == 1) {
					query = "SELECT * FROM patient_visit_view "
							+ "WHERE (visit_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND (clinic = 'opd') ";
				}else if (column_list.size() == 2) {
					query = "SELECT * FROM patient_visit_view "
							+ "WHERE (visit_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') "
									+ "AND (clinic = 'opd') ";
				}else if (column_list.size() == 3) {
					query = "SELECT * FROM patient_visit_view "
							+ "WHERE (visit_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') "
									+ "AND ("+column_list.get(2) +" = '"+data_list.get(2)+"') "
									+ "AND (clinic = 'opd') ";
				}
			
				
			Statement statement = connection.createStatement();
			ResultSet rs= statement.executeQuery(query);
			
			while(rs.next()) {
				
				
				PatientVisit item = new PatientVisit();				
				item.setInvoiceNo(rs.getString(1));
				item.setFullname(rs.getString(10) + " " + rs.getString(11));
				item.setDate(rs.getDate(4).toString());
				item.setEmrSratus(rs.getString(9));
				item.setStatus(rs.getString(7));
				item.setDoctor(rs.getString(3));
						
				visit_list.add(item);
				
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return visit_list;
	}

	
	@Override
	public ArrayList<InpatientAdmission> getInpatientAdmissionReport(ArrayList<String> column_list,
			ArrayList<String> data_list, Date fromDate, Date todateDate) throws RemoteException {
		ArrayList<InpatientAdmission> admissions_list = new ArrayList<InpatientAdmission>();
		try {
			
			Connection connection = DBConnection.getConnection();
			String query = "";
			
				if(column_list.size()==0) {
				query = "SELECT * FROM admission_slip "
						+ "WHERE (admission_date between '"+fromDate+"' AND '"+todateDate+"') ";
				}else if (column_list.size() == 1) {
					query = "SELECT * FROM admission_slip "
							+ "WHERE (admission_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') ";
									
				}else if (column_list.size() == 2) {
					query = "SELECT * FROM admission_slip "
							+ "WHERE (admission_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') ";
									
				}else if (column_list.size() == 3) {
					query = "SELECT * FROM admission_slip "
							+ "WHERE (admission_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') "
									+ "AND ("+column_list.get(2) +" = '"+data_list.get(2)+"') ";
									
				}
			
				
			Statement statement = connection.createStatement();
			ResultSet rs= statement.executeQuery(query);
			
			while(rs.next()) {
				
				InpatientAdmission item = new InpatientAdmission();
				item.setInvoiceNo(rs.getString(1));
				item.setFullname(rs.getString(3) + " " + rs.getString(4));
				item.setAdmittingDoctor(rs.getString(9));
				item.setAdmissionDate(rs.getDate(7).toString());
				item.setAdmissionStatus(rs.getString(14));
				item.setEmrStatus(rs.getString(16));
				item.setAdmBedDays(rs.getInt(17));
				
				admissions_list.add(item);
				
				
								
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return admissions_list;
	}


	@Override
	public ArrayList<MedicinePurchaseItem> getPharmacyPurchaseReport(Date fromDate, Date todateDate) throws RemoteException {
		ArrayList<MedicinePurchaseItem> medicine_list = new ArrayList<MedicinePurchaseItem>();
		try {
			
			Connection connection = DBConnection.getConnection();
			String query = "";		
				
			query = "SELECT medicine_description, sum(quantity), sum(price) FROM pharmacy_purchase_history "
					+ "WHERE date between '"+fromDate+"' AND '"+todateDate+"' "
					+ "group by medicine_code";			
				
			Statement statement = connection.createStatement();
			ResultSet rs= statement.executeQuery(query);
			
			while(rs.next()) {
				
				MedicinePurchaseItem item = new MedicinePurchaseItem();
				item.setMedicimeName(rs.getString(1));
				item.setQty(rs.getInt(2));
				item.setMedicinePrice(rs.getInt(3));
				
				medicine_list.add(item);
				
				
				
				
				
								
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return medicine_list;
	}

	
	@Override
	public ArrayList<MedicineWithdrawalItem> getPharmacyWithdrawalReport(Date fromDate, Date todateDate)
			throws RemoteException {
		ArrayList<MedicineWithdrawalItem> medicine_list = new ArrayList<MedicineWithdrawalItem>();
		try {
			
			Connection connection = DBConnection.getConnection();
			String query = "";		
				
			query = "SELECT medicine_description, sum(quantity) FROM pharmacy_withdrawal_view "
					+ "WHERE date between '"+fromDate+"' AND '"+todateDate+"' "
					+ "group by medicine_code";			
				
			Statement statement = connection.createStatement();
			ResultSet rs= statement.executeQuery(query);
			
			while(rs.next()) {
				
				MedicineWithdrawalItem item = new MedicineWithdrawalItem();
				item.setDrugName(rs.getString(1));
				item.setQuantity(rs.getInt(2));
				
				medicine_list.add(item);								
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return medicine_list;
	}

	public ArrayList<FamilyCard> getFamilyRegistrationReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate){
		ArrayList<FamilyCard> list = new ArrayList<FamilyCard>();
		try {
			
			Connection connection = DBConnection.getConnection();
			String query = "";
			
				if(column_list.size()==0) {
				query = "SELECT * FROM family_cards "
						+ "WHERE reg_date between '"+fromDate+"' AND '"+todateDate+"' ";
				}else if (column_list.size() == 1) {
					query = "SELECT * FROM family_cards "
							+ "WHERE (reg_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND "+column_list.get(0) +" = '"+data_list.get(0)+"' ";
				}
			
				
			Statement statement = connection.createStatement();
			ResultSet rs= statement.executeQuery(query);
			
			while(rs.next()) {
								
				FamilyCard item = new FamilyCard();
				item.setFamilyNo(rs.getString(2));
				item.setFamilyName(rs.getString(3));
				item.setDate(rs.getDate(4).toString());
				item.setRegOfficer(rs.getString(5));
				item.setRegFee(rs.getDouble(6));				
				
				list.add(item);
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return list;
	}

	
	@Override
	public ArrayList<GeneralDeposit> getGeneralDepositReport(ArrayList<String> column_list, ArrayList<String> data_list,
			Date fromDate, Date todateDate) throws RemoteException {
		ArrayList<GeneralDeposit> list = new ArrayList<GeneralDeposit>();
		try {
			
			Connection connection = DBConnection.getConnection();
			String query = "";
			
				if(column_list.size()==0) {
				query = "SELECT * FROM general_deposit "
						+ "WHERE date between '"+fromDate+"' AND '"+todateDate+"' ";
				}else if (column_list.size() == 1) {
					query = "SELECT * FROM general_deposit "
							+ "WHERE (date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND "+column_list.get(0) +" = '"+data_list.get(0)+"' ";
				}
			
				
			Statement statement = connection.createStatement();
			ResultSet rs= statement.executeQuery(query);
			
			while(rs.next()) {
								
				GeneralDeposit item = new GeneralDeposit();
				item.setInvoiceNo(rs.getString(2));
				item.setModeOfPayment(rs.getString(3));
				item.setDate(rs.getDate(5).toString());
				item.setAmount(rs.getDouble(7));
				
				list.add(item);
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return list;
	}

	@Override
	public ArrayList<PatientBiodata> getPatientRegistrationReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate){
		ArrayList<PatientBiodata> list = new ArrayList<PatientBiodata>();
		try {
			
			Connection connection = DBConnection.getConnection();
			String query = "";
			
				if(column_list.size()==0) {
				query = "SELECT * FROM patient_biodata "
						+ "WHERE reg_date between '"+fromDate+"' AND '"+todateDate+"' ";
				}else if (column_list.size() == 1) {
					query = "SELECT * FROM patient_biodata "
							+ "WHERE (reg_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND "+column_list.get(0) +" = '"+data_list.get(0)+"' ";
				}
			
				
			Statement statement = connection.createStatement();
			ResultSet rs= statement.executeQuery(query);
			
			while(rs.next()) {
								
				PatientBiodata item = new PatientBiodata();
				
				item.setHospital_no(rs.getString(2));
				item.setSurname(rs.getString(3));
				item.setFirstname(rs.getString(4));
				item.setDob(rs.getDate(6).toString());
				item.setRegDate(rs.getDate(5).toString());
				item.setRegistrationOfficer(rs.getString(22));
				item.setCharge(rs.getDouble(23));				
				item.setFamilyNo(rs.getString(24));						
				
				list.add(item);
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return list;
	}













}
