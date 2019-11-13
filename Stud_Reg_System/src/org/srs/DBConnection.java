package org.srs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class DBConnection {

	String url = "jdbc:mysql://localhost:3306/srs_db?autoReconnect=true&useSSL=false";
	Connection con;
	String username = "root";
	String password = "root";

	public Connection db_connect() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
		if(con==null) {
			System.out.println("Connection Failed!!!");
		}
		return con;
	}
}
