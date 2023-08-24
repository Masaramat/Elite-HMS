package com.ahms.server;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.clinicmgt.entities.BookedProcedure;
import com.ahms.clinicmgt.entities.ClinicBill;
import com.ahms.clinicmgt.entities.ClinicProcedure;
import com.ahms.clinicmgt.entities.ConsultationNote;
import com.ahms.clinicmgt.entities.DischargeSummary;
import com.ahms.clinicmgt.entities.DiseaseCategory;
import com.ahms.clinicmgt.entities.DiseaseCondition;
import com.ahms.clinicmgt.entities.DiseaseSubcategory;
import com.ahms.clinicmgt.entities.DiseeaseIndex;
import com.ahms.clinicmgt.entities.ExaminationDetail;
import com.ahms.clinicmgt.entities.ExaminationGroup;
import com.ahms.clinicmgt.entities.ExaminationSection;
import com.ahms.clinicmgt.entities.InpatientAdmission;
import com.ahms.clinicmgt.entities.InpatientObservation;
import com.ahms.clinicmgt.entities.NursingNote;
import com.ahms.clinicmgt.entities.OperationNote;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.clinicmgt.entities.PhysicalExamNote;
import com.ahms.clinicmgt.entities.PrescriptionOrderItem;
import com.ahms.clinicmgt.entities.ProgressNote;
import com.ahms.clinicmgt.entities.Symptom;
import com.ahms.clinicmgt.entities.SymptomCategory;
import com.ahms.clinicmgt.entities.SystemReviewNote;
import com.ahms.clinicmgt.entities.VitalSignEntry;
import com.ahms.hmgt.entities.Appointment;
import com.ahms.hmgt.entities.Bed;
import com.ahms.hmgt.entities.Doctor;
import com.ahms.hmgt.entities.ProcedureItem;
import com.ahms.hmgt.entities.Room;
import com.ahms.labmgt.entities.TestOrderItem;
import com.ahms.pharmarcymgt.entities.MedicineStockItem;
import com.mysql.cj.protocol.a.NativeSocketConnection;


public class ClinicManagementImplementation implements ClinicManagementInterface {
	
	
	
	Connection conn;

	public ClinicManagementImplementation() {}

	@Override
	public ArrayList<Appointment> searchWaitingAppointment(Date date1, Date date2) throws RemoteException {
		ArrayList<Appointment> apptList = new ArrayList<>();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * "
					+ "FROM opd_appointment_view "
					+ "WHERE date between '" + date1 +"' and '" + date2 +"' and appointment_state = 'Booked' " ; 
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Appointment appt = new Appointment();
				appt.setHospitalNno(rs.getString(1));
				appt.setSurname(rs.getString(2));
				appt.setOthernames(rs.getString(3));
				appt.setDocId(rs.getString(4));
				appt.setDate(rs.getDate(5).toString());
				appt.setTime(rs.getString(6));
				appt.setState(rs.getString(7));
				
				apptList.add(appt);					
				
			}
			conn.close();
		}catch(Exception c){c.printStackTrace();}
	
	return apptList;
	}

	@Override
	public String getPatientName(String hosp_no) throws RemoteException {
		String patient_name = "";
		try{
			conn = DBConnection.getConnection();
			
			String query = "SELECT patient_surname, patient_firstname "
					+ "FROM patient_biodata "
					+ "WHERE hospital_no ='" + hosp_no +"'";
			
			Statement stmt1 = conn.createStatement();
			ResultSet rs = stmt1.executeQuery(query);
			while(rs.next()){
				patient_name = rs.getString(1)+", " +rs.getString(2);
			}
			conn.close();		
		}catch(Exception ex){ex.printStackTrace();}
					
		return patient_name;
	}

	@Override
	public ArrayList<Doctor> getDoctorList() throws RemoteException {
		ArrayList<Doctor> doclist = new ArrayList<>();
		
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT doc_id, doctor_name "
					+ "from doctor";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Doctor doctor = new Doctor();
				doctor.setDoctorId(rs.getString(1));
				doctor.setDoctorName(rs.getString(2));
				
				doclist.add(doctor);
				
			}
			conn.close();
		}catch(Exception e){ e.printStackTrace();}
					
		return doclist;  
	}

	@Override
	public ArrayList<String> generateInvoice(PatientVisit pvisit) throws RemoteException {
		ArrayList<String> list = new ArrayList<>();
		
		String caseNo = "";
		String invoiceNo = "";
		
		int countValue = 0;
		int countValue2 = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try{
			conn = DBConnection.getConnection();
			java.util.Date  date = sdf.parse(pvisit.getDate());
			java.sql.Date sqldate = new java.sql.Date(date.getTime());
			
			//query to get the latest case
			Statement s2 = conn.createStatement();
			ResultSet rss = s2.executeQuery("select value from counter where id= "+ 12);
			while(rss.next()){
				countValue2=  rss.getInt("value");
				caseNo = "SH"+leftPad("" + countValue2, 6); 				
			}
			
			// query to find last number and attach it to the new invoice.
			Statement stmt=conn.createStatement();
			ResultSet rs = stmt.executeQuery("select value from counter where id= "+ 11);
			   		
			while(rs.next()){
				countValue =  rs.getInt("value");
				invoiceNo = "BB"+leftPad("" + countValue, 6); 
				
				String queryy = "INSERT into patient_visit(case_no, invoice_no, hospital_no, doctor_id, visit_date, "
						+ "visit_time, clinic, visit_status, visit_type, remarks, emr_status) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
				
				PreparedStatement pstmt = conn.prepareStatement(queryy);
				pstmt.setString(1, caseNo);
				pstmt.setString(2, invoiceNo);
				pstmt.setString(3, pvisit.getHospitalNo());
				pstmt.setString(4, pvisit.getDoctor());
				pstmt.setDate(5, sqldate);
				pstmt.setString(6, pvisit.getTime());
				pstmt.setString(7, pvisit.getClinic());
				pstmt.setString(8, pvisit.getStatus());
				pstmt.setString(9, pvisit.getType());
				pstmt.setString(10, "");
				pstmt.setString(11, "open");
				
				pstmt.execute();
				list.add(invoiceNo);
				list.add(caseNo);
				
				String queryy4 = "INSERT into consultation_notes(invoice_no, past_history, complaints, physical_exam, treatment_plan) VALUES(?,?,?,?,?)";				
				PreparedStatement pstmt2 = conn.prepareStatement(queryy4);
				pstmt2.setString(1, invoiceNo);
				pstmt2.setString(2, "");
				pstmt2.setString(3, "");
				pstmt2.setString(4, "");
				pstmt2.setString(5, "");
				
				pstmt2.execute();	
				
				
				conn = DBConnection.getConnection();
				String sql_one = "INSERT INTO discharge_summaries(invoice_no, date, time, discharge_mode, discharge_note, physician_name) "
						+ "VALUES (?, curdate(), curtime(), ?, ?, ?)";
				PreparedStatement ps_one = conn.prepareStatement(sql_one);
				ps_one.setString(1, invoiceNo);
				ps_one.setString(2, "");
				ps_one.setString(3, "");
				ps_one.setString(4, "");
				ps_one.execute();
				
			}
			
			String query2 = "update counter set value = ? where id = ?";
			PreparedStatement pms = conn.prepareStatement(query2);
			pms.setInt(1, countValue2+1);
			pms.setInt(2, 12);
			pms.executeUpdate();
			
			String query3 = "update counter set value = ? where id = ?";
			PreparedStatement pms2 = conn.prepareStatement(query3);
			pms2.setInt(1, countValue+1);
			pms2.setInt(2, 11);
			pms2.executeUpdate();
			
			String query4 = "DELETE FROM opd_appointments WHERE hospital_no = '"+pvisit.getHospitalNo() +"' ";			
			Statement statement = conn.createStatement();
			boolean xx = statement.execute(query4);
			
			conn.close();
			
		}catch(Exception ex){ ex.printStackTrace(); }
		return list;
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
	public ArrayList<PatientVisit> searchPatientVisit(String text, String searchby) throws RemoteException {
		 ArrayList<PatientVisit> visitList = new  ArrayList<PatientVisit>();
		 Statement stmt1 = null;
		 ResultSet rs = null;
		 
		 try{
			 conn = DBConnection.getConnection();
			 String query = "";
			 
			 if (searchby.equalsIgnoreCase("Patient Name")) {
				 query = "Select * from patient_visit_view "
				 		+ "where (patient_surname like '%" +text+"%' OR patient_firstname like '%" +text+"%') "
				 		+ "AND clinic = 'opd'";
			}else if (searchby.equalsIgnoreCase("hospital number")) {
				 query = "Select * from patient_visit_view "
				 		+ "where hospital_no = '" +text+"' "
				 		+ "AND clinic = 'opd'";
			}else if (searchby.equalsIgnoreCase("invoice number")) {
				 query = "Select * from patient_visit_view "
				 		+ "where invoice_no = '" +text+"' "
				 		+ "AND clinic = 'opd'";
			}
			 	
					stmt1 = conn.createStatement();					
					rs = stmt1.executeQuery(query);
				
				while(rs.next()){
					PatientVisit pv = new PatientVisit();
					
					pv.setInvoiceNo(rs.getString(1));					
					pv.setHospitalNo(rs.getString(2));
					pv.setSurname(rs.getString(10));
					pv.setOthernames(rs.getString(11));
					pv.setDob(rs.getDate(12).toString());
					pv.setGender(rs.getString(13));
					pv.setClinic(rs.getString(6));
					pv.setDoctor(rs.getString(3));
					pv.setDate(rs.getDate(4).toString());
					pv.setTime(rs.getString(5));
					pv.setStatus(rs.getString(7));
					pv.setEmrSratus(rs.getString(9));
					
					visitList.add(pv);			
				}
			 	conn.close();		 
		 }catch(Exception e){ 	 e.printStackTrace();		 }		
		
		return visitList;
	}
	
	@Override
	public ArrayList<PatientVisit> searchPatientVisit(String text, String searchby, String status) throws RemoteException {
		 ArrayList<PatientVisit> visitList = new  ArrayList<PatientVisit>();
		 Statement stmt1 = null;
		 ResultSet rs = null;
		 
		 try{
			 conn = DBConnection.getConnection();
			 String query = "";
			 
			 if (searchby.equalsIgnoreCase("Patient Name")) {
				 query = "Select * from patient_visit_view "
				 		+ "where (patient_surname like '%" +text+"%' OR patient_firstname like '%" +text+"%') "
				 		+ "AND clinic = 'opd'"
				 		+ "AND visit_status = '"+status+"' ";
			}else if (searchby.equalsIgnoreCase("hospital number")) {
				 query = "Select * from patient_visit_view "
				 		+ "where hospital_no = '" +text+"' "
				 		+ "AND clinic = 'opd'"
				 		+ "AND visit_status = '"+status+"' ";
			}else if (searchby.equalsIgnoreCase("invoice number")) {
				 query = "Select * from patient_visit_view "
				 		+ "where invoice_no = '" +text+"' "
				 		+ "AND visit_status = '"+status+"' ";
			}
			 	
					stmt1 = conn.createStatement();					
					rs = stmt1.executeQuery(query);
				
				while(rs.next()){
					PatientVisit pv = new PatientVisit();
					
					pv.setInvoiceNo(rs.getString(1));					
					pv.setHospitalNo(rs.getString(2));
					pv.setSurname(rs.getString(10));
					pv.setOthernames(rs.getString(11));
					pv.setDob(rs.getDate(12).toString());
					pv.setGender(rs.getString(13));
					pv.setClinic(rs.getString(6));
					pv.setDoctor(rs.getString(3));
					pv.setDate(rs.getDate(4).toString());
					pv.setTime(rs.getString(5));
					pv.setStatus(rs.getString(7));
					pv.setEmrSratus(rs.getString(9));
					
					visitList.add(pv);			
				}
			 	conn.close();		 
		 }catch(Exception e){ 	 e.printStackTrace();		 }		
		
		return visitList;
	}
	
	@Override
	public ArrayList<PatientVisit> searchPatientVisit(String clinic, java.util.Date date1, java.util.Date date2)throws RemoteException {
		ArrayList<PatientVisit> visitList = new  ArrayList<PatientVisit>();
		java.sql.Date date_1 = new java.sql.Date(date1.getTime());
		java.sql.Date date_2 = new java.sql.Date(date2.getTime());
		
		
		 Statement stmt1 = null;
		 ResultSet rs = null;
		 try{
			 conn = DBConnection.getConnection();
			 String query = "select * from patient_visit_view "		+ "where visit_date between '" +date_1+"' and '"+date_2+"'";
			 String query1 = "select * from patient_visit_view where clinic = '" +clinic+"' and visit_date between '" +date_1+"' and '"+date_2+"'";
			 
			 if(clinic.equalsIgnoreCase("all")){
					stmt1 = conn.createStatement();
					rs = stmt1.executeQuery(query);
				
					
				}else {
					stmt1 = conn.createStatement();					
					rs = stmt1.executeQuery(query1);
					
				}
			//extract data from result set
				while(rs.next()){
					PatientVisit pv = new PatientVisit();
					pv.setInvoiceNo(rs.getString(1));
					
					pv.setHospitalNo(rs.getString(2));
					pv.setSurname(rs.getString(10));
					pv.setOthernames(rs.getString(11));
					pv.setDob(rs.getDate(12).toString());
					pv.setGender(rs.getString(13));
					pv.setClinic(rs.getString(6));
					pv.setDoctor(rs.getString(3));
					pv.setDate(rs.getDate(4).toString());
					pv.setTime(rs.getString(5));
					pv.setStatus(rs.getString(7));
					pv.setEmrSratus(rs.getString(9));
					
					
					visitList.add(pv);				
				}
				conn.close();
		 }catch(Exception e){ 	 e.printStackTrace();		 }	
		
		return visitList;
	}
			
	@Override
	public ArrayList<Room> getAllWards() throws RemoteException {
		ArrayList<Room> list = new ArrayList<>();
		try{
			conn = DBConnection.getConnection();
			String query = "select * from wards_view";
			Statement st = conn.createStatement(); 
			ResultSet rs = st.executeQuery(query);
			
			
			while(rs.next()){
				list.add(new Room(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
			conn.close();		
		}catch(Exception ex){ ex.printStackTrace();}
		
		return list;
	}
	
	@Override
	public ArrayList<Bed> getFreeBeds(String room_id) throws RemoteException {
		ArrayList<Bed> list = new ArrayList<>();
		try{
			conn = DBConnection.getConnection();
			String query = "select * from beds_view where ward_name = '"+ room_id +"' and bed_state = 'free' ";
			Statement st= conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				Bed bed = new Bed();
				bed.setBedNo(rs.getString(1));
				bed.setBedDetails(rs.getString(2));
				bed.setWardName(rs.getString(6));
				bed.setBedCharge(rs.getDouble(3));
				
				list.add(bed);
				
			}
			conn.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return list;
	}
	
	@Override
	public PatientVisit getPatientVisit(String invoice_no) throws RemoteException {
		PatientVisit pv = new PatientVisit();
		try{
			conn = DBConnection.getConnection();
			String query = "select * from patient_visit_view "
					+ "where invoice_no ='"+invoice_no+"'";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				pv.setInvoiceNo(rs.getString(1));
				
				pv.setHospitalNo(rs.getString(2));
				pv.setSurname(rs.getString(10));
				pv.setOthernames(rs.getString(11));
				pv.setDob(rs.getDate(12).toString());
				pv.setGender(rs.getString(13));
				pv.setClinic(rs.getString(6));
				pv.setDoctor(rs.getString(3));
				pv.setDate(rs.getDate(4).toString());
				pv.setTime(rs.getString(5));
				pv.setStatus(rs.getString(7));
				pv.setEmrSratus(rs.getString(9));
			}
			conn.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return pv;
	}
	
	@Override
	public InpatientAdmission saveAdmission(InpatientAdmission ipadm) throws RemoteException {
		String admNo = "";
		int countValue = 0;
		
		try{
			conn = DBConnection.getConnection();
			Statement s2 = conn.createStatement();
			ResultSet rss = s2.executeQuery("select value from counter where id= "+ 13);
			while(rss.next()){
				countValue=  rss.getInt("value");
				admNo = "AD"+leftPad("" + countValue, 6); 
				
				String query = "INSERT into admission_register(invoice_no, bed_price, admission_date, admission_time, "
						+ "department, admitting_doctor, contact_relation, contact_phone, bed_code, admission_status) "
						+ "values(?, ?, curdate(), curtime(), ?,?,?,?, ?, ?)";
				
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, ipadm.getInvoiceNo());
				pstmt.setDouble(2, ipadm.getBedCharge());				
				pstmt.setString(3, ipadm.getDepartment());
				pstmt.setString(4, ipadm.getAdmittingDoctor());
				pstmt.setString(5, ipadm.getContactRelation());
				pstmt.setString(6, ipadm.getContactPhone());
				pstmt.setString(7, ipadm.getBedCode());
				
				pstmt.setString(8, "active");
				
				pstmt.execute();
				
				
			}//end while
			
			String query1 = "update counter set value = ? where id = ?";
			PreparedStatement pms2 = conn.prepareStatement(query1);
			pms2.setInt(1, countValue+1);
			pms2.setInt(2, 13);
			pms2.executeUpdate();
			
			String query2 = "update patient_visit set clinic = ? where invoice_no = ?";
			PreparedStatement pms3 = conn.prepareStatement(query2);
			pms3.setString(1, "IPD");
			pms3.setString(2, ipadm.getInvoiceNo());
			pms3.executeUpdate();
			
			String query3 = "update beds set bed_state = ? where bed_no = ?";
			PreparedStatement pms4 = conn.prepareStatement(query3);
			pms4.setString(1, "occupied");
			pms4.setString(2, ipadm.getBedCode());
			pms4.executeUpdate();
			
			conn.close();
			
		}catch(Exception ex){ex.printStackTrace();}
		
		return ipadm;
	}
	
	@Override
	public ArrayList<InpatientAdmission> searchInpatientAdmission(String status, java.util.Date date1,java.util.Date date2) throws RemoteException {
		ArrayList<InpatientAdmission> admlist = new ArrayList<InpatientAdmission>();
		java.sql.Date date_1 = new java.sql.Date(date1.getTime());
		java.sql.Date date_2 = new java.sql.Date(date2.getTime());
		
		Statement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBConnection.getConnection();
			 String query = "select * from admission_slip where admission_date between '" +date_1+"' and '"+date_2+"'";			 
			 String query1 = "select * from admission_slip where admission_status = '" +status+"' and admission_date between '" +date_1+"' and '"+date_2+"'";
			 
			 if(status.equalsIgnoreCase("all")){
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);					
				}else {
					stmt = conn.createStatement();					
					rs = stmt.executeQuery(query1);					
				}
			 while(rs.next()){
				 InpatientAdmission ipadm = new InpatientAdmission();
				 ipadm.setInvoiceNo(rs.getString(1));			
				 ipadm.setHospitalNo(rs.getString(2));
				 ipadm.setPatientSurname(rs.getString(3));
				 ipadm.setPatientOthernames(rs.getString(4));
				 ipadm.setDob(rs.getDate(5).toString());
				 ipadm.setGender(rs.getString(6));						
				 ipadm.setAdmissionDate(rs.getDate(7).toString());
				 ipadm.setDepartment(rs.getString(8));
				 ipadm.setAdmittingDoctor(rs.getString(9));
				
				 ipadm.setContactRelation(rs.getString(10));
				 ipadm.setContactPhone(rs.getString(11));
				  
				 ipadm.setBedDetails(rs.getString(12));
				 ipadm.setBedCharge(rs.getDouble(13));
				 ipadm.setAdmissionStatus(rs.getString(14));
				 ipadm.setEmrStatus(rs.getString(16));
				 ipadm.setAdmBedDays(rs.getInt(17));
				 ipadm.setBedCode(rs.getString(18));
				 
				 admlist.add(ipadm);				 
			 }
			 conn.close();
		}catch(Exception ex){ex.printStackTrace();}	
		
		return admlist;
	}
	
	@Override
	public ArrayList<Integer> getBedCount() throws RemoteException {
		ArrayList<Integer> list = new ArrayList<>();
		int one, two, three;
		try{
			conn = DBConnection.getConnection();
			Statement st = conn.createStatement();
			
			String query1 = "select count(*) from beds_view";			
			ResultSet rs = st.executeQuery(query1);
			while(rs.next()){
				one = rs.getInt(1);
				list.add(one);
			}
			
			String query2 = "select count(*) from beds_view where bed_state = 'free' ";
			ResultSet rs2 = st.executeQuery(query2);
			while(rs2.next()){
				two = rs2.getInt(1);
				list.add(two);
			}
			
			String query3 = "select count(*) from beds_view where bed_state = 'occupied' ";
			ResultSet rs3 = st.executeQuery(query3);
			while(rs3.next()){
				three = rs3.getInt(1);
				list.add(three);
			}
			conn.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return list;
	}
	
	@Override
	public String getHistoryOfIllness(String invoice_no) throws RemoteException {
		String history = "";
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT history from history_of_illness where invoice_no = '" + invoice_no + "' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				history = rs.getString(1);
			}
			conn.close();
		}catch(Exception ex){	ex.printStackTrace();	}
		
		return history;
	}
	
	@Override
	public ArrayList<String> getComplaints(String invoice_no) throws RemoteException {
		ArrayList<String> list = new ArrayList<>();
		try{
			conn = DBConnection.getConnection();
			String query = "select complaint "
					+ "from patient_encounter_complaints "
					+ "where invoice_no = '" +invoice_no+"' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				list.add(rs.getString(1));
			}
			
			conn.close();
		}catch(Exception ex){ ex.printStackTrace();}
		
		return list;
	}
	
	
	@Override
	public ArrayList<DiseeaseIndex> getAllDiagnosis(String invoice_no) throws RemoteException {
		ArrayList<DiseeaseIndex> dlist = new ArrayList<DiseeaseIndex>();
		try{
			conn = DBConnection.getConnection();
			String query = "select * from di_register where invoice_no = '" + invoice_no +"' order by 1 asc";
			Statement st  = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				DiseeaseIndex di = new DiseeaseIndex();
				di.setInvoiceNo(rs.getString(2));
				di.setCaseNo("");
				di.setDiseaseCode(rs.getString(3));
				di.setDiseaseCondition(rs.getString(4));
				di.setDiagnosisType("");
				
				dlist.add(di);
			}
			conn.close();
		}catch(Exception ex){	ex.printStackTrace();	}
		
		return dlist;
	}
	
	@Override
	public ArrayList<PrescriptionOrderItem> getPrescriptions(String invoice_no) throws RemoteException {
		ArrayList<PrescriptionOrderItem> list = new ArrayList<PrescriptionOrderItem>();
		try{
			conn = DBConnection.getConnection();
			String query = "select * from prescription_order_view where invoice_no = '" +invoice_no+"' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				PrescriptionOrderItem pres = new PrescriptionOrderItem();
				pres.setInvoiceNumber(rs.getString(2));
				pres.setMedicineCode(rs.getString(3));
				pres.setMedicineName(rs.getString(4));
				pres.setDose(rs.getInt(5));
				pres.setFrequency(rs.getInt(6));
				pres.setDuration(rs.getInt(7));
				pres.setDispenseQauntity(rs.getInt(8));
				pres.setOrderState(rs.getString(9));
				pres.setMedicineForm(rs.getString(10));
				
				
				list.add(pres);
				
			}			
			conn.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return list;
	}
	
	@Override
	public ArrayList<VitalSignEntry> getVitalSignEntries(String invoice_no) throws RemoteException {
		ArrayList<VitalSignEntry> vslist = new ArrayList<>();
		try{
			conn =DBConnection.getConnection();
			String query = "SELECT * FROM vital_signs_entries WHERE invoice_no = '"+ invoice_no +"' ";
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				VitalSignEntry vse = new VitalSignEntry();
				vse.setSerialNo(rs.getInt(1));
				vse.setInvoiceNumber(rs.getString(2));
				vse.setDate(rs.getDate(3).toString());
				vse.setTime(rs.getTime(4).toString());
				vse.setHeight(rs.getInt(5));
				vse.setWeight(rs.getInt(6));
				vse.setBmi(rs.getDouble(7));
				vse.setTemperature(rs.getDouble(8));
				vse.setBpSystole(rs.getInt(9));
				vse.setBpDiastole(rs.getInt(10));
				vse.setPulseRate(rs.getInt(11));
				vse.setRespirationRate(rs.getInt(12));
				
				
				
				vslist.add(vse);				
			}
			conn.close();
		}catch(Exception ex){ex.printStackTrace();}
				
		return vslist;
	}
	
	@Override
	public ArrayList<SystemReviewNote> getSystemReviewNotes(String invoice_no) throws RemoteException {
		ArrayList<SystemReviewNote> srn_list = new ArrayList<SystemReviewNote>();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM system_review_notes WHERE invoice_no = '"+ invoice_no +"' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				SystemReviewNote srn = new SystemReviewNote(rs.getString(2), rs.getString(3), rs.getString(4));
				srn_list.add(srn);
			}
			conn.close();
		}catch(Exception ex){ex.printStackTrace();}
		
		return srn_list;
	}
	
	@Override
	public ArrayList<PhysicalExamNote> getPhysicalExamNote(String invoice_no) throws RemoteException {
		ArrayList<PhysicalExamNote> list = new ArrayList<PhysicalExamNote>();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM physical_examination_notes WHERE invoice_no ='" + invoice_no + "' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				PhysicalExamNote pen = new PhysicalExamNote(rs.getString(2), rs.getString(3), rs.getString(4));
				list.add(pen);
			}
		
			conn.close();
		}catch(Exception ex){ex.printStackTrace();}
		return list;
	}
	
	@Override
	public ArrayList<ClinicBill> getClinicBills(String invoice) throws RemoteException {
		ArrayList<ClinicBill> list = new ArrayList<ClinicBill>();
		try{
			conn = DBConnection.getConnection();
			String sql = "SELECT * FROM clinic_charges WHERE invoice_no = '"+invoice+"' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				list.add(new ClinicBill(rs.getInt(1)+"", rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6)));
			}
			conn.close();			
		}catch(Exception ex){ ex.printStackTrace();}
		return list;
	}
	
	@Override
	public ArrayList<BookedProcedure> getBookedProcedures(String invoice_no) throws RemoteException {
		ArrayList<BookedProcedure> list = new ArrayList<BookedProcedure>();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM procedure_booking where invoice_no = '" + invoice_no + "' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				BookedProcedure proc  = new BookedProcedure();
				proc.setSno(rs.getInt(1) + "");
				proc.setInvoiceNo(rs.getString(2));
				proc.setProcedure(rs.getString(3));
				proc.setCptCode(rs.getString(4));
				proc.setAnesthesia(rs.getString(5));
				proc.setTheatre(rs.getString(6));
				proc.setDate(rs.getDate(7).toString());
				proc.setTime(rs.getString(8));
				proc.setProcedureCost(rs.getDouble(9));
				proc.setAnesthesiaCost(rs.getDouble(10));
				proc.setBookedBy(rs.getString(11));
				proc.setRemarks(rs.getString(12));
				proc.setConfirmDate("");
				proc.setConfirmTime("");
				proc.setProcedureState(rs.getString(15));
				
				list.add(proc);
			}
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		return list;
	}
	
	@Override
	public void saveClinicCharge(String invoice, String service, double amount) throws RemoteException {
		try {
			conn = DBConnection.getConnection();
			String query = "INSERT INTO clinic_charges(invoice_no, date, time, service_description, amount) "
					+ "VALUES(?, curdate(), curtime(), ?,?)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, invoice);
			ps.setString(2, service);
			ps.setDouble(3, amount);
			
			ps.execute();
			
			conn.close();
		} catch (Exception e) {	e.printStackTrace(); }
		
	}
	
	@Override
	public ArrayList<PatientVisit> getPatientVisitArray(String invoiceNo) throws RemoteException {
		ArrayList<PatientVisit> pvList = new ArrayList<>();
		
		try{
			conn = DBConnection.getConnection();
			String query = "select * from patient_visit_view "
					+ "where invoice_no ='"+invoiceNo+"' AND clinic = 'OPD'";
			Statement stmt  = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				PatientVisit pv = new PatientVisit();
				pv.setInvoiceNo(rs.getString(1));
			
				pv.setHospitalNo(rs.getString(2));
				pv.setSurname(rs.getString(10));
				pv.setOthernames(rs.getString(11));
				pv.setDob(rs.getDate(12).toString());
				pv.setGender(rs.getString(13));
				pv.setClinic(rs.getString(6));
				pv.setDoctor(rs.getString(3));
				pv.setDate(rs.getDate(4).toString());
				pv.setTime(rs.getString(5));
				pv.setStatus(rs.getString(7));
				pv.setEmrSratus(rs.getString(9));
				
				
				pvList.add(pv);					
			}
			conn.close();
		}catch(Exception ex){ ex.printStackTrace();}		
		
		return pvList;
	}
	
	@Override
	public void saveVSEntry(VitalSignEntry vs) throws RemoteException {
		try{
			conn =  DBConnection.getConnection();
			String query = "INSERT INTO vital_signs_entries(invoice_no, date, time, height, weight, bmi, "
					+ "temperature, bp_systole, bp_diastole, pulse_rate, respiration_rate) values(?, curdate(), curtime(), ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, vs.getInvoiceNumber());
			pst.setInt(2, vs.getHeight());
			pst.setInt(3, vs.getWeight());
			pst.setDouble(4, vs.getBmi());
			pst.setDouble(5, vs.getTemperature());
			pst.setInt(6, vs.getBpSystole());
			pst.setInt(7, vs.getBpDiastole());
			pst.setInt(8, vs.getPulseRate());
			pst.setInt(9, vs.getRespirationRate());
			
			pst.execute();
			
			conn.close();			
		}catch(Exception ex){ex.printStackTrace();}
		
	}
	
	@Override
	public ArrayList<Symptom> getAllSymptoms() throws RemoteException {
		ArrayList<Symptom> list = new ArrayList<Symptom>();
		try{
		conn = DBConnection.getConnection();
		String query = "select * "
				+ "from symptoms_view "
				+ "order by 2";
				
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()){
			list.add(new Symptom(rs.getString(3), rs.getString(4), rs.getString(1), rs.getString(2)));
		}
		conn.close();
		}catch(Exception ex){ex.printStackTrace();}
		return list;
	}
	
	@Override
	public void saveComplaints(String invoice_no, String complaints) throws RemoteException {
		try {			
			conn = DBConnection.getConnection();
			String query = "insert into patient_encounter_complaints(invoice_no, complaint)"
					+ " values(?,?)";
			PreparedStatement pst = conn.prepareStatement(query);
				pst.setString(1, invoice_no);
				pst.setString(2, complaints);
							
				pst.execute();
			conn.close();
		} catch (Exception e) {	e.printStackTrace();	}
		
	}
		
		
	@Override
	public ArrayList<SymptomCategory> getSymptomCategories() throws RemoteException {
		ArrayList<SymptomCategory> list = new ArrayList<SymptomCategory>();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * "
					+ "FROM systemic_review_categories "
					+ "order by 3";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				list.add(new SymptomCategory(rs.getString(2), rs.getString(3)));				
			}
			conn.close();
		}catch(Exception ex){	ex.printStackTrace();}		
		return list;
	}
	
	@Override
	public ArrayList<Symptom> getSymptomsByCategory(String categoryCode) throws RemoteException {
		ArrayList<Symptom> list = new ArrayList<Symptom>();
		try{
		conn = DBConnection.getConnection();
		String query = "select * "
				+ "from symptoms_view "
				+ "where category_code ='"+categoryCode+"'";
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()){
			list.add(new Symptom(rs.getString(3), rs.getString(4), rs.getString(1), rs.getString(2)));
		}
		conn.close();
		}catch(Exception ex){ex.printStackTrace();}
		return list;
	}
	
	@Override
	public void saveReviewNotes(ArrayList<SystemReviewNote> list) throws RemoteException {
		try{
			conn  = DBConnection.getConnection();
			String query = "INSERT INTO system_review_notes(invoice_no, system, review_note) VALUES (?,?,?)";
			PreparedStatement ps = conn.prepareStatement(query);
			for(int i=0; i<list.size(); i++){
				ps.setString(1, list.get(i).getInvoiceNo());
				ps.setString(2, list.get(i).getSystem());
				ps.setString(3, list.get(i).getReviewVotes());
				
				ps.addBatch();
			}
			
			ps.executeBatch();
			conn.close();
			}catch(Exception ex){ ex.printStackTrace(); }
		
	}
	
	@Override
	public ArrayList<ExaminationSection> getAllSections() throws RemoteException {
		ArrayList<ExaminationSection> list = new ArrayList<>();
		try{
			conn = DBConnection.getConnection();
			String query = "select * "
					+ "from physical_exam_sections";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				list.add(new ExaminationSection(rs.getString(2), rs.getString(3)));
			}
			conn.close();
		}catch(Exception e){e.printStackTrace();}		
		
		return list;
	}
	
	@Override
	public ArrayList<ExaminationGroup> getExamGroups(String sectionCode) throws RemoteException {
		ArrayList<ExaminationGroup> list = new ArrayList<>();
		try{
			conn = DBConnection.getConnection();
			String query =  "select * "
					+ "from physical_exam_groups "
					+ "where section_code = '"+ sectionCode +"' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				list.add(new ExaminationGroup(rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			conn.close();
		}catch(Exception ex){	ex.printStackTrace();	}
		
		return list;
	}
	
	@Override
	public ArrayList<ExaminationDetail> getExamDetails(String group_code) throws RemoteException {
		ArrayList<ExaminationDetail> detail_list = new ArrayList<>();
		try{
			conn = DBConnection.getConnection();
			String query = "select * from "
					+ "physical_exam_details "
					+ "where exam_group_code = '"+ group_code +"' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				detail_list.add(new ExaminationDetail(rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			
			conn.close();
		}catch(Exception ex){ex.printStackTrace();	}
		
		return detail_list;
	}
	
	@Override
	public void savePhysicalExamNotes(String invoice_no, ArrayList<PhysicalExamNote> list) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
			String query = "INSERT INTO physical_examination_notes(invoice_no, exam_section, exam_note) VALUES(?,?,?)";
			PreparedStatement pst = conn.prepareStatement(query);
			for(int a=0; a<list.size(); a++){
			pst.setString(1, invoice_no);
			pst.setString(2, list.get(a).getExamSection());
			pst.setString(3, list.get(a).getExamNote());
			
			pst.addBatch();
			}
			pst.executeBatch();
			conn.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	@Override
	public ArrayList<DiseaseCategory> getAllDiseaseCategories() throws RemoteException {
		ArrayList<DiseaseCategory> list = new ArrayList<DiseaseCategory>();
		try{
			conn = DBConnection.getConnection();
			String query = "select * "
					+ "from disease_group";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				list.add(new DiseaseCategory(rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			
			conn.close();
		}catch(Exception ex){ex.printStackTrace();}		
		return list;
	}

	@Override
	public ArrayList<DiseaseSubcategory> getDiseaseSubcategories(String text) throws RemoteException {
		ArrayList<DiseaseSubcategory> list =new ArrayList<DiseaseSubcategory>();
		try{
			conn = DBConnection.getConnection();
			String query = "select * "
					+ "from disease_subgroup "
					+ "where group_id = '"+ text +"' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				list.add(new DiseaseSubcategory(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(2)));
			}
			conn.close();
		}catch(Exception e){e.printStackTrace();}		
		return list;
	}

	@Override
	public ArrayList<DiseaseCondition> getDiseaseCondtions(String text) throws RemoteException {
		ArrayList<DiseaseCondition> list =new ArrayList<DiseaseCondition>();
		try{
			conn = DBConnection.getConnection();
			String query = "select * "
					+ "from disease_detail "
					+ "where subgroup_id = '"+ text +"' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				list.add(new DiseaseCondition(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(2)));
			}
			conn.close();
		}catch(Exception e){e.printStackTrace();}		
		return list;
		
	}
	
	@Override
	public ArrayList<DiseaseCondition> searchDiseaseCondtions(String text) throws RemoteException {
		ArrayList<DiseaseCondition> list =new ArrayList<DiseaseCondition>();
		try{
			conn = DBConnection.getConnection();
			String query = "select * "
					+ "from disease_detail "
					+ "where detail_name like '%"+ text +"%' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				list.add(new DiseaseCondition(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(2)));
			}
			conn.close();
		}catch(Exception e){e.printStackTrace();}		
		return list;
		
	}

	@Override
	public void saveDiagnosis(DiseeaseIndex inlist) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
			String query = "insert into di_register(invoice_no, disease_code, disease_name) values (?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			
				pstmt.setString(1, inlist.getInvoiceNo());
				pstmt.setString(2, inlist.getDiseaseCode());
				pstmt.setString(3, inlist.getDiseaseCondition());			
		
			pstmt.execute();		
			conn.close();
		}catch(Exception ex){ex.printStackTrace();	}
		
	}
	
	@Override
	public ArrayList<ClinicProcedure> getAllProcedures() throws RemoteException {
		ArrayList<ClinicProcedure> list = new ArrayList<>();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM clinic_procedures "
						+ "ORDER BY 3 ASC";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				list.add(new ClinicProcedure(rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			conn.close();
		}catch(Exception ex){ ex.printStackTrace();}
		return list;
	}
	
	@Override
	public void saveBookedProcedure(BookedProcedure bp) throws RemoteException {
		try{
			SimpleDateFormat sddf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date dt = sddf.parse(bp.getDate());
			java.sql.Date date = new java.sql.Date(dt.getTime());
			conn = DBConnection.getConnection();
			String query = "INSERT INTO procedure_booking(invoice_no, clinic_procedure, cpt_code, anesthesia, theatre, date, time, "
					+ "procedure_cost, anesthesia_cost, booked_by, remarks, procedure_state)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps= conn.prepareStatement(query);
			ps.setString(1, bp.getInvoiceNo());
			ps.setString(2, bp.getProcedure());
			ps.setString(3, bp.getCptCode());
			ps.setString(4, bp.getAnesthesia());
			ps.setString(5, bp.getTheatre());
			ps.setDate(6, date);
			ps.setString(7, bp.getTime());
			ps.setDouble(8, bp.getProcedureCost());
			ps.setDouble(9, bp.getAnesthesiaCost());
			ps.setString(10, bp.getBookedBy());
			ps.setString(11, bp.getRemarks());
			ps.setString(12, "pending");
			
			ps.execute();
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		
	}
	
	public void updateClinicProcedure(String procedure_id, String procedure_name) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
			String query = "UPDATE clinic_procedures "
					+ "SET procedure_desc = ? "
					+ "WHERE procedure_id = ? "; 
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, procedure_name);
			ps.setString(2, procedure_id);	
			
			ps.executeUpdate();
		}catch(Exception ex){ ex.printStackTrace();}
	}
	
	
	@Override
	public ArrayList<MedicineStockItem> getAvailableDrugs() throws RemoteException {
		ArrayList<MedicineStockItem> stock_items = new ArrayList<MedicineStockItem>();
		try{
			 conn = DBConnection.getConnection();
			 String query ="SELECT * FROM pharmacy_stock_view";
			 Statement st = conn.createStatement();
			 ResultSet rs = st.executeQuery(query);
			 while(rs.next()){
				 MedicineStockItem msi = new MedicineStockItem();
				 msi.setMedicineCode(rs.getString(1));
				 msi.setDrugName(rs.getString(2));
				 msi.setMedicineForm(rs.getString(3));
				 msi.setAvailableQty(rs.getInt(9));
				 msi.setSalesUnit(rs.getString(4));
				 msi.setCountState(rs.getString(5));
				 
				 stock_items.add(msi);
			 }
			 conn.close();
		}catch(Exception ex){ex.printStackTrace();}
		return stock_items;
	}
	
	@Override
	public void savePrescriptionEntry(PrescriptionOrderItem item) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
			String query ="INSERT INTO prescription_order_items(invoice_no, medicine_code, medicine_name, dose, frequency, duration, dispense_qty, order_state) "
					+ "VALUES(?,?,?,?,?,?,?,?)";
			
			PreparedStatement pst = conn.prepareStatement(query);
			
				pst.setString(1, item.getInvoiceNumber());
				pst.setString(2, item.getMedicineCode());
				pst.setString(3, item.getMedicineName());
				pst.setInt(4, item.getDose());
				pst.setInt(5, item.getFrequency());
				pst.setInt(6, item.getDuration());
				pst.setInt(7, item.getDispenseQauntity());
				pst.setString(8, item.getOrderState());
				
				
			pst.execute();
			conn.close();
		}catch(Exception ex){ex.printStackTrace();}
		
	}
	
	@Override
	public InpatientAdmission getAdmissionSlip(String invoice_no) throws RemoteException {
		InpatientAdmission ipadm = new InpatientAdmission();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM admission_slip where invoice_no ='" +invoice_no+ "' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				 ipadm.setInvoiceNo(rs.getString(1));			
				 ipadm.setHospitalNo(rs.getString(2));
				 ipadm.setPatientSurname(rs.getString(3));
				 ipadm.setPatientOthernames(rs.getString(4));
				 ipadm.setDob(rs.getDate(5).toString());
				 ipadm.setGender(rs.getString(6));						
				 ipadm.setAdmissionDate(rs.getDate(7).toString());
				 ipadm.setDepartment(rs.getString(8));
				 ipadm.setAdmittingDoctor(rs.getString(9));
				
				 ipadm.setContactRelation(rs.getString(10));
				 ipadm.setContactPhone(rs.getString(11));
				  
				 ipadm.setBedDetails(rs.getString(12));
				 ipadm.setBedCharge(rs.getDouble(13));
				 ipadm.setAdmissionStatus(rs.getString(14));
				 ipadm.setEmrStatus(rs.getString(16));
				 ipadm.setAdmBedDays(rs.getInt(17));
				 ipadm.setBedCode(rs.getString(18));
			}
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		return ipadm;
	}
	
	@Override
	public ArrayList<ProgressNote> getProgressNotes(String invoice_no) throws RemoteException {
		ArrayList<ProgressNote> list = new ArrayList<ProgressNote>();
		try{
			conn = DBConnection.getConnection();
			String sql = "SELECT * FROM clinic_progress_notes where invoice_no = '" + invoice_no +"' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				list.add(new ProgressNote(rs.getInt(1), rs.getString(2), rs.getDate(3).toString(), rs.getTime(4).toString(), rs.getString(6), rs.getString(5), rs.getString(7)));
			}
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		return list;
	}
	
	@Override
	public ArrayList<java.util.Date> getAdmissionDates(String invoice_no) throws RemoteException {
		ArrayList<java.util.Date> datelist = new ArrayList<java.util.Date>();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT DISTINCT date "
					+ "FROM inpatient_observations "
					+ "WHERE invoice_no = '"+invoice_no+"' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				String date = rs.getDate(1).toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date dd = sdf.parse(date);
				
				datelist.add(dd);
			}
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		
		return datelist;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public ArrayList<InpatientObservation> getInpatientObservations(String invoice_no, java.util.Date date) throws RemoteException {
		ArrayList<InpatientObservation> obslist = new ArrayList<InpatientObservation>();
		java.sql.Date dd = new java.sql.Date(date.getTime());
		try{
			conn = DBConnection.getConnection();
			String sql = "SELECT * "
					+ "FROM inpatient_observations "
					+ "WHERE invoice_no = '"+invoice_no+"' AND date ='"+dd+"' " ;
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				InpatientObservation io = new InpatientObservation();
				io.setDate(rs.getDate(4).toString());
				io.setTime(rs.getTime(5)+"");
				io.setTemperature(rs.getDouble(8));
				io.setBp_systole(rs.getInt(6));
				io.setBy_diastole(rs.getInt(7));
				io.setPulse(rs.getInt(9));
				io.setRespiration(rs.getInt(10));
				
				obslist.add(io);
			}
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		return obslist;
	}
	
	@Override
	public void dischargePatient(String invoice_no, String bed_code, DischargeSummary ds) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
			String sql_one = "UPDATE discharge_summaries "
					+ "SET date = curdate(), time = curtime(), discharge_mode = ?, discharge_note = ?, physician_name = ? "
					+ " WHERE invoice_no = ?";
							
			String sql_two = "UPDATE admission_register "
					+ "SET admission_status = ?, discharge_date = curdate() "
					+ "WHERE invoice_no = ?";
			
			String sql_four = "UPDATE patient_visit "
					+ "SET visit_status = ? "
					+ "WHERE invoice_no = ?";
			
			String sql_three = "UPDATE beds "
					+ "SET bed_state = 'free' "
					+ "WHERE bed_no = '"+ bed_code +"' ";
			
			
			PreparedStatement ps_one = conn.prepareStatement(sql_one);
			ps_one.setString(4, invoice_no);
			ps_one.setString(1, ds.getDischargeMOde());
			ps_one.setString(2, ds.getDischargeNote());
			ps_one.setString(3, ds.getPhysician());
			ps_one.executeUpdate();
			
			PreparedStatement ps_two = conn.prepareStatement(sql_two);
			ps_two.setString(1, "discharged");
			ps_two.setString(2, invoice_no);
			ps_two.executeUpdate();
			
			PreparedStatement ps_four = conn.prepareStatement(sql_four);
			ps_four.setString(1, "discharged");
			ps_four.setString(2, invoice_no);
			ps_four.executeUpdate();
			
			PreparedStatement ps_three = conn.prepareStatement(sql_three);
			ps_three.executeUpdate();
			
			conn.close();
			}catch(Exception ex){ ex.printStackTrace();}
		
	}
	
	@Override
	public void saveInpatientObservation(InpatientObservation io) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
			String query = "INSERT INTO inpatient_observations(invoice_no, date, time, bp_systole, bp_diastole, temperature, pulse_rate, respiration_rate) "
					+ "VALUES(?, curdate(), curtime(), ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, io.getInvoiceNo());
			ps.setInt(2, io.getBp_systole());
			ps.setInt(3, io.getBy_diastole());
			ps.setDouble(4, io.getTemperature());
			ps.setInt(5, io.getPulse());
			ps.setInt(6, io.getRespiration());
			
			ps.execute();
			
			conn.close();			
		}catch(Exception ex){ex.printStackTrace();}
		
	}
	
	@Override
	public void saveProgressNote(ProgressNote pn) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
			String sql = "INSERT INTO clinic_progress_notes(invoice_no, date, time, physician, note, note_status) VALUES(?, curdate(), curtime(), ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, pn.getInvoice_no());
			ps.setString(2, pn.getPhysician());
			ps.setString(3, pn.getNote());
			ps.setString(4, "open");
			
			ps.execute();
			
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		
	}

	@Override
	public void lockProgressNote(ProgressNote note) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
			String query = "UPDATE clinic_progress_notes "
					+ "SET note_status = ? "
					+ "WHERE invoice_no = ? AND sno = ?";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, "locked");
			ps.setString(2, note.getInvoice_no());
			ps.setInt(3, note.getSno());
			
			ps.executeUpdate();
		}catch(Exception ex){ ex.printStackTrace();}
		
	}
	
	@Override
	public void updateProgressNote(ProgressNote note) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
			String query = "UPDATE clinic_progress_notes "
					+ "SET note = ? "
					+ "WHERE invoice_no = ? AND sno = ?";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, note.getNote());
			ps.setString(2, note.getInvoice_no());
			ps.setInt(3, note.getSno());
			
			ps.executeUpdate();
		}catch(Exception ex){ ex.printStackTrace();}
		
	}
	
	public int getOccupiedBeds() throws RemoteException {
		int occupied = 0;
		try{
		conn = DBConnection.getConnection();
		String query = "select count(*) from beds_view where bed_state = 'occupied' ";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()){
		occupied = rs.getInt(1);
		}
		}catch(Exception ex){ ex.printStackTrace();}
		
		return occupied;
	}
	
	@Override
	public int getFreeBeds() throws RemoteException {
		int free = 0;
		try{
		conn = DBConnection.getConnection();
		String query = "select count(*) from beds_view where bed_state = 'free' ";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()){
		free = rs.getInt(1);
		}
		}catch(Exception ex){ ex.printStackTrace();}
		
		return free;
	}
	
	@Override
	public int getPendingBeds() throws RemoteException {
		int pending = 0;
		try{
		conn = DBConnection.getConnection();
		String query = "select count(*) from beds_view where bed_state = 'pending' ";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()){
			pending = rs.getInt(1);
		}
		}catch(Exception ex){ ex.printStackTrace();}
		
		return pending;
	}
	
	@Override
	public int getAllBedsCount() throws RemoteException {
		int all = 0;
		try{
		conn = DBConnection.getConnection();
		String query = "select count(*) from beds_view";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()){
			all = rs.getInt(1);
		}
		}catch(Exception ex){ ex.printStackTrace();}
		
		return all;
	}
	
	@Override
	public int getAdmissionsCount() throws RemoteException {
		int value = 0;
		try{
			conn = DBConnection.getConnection();
			String query = "select count(*) from admission_slip where admission_status = 'active' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				value = rs.getInt(1);
			}
			}catch(Exception ex){ ex.printStackTrace();}
				
				return value;
	}
	
	@Override
	public ArrayList<InpatientAdmission> searchInpatientAdmission(java.util.Date date1, java.util.Date date2)	throws RemoteException {
		ArrayList<InpatientAdmission> admlist = new ArrayList<InpatientAdmission>();
		java.sql.Date date_1 = new java.sql.Date(date1.getTime());
		java.sql.Date date_2 = new java.sql.Date(date2.getTime());
		
		Statement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBConnection.getConnection();
			String query = "select * from admission_slip where admission_date between '" +date_1+"' and '"+date_2+"' ";
										 						
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
										
				while(rs.next()){
				 InpatientAdmission ipadm = new InpatientAdmission();
				 ipadm.setInvoiceNo(rs.getString(1));			
				 ipadm.setHospitalNo(rs.getString(2));
				 ipadm.setPatientSurname(rs.getString(3));
				 ipadm.setPatientOthernames(rs.getString(4));
				 ipadm.setDob(rs.getDate(5).toString());
				 ipadm.setGender(rs.getString(6));						
				 ipadm.setAdmissionDate(rs.getDate(7).toString());
				 ipadm.setDepartment(rs.getString(8));
				 ipadm.setAdmittingDoctor(rs.getString(9));
				
				 ipadm.setContactRelation(rs.getString(10));
				 ipadm.setContactPhone(rs.getString(11));
				  
				 ipadm.setBedDetails(rs.getString(12));
				 ipadm.setBedCharge(rs.getDouble(13));
				 ipadm.setAdmissionStatus(rs.getString(14));
				 ipadm.setEmrStatus(rs.getString(16));
				 ipadm.setAdmBedDays(rs.getInt(17));
				 ipadm.setBedCode(rs.getString(18));
				 
				 admlist.add(ipadm);
				 														 
			 }
				
			 conn.close();
		}catch(Exception ex){ex.printStackTrace();}
							
		return admlist;
	}

	@Override
	public void updateProcedureState(String invoice_no, String cpt_code) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
			String query = "UPDATE procedure_booking "
					+ "SET procedure_state = ? "
					+ "WHERE invoice_no = ? AND cpt_code = ?";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, "confirmed");
			ps.setString(2, invoice_no);
			ps.setString(3, cpt_code);
			
			ps.executeUpdate();
			
			String queryy4 = "INSERT into operation_notes(invoice_no, cpt_code, name_of_procedure, findings, procedures, drain, "
					+ "details_of_specimen, histology_sent, remarks, surgeon, asst_surgeon, anaesthetist, scrub_nurse, "
					+ " pre_op_diagnosis, post_op_diagnosis, post_op_treatment) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";				
			PreparedStatement pstmt2 = conn.prepareStatement(queryy4);
			pstmt2.setString(1, invoice_no);
			pstmt2.setString(2, cpt_code);
			pstmt2.setString(3, "");
			pstmt2.setString(4, "");
			pstmt2.setString(5, "");
			pstmt2.setString(6, "");
			pstmt2.setString(7, "");
			pstmt2.setString(8, "");
			pstmt2.setString(9, "");
			pstmt2.setString(10, "");
			pstmt2.setString(11, "");
			pstmt2.setString(12, "");
			pstmt2.setString(13, "");
			pstmt2.setString(14, "");
			pstmt2.setString(15, "");
			pstmt2.setString(16, "");
						
			pstmt2.execute();			
			
			conn.close();		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateOperationNotes(String invoice_no, String cpt_code, String a, String b, String c, String d,	String e, String f) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
			String query = "UPDATE operation_notes "
					+ "SET procedure_state = ? "
					+ "WHERE invoice_no = ? AND cpt_code = ?";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, "confirmed");
			ps.setString(2, invoice_no);
			ps.setString(3, cpt_code);
			
			ps.executeUpdate();
			conn.close();
		}catch(Exception ex){ ex.printStackTrace();}
		
	}
	
	@Override
	public void saveNursingNote(String invoice_no, String text, String nurse) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
						
			String queryy4 = "INSERT into nursing_notes(invoice_no, note, nurse, date, time, status) VALUES(?,?,?,curdate(), curtime(), ?)";				
			PreparedStatement pstmt2 = conn.prepareStatement(queryy4);
			pstmt2.setString(1, invoice_no);
			pstmt2.setString(2, text);
			pstmt2.setString(3, nurse);
			pstmt2.setString(4, "open");
													
			pstmt2.execute();			
			
			conn.close();					
		}catch(Exception e){	e.printStackTrace();	}
		
	}
	
	@Override
	public void updateNursingNote(NursingNote note) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
						
			String queryy4 = "UPDATE nursing_notes "
							+ "SET note = ? "
							+ "WHERE sno = ?";				
			PreparedStatement pstmt2 = conn.prepareStatement(queryy4);
			
			pstmt2.setString(1, note.getNote());
			pstmt2.setInt(2, note.getSerialNo());
			
													
			pstmt2.execute();			
			
			conn.close();					
		}catch(Exception e){	e.printStackTrace();	}
		
	}
	
	@Override
	public void updateNursingNoteStatus(NursingNote note) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
						
			String queryy4 = "UPDATE nursing_notes "
							+ "SET status = ? "
							+ "WHERE sno = ?";				
			PreparedStatement pstmt2 = conn.prepareStatement(queryy4);
			
			pstmt2.setString(1, "locked");
			pstmt2.setInt(2, note.getSerialNo());
			
													
			pstmt2.execute();			
			
			conn.close();					
		}catch(Exception e){	e.printStackTrace();	}
		
	}

	@Override
	public ArrayList<NursingNote> getNursingNotes(String invoice_no) throws RemoteException {
		ArrayList<NursingNote> notelist = new ArrayList<NursingNote>();
		
		
		Statement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBConnection.getConnection();
			String query = "select * from nursing_notes  where invoice_no = '" + invoice_no +"' ";
										 						
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
										
				while(rs.next()){
					NursingNote nn = new NursingNote();
					
					nn.setInvoiceNo(rs.getString(2));
					nn.setNote(rs.getString(3));
					nn.setNurse(rs.getString(4));
					nn.setDate(rs.getDate(5).toString());
					nn.setTime(rs.getTime(6).toString());
					nn.setSerialNo(rs.getInt(1));
					nn.setStatus(rs.getString(7));
				 
					notelist.add(nn);
				 														 
			 }
				
			 conn.close();
		}catch(Exception ex){ex.printStackTrace();}
							
		return notelist;
	}

	@Override
	public void saveOperationNotes(OperationNote opn) throws RemoteException {
		try {
			conn = DBConnection.getConnection();
			String query2 = "update operation_notes "
					+ " set name_of_procedure = ?, findings = ?, procedures = ?, drain = ?, details_of_specimen = ?, histology_sent = ?, "
					+ " remarks = ?, surgeon = ?, asst_surgeon = ?, anaesthetist = ?, scrub_nurse = ?, pre_op_diagnosis = ?, "
					+ " post_op_diagnosis = ?, post_op_treatment = ? where invoice_no = ? and cpt_code =? ";
			PreparedStatement pms;
			pms = conn.prepareStatement(query2);
			
			pms.setString(1, opn.getProcedureName());
			pms.setString(2, opn.getFindings());
			pms.setString(3, opn.getProcedure());
			pms.setString(4, opn.getDrain());
			pms.setString(5, opn.getDetailsOfSpecimen());
			pms.setString(6, opn.getHistologySent());
			pms.setString(7, opn.getRemarks());
			pms.setString(8, opn.getSurgeon());
			pms.setString(9, opn.getAsstSurgeon());
			pms.setString(10, opn.getAnaesthetist());
			pms.setString(11, opn.getScrubNurse());
			pms.setString(12, opn.getPreOpDiagnosis());
			pms.setString(13, opn.getPostOpDiagnosis());
			pms.setString(14, opn.getPostOpTreatment());
			pms.setString(15, opn.getInvoiceNo());
			pms.setString(16, opn.getCPTCode());			
			
			pms.executeUpdate();
			conn.close();
		} catch (SQLException | ClassNotFoundException e) {	e.printStackTrace();	}
		
		
	}

	@Override
	public OperationNote getOperationNote(String invoice_no, String cpt_code) throws RemoteException {
		Statement stmt = null;
		ResultSet rs = null;
		OperationNote opn = new OperationNote();
		try{
			conn = DBConnection.getConnection();
			String query = "select * from operation_notes  where invoice_no = '" + invoice_no +"' and cpt_code = '" +cpt_code +"'";
										 						
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
										
				while(rs.next()){
				opn.setInvoiceNo(rs.getString(2));
				opn.setProcedureName(rs.getString(4));	
				opn.setFindings(rs.getString(5));
				opn.setProcedure(rs.getString(6));
				opn.setDrain(rs.getString(7));
				opn.setDetailsOfSpecimen(rs.getString(8));
				opn.setHistologySent(rs.getString(9));
				opn.setRemarks(rs.getString(10));
				opn.setSurgeon(rs.getString(11));
				opn.setAsstSurgeon(rs.getString(12));
				opn.setAnaesthetist(rs.getString(13));
				opn.setScrubNurse(rs.getString(14));
				opn.setPreOpDiagnosis(rs.getString(15));
				opn.setPostOpDiagnosis(rs.getString(16));
				opn.setPostOpTreatment(rs.getString(17));
				
			 }
				
			 conn.close();
		}catch(Exception ex){ex.printStackTrace();}
		return opn;
	}
	
	@Override
	public ArrayList<PatientVisit> getOutPatientVisit(String visit_status, java.util.Date date1, java.util.Date date2)	throws RemoteException {
		ArrayList<PatientVisit> visitList = new  ArrayList<PatientVisit>();
		java.sql.Date date_1 = new java.sql.Date(date1.getTime());
		java.sql.Date date_2 = new java.sql.Date(date2.getTime());
		
		
		 Statement stmt1 = null;
		 ResultSet rs = null;
		 try{
			 conn = DBConnection.getConnection();
			 String query = "select * from patient_visit_view where clinic = 'opd' and visit_date between '" +date_1+"' and '"+date_2+"' ";
			 String query1 = "select * from patient_visit_view where clinic = 'opd' and visit_status = '" +visit_status+"' and visit_date between '" +date_1+"' and '"+date_2+"'";
			 
			 if(visit_status.equalsIgnoreCase("all")){
					stmt1 = conn.createStatement();
					rs = stmt1.executeQuery(query);					
				}else {
					stmt1 = conn.createStatement();					
					rs = stmt1.executeQuery(query1);					
				}
			
			 //extract data from result set
				while(rs.next()){
					PatientVisit pv = new PatientVisit();
					pv.setInvoiceNo(rs.getString(1));
				
					pv.setHospitalNo(rs.getString(2));
					pv.setSurname(rs.getString(10));
					pv.setOthernames(rs.getString(11));
					pv.setDob(rs.getDate(12).toString());
					pv.setGender(rs.getString(13));
					pv.setClinic(rs.getString(6));
					pv.setDoctor(rs.getString(3));
					pv.setDate(rs.getDate(4).toString());
					pv.setTime(rs.getString(5));
					pv.setStatus(rs.getString(7));
					pv.setEmrSratus(rs.getString(9));
					
					
					visitList.add(pv);							
				}
				conn.close();
		 }catch(Exception e){ 	 e.printStackTrace();		 }	
		
		return visitList;
	}
	
	
	@Override
	public void dischargeOutpatient(String invoice_no, DischargeSummary ds) throws RemoteException {
		
		try{
			conn = DBConnection.getConnection();
			String sql_one = "UPDATE discharge_summaries "
					+ "SET date = curdate(), time = curtime(), discharge_mode = ?, discharge_note = ?, physician_name = ? "
					+ " WHERE invoice_no = ?";
			
			String sql_four = "UPDATE patient_visit "
					+ "SET visit_status = ? "
					+ "WHERE invoice_no = ?";
		
			
			
			PreparedStatement ps_one = conn.prepareStatement(sql_one);
			ps_one.setString(4, invoice_no);
			ps_one.setString(1, ds.getDischargeMOde());
			ps_one.setString(2, ds.getDischargeNote());
			ps_one.setString(3, ds.getPhysician());
			ps_one.executeUpdate();
			
			
			PreparedStatement ps_four = conn.prepareStatement(sql_four);
			ps_four.setString(1, "discharged");
			ps_four.setString(2, invoice_no);
			ps_four.executeUpdate();		
		
			
			conn.close();
			}catch(Exception ex){ ex.printStackTrace();}
		
	}

	@Override
	public DischargeSummary getDishargeSummary(String invoice_no) throws RemoteException {
		DischargeSummary dds = new DischargeSummary();
		 Statement stmt1 = null;
		 ResultSet rs = null;
		 try{
			 conn = DBConnection.getConnection();
			 String query = "select * from discharge_summaries where invoice_no = '"+ invoice_no + "'";
			 stmt1 = conn.createStatement();
				rs = stmt1.executeQuery(query);	
				while(rs.next()){
					dds.setDate(rs.getDate(3)+"");
					dds.setTime(rs.getTime(4).toString());
					dds.setDischargeMOde(rs.getString(5));
					dds.setDischargeNote(rs.getString(6));
					dds.setPhysician(rs.getString(7));
				}
				conn.close();
		 }catch(Exception e){ 	 e.printStackTrace();		 }	
		 return dds;
	}

	@Override
	public ArrayList<InpatientAdmission> searchInpatientAdmission(String text, String Searchby) throws RemoteException {
		ArrayList<InpatientAdmission> admlist = new ArrayList<InpatientAdmission>();
				
		Statement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBConnection.getConnection();
			 String query = "select * from admission_slip where invoice_no = '" + text +"' ";			 
			 String query1 = "select * from admission_slip where hospital_no = '" + text +"' ";
			 String  query2 = "Select * from admission_slip "
				 		+ "where (patient_surname like '%" +text+"%' OR patient_firstname like '%" +text+"%') ";
				 		
			 
			 if(Searchby.equalsIgnoreCase("invoice number")){
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);					
				}else if(Searchby.equalsIgnoreCase("hospital number")){
					stmt = conn.createStatement();					
					rs = stmt.executeQuery(query1);					
				}else if(Searchby.equalsIgnoreCase("patient name")){
					stmt = conn.createStatement();					
					rs = stmt.executeQuery(query2);					
				}
			 while(rs.next()){
				 InpatientAdmission ipadm = new InpatientAdmission();
				 ipadm.setInvoiceNo(rs.getString(1));			
				 ipadm.setHospitalNo(rs.getString(2));
				 ipadm.setPatientSurname(rs.getString(3));
				 ipadm.setPatientOthernames(rs.getString(4));
				 ipadm.setDob(rs.getDate(5).toString());
				 ipadm.setGender(rs.getString(6));						
				 ipadm.setAdmissionDate(rs.getDate(7).toString());
				 ipadm.setDepartment(rs.getString(8));
				 ipadm.setAdmittingDoctor(rs.getString(9));
				
				 ipadm.setContactRelation(rs.getString(10));
				 ipadm.setContactPhone(rs.getString(11));
				  
				 ipadm.setBedDetails(rs.getString(12));
				 ipadm.setBedCharge(rs.getDouble(13));
				 ipadm.setAdmissionStatus(rs.getString(14));
				 ipadm.setEmrStatus(rs.getString(16));
				 ipadm.setAdmBedDays(rs.getInt(17));
				 ipadm.setBedCode(rs.getString(18));
				 admlist.add(ipadm);				 
			 }
			 conn.close();
		}catch(Exception ex){ex.printStackTrace();}	
		
		return admlist;
	}

	@Override
	public int searchActivePatient(String hosp_no) throws RemoteException {
		
		 Statement stmt1 = null;
		 ResultSet rs = null;
		 
		 int xx = -1;
		 try{
			 conn = DBConnection.getConnection();
			 String query = "select count(*) from patient_visit where hospital_no = '"+ hosp_no + "' AND visit_status = 'active' ";
			 stmt1 = conn.createStatement();
				rs = stmt1.executeQuery(query);	
				while(rs.next()){		xx=rs.getInt(1);	}
				conn.close();
		 }catch(Exception e){ 	 e.printStackTrace();		 }	
		
		return xx;
	}

	@Override
	public String createProcedure(ProcedureItem pitem) throws RemoteException {
		int countvalue = 0;
		 Statement stmt;
		 String pcId = "";
		 
		 try{
				//create a connection to db with 2 statements		
				conn=DBConnection.getConnection();
				
				stmt =conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT value FROM counter where id =" +8 );
				while(rs.next()){
					countvalue =  rs.getInt("value");
					pcId = "PC-" +leftPad("" + countvalue, 3); 
					
					//Now insert a new case
					String query = "INSERT into clinic_procedures(procedure_id, procedure_desc, cpt_code)" + "values (?,?,?)";

					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1, pcId);
					pstmt.setString(2, pitem.getProcedureDesc());
					pstmt.setString(3,"");
					
					pstmt.execute();					
				}
										
				String query2 = "update counter set value = ? where id = ?";
				PreparedStatement pms = conn.prepareStatement(query2);
				pms.setInt(1, countvalue+1);
				pms.setInt(2, 8);
				pms.executeUpdate();
				
				conn.close();									
			}catch(Exception e){	e.printStackTrace();	}
		 		 	 
		 return pcId;	}

	@Override
	public void updateProcedure(ProcedureItem pitem) throws RemoteException {
		try {
			conn=DBConnection.getConnection();
			String query2 = "UPDATE clinic_procedures "
								+ "SET procedure_desc = ?, cpt_code = ? "
								+ "WHERE procedure_id = ? ";
			PreparedStatement pms = conn.prepareStatement(query2);
			pms.setString(1, pitem.getProcedureDesc());
			pms.setString(2, pitem.getCptCode());
			pms.setString(3, pitem.getProcedureCode());			
								
			pms.executeUpdate();
			
			conn.close();														
		} catch (Exception e) {	e.printStackTrace();	}
	}

	@Override
	public ArrayList<ProcedureItem> getAllProcedureItems() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<InpatientAdmission> searchInpatientAdmission(String status) throws RemoteException {
		ArrayList<InpatientAdmission> admlist = new ArrayList<InpatientAdmission>();
		
		
		Statement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBConnection.getConnection();
			 String query = "select * from admission_slip where admission_status = 'active' ";					 
			 
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);					
				
			 while(rs.next()){
				 InpatientAdmission ipadm = new InpatientAdmission();
				
				 ipadm.setInvoiceNo(rs.getString(1));			
				 ipadm.setHospitalNo(rs.getString(2));
				 ipadm.setPatientSurname(rs.getString(3));
				 ipadm.setPatientOthernames(rs.getString(4));
				 ipadm.setDob(rs.getDate(5)+"");
				 ipadm.setGender(rs.getString(6));						
				 ipadm.setAdmissionDate(rs.getDate(7).toString());
				 ipadm.setDepartment(rs.getString(8));
				 ipadm.setAdmittingDoctor(rs.getString(9));
				
				 ipadm.setContactRelation(rs.getString(10));
				 ipadm.setContactPhone(rs.getString(11));
				  
				 ipadm.setBedDetails(rs.getString(12));
				 ipadm.setBedCharge(rs.getDouble(13));
				 ipadm.setAdmissionStatus(rs.getString(14));
				 ipadm.setEmrStatus(rs.getString(16));
				 ipadm.setAdmBedDays(rs.getInt(17));
				 ipadm.setBedCode(rs.getString(18));
				 
				 admlist.add(ipadm);				 
			 }
			 conn.close();
		}catch(Exception ex){ex.printStackTrace();}	
		
		return admlist;
	}

	@Override
	public ArrayList<PatientVisit> searchPatientVisit(String status) throws RemoteException {
		ArrayList<PatientVisit> visitList = new  ArrayList<PatientVisit>();
		
		 Statement stmt1 = null;
		 ResultSet rs = null;
		 try{
			 conn = DBConnection.getConnection();
			 String query = "select * from patient_visit_view "		
					 		+ "where visit_status = 'active' and clinic = 'opd' ";
			
			 
		
					stmt1 = conn.createStatement();
					rs = stmt1.executeQuery(query);
								
			//extract data from result set
				while(rs.next()){
					PatientVisit pv = new PatientVisit();
					pv.setInvoiceNo(rs.getString(1));
				
					pv.setHospitalNo(rs.getString(2));
					pv.setSurname(rs.getString(10));
					pv.setOthernames(rs.getString(11));
					pv.setDob(rs.getDate(12).toString());
					pv.setGender(rs.getString(13));
					pv.setClinic(rs.getString(6));
					pv.setDoctor(rs.getString(3));
					pv.setDate(rs.getDate(4).toString());
					pv.setTime(rs.getString(5));
					pv.setStatus(rs.getString(7));
					pv.setEmrSratus(rs.getString(9));
					
					
					visitList.add(pv);				
				}
				conn.close();
		 }catch(Exception e){ 	 e.printStackTrace();		 }	
		
		return visitList;
	}

	
	@Override
	public void updateConsultationNote(ConsultationNote cNote) throws RemoteException {
		try {
			conn=DBConnection.getConnection();
			String query2 = "UPDATE consultation_notes "
								+ "SET past_history = ?, complaints = ?, physical_exam = ?, treatment_plan = ? "
								+ "WHERE invoice_no = ? ";
			PreparedStatement pms = conn.prepareStatement(query2);
			pms.setString(1, cNote.getPatientHistory());
			pms.setString(2, cNote.getComplaints());
			pms.setString(3, cNote.getPhysicalExam());	
			pms.setString(4, cNote.getTreatmentPlan());
			pms.setString(5, cNote.getInvoiceNo());	
								
			pms.executeUpdate();
			
			conn.close();														
		} catch (Exception e) {	e.printStackTrace();	}
		
	}

	
	@Override
	public ConsultationNote getConsultationNote(String invoice_no) {
		ConsultationNote note = new ConsultationNote();
		Statement stmt1 = null;
		 ResultSet rs = null;
		 try{
			 conn = DBConnection.getConnection();
			 String query = "select * from consultation_notes where invoice_no = '"+ invoice_no + "'";
			 stmt1 = conn.createStatement();
				rs = stmt1.executeQuery(query);	
				while(rs.next()){
					note.setInvoiceNo(invoice_no);;
					note.setPatientHistory(rs.getString(3));
					note.setComplaints(rs.getString(4));
					note.setPhysicalExam(rs.getString(5));
					note.setTreatmentPlan(rs.getString(6));
					}
				conn.close();
		 }catch(Exception e){ 
			 e.printStackTrace();		
			 }
		 return note;
		 
	}

	@Override
	public void endConsultation(String invoice_no) throws RemoteException {
		try {
			conn=DBConnection.getConnection();
			String query2 = "UPDATE patient_visit "
								+ "SET emr_status = ? "
								+ "WHERE invoice_no = ? ";
			PreparedStatement pms = conn.prepareStatement(query2);
			pms.setString(1, "closed");
			pms.setString(2,invoice_no);
			
								
			pms.executeUpdate();
			
			conn.close();														
		} catch (Exception e) {	e.printStackTrace();	}
		
	}

	
	@Override	
	public void updateVSEntry(VitalSignEntry vs) throws RemoteException {
		try{
			conn = DBConnection.getConnection();
			String query = "UPDATE vital_signs_entries "
					+ "SET date = curdate(), time = curtime(), height = ?, weight = ?, bmi = ?, temperature = ?, bp_systole = ?, "
					+ "bp_diastole = ?, pulse_rate = ?, respiration_rate = ? "
					+ "WHERE sno = ? ";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, vs.getHeight());
			ps.setInt(2, vs.getWeight());
			ps.setDouble(3, 0.00);
			ps.setDouble(4, vs.getTemperature());
			ps.setInt(6, vs.getBpDiastole());
			ps.setInt(5, vs.getBpSystole());
			ps.setInt(7, vs.getPulseRate());
			ps.setInt(8, vs.getRespirationRate());
			ps.setInt(9, vs.getSerialNo());
			
			ps.executeUpdate();
			conn.close();
		}catch(Exception ex){ ex.printStackTrace();}
		
	}

	@Override
	public void updatePatientProcedure(BookedProcedure procedure) throws RemoteException {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date1 = sdf.parse(procedure.getDate());
			java.sql.Date date = new java.sql.Date(date1.getTime());
			conn = DBConnection.getConnection();
			String query = "UPDATE procedure_booking "
					+ "SET clinic_procedure = ?, cpt_code= ?, anesthesia = ?, theatre = ?, date = ?, time = ?, procedure_cost = ?, "
					+ "anesthesia_cost = ?, remarks = ?, procedure_state = ? "
					+ "WHERE sno = ? ";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, procedure.getProcedure());
			ps.setString(2, procedure.getCptCode());
			ps.setString(3, procedure.getAnesthesia());
			ps.setString(4, procedure.getTheatre());
			ps.setDate(5, date);
			ps.setString(6, procedure.getTime());
			ps.setDouble(7, procedure.getProcedureCost());
			ps.setDouble(8, procedure.getAnesthesiaCost());
			ps.setString(9, procedure.getRemarks());
			ps.setString(10, "pending");
			ps.setInt(11, Integer.parseInt(procedure.getSno()));
			
			
			ps.executeUpdate();
			conn.close();
		}catch(Exception ex){ ex.printStackTrace();}
		
	}

	//end of methods

}
