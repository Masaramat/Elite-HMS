package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public DBConnection() {
		// TODO Auto-generated constructor stub
	}
	
	Connection conn;		
	 
	// JDBC driver name and database URL
		private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		private static String DB_URL = "jdbc:mysql://localhost:3306/hospisoft";
				
		// Database credentials
		private static String USER = "root";
		private static String PASS = "longji94";

		// method to get database connection
		public static Connection getConnection()throws ClassNotFoundException, SQLException{
			  Class.forName(JDBC_DRIVER);
			  Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			  return conn;
		} 


}
