package com.ahms.server;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.ahms.api.LabManagementInterface;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.labmgt.entities.LabRequest;
import com.ahms.labmgt.entities.ResultLine;
import com.ahms.labmgt.entities.TestGroup;
import com.ahms.labmgt.entities.TestItem;
import com.ahms.labmgt.entities.TestOrderItem;
import com.ahms.labmgt.entities.TestParameter;


public class LabManagementImplementation implements LabManagementInterface {
	Connection conn;
	ArrayList<ResultLine> resultItems = new ArrayList<ResultLine>();

	public LabManagementImplementation() {
		
	}

	@Override
	public String createTestGroup(String group_name) throws RemoteException {
		int countvalue = 0;
		 Statement stmt;
		 String testGrpId = "";
		 
			try{
				//create a connection to db with 2 statements		
				conn=DBConnection.getConnection();
				
				stmt =conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT value FROM counter where id =" +4 );
				while(rs.next()){
					countvalue =  rs.getInt("value");
					testGrpId = "TG-" +leftPad("" + countvalue, 3);
					
					//Now insert a new case
					String query = "INSERT into test_groups(test_group_id, test_group_code, test_group_name )" + "values (?,?,?)";

					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1, testGrpId);
					pstmt.setString(2, "");
					pstmt.setString(3, group_name);					
					
					pstmt.execute();
					
				}
										
				String query2 = "update counter set value = ? where id = ?";
				PreparedStatement pms = conn.prepareStatement(query2);
				pms.setInt(1, countvalue+1);
				pms.setInt(2, 4);
				pms.executeUpdate();
				conn.close();
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		
		return testGrpId;
	}

	@Override
	public void saveTestGroup(String groupid, String groupcode, String groupname) throws RemoteException {
		try {
			conn=DBConnection.getConnection();
			String query2 = "update test_groups set test_group_code = ?, test_group_name = ? where test_group_id = ?";
			PreparedStatement pms = conn.prepareStatement(query2);
			pms.setString(1, groupcode);
			pms.setString(2, groupname);
			pms.setString(3, groupid);
			
			pms.executeUpdate();
			
			conn.close();
									
		} catch (Exception e) {	e.printStackTrace();	}

	}

	@Override
	public ArrayList<TestGroup> getTestGroups() throws RemoteException {
		ArrayList<TestGroup> testgroups = new ArrayList<>();
		
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from test_groups";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
			testgroups.add(new TestGroup(rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			conn.close();					
		} catch (Exception e) {	e.printStackTrace();	}		
		return testgroups;
	}

	@Override
	public String leftPad(String str, int xy) throws RemoteException {
				String emptystr = "";
			int diff = xy - str.length();
			
			for(int i=0; i<diff; i++){	emptystr = emptystr.concat("0");	}
			return emptystr+str;
		
		
	}

	@Override
	public String createTest(TestItem test) throws RemoteException {
		 int countvalue = 0;
		 Statement stmt;
		 String testId= "";
		 
			try{
				//create a connection to db with 2 statements		
				conn=DBConnection.getConnection();
				
				stmt =conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT value FROM counter where id =" +5 );
				while(rs.next()){
					countvalue =  rs.getInt("value");
					testId = "LT-" +leftPad("" + countvalue, 4);
					
					//Now insert a new case
					String query = "INSERT into test_items(test_id, test_code, test_name, test_type, test_group_id,"
							+ "test_title, price, discount, vat )" + "values (?,?,?,?,?,?,?,?,?)";

					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1, testId);
					pstmt.setString(2, "");
					pstmt.setString(3, test.getTestName());
					pstmt.setString(4, "");
					pstmt.setString(5, test.getTestGroupCode());
					pstmt.setString(6, "");
					pstmt.setDouble(7, test.getPrice());
					pstmt.setDouble(8, 0.00);
					pstmt.setDouble(9, 0.00);
					
					pstmt.execute();
					
				}
										
				String query2 = "update counter set value = ? where id = ?";
				PreparedStatement pms = conn.prepareStatement(query2);
				pms.setInt(1, countvalue+1);
				pms.setInt(2, 5);
				pms.executeUpdate();
				
				conn.close();				
			}catch(Exception e){ e.printStackTrace();	}
		
		return testId;
	}

	@Override
	public void saveTest(TestItem test) throws RemoteException {
		//note that test.getTestGroupCode() actually contains groupId
				try {
					conn=DBConnection.getConnection();
					String query2 = "update test_items "
										+ "set test_name = ?, price = ? where test_id =? ";
					PreparedStatement pms = conn.prepareStatement(query2);
					pms.setString(1, test.getTestName());
					pms.setDouble(2, test.getPrice());
					pms.setString(3, test.getTestId());
					
					pms.executeUpdate();
					
					conn.close();							
				} catch (Exception e) {	e.printStackTrace();	}
		
	}

	@Override
	public ArrayList<TestItem> getTestItems() throws RemoteException {
		ArrayList<TestItem> testsList = new ArrayList<>();
		
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from test_items order by 4";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
				TestItem ti = new TestItem();
				ti.setTestId(rs.getString(2));
				ti.setTestCode(rs.getString(3));
				ti.setTestName(rs.getString(4));
				ti.setTestType(rs.getString(5));
				ti.setTestGroupCode(rs.getString(6));
				ti.setTestTitle(rs.getString(7));
				ti.setPrice(rs.getDouble(8));
				ti.setDiscount(rs.getDouble(9));
				ti.setVat(rs.getDouble(10));
				
				testsList.add(ti);
				
			}
			conn.close();
			
					
		} catch (Exception e) {	e.printStackTrace();	}
		
		return testsList;
	}
	
	@Override
	public ArrayList<TestItem> getTestItems(String grpId) throws RemoteException {
		ArrayList<TestItem> testsList = new ArrayList<>();
		
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from test_items "
					+ "where test_group_id ='" +grpId + "' ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
				TestItem ti = new TestItem();
				ti.setTestId(rs.getString(2));
				ti.setTestCode(rs.getString(3));
				ti.setTestName(rs.getString(4));
				ti.setTestType(rs.getString(5));
				ti.setTestGroupCode(rs.getString(6));
				ti.setTestTitle(rs.getString(7));
				ti.setPrice(rs.getDouble(8));
				ti.setDiscount(rs.getDouble(9));
				ti.setVat(rs.getDouble(10));
				
				testsList.add(ti);
				
			}
			conn.close();					
		} catch (Exception e) {	e.printStackTrace();	}		
		return testsList;
	}
	
	@Override
	public String createTestParameter(String test_id) throws RemoteException {
		 int countvalue = 0;
		 Statement stmt;
		 String paramId = "";
		 
			try{
				//create a connection to db with 2 statements		
				conn=DBConnection.getConnection();
				
				stmt =conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT value FROM counter where id =" +6 );
				while(rs.next()){
					countvalue =  rs.getInt("value");
					paramId = "Par-" +leftPad("" + countvalue, 4);
					
					//Now insert a new case
					String query = "INSERT into test_parameters(par_code, test_parameter, unit, normal_range, test_id )" + "values (?,?,?,?,?)";

					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1, paramId);
					pstmt.setString(2, "");
					pstmt.setString(3, "");
					pstmt.setString(4, "");
					pstmt.setString(5, test_id);
					
											
					pstmt.execute();
					
				}
										
				String query2 = "update counter set value = ? where id = ?";
				PreparedStatement pms = conn.prepareStatement(query2);
				pms.setInt(1, countvalue+1);
				pms.setInt(2, 6);
				pms.executeUpdate();
				conn.close();
									
			}catch(Exception e){	e.printStackTrace();	}
				
		return paramId;
	}
	
	@Override
	public void saveParameters(String testPar, String unit, String range, String param) throws RemoteException {
		try {
			conn=DBConnection.getConnection();
			String query2 = "update test_parameters "
								+ "set test_parameter = ?, unit = ?, normal_range = ? "
								+ "where par_code =? ";
			PreparedStatement pms = conn.prepareStatement(query2);
			pms.setString(1, testPar);
			pms.setString(2, unit);
			pms.setString(3, range);
			pms.setString(4, param);				
						
			pms.executeUpdate();				
			conn.close();
														
		} catch (Exception e) {	e.printStackTrace();	}		
				
	}
	
	@Override
	public ArrayList<TestParameter> getAllParameters(String testId) throws RemoteException {
		ArrayList<TestParameter> paramList = new ArrayList<>();
		
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from test_parameters "
					+ "where test_id ='" +testId + "' ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
				paramList.add(new TestParameter(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));															
			}
			conn.close();		
		} catch (Exception e) {	e.printStackTrace();	}			
		return paramList;
	}
	
	@Override
	public PatientVisit getPatientVisitView(String invoice_no) throws RemoteException {
		PatientVisit pv = new PatientVisit();
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from patient_visit_view " 
					+ "where invoice_no = '" + invoice_no +"' ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
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
			} catch (Exception e) {	e.printStackTrace();	}
						
		return pv;
	}

	public void saveTestOrder(String invoice, String test_id, double price) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			conn= DBConnection.getConnection();
			String query = "INSERT into lab_test_orders(invoice_no, test_id, status, test_order_date, time, price) VALUES(?, ?, ?, curdate(), curtime(), ?)";
			PreparedStatement ps = conn.prepareStatement(query);
			
				ps.setString(1, invoice);
				ps.setString(2, test_id);
				ps.setString(3, "pending");
				ps.setDouble(4, price);					
			
			
			ps.execute();
			conn.close();	
	
			}catch (Exception e) {	e.printStackTrace();	}
		
	}
	
	@Override
	public ArrayList<TestOrderItem> getOrderItems(String invoice_no) throws RemoteException {
		ArrayList<TestOrderItem> itemlist = new ArrayList<TestOrderItem>();
		
		try {
			conn= DBConnection.getConnection();
			String query2 = "";
			
			query2 = "select * from test_order_view " 
						+ "where invoice_no = '" + invoice_no +"' " +" and status in ('pending', 'confirmed', 'ready') ";
					
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
				TestOrderItem toi = new TestOrderItem();
				toi.setInvoice_no(rs.getString(1));
				toi.setTest_id(rs.getString(10));
				toi.setStatus(rs.getString(12));
				toi.setDate(rs.getDate(13).toString());
				toi.setTest_name(rs.getString(11));
				toi.setPrice(rs.getDouble(14));
				
				
				itemlist.add(toi);
			}
			conn.close();
					
		} catch (Exception e) {	e.printStackTrace();	}
		return itemlist;
	}
	
	@Override
	public ArrayList<TestOrderItem> getOrderItems(String invoice_no, String status) throws RemoteException {
		ArrayList<TestOrderItem> itemlist = new ArrayList<TestOrderItem>();
		
		try {
			conn= DBConnection.getConnection();
			String query2 = "";
			if(status.equalsIgnoreCase("all")) {
				 query2 = "select * from test_order_view " 
							+ "where invoice_no = '" + invoice_no +"' ";
			}else {
				 query2 = "select * from test_order_view " 
							+ "where invoice_no = '" + invoice_no +"' " +" and status = '"+ status+"' ";
			}			
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
				TestOrderItem toi = new TestOrderItem();
				toi.setInvoice_no(rs.getString(1));
				toi.setTest_id(rs.getString(10));
				toi.setStatus(rs.getString(12));
				toi.setDate(rs.getDate(13).toString());
				toi.setTest_name(rs.getString(11));
				toi.setPrice(rs.getDouble(14));
				
				
				itemlist.add(toi);
			}
			conn.close();
					
		} catch (Exception e) {	e.printStackTrace();	}
		return itemlist;
	}
	
	@Override
	public void updateOrderStatus(String invoice_no, ArrayList<String> list) throws RemoteException {
		String status = "confirmed";
		try{
		conn= DBConnection.getConnection();
		
		for (int i=0; i<list.size(); i++){
			
		String query2 = "update lab_test_orders "
				+ " set status ='" + status +"' "
				+ "where invoice_no = '" + invoice_no +"' " +"and test_id = '" + list.get(i) + "' ";
		
		//get all parameters for a certain test code
		ArrayList<String> parlist = getParameters(list.get(i));
		
		// create a  test result template with the parameters above
		for (int j=0; j<parlist.size(); j++){
		String query3 = "Insert into lab_test_results(invoice_no, test_id, par_code, result) VALUES (?, ?, ?, ?)";
		PreparedStatement pmts2 = conn.prepareStatement(query3);
		
		pmts2.setString(1, invoice_no);
		pmts2.setString(2, list.get(i));
		pmts2.setString(3, parlist.get(j));
		pmts2.setString(4, "");
		
			pmts2.execute();
		
		}
		
		//update the status
		PreparedStatement pstmt = conn.prepareStatement(query2);
		pstmt.executeUpdate();
		}
		conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		
	}
	
	@Override
	public ArrayList<String> getParameters(String test_id) throws RemoteException {
		ArrayList<String> paramList = new ArrayList<>();
		
		try{
		conn= DBConnection.getConnection();
		String query2 = "select par_code from test_parameters " 
				+ "where test_id = '" + test_id +"' " ;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query2);
		
		while(rs.next()){
			paramList.add(rs.getString(1));
		}
						
		}catch(Exception e){ e.printStackTrace(); }
		
		return paramList;
	}
	
	@Override
	public ArrayList<TestOrderItem> getConfirmedTest(String invoice_no) throws RemoteException {
		ArrayList<TestOrderItem> list = new ArrayList<TestOrderItem>(); 
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from test_order_view " 
					+ "where invoice_no = '" + invoice_no +"' " +" and (status ='confirmed' or status = 'ready') ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
				TestOrderItem toi = new TestOrderItem();
				toi.setInvoice_no(rs.getString(1));
				toi.setTest_id(rs.getString(10));
				toi.setStatus(rs.getString(12));
				toi.setDate(rs.getDate(13).toString());
				toi.setTest_name(rs.getString(11));
				toi.setPrice(rs.getDouble(14));
								
				list.add(toi);
			}
			conn.close();
		} catch (Exception e) {	e.printStackTrace();	}
		return list;
	}
	
	@Override
	public ArrayList<ResultLine> getTestResult(String invoice_no, String test) throws RemoteException {
		ArrayList<ResultLine> lineItems = new ArrayList<ResultLine>();
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from test_result_view " 
					+ "where invoice_no = '" + invoice_no +"' " +" and test_id ='" + test +"' ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
				ResultLine rl = new ResultLine();
				rl.setInvoice_no(rs.getString(1));
				rl.setTest_id(rs.getString(2));
				rl.setParameter_code(rs.getString(3));
				rl.setResult(rs.getString(4));
				rl.setTest_parameter(rs.getString(5));
				rl.setUnit(rs.getString(6));
				rl.setNormal_range(rs.getString(7));
				
				lineItems.add(rl);
			}
		
			//conn.close();
									
		} catch (Exception e) {	e.printStackTrace();	}
			
		return lineItems;
	}
	
	@Override
	public void updateResults(ArrayList<String> resultlist, String test_id, String invoice_no) throws RemoteException { 
		try{
			//create a connection to db with 2 statements. This method is a good candidate for db transactions	
			conn=DBConnection.getConnection();
			ArrayList<ResultLine> resultItems = getTestResult(invoice_no, test_id);					
				//Now insert into temp
				String query = "INSERT into results_temp(invoice_no, test_id, par_code, result )" + "values (?,?,?,?)";
				PreparedStatement pstmt = conn.prepareStatement(query);
					for(int i=0; i<resultItems.size(); i++){
					pstmt.setString(1, invoice_no);
					pstmt.setString(2, test_id);
					pstmt.setString(3, resultItems.get(i).getParameter_code());
					pstmt.setString(4, resultlist.get(i));
					
					pstmt.addBatch();
					}	
					
				pstmt.executeBatch();
				//System.out.println("insert done");
				
				String query2 = "update lab_test_results "
						+ "set lab_test_results.result = "
						+ "(select result from results_temp where results_temp.par_code=lab_test_results.par_code and results_temp.invoice_no='"+invoice_no+ "') "
								+ "where lab_test_results.invoice_no ='" + invoice_no +"' and test_id = '"+test_id+"' ";
							
				PreparedStatement pstmt2 = conn.prepareStatement(query2);
				pstmt2.executeUpdate();
				//System.out.println("update done");
			
				//delete from temp_results
				String query3 = "delete from results_temp " 
						+ "where invoice_no = '" + invoice_no +"' and test_id = '"+test_id+"' ";
				PreparedStatement pstmt3 = conn.prepareStatement(query3);
				pstmt3.execute();
				//System.out.println("delete done");
				
				//now update the status in the 
				String query4 ="UPDATE lab_test_orders "
						+ "SET status = 'ready' "
						+ "WHERE invoice_no = '"+ invoice_no +"' "
								+ "AND test_id = '" +test_id+"'";
				PreparedStatement pst4 = conn.prepareStatement(query4);
				pst4.executeUpdate();			
				
				conn.close();				
			}catch(Exception e){ e.printStackTrace();}		
	}

	@Override
	public ArrayList<TestOrderItem> getPreparedResult(String invoice_no) throws RemoteException {
		ArrayList<TestOrderItem> list = new ArrayList<TestOrderItem>();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM test_order_view "
					+ "WHERE invoice_no ='" +invoice_no + "' "
					+ "AND status ='confirmed' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				TestOrderItem toi = new TestOrderItem();
				toi.setInvoice_no(rs.getString(1));
				toi.setTest_id(rs.getString(10));
				toi.setStatus(rs.getString(12));
				toi.setDate(rs.getDate(13).toString());
				toi.setTest_name(rs.getString(11));
				toi.setPrice(rs.getDouble(14));
				
				list.add(toi);
			}
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		return list;
	}

	@Override
	public void updateToDelivery(String invoice_no, ArrayList<String> tests_list) throws RemoteException {
		String status = "delivered";
		try{
		conn= DBConnection.getConnection();
		
		for (int i=0; i<tests_list.size(); i++){
			
		String query2 = "update lab_test_orders "
				+ " set status ='" + status +"' "
				+ "where invoice_no = '" + invoice_no +"' " +"and test_id = '" + tests_list.get(i) + "' ";
	
		PreparedStatement pstmt = conn.prepareStatement(query2);
		pstmt.executeUpdate();
		}
		conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
	}

	@Override
	public ArrayList<TestOrderItem> getDeliveredResult(String invoice_no) throws RemoteException {
		ArrayList<TestOrderItem> list = new ArrayList<TestOrderItem>();
		try{
			conn = DBConnection.getConnection();
			String query = "SELECT * FROM test_order_view "
					+ "WHERE invoice_no ='" +invoice_no + "' "
					+ "AND status ='delivered' ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				TestOrderItem toi = new TestOrderItem();
				toi.setInvoice_no(rs.getString(1));
				toi.setTest_id(rs.getString(10));
				toi.setStatus(rs.getString(12));
				toi.setDate(rs.getDate(13).toString());
				toi.setTest_name(rs.getString(11));
				toi.setPrice(rs.getDouble(14));
								
				list.add(toi);
			}
			conn.close();
		}catch(Exception ex){ ex.printStackTrace(); }
		return list;
	}

	
	@Override
	public ArrayList<ResultLine> getInvoiceResult(String invoice_no) throws RemoteException {
		ArrayList<ResultLine> lineItems = new ArrayList<ResultLine>();
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from lab_delivered_results_view where invoice_no = '" + invoice_no +"' ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
				ResultLine rl = new ResultLine();
				rl.setInvoice_no(rs.getString(1));
				rl.setTest_id(rs.getString(2));
				rl.setTest_name(rs.getString(3));
				rl.setParameter_code(rs.getString(4));
				rl.setResult(rs.getString(6));
				rl.setTest_parameter(rs.getString(5));
				rl.setUnit(rs.getString(7));
				rl.setNormal_range(rs.getString(8));
				
				lineItems.add(rl);
			}
		
			//conn.close();
									
		} catch (Exception e) {	e.printStackTrace();	}
			
		return lineItems;
	}

	@Override
	public ArrayList<LabRequest> getPendingRequest() throws RemoteException {
		ArrayList<LabRequest> testgroups = new ArrayList<>();
		
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from pending_lab_requests";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
			testgroups.add(new LabRequest(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
			}
			conn.close();					
		} catch (Exception e) {	e.printStackTrace();	}		
		return testgroups;
	}

	@Override
	public ArrayList<LabRequest> getConfirmedRequests() throws RemoteException {
		ArrayList<LabRequest> testgroups = new ArrayList<>();
		
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from confirmed_lab_requests";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
			testgroups.add(new LabRequest(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
			}
			conn.close();					
		} catch (Exception e) {	e.printStackTrace();	}		
		return testgroups;
	}

	@Override
	public ArrayList<LabRequest> getDeliveredRequest() throws RemoteException {
		ArrayList<LabRequest> testgroups = new ArrayList<>();
		
		try {
			conn= DBConnection.getConnection();
			String query2 = "select * from delivered_lab_requests";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			//extract data from result set
			while(rs.next()){
			testgroups.add(new LabRequest(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
			}
			conn.close();					
		} catch (Exception e) {	e.printStackTrace();	}		
		return testgroups;
	}

	@Override
	public ArrayList<LabRequest> getLabRequestSummary(String status) throws RemoteException {
		ArrayList<LabRequest> testgroups = new ArrayList<>();
		
		try {
			conn= DBConnection.getConnection();
			String query = "";
			if (status.equalsIgnoreCase("active")) {
				query = "select * from lab_request_summary_view "
						+ "WHERE status in ('pending', 'confirmed') ";
			}else if (status.equalsIgnoreCase("delivered")) {
				query = "select * from lab_request_summary_view "
						+ "WHERE status = 'delivered' ";
			}			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			//extract data from result set
			while(rs.next()){
			testgroups.add(new LabRequest(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(5)));
			}
			conn.close();					
		} catch (Exception e) {	e.printStackTrace();	}		
		return testgroups;
	}

	@Override
	public ArrayList<LabRequest> getLabRequestSummary(String search_by, String search_text) throws RemoteException {
		ArrayList<LabRequest> testgroups = new ArrayList<>();
		
		try {
			conn= DBConnection.getConnection();
			
			String query2 = " ";
			if (search_by.equalsIgnoreCase("patient name")) {
				query2 = "select invoice_no, patient_surname, patient_firstname, status, count(*) as no_of_items from test_order_view "
						+ "WHERE (patient_surname LIKE '" +search_text+ "%' " + "OR patient_firstname LIKE '" +search_text+"%') AND status = 'delivered' "
						+ " GROUP BY 1";
			}else {
				query2 = "select invoice_no, patient_surname, patient_firstname, status, count(*) as no_of_items from test_order_view "
						+ "WHERE invoice_no = '"+search_text+"' AND status = 'delivered' "
						+ " GROUP BY 1";
			}			
			 
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			while(rs.next()){
			testgroups.add(new LabRequest(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(5)));
			}
						
			conn.close();					
		} catch (Exception e) {
			e.printStackTrace();	
			}		
		return testgroups;
	}

	
	public ArrayList<LabRequest> getLabRequestSummary(java.sql.Date from_date, java.sql.Date to_date) throws RemoteException {
		ArrayList<LabRequest> testgroups = new ArrayList<>();
		
		try {
			conn= DBConnection.getConnection();
			
			String query2 = "select invoice_no, patient_surname, patient_firstname, status, count(*) as no_of_items from test_order_view "
						+ "WHERE status = 'delivered' AND test_order_date BETWEEN '"+from_date+ "' AND '" +to_date+"' "
						+ " GROUP BY 1";
					 
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
						
			while(rs.next()){
			testgroups.add(new LabRequest(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(5)));
			}
			
			conn.close();					
		} catch (Exception e) {	e.printStackTrace();	}		
		return testgroups;
	}


}
