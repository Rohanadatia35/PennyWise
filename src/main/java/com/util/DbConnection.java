package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

		public static  Connection getConnection() {
			
			String dbUrl="jdbc:oracle:thin:@localhost:1521/free";
			String Username="ExpenseTracker";
			String password="root";
			String Drivername="oracle.jdbc.OracleDriver";
			
			try {
				Class.forName(Drivername);
				Connection con=DriverManager.getConnection(dbUrl,Username,password);
				
				return con;
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	
}
