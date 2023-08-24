package com.ahms.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.ahms.api.HospitalManagementInterface;
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


public class HospitalManagementImplementation implements HospitalManagementInterface {
	
	private Connection conn;

	public HospitalManagementImplementation() {	}
	
	// login
	@Override
	public UserCard getUserCard(UserCard uc) throws RemoteException {
		UserCard ucx = new UserCard();
		try{
		Connection conn = DBConnection.getConnection();
		Statement st = conn.createStatement();
		String sql = "SELECT * FROM application_users "
				+ "WHERE username = '"+ uc.getUsername()+"' "
						+ "AND password = '"+ uc.getPassword()+"' ";
								
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()){
			ucx.setFullNames(rs.getString(2));
			ucx.setUsername(rs.getString(4));
			ucx.setRole(rs.getString(6));
			ucx.setStatus(rs.getString(3));
			ucx.setPassword(rs.getString(5));
			
		}
		conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		return ucx;
	}

	
	//hospital administration

	@Override
	public ArrayList<InventoryDepartment> getInventoryDepartments() throws RemoteException {
		ArrayList<InventoryDepartment> list = new  ArrayList<InventoryDepartment>();
		 String query = "select * from department_store";
		 try{
			 conn = DBConnection.getConnection();
			 Statement st = conn.createStatement();
			 ResultSet rs = st.executeQuery(query);
			 while(rs.next()){
				 list.add(new InventoryDepartment(rs.getString(2), rs.getString(3)));
			 }
		 }catch(Exception ex){ ex.printStackTrace();}
		 
		 return list;
	}

	@Override
	public Product saveProduct(Product p) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createUser(UserCard ucd) throws RemoteException {

		
		//Now insert a new case
		String query = "INSERT into application_users(full_names, username, password, role, status)" + "values (?,?,?,?,?)";

		try{
			conn=DBConnection.getConnection();
			
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, ucd.getFullNames());
		pstmt.setString(2, ucd.getUsername());
		
		pstmt.setString(3, ucd.getPassword());
		pstmt.setString(4, ucd.getRole());
		pstmt.setString(5, ucd.getStatus());		
							
		pstmt.execute();
		}catch(Exception ex){ ex.printStackTrace(); }
		
		
	}

	@Override
	public ArrayList<UserCard> getAllUsers() throws RemoteException {
		ArrayList<UserCard> list = new ArrayList<UserCard>();
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from application_users";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
				UserCard fc = new UserCard();
				fc.setFullNames(rs.getString(2));
				fc.setStatus(rs.getString(3));
				fc.setUsername(rs.getString(4));
				fc.setPassword(rs.getString(5));
				fc.setRole(rs.getString(6));
				
				list.add(fc);				
			}
			conn.close();
								
		} catch (Exception e) {	e.printStackTrace();	}	
		
		return list;
	}

	@Override
	public void updateUser(UserCard ucd) throws RemoteException {
		try {
			conn=DBConnection.getConnection();
			String query2 = "UPDATE application_users "
								+ "SET full_names = ?, password = ?, role = ?,  status = ?"
								+ "WHERE username = ? ";
			PreparedStatement pms = conn.prepareStatement(query2);
			pms.setString(1, ucd.getFullNames());
			pms.setString(5, ucd.getUsername());
			pms.setString(2, ucd.getPassword());
			pms.setString(3, ucd.getRole());
			pms.setString(4, ucd.getStatus());
								
			pms.executeUpdate();
			
			conn.close();														
		} catch (Exception e) {	e.printStackTrace();	}
		
	}


	
	@Override
	public ArrayList<State> getAllStates() throws RemoteException {
		ArrayList<State> statelist = new ArrayList<State>();
		try {
			//Execute a query
			conn = DBConnection.getConnection();
			Statement	stmt = conn.createStatement();
								
			String sql = "SELECT * FROM nigerian_states order by 3";
			ResultSet rs = stmt.executeQuery(sql);
								
			//Extract data from result set
			while(rs.next()){		
				statelist.add(new State(rs.getString(2), rs.getString(3), rs.getString(3)));	
				}
			conn.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return statelist;
	}

	@Override
	public ArrayList<LGA> getLGAs(String state_id) throws RemoteException {
		ArrayList<LGA> lgalist = new ArrayList<>();
		Statement stmt;
		ResultSet rs;
		
		try{
			conn = DBConnection.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("SELECT * FROM local_govt_areas WHERE state_id= '" + state_id +"' ");
			
			LGA lclgvt;
			while(rs.next()){				
				lclgvt = new LGA(rs.getInt(1)+"", rs.getString(2), rs.getString(3));
				lgalist.add(lclgvt);
			}
			conn.close();			
		}catch(Exception ex){ ex.printStackTrace(); }
		
		return lgalist;
	}  //end of method

	@Override
	public PatientBiodata saveNewRegistration(PatientBiodata pat) throws RemoteException {
		int countValue = 0;
		
		java.sql.Date sqldob = null;    	
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	try {
    		java.util.Date date = sdf.parse(pat.getDob());    		    		
    		sqldob = new java.sql.Date(date.getTime());
    		
    		
    		conn = DBConnection.getConnection();
    		// query to find latest available registration number number and attach it to the new patient.
			Statement stmt=conn.createStatement();
			ResultSet rs = stmt.executeQuery("select value from counter where id= "+ 10);
			
    		
			while(rs.next()){
				
				countValue =  rs.getInt("value");
				String hospiNum = leftPad("" + countValue, 6); 
				
				String query = "INSERT into patient_biodata(hospital_no, patient_surname, patient_firstname, reg_date, patient_dob, "
						+ "gender, blood_group, genotype, marital_status, nationality, state, occupation, religion, phone_mobile, email, "
						+ "patient_address1, nok_surname, nok_address, nok_relationship, nok_mobile, reg_charge, family_no, reg_offr)" 
						+ "values(?,?,?, curdate(), ?,?,?,  ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?)";
				
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, hospiNum);
				pstmt.setString(2, pat.getSurname());
				pstmt.setString(3, pat.getFirstname());				
				pstmt.setDate(4, sqldob);				
				pstmt.setString(5, pat.getGender());
				pstmt.setString(6, pat.getBloodGroup());
				pstmt.setString(7, pat.getGenotype());
				pstmt.setString(8, pat.getMaritalStatus());				
				pstmt.setString(9, pat.getNationality());
				pstmt.setString(10, pat.getState());				
				pstmt.setString(11, pat.getOccupation());
				pstmt.setString(12, pat.getReligion());				
				pstmt.setString(13, pat.getPhoneMobile());							
				pstmt.setString(14, pat.getEmail());				
				pstmt.setString(15, pat.getAddress1());								
				pstmt.setString(16, pat.getNok_surname());				
				pstmt.setString(17, pat.getNok_address());
				pstmt.setString(18, pat.getNok_relationship());
				pstmt.setString(19, pat.getNok_mobile());
				
				pstmt.setDouble(20, pat.getCharge());
				pstmt.setString(21, pat.getFamilyNo());
				pstmt.setString(22, pat.getRegistrationOfficer());
				
				pstmt.execute();
				pat.setHospital_no(hospiNum);
				
			}//end while
    		
			String query2 = "update counter set value = ? where id = ?";
			PreparedStatement pms = conn.prepareStatement(query2);
			pms.setInt(1, countValue+1);
			pms.setInt(2, 10);
			pms.executeUpdate();
				    		
    		conn.close();
    		} catch (Exception e) {		e.printStackTrace();   		}
		
		
		return pat;
	}

	@Override
	public ArrayList<PatientBiodata> getPatient(String searchText, String searchBy) throws RemoteException {
		ArrayList<PatientBiodata> patlist = new ArrayList<PatientBiodata>();
		
		Statement stmt1 = null;
		ResultSet rs = null;
		try{
			conn = DBConnection.getConnection();
			String query11 = "SELECT * FROM patient_biodata where patient_surname LIKE '" +searchText+ "%' " +"OR patient_firstname LIKE '" +searchText+"%' ";
			String query10 = "SELECT * FROM patient_biodata where hospital_no = '" +searchText+ "' ";
			
			
			if(searchBy.equalsIgnoreCase("Patient Name")){
				stmt1 = conn.createStatement();
				rs = stmt1.executeQuery(query11);
				
			}else if(searchBy.equalsIgnoreCase("Hospital Number")){
				stmt1 = conn.createStatement();					
				rs = stmt1.executeQuery(query10);
			}
				//extract data from result set
				while(rs.next()){
					//now set the attributes of the object
					PatientBiodata pbd = new PatientBiodata();
					
					pbd.setHospital_no(rs.getString(2));					
					pbd.setSurname(rs.getString(3));
					pbd.setFirstname(rs.getString(4));								
					pbd.setRegDate(rs.getString(5));
					pbd.setDob(rs.getString(6));					
					pbd.setGender(rs.getString(7));					
					pbd.setBloodGroup(rs.getString(8));
					pbd.setGenotype(rs.getString(9));					
					pbd.setNationality(rs.getString(11));
					pbd.setState(rs.getString(12));			
					pbd.setMaritalStatus(rs.getString(10));
					pbd.setReligion(rs.getString(14));
					pbd.setOccupation(rs.getString(13));
					pbd.setAddress1(rs.getString(17));		
					pbd.setPhoneMobile(rs.getString(15));				
					pbd.setEmail(rs.getString(16));				
					pbd.setNok_surname(rs.getString(18));					
					pbd.setNok_address(rs.getString(19));
					pbd.setNok_relationship(rs.getString(20));
					pbd.setNok_mobile(rs.getString(21));
					pbd.setRegistrationOfficer(rs.getString(22));
					pbd.setCharge(rs.getDouble(23));
					pbd.setFamilyNo(rs.getString(24));
					
					
					patlist.add(pbd);
				
				}
				   
				conn.close();
		}catch(Exception ex){	ex.printStackTrace();	}
		
		return patlist;
	}
	
	
	@Override
	public ArrayList<PatientBiodata> getPatient(String family_no) throws RemoteException {
		ArrayList<PatientBiodata> patlist = new ArrayList<PatientBiodata>();
		
		Statement stmt1 = null;
		ResultSet rs = null;
		try{
			conn = DBConnection.getConnection();
			
			String query10 = "SELECT * FROM patient_biodata where family_no = '" +family_no+ "' ";
			
			
			
				stmt1 = conn.createStatement();					
				rs = stmt1.executeQuery(query10);
				while(rs.next()){
					//now set the attributes of the object
					PatientBiodata pbd = new PatientBiodata();
					pbd.setHospital_no(rs.getString(2));
					
					pbd.setSurname(rs.getString(3));
					pbd.setFirstname(rs.getString(4));
					
								
					pbd.setRegDate(rs.getString(5));
					pbd.setDob(rs.getString(6));					
					pbd.setGender(rs.getString(7));					
					pbd.setBloodGroup(rs.getString(8));
					pbd.setGenotype(rs.getString(9));					
					pbd.setNationality(rs.getString(11));
					pbd.setState(rs.getString(12));			
					pbd.setMaritalStatus(rs.getString(10));
					pbd.setReligion(rs.getString(14));
					pbd.setOccupation(rs.getString(13));
					pbd.setAddress1(rs.getString(17));		
					pbd.setPhoneMobile(rs.getString(15));				
					pbd.setEmail(rs.getString(16));				
					pbd.setNok_surname(rs.getString(18));					
					pbd.setNok_address(rs.getString(19));
					pbd.setNok_relationship(rs.getString(20));
					pbd.setNok_mobile(rs.getString(21));
					pbd.setRegistrationOfficer(rs.getString(22));
					pbd.setCharge(rs.getDouble(23));
					pbd.setFamilyNo(rs.getString(24));
					
					patlist.add(pbd);
				
				}
				   
				conn.close();
		}catch(Exception ex){	ex.printStackTrace();	}
		
		return patlist;
	}

	@Override
	public ArrayList<PatientBiodata> getPatient(Date date1, Date date2) throws RemoteException {
		ArrayList<PatientBiodata> patlist = new ArrayList<PatientBiodata>();
		
		Statement stmt1 = null;
		ResultSet rs = null;
		
		java.sql.Date dateOne = new java.sql.Date(date1.getTime());
		java.sql.Date dateTwo = new java.sql.Date(date2.getTime());
		
		
		try{
				conn = DBConnection.getConnection();			
			String query = "SELECT * FROM patient_biodata "
					+ "where reg_date between '" + dateOne + "' and '" + dateTwo + "' ";
							
				stmt1 = conn.createStatement();
				rs = stmt1.executeQuery(query);
						
				//extract data from result set 
				while(rs.next()){
					//now set the attributes of the object
					PatientBiodata pbd = new PatientBiodata();
					pbd.setHospital_no(rs.getString(2));
					
					pbd.setSurname(rs.getString(3));
					pbd.setFirstname(rs.getString(4));
					
								
					pbd.setRegDate(rs.getString(5));
					pbd.setDob(rs.getString(6));					
					pbd.setGender(rs.getString(7));					
					pbd.setBloodGroup(rs.getString(8));
					pbd.setGenotype(rs.getString(9));					
					pbd.setNationality(rs.getString(11));
					pbd.setState(rs.getString(12));			
					pbd.setMaritalStatus(rs.getString(10));
					pbd.setReligion(rs.getString(14));
					pbd.setOccupation(rs.getString(13));
					pbd.setAddress1(rs.getString(17));		
					pbd.setPhoneMobile(rs.getString(15));				
					pbd.setEmail(rs.getString(16));				
					pbd.setNok_surname(rs.getString(18));					
					pbd.setNok_address(rs.getString(19));
					pbd.setNok_relationship(rs.getString(20));
					pbd.setNok_mobile(rs.getString(21));
					pbd.setRegistrationOfficer(rs.getString(22));
					pbd.setCharge(rs.getDouble(23));
					pbd.setFamilyNo(rs.getString(24));
					
					patlist.add(pbd);
				
				}
				  
				conn.close();
		}catch(Exception ex){	ex.printStackTrace();	}
		return patlist;
	}
	
	public String leftPad(String str, int xy){
		String emptystr = "";
		int diff = xy - str.length();
		
		for(int i=0; i<diff; i++){
		emptystr = emptystr.concat("0");
		}
		
		return emptystr+str;
	
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
	public ArrayList<Doctor> getAllDoctors() throws RemoteException {
		ArrayList<Doctor> doclist = new ArrayList<>();
		
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * from doctor";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Doctor doctor = new Doctor();
				doctor.setDoctorId(rs.getString(2));
				doctor.setDoctorName(rs.getString(3));
				doctor.setQualifications(rs.getString(4));
				doctor.setSpecialty(rs.getString(5));
				doctor.setStatus(rs.getString(6));
				
				
				doclist.add(doctor);
			}
			conn.close();
		}catch(Exception e){ e.printStackTrace();}
		
		return doclist; 
	}
	
	@Override
	public ArrayList<Doctor> getAllDoctors(String status) throws RemoteException {
		ArrayList<Doctor> doclist = new ArrayList<>();
		
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * "
					+ "from doctor where status = '"+status+"' ";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Doctor doctor = new Doctor();
				doctor.setDoctorId(rs.getString(2));
				doctor.setDoctorName(rs.getString(3));
				doctor.setQualifications(rs.getString(4));
				doctor.setSpecialty(rs.getString(5));
				doctor.setStatus(rs.getString(6));
				
				
				doclist.add(doctor);
			}
			conn.close();
		}catch(Exception e){ e.printStackTrace();}
		
		return doclist; 
	}
	
	@Override
	public void saveAppointment(String hosp_no, String doc_id, java.sql.Date date, String time, String status)throws RemoteException {
		try {
			conn = DBConnection.getConnection();
			String query = "INSERT into opd_appointments(hospital_no, doc_id, date, time, state) VALUES(?, ?, ?, ?, ?)";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, hosp_no);
			pstmt.setString(2, doc_id);
			pstmt.setDate(3, date);
			pstmt.setString(4, time);
			pstmt.setString(5, status);
			
			pstmt.execute();
			conn.close();
		} catch (Exception e) {		e.printStackTrace();	}
	}
	
	@Override
	public ArrayList<Appointment> searchAppointment(java.sql.Date date1, java.sql.Date date2) throws RemoteException {
		ArrayList<Appointment> apptList = new ArrayList<>();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * "
					+ "FROM opd_appointments_view "
					+ "WHERE date between '" + date1 +"' and '" + date2 +"'"; 
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Appointment appt = new Appointment();
				appt.setHospitalNno(rs.getString(1));
				appt.setSurname(rs.getString(2));
				appt.setOthernames(rs.getString(3));
				appt.setDoctorName(rs.getString(6));
				appt.setDate(rs.getDate(7).toString());
				appt.setTime(rs.getString(8));
				appt.setState(rs.getString(9));
				
				apptList.add(appt);					
				
			}
			conn.close();
		}catch(Exception c){c.printStackTrace();}
	
	return apptList;
	}
	
	@Override
	public ArrayList<Appointment> searchAppointment(String search_criterion, String search_text) throws RemoteException {
		ArrayList<Appointment> apptList = new ArrayList<>();
		try{
			conn = DBConnection.getConnection();
			String queryString = "";
			
			if (search_criterion.equalsIgnoreCase("patient name")) {
				queryString = "SELECT * "
						+ "FROM opd_appointments_view "
						+ "WHERE patient_surname LIKE '%" +search_text+"%' OR patient_firstname LIKE '%" +search_text+"%' ";
			}else if (search_criterion.equalsIgnoreCase("hospital number")) {
				queryString = "SELECT * "
						+ "FROM opd_appointments_view "
						+ "WHERE hospital_no = '" +search_text+"'";
			}
			
			
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(queryString);
			while(rs.next()){
				Appointment appt = new Appointment();
				appt.setHospitalNno(rs.getString(1));
				appt.setSurname(rs.getString(2));
				appt.setOthernames(rs.getString(3));
				appt.setDoctorName(rs.getString(6));
				appt.setDate(rs.getDate(7).toString());
				appt.setTime(rs.getString(8));
				appt.setState(rs.getString(9));
				
				apptList.add(appt);					
								
				
			}
			conn.close();
		}catch(Exception c){c.printStackTrace();}
	
	return apptList;
	}

	@Override
	public String createWard(String ward_desc) throws RemoteException {
		int countvalue = 0;
		 Statement stmt;
		 String wardId = "";
		 
		 try{
				//create a connection to db with 2 statements		
				conn=DBConnection.getConnection();
				
				stmt =conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT value FROM counter where id =" +7 );
				while(rs.next()){
					countvalue =  rs.getInt("value");
					wardId = "WC-" +leftPad("" + countvalue, 2); 
					
					//Now insert a new case
					String query = "INSERT into ward_categories(cat_id, description)" + "values (?,?)";

					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1, wardId);
					pstmt.setString(2, ward_desc);
										
					pstmt.execute();					
				}
										
				String query2 = "update counter set value = ? where id = ?";
				PreparedStatement pms = conn.prepareStatement(query2);
				pms.setInt(1, countvalue+1);
				pms.setInt(2, 7);
				pms.executeUpdate();
				
				conn.close();									
			}catch(Exception e){	e.printStackTrace();	}
		 		 	 
		 return wardId;
	}
	
	@Override
	public void updateWard(String ward_id, String ward_desc) throws RemoteException {
		try {
			conn=DBConnection.getConnection();
			String query2 = "UPDATE ward_categories "
								+ "SET description = ? "
								+ "WHERE cat_id = ? ";
			PreparedStatement pms = conn.prepareStatement(query2);
			pms.setString(1, ward_desc);
			pms.setString(2, ward_id);
								
			pms.executeUpdate();
			
			conn.close();														
		} catch (Exception e) {	e.printStackTrace();	}
		
	}

	@Override
	public ArrayList<Ward> getAllWards() throws RemoteException {
		ArrayList<Ward> list = new ArrayList<>();
		try {
			conn= DBConnection.getConnection();
			String query2 = "SELECT * from ward_categories";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
			list.add(new Ward(rs.getString(2), rs.getString(3)));
			}
			
			conn.close();								
		} catch (Exception e) {	e.printStackTrace();	}	
		
		return list;
	}
	
	@Override
	public String createRoom(String ward_id, String room_name, String room_desc) throws RemoteException {
		int countvalue = 0;
		 Statement stmt;
		 String roomId = "";
		 
		 try{
				//create a connection to db with 2 statements		
				conn=DBConnection.getConnection();
				
				stmt =conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT value FROM counter where id =" +8 );
				while(rs.next()){
					countvalue =  rs.getInt("value");
					roomId = "RM-" +leftPad("" + countvalue, 2);
					
					//Now insert a new case
					String query = "INSERT into wards(ward_code, ward_name, ward_description, category_id)" + "values (?,?,?,?)";

					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1, roomId);
					pstmt.setString(2, room_name);
					pstmt.setString(3, room_desc);
					pstmt.setString(4, ward_id);
										
					pstmt.execute();					
				}
										
				String query2 = "update counter set value = ? where id = ?";
				PreparedStatement pms = conn.prepareStatement(query2);
				pms.setInt(1, countvalue+1);
				pms.setInt(2, 8);
				pms.executeUpdate();
				
				conn.close();									
			}catch(Exception e){	e.printStackTrace();	}
		
			return roomId;
	}
	
	@Override
	public void updateRoom(String room_id, String ward_id, String room_name, String room_desc) throws RemoteException {
		try {
			conn=DBConnection.getConnection();
			String query2 = "update wards "
								+ "set ward_name = ?, ward_description = ?, category_id = ? "
								+ "where ward_code =? ";
			PreparedStatement pms = conn.prepareStatement(query2);
			pms.setString(1, room_name);
			pms.setString(2, room_desc);
			pms.setString(3, ward_id);
			pms.setString(4, room_id);
									
			pms.executeUpdate();
			
			conn.close();
														
		} catch (Exception e) {	e.printStackTrace();	}
		
	}
	
	@Override
	public ArrayList<Room> getAllRooms(String ward_id) throws RemoteException {
		ArrayList<Room> list = new ArrayList<Room>();
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from wards_view WHERE category_id = '"+ward_id+"' ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
						
			while(rs.next()){
				Room room = new Room();
				room.setRoomId(rs.getString(1));
				room.setWardName(rs.getString(2));
				room.setRoomDescription(rs.getString(3));
				room.setWardId(rs.getString(4));
				room.setWardName(rs.getString(5));
				
				list.add(room);
			}
			
			conn.close();								
		} catch (Exception e) {	e.printStackTrace();	}
				
		return list;
	}

	@Override
	public ArrayList<Room> getAllRooms() throws RemoteException {
		ArrayList<Room> list = new ArrayList<Room>();
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from wards_view ORDER BY category_id ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
						
			while(rs.next()){
				Room room = new Room();
				room.setRoomId(rs.getString(1));
				room.setRoomName(rs.getString(2));
				room.setRoomDescription(rs.getString(3));
				room.setWardId(rs.getString(4));
				room.setWardName(rs.getString(5));
				
				list.add(room);
			}
			
			conn.close();								
		} catch (Exception e) {	e.printStackTrace();	}
				
		return list;
	}

	@Override
	public String createBed(String room_id, Bed bed) throws RemoteException {
		int countvalue = 0;
		 Statement stmt;
		 String bedNo = "";
		 
		 try{
				//create a connection to db with 2 statements		
				conn=DBConnection.getConnection();
				
				stmt =conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT value FROM counter where id =" +9 );
				while(rs.next()){
					countvalue =  rs.getInt("value");
					bedNo = "BD-" +leftPad("" + countvalue, 2);
					
					//Now insert a new case
					String query = "INSERT into beds(bed_no, bed_details, bed_charge, ward_code, bed_state)" + "values (?,?,?,?,?)";

					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1, bedNo);
					pstmt.setString(2, bed.getBedDetails());					
					pstmt.setDouble(3, bed.getBedCharge());
					pstmt.setString(4, room_id);
					pstmt.setString(5, "free");
										
					pstmt.execute();					
				}
										
				String query2 = "update counter set value = ? where id = ?";
				PreparedStatement pms = conn.prepareStatement(query2);
				pms.setInt(1, countvalue+1);
				pms.setInt(2, 9);
				pms.executeUpdate();
				conn.close();
									
			}catch(Exception e){	e.printStackTrace();	}
		
		return bedNo;
	}
	
	@Override
	public void updateBed(Bed bed) throws RemoteException {
		try {
			conn=DBConnection.getConnection();
			String query2 = "update beds "
								+ "set bed_details = ?, bed_charge=? where bed_no =? ";
			
						
			PreparedStatement pms = conn.prepareStatement(query2);
			pms.setString(1, bed.getBedDetails());  					
			pms.setDouble(2, bed.getBedCharge());
			pms.setString(3, bed.getBedNo());
			pms.executeUpdate();
			
			conn.close();							
			
		} catch (Exception e) {	e.printStackTrace();	}
		
	}
	
	@Override
	public ArrayList<Bed> getAllBeds() throws RemoteException {
		ArrayList<Bed> bedlist = new ArrayList<Bed>();
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from beds_view";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
				Bed bed = new Bed();
				bed.setBedNo(rs.getString(1));
				bed.setBedDetails(rs.getString(2));				
				bed.setBedCharge(rs.getDouble(3));
				bed.setRoomId(rs.getString(5));
				bed.setWardName(rs.getString(6));
				bed.setStatus(rs.getString(4));
				
				bedlist.add(bed);				
			}
			conn.close();
								
		} catch (Exception e) {	e.printStackTrace();	}	
		
		
		return bedlist;
	}

	@Override
	public ArrayList<OPInvoice> getOPDinvoice(String status, Date date1, Date date2) throws RemoteException {
		ArrayList<OPInvoice> list = new ArrayList<OPInvoice>();
		
		java.sql.Date date_1 = new java.sql.Date(date1.getTime());
		java.sql.Date date_2 = new java.sql.Date(date2.getTime());
		
		Statement stmt1 = null;
		 ResultSet rs = null;
		 try{
			 conn = DBConnection.getConnection();
			 String query = "select * from final_opd_invoice "
			 		+ "where visit_date between '" +date_1+"' and '"+date_2+"'";
			 String query1 = "select * from final_opd_invoice where visit_status = '" +status+"' and visit_date between '" +date_1+"' and '"+date_2+"'";
			 
			 if(status.equalsIgnoreCase("all")){
					stmt1 = conn.createStatement();
					rs = stmt1.executeQuery(query);					
				}else {
					stmt1 = conn.createStatement();					
					rs = stmt1.executeQuery(query1);					
				}
			//extract data from result set
				while(rs.next()){
					OPInvoice opi = new OPInvoice();
					opi.setInvoiceNo(rs.getString(1));					
					opi.setSurname(rs.getString(2));
					opi.setOthernames(rs.getString(3));
					opi.setInvoiceDate(rs.getDate(4).toString());
					opi.setVisitStatus(rs.getString(5));
					opi.setClinicDue(rs.getDouble(6)+"");
					opi.setPharmacyDue(rs.getDouble(7)+"");
					opi.setLaboratoryDue(rs.getDouble(8)+"");
					opi.setProcedureDue(rs.getDouble(9)+"");
					opi.setTotalDeposit(rs.getDouble(11)+"");					
					opi.setTotalReceivable(rs.getDouble(10)+"");
					opi.setNetReceivable(rs.getDouble(10)+"");
					opi.setTotalDue(rs.getDouble(12)+"");
					
					list.add(opi);
				}
				conn.close();
		 }catch(Exception e){ 	 e.printStackTrace();		 }		
		
		return list;
	}

	@Override
	public ArrayList<OPInvoice> getOPDinvoice(String status) throws RemoteException {
		ArrayList<OPInvoice> list = new ArrayList<OPInvoice>();
		
		
		Statement stmt1 = null;
		 ResultSet rs = null;
		 try{
			 conn = DBConnection.getConnection();
			 
			 String query1 = "select * from final_opd_invoice where visit_status = '" +status+"' "; 
			
			stmt1 = conn.createStatement();					
			rs = stmt1.executeQuery(query1);			
			
				while(rs.next()){
					OPInvoice opi = new OPInvoice();
					opi.setInvoiceNo(rs.getString(1));					
					opi.setSurname(rs.getString(2));
					opi.setOthernames(rs.getString(3));
					opi.setInvoiceDate(rs.getDate(4).toString());
					opi.setVisitStatus(rs.getString(5));
					opi.setClinicDue(rs.getDouble(6)+"");
					opi.setPharmacyDue(rs.getDouble(7)+"");
					opi.setLaboratoryDue(rs.getDouble(8)+"");
					opi.setProcedureDue(rs.getDouble(9)+"");
					opi.setTotalDeposit(rs.getDouble(11)+"");					
					opi.setTotalReceivable(rs.getDouble(10)+"");
					opi.setNetReceivable(rs.getDouble(10)+"");
					opi.setTotalDue(rs.getDouble(12)+"");
					
					list.add(opi);
				}
				conn.close();
		 }catch(Exception e){ 	 e.printStackTrace();		 }		
		
		return list;
	}
	
	
	@Override
	public ArrayList<IPInvoice> getIPDInvoice(String status, Date date1, Date date2) throws RemoteException {
		ArrayList<IPInvoice> list = new ArrayList<IPInvoice>();
		
		java.sql.Date date_1 = new java.sql.Date(date1.getTime());
		java.sql.Date date_2 = new java.sql.Date(date2.getTime());
		
		Statement stmt1 = null;
		 ResultSet rs = null;
		 try{
			 conn = DBConnection.getConnection();
			 String query = "select * from final_ipd_invoice "
			 		+ "where admission_date between '" +date_1+"' and '"+date_2+"'";
			 String query1 = "select * from final_ipd_invoice where admission_status = '" +status+"' and admission_date between '" +date_1+"' and '"+date_2+"'";
			 
			 if(status.equalsIgnoreCase("all")){
					stmt1 = conn.createStatement();
					rs = stmt1.executeQuery(query);					
				}else {
					stmt1 = conn.createStatement();					
					rs = stmt1.executeQuery(query1);					
				}
			//extract data from result set
				while(rs.next()){
					IPInvoice ipi = new IPInvoice();
					ipi.setInvoiceNo(rs.getString(1));
					ipi.setAdmissionDate(rs.getDate(4).toString());
					ipi.setSurname(rs.getString(2));
					ipi.setOthernames(rs.getString(3));					
					ipi.setAdmissionStatus(rs.getString(5));
					ipi.setNoOfDays(rs.getInt(6)+"");
					ipi.setAdmissionDues(rs.getDouble(11)+"");
					ipi.setClinicDues(rs.getDouble(7)+"");
					ipi.setLaboratoryDues(rs.getDouble(9)+"");
					ipi.setProcedureDues(rs.getDouble(10)+"");
					ipi.setPharmacyDues(rs.getDouble(8)+"");					
					ipi.setTotalDeposit(rs.getDouble(13)+"");					
					ipi.setTotalReceivable(rs.getDouble(12)+"");
					ipi.setNetReceivable(rs.getDouble(12)+"");
					ipi.setTotalDue(rs.getDouble(14)+"");				
					
					list.add(ipi);
				}
				conn.close();
		 }catch(Exception e){ 	 e.printStackTrace();		 }		
		
		return list;
	}
	
	@Override
	public ArrayList<IPInvoice> getIPDInvoice(String status) throws RemoteException {
		ArrayList<IPInvoice> list = new ArrayList<IPInvoice>();	
		Locale locale = new Locale("en", "NG");
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		Statement stmt1 = null;
		 ResultSet rs = null;
		 try{
			 conn = DBConnection.getConnection();
			 String query1 = "select * from final_ipd_invoice where admission_status = '" +status+"' ";
			 			
			stmt1 = conn.createStatement();					
			rs = stmt1.executeQuery(query1);					
				
				while(rs.next()){
					IPInvoice ipi = new IPInvoice();
					ipi.setInvoiceNo(rs.getString(1));
					ipi.setAdmissionDate(rs.getDate(4).toString());
					ipi.setSurname(rs.getString(2));
					ipi.setOthernames(rs.getString(3));					
					ipi.setAdmissionStatus(rs.getString(5));
					ipi.setNoOfDays(rs.getInt(6)+"");
					ipi.setAdmissionDues(rs.getDouble(11)+"");
					ipi.setClinicDues(rs.getDouble(7)+"");
					ipi.setLaboratoryDues(rs.getDouble(9)+"");
					ipi.setProcedureDues(rs.getDouble(10)+"");
					ipi.setPharmacyDues(rs.getDouble(8)+"");					
					ipi.setTotalDeposit(nf.format(rs.getDouble(13)));					
					ipi.setTotalReceivable(nf.format(rs.getDouble(12)));
					ipi.setNetReceivable(rs.getDouble(12)+"");
					ipi.setTotalDue(nf.format(rs.getDouble(14)));				
					
					list.add(ipi);
				}
				conn.close();
		 }catch(Exception e){ 	 e.printStackTrace();		 }		
		
		return list;
	}
	

	@Override
	public void saveDeposit(String invoice_no, String paymode, String bank, double amount) throws RemoteException {
		try{
			 conn = DBConnection.getConnection();
			 String query = "INSERT INTO general_deposit(invoice_no, payment_mode, bank, date, time, amount) VALUES(?,?,?,curdate(),curtime(),?)";
			 PreparedStatement pst = conn.prepareStatement(query);
			 pst.setString(1, invoice_no);
			 pst.setString(2, paymode);
			 pst.setString(3, bank);
			 pst.setDouble(4, amount);
			 
			 pst.execute();
			 
			 conn.close();
			 
		}catch(Exception ex){ ex.printStackTrace();}
		
	}

	@Override
	public PatientBiodata getPatientBiodata(String file_no) throws RemoteException {
		Statement stmt1 = null;
		ResultSet rs = null;
		PatientBiodata pbd = new PatientBiodata();		
		try{
				conn = DBConnection.getConnection();			
			String query = "SELECT * FROM patient_biodata "
					+ "where hospital_no = '" + file_no + "'";
							
				stmt1 = conn.createStatement();
				rs = stmt1.executeQuery(query);
						
				//extract data from result set 
				while(rs.next()){
					//now set the attributes of the object
					
					pbd.setHospital_no(rs.getString(2));
					
					pbd.setSurname(rs.getString(3));
					pbd.setFirstname(rs.getString(4));
					
								
					pbd.setRegDate(rs.getString(5));
					pbd.setDob(rs.getString(6));					
					pbd.setGender(rs.getString(7));					
					pbd.setBloodGroup(rs.getString(8));
					pbd.setGenotype(rs.getString(9));					
					pbd.setNationality(rs.getString(11));
					pbd.setState(rs.getString(12));			
					pbd.setMaritalStatus(rs.getString(10));
					pbd.setReligion(rs.getString(14));
					pbd.setOccupation(rs.getString(13));
					pbd.setAddress1(rs.getString(17));		
					pbd.setPhoneMobile(rs.getString(15));				
					pbd.setEmail(rs.getString(16));				
					pbd.setNok_surname(rs.getString(18));					
					pbd.setNok_address(rs.getString(19));
					pbd.setNok_relationship(rs.getString(20));
					pbd.setNok_mobile(rs.getString(21));
					pbd.setRegistrationOfficer(rs.getString(22));
					pbd.setCharge(rs.getDouble(23));
					pbd.setFamilyNo(rs.getString(24));
								
				}
				  
				conn.close();
		}catch(Exception ex){	ex.printStackTrace();	}
		return pbd;
	}

	@Override
	public void updatePatientBiodata(PatientBiodata pat) throws RemoteException {
		java.sql.Date sqldob = null;
    	    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	try {
    		java.util.Date date = sdf.parse(pat.getDob());
    		
    		
    		sqldob = new java.sql.Date(date.getTime());
    		
    		
    		conn = DBConnection.getConnection();
    		
    		String query = "UPDATE patient_biodata "
    				+ "SET patient_surname=?, patient_firstname=?, patient_dob=?,"
    				+ "gender=?, blood_group=?, genotype=?, marital_status=?, nationality=?, state=?, occupation=?, religion=?, "
    				+ "phone_mobile=?, email=?, patient_address1=?, "
    				+ "nok_surname=?, nok_address=?, nok_relationship=?, nok_mobile=?"
    				+ "WHERE hospital_no = ?";
    		
    		PreparedStatement pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, pat.getSurname());
			pstmt.setString(2, pat.getFirstname());						
			pstmt.setDate(3, sqldob);
			
			pstmt.setString(4, pat.getGender());
			pstmt.setString(5, pat.getBloodGroup());
			pstmt.setString(6, pat.getGenotype());
			pstmt.setString(7, pat.getMaritalStatus());			
			pstmt.setString(8, pat.getNationality());
			pstmt.setString(9, pat.getState());			
			
			pstmt.setString(10, pat.getOccupation());
			pstmt.setString(11, pat.getReligion());			
			pstmt.setString(12, pat.getPhoneMobile());			
			pstmt.setString(13, pat.getEmail());			
			pstmt.setString(14, pat.getAddress1());			
			
			pstmt.setString(15, pat.getNok_surname());			
			pstmt.setString(16, pat.getNok_address());
			pstmt.setString(17, pat.getNok_relationship());
			pstmt.setString(18, pat.getNok_mobile());
			
			pstmt.setString(19, pat.getHospital_no());
			
			pstmt.executeUpdate();
    		
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
	
	}

	@Override
	public OPInvoice getOneOPDinvoice(String invoice_no) throws RemoteException {
		OPInvoice opi = new OPInvoice();
				
		Statement stmt1 = null;
		 ResultSet rs = null;
		 try{
			 conn = DBConnection.getConnection();
			 String query = "select * from final_opd_invoice "+ "where invoice_no = '" +invoice_no+"' ";
			 			 
			
					stmt1 = conn.createStatement();
					rs = stmt1.executeQuery(query);					
				
			//extract data from result set
				while(rs.next()){
					
				
					opi.setInvoiceNo(rs.getString(1));					
					opi.setSurname(rs.getString(2));
					opi.setOthernames(rs.getString(3));
					opi.setInvoiceDate(rs.getDate(4).toString());
					opi.setVisitStatus(rs.getString(5));
					opi.setClinicDue(rs.getDouble(6)+"");
					opi.setPharmacyDue(rs.getDouble(7)+"");
					opi.setLaboratoryDue(rs.getDouble(8)+"");
					opi.setProcedureDue(rs.getDouble(9)+"");
					opi.setTotalDeposit(rs.getDouble(11)+"");					
					opi.setTotalReceivable(rs.getDouble(10)+"");
					opi.setNetReceivable(rs.getDouble(10)+"");
					opi.setTotalDue(rs.getDouble(12)+"");
					
					
					
					
				}
				conn.close();
		 }catch(Exception e){ 	 e.printStackTrace();		 }		
		
		return opi;
	}

	@Override
	public IPInvoice getOneIPDInvoice(String invoice_no) throws RemoteException {
		IPInvoice ipi = new IPInvoice();
		
		Statement stmt1 = null;
		 ResultSet rs = null;
		 try{
			 conn = DBConnection.getConnection();
			 String query = "select * from final_ipd_invoice "+ "where invoice_no ='" +invoice_no+"' ";			 			 
			 
					stmt1 = conn.createStatement();
					rs = stmt1.executeQuery(query);					
				
			//extract data from result set
				while(rs.next()){
					
					ipi.setInvoiceNo(rs.getString(1));
					ipi.setAdmissionDate(rs.getDate(4).toString());
					ipi.setSurname(rs.getString(2));
					ipi.setOthernames(rs.getString(3));					
					ipi.setAdmissionStatus(rs.getString(5));
					ipi.setNoOfDays(rs.getInt(6)+"");
					ipi.setAdmissionDues(rs.getDouble(11)+"");
					ipi.setClinicDues(rs.getDouble(7)+"");
					ipi.setLaboratoryDues(rs.getDouble(9)+"");
					ipi.setProcedureDues(rs.getDouble(10)+"");
					ipi.setPharmacyDues(rs.getDouble(8)+"");					
					ipi.setTotalDeposit(rs.getDouble(13)+"");					
					ipi.setTotalReceivable(rs.getDouble(12)+"");
					ipi.setNetReceivable(rs.getDouble(12)+"");
					ipi.setTotalDue(rs.getDouble(14)+"");				
					
					
				}
				conn.close();
		 }catch(Exception e){ 	 e.printStackTrace();		 }		
		
		return ipi;
	}

	@Override
	public void getDeposits(String invoice_no) throws RemoteException {
		
	}

	@Override
	public void saveHospitalCharge(String chargeDesc, double amount) throws RemoteException {
		try{
			 conn = DBConnection.getConnection();
			 String query = "INSERT INTO clinic_charge_items(charge_name, charge_amount) VALUES(?,?)";
			 PreparedStatement pst = conn.prepareStatement(query);
			 pst.setString(1, chargeDesc);
			 pst.setDouble(2, amount);			
			 
			 pst.execute();
			 
			 conn.close();
			 
		}catch(Exception ex){ ex.printStackTrace();}
		
	}
	
	@Override
	public ArrayList<HospitalCharge> getAllCharges() throws RemoteException {
		ArrayList<HospitalCharge> list = new ArrayList<HospitalCharge>();
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from clinic_charge_items";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
				HospitalCharge hc = new HospitalCharge();
				hc.setChargeName(rs.getString(2));
				hc.setAmount(rs.getDouble(3));
				
				list.add(hc);				
			}
			conn.close();
								
		} catch (Exception e) {	e.printStackTrace();	}	
		
		return list;
	}
	
	@Override
	public void updateHospitalCharge(String chargeDesc, double amount) throws RemoteException {
		try {
			conn=DBConnection.getConnection();
			String query2 = "UPDATE clinic_charge_items "
								+ "SET amount = ? "
								+ "WHERE charge_name = ? ";
			PreparedStatement pms = conn.prepareStatement(query2);
			pms.setDouble(1, amount);
			pms.setString(2, chargeDesc);
								
			pms.executeUpdate();
			
			conn.close();														
		} catch (Exception e) {	e.printStackTrace();	}
		
	}
	
	@Override
	public FamilyCard createFamilyCard(FamilyCard fam) throws RemoteException {
		int countvalue = 0;
		 Statement stmt;
		 String famNo = "";
		 
		 try{
				//create a connection to db with 2 statements		
				conn=DBConnection.getConnection();
				
				stmt =conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT value FROM counter where id =" +17 );
				while(rs.next()){
					countvalue =  rs.getInt("value");
					famNo = "FC-" +leftPad("" + countvalue, 5);
					
					//Now insert a new case
					String query = "INSERT into family_cards(family_no, family_name, reg_date, reg_officer, reg_fee)" + "values (?,?,curdate(),?,?)";

					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1, famNo);
					pstmt.setString(2, fam.getFamilyName());
					
					pstmt.setString(3, fam.getRegOfficer());
					pstmt.setDouble(4, fam.getRegFee());
					
										
					pstmt.execute();
					
					fam.setFamilyNo(famNo);
				}
										
				String query2 = "update counter set value = ? where id = ?";
				PreparedStatement pms = conn.prepareStatement(query2);
				pms.setInt(1, countvalue+1);
				pms.setInt(2, 17);
				pms.executeUpdate();
				conn.close();
									
			}catch(Exception e){	e.printStackTrace();	}
		
		return fam;
	}

	
	@Override
	public ArrayList<FamilyCard> getAllFamilyCards() throws RemoteException {
		ArrayList<FamilyCard> list = new ArrayList<FamilyCard>();
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from family_cards";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
				FamilyCard fc = new FamilyCard();
				fc.setFamilyNo(rs.getString(2));
				fc.setFamilyName(rs.getString(3));
				fc.setDate(rs.getDate(4).toString());
				fc.setRegOfficer(rs.getString(5));
				fc.setRegFee(rs.getDouble(6));
				
				list.add(fc);				
			}
			conn.close();
								
		} catch (Exception e) {	e.printStackTrace();	}	
		
		return list;
	}

	
	@Override
	public void updateFamilyCard(FamilyCard fam) throws RemoteException {
		try {
			conn=DBConnection.getConnection();
			String query2 = "UPDATE family_cards "
								+ "SET family_name = ?, reg_officer = ?, reg_fee = ? "
								+ "WHERE family_no = ? ";
			PreparedStatement pms = conn.prepareStatement(query2);
			pms.setString(1, fam.getFamilyName());
			pms.setString(2, fam.getRegOfficer());
			pms.setDouble(3, fam.getRegFee());
			pms.setString(4, fam.getFamilyNo());
								
			pms.executeUpdate();
			
			conn.close();														
		} catch (Exception e) {	e.printStackTrace();	}
		
		
	}

	@Override
	public ArrayList<Deposit> getAllDeposits(String invoice_no) throws RemoteException {
		ArrayList<Deposit> list = new ArrayList<Deposit>();
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from general_deposit where invoice_no = '"+invoice_no+"' ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			
			
			//extract data from result set
			while(rs.next()){
				
				java.sql.Date j_date = rs.getDate(5);
				java.util.Date u_date = new java.util.Date(j_date.getTime());			
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
				String date = sdf.format(u_date);
				
				
				Deposit fc = new Deposit();
				fc.setInvoice_no(rs.getString(2));	
				fc.setPayment_mode(rs.getString(3));
				fc.setBank(rs.getString(4));
				fc.setDate(date);
				fc.setTime(rs.getTime(6).toString());
				fc.setAmount(rs.getDouble(7));
				list.add(fc);				
			}
			conn.close();
								
		} catch (Exception e) {	e.printStackTrace();	}	
		
		return list;
	}

	@Override
	public ArrayList<FamilyCard> getFamilyCards(String search_criterion, String search_text) throws RemoteException {
		ArrayList<FamilyCard> list = new ArrayList<FamilyCard>();
		try {
			conn= DBConnection.getConnection();
			String query2 = "";
			
			if (search_criterion.equalsIgnoreCase("family name")) {
				 query2 = "SELECT * FROM family_cards "
						 + "WHERE family_name like '%"+search_text+"%' ";
			}else if (search_criterion.equalsIgnoreCase("family number")) {
				 query2 = "SELECT * FROM family_cards "
							+ "WHERE family_no = '"+search_text+"' ";
			}
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
				FamilyCard fc = new FamilyCard();
				fc.setFamilyNo(rs.getString(2));
				fc.setFamilyName(rs.getString(3));
				fc.setDate(rs.getDate(4).toString());
				fc.setRegOfficer(rs.getString(5));
				fc.setRegFee(rs.getDouble(6));
				
				list.add(fc);				
			}
			conn.close();
								
		} catch (Exception e) {	e.printStackTrace();	}	
		
		return list;
	}

	@Override
	public void changePassword(String new_password, UserCard user) throws RemoteException {
		try {			
			Connection conn = DBConnection.getConnection();
			String update_query = "UPDATE application_users "
									+" SET password = ?  "
									+" WHERE username = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, new_password);		
			
			ps.setString(2, user.getUsername());
			
			ps.executeUpdate();
			conn.close();
		}catch(Exception ex) { 
			ex.printStackTrace();
			}
		
	}

	@Override
	public void saveDoctor(Doctor doctor) throws RemoteException {
		try{
			 conn = DBConnection.getConnection();
			 
			 	int countValue = 0;
				Statement stmt=conn.createStatement();
				ResultSet rs = stmt.executeQuery("select value from counter where id= "+ 19);
				
				while (rs.next()) {
					countValue =  rs.getInt("value");
					String doc_id = leftPad("" + countValue, 3); 
				 
					 String query = "INSERT INTO doctor(doc_id, doctor_name, qualification, specialty, status) VALUES(?,?,?,?,?)";
					 PreparedStatement pst = conn.prepareStatement(query);
					 pst.setString(1, doc_id);
					 pst.setString(2, doctor.getDoctorName());
					 pst.setString(3, doctor.getQualifications());
					 pst.setString(4, doctor.getSpecialty());
					 pst.setString(5, doctor.getStatus());
					 
					 pst.execute();
					
				}
				
			 
			 
			 String query2 = "update counter set value = ? where id = ?";
			PreparedStatement pms = conn.prepareStatement(query2);
			pms.setInt(1, countValue+1);
			pms.setInt(2, 19);
			pms.executeUpdate();
			 
			 conn.close();
			 
		}catch(Exception ex){ ex.printStackTrace();}
		
	}

	@Override
	public void updateDoctor(Doctor doctor) throws RemoteException {
		try {
			 conn = DBConnection.getConnection();
			 
			 String query2 = "update doctor set doctor_name = ?, qualification = ?, specialty = ?, status = ?"
			 		+ " where doc_id = ?";
				PreparedStatement pms = conn.prepareStatement(query2);
				pms.setString(1, doctor.getDoctorName());
				pms.setString(2, doctor.getQualifications());
				pms.setString(3, doctor.getSpecialty());
				pms.setString(4, doctor.getStatus());
				pms.setString(5, doctor.getDoctorId());
				pms.executeUpdate();
				 
				 conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String databaseBackup(String filepath) throws RemoteException {
	    int portNumber = 3306; // Change this to the desired port number
	    String mysqlDumpPath = "C:/xampp/mysql/bin/mysqldump";
	    String executeCmd = mysqlDumpPath + " -u root -pSudoP@ssw0rd@1234 -P " + portNumber + " hospisoft -r " + filepath + ".sql";

	    String message = "";
	    ProcessBuilder processBuilder = new ProcessBuilder();
	    
	    // Split the command into individual arguments
	    List<String> command = Arrays.asList("cmd.exe", "/c", executeCmd);
	    processBuilder.command(command);
	    
	    try {
	        Process process = processBuilder.start();
	        int processComplete = process.waitFor();

	        if (processComplete == 0) {
	            System.out.println("Backup created successfully");
	            message = "Backup created successfully";
	        } else {
	            System.out.println("Could not create the backup");

	            // Print the error message
	            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
	            StringBuilder errorMessage = new StringBuilder();
	            String line;
	            while ((line = errorReader.readLine()) != null) {
	                errorMessage.append(line).append("\n");
	            }
	            System.out.println("Error message: " + errorMessage.toString());

	            message = "Could not create the backup";
	        }
	    } catch (IOException | InterruptedException ex) {
	        System.out.println("Error at Backuprestore" + ex.getMessage());
	    }

	    return message;
	}



	

}
