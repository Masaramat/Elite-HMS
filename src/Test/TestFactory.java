package Test;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.ahms.hmgt.entities.Bed;

public class TestFactory {

	public TestFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<Bed> getAllBeds(){
		ArrayList<Bed> pblist = new ArrayList<Bed>();	
		try {
			Connection conn  = DBConnection.getConnection();
			String query = "select * from beds_view ";
			Statement st= conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				Bed bed = new Bed();
				bed.setBedNo(rs.getString(1));
				bed.setBedDetails(rs.getString(2));
				
				bed.setWardName(rs.getString(7));
				bed.setBedCharge(rs.getDouble(5));
				bed.setStatus(rs.getString(6));
				
				pblist.add(bed);
				
			}
			conn.close();
			
			
		} catch (Exception e) {	e.printStackTrace();	}		
				
		return pblist;
	}
	
	

}
