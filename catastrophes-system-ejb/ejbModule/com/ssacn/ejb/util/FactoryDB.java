package com.ssacn.ejb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class FactoryDB {
	public FactoryDB() {
		// TODO Auto-generated constructor stub
	}

	public void createDB(String db) {
		Connection conn = null;
		Statement stmt = null;
		try {
			PropertiesManager config = new PropertiesManager();
			String driver = config.getPropiedad("driver");
			String url = config.getPropiedad("url");
			String user = config.getPropiedad("user");
			String pass = config.getPropiedad("password");

			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			stmt = conn.createStatement();
			String sql = "CREATE DATABASE " + db.trim();
			stmt.executeUpdate(sql);
			System.out.println("Database created successfully...");

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}

}
