package com.example.sql_folder;

import java.sql.*;

/**
 * Created by Jens on 11-Jan-17.
 */
public class DBConnection {

	static Connection conn;

	public DBConnection() {
		connect();
	}

	boolean connect() {
		//Sjekker om connection allerede finnes
		try {
			if (conn == null || conn.isClosed()) {
				String url = "jdbc:mysql://mysql.stud.iie.ntnu.no/g_scrum06";
				String user = "g_scrum06";
				String pass = "aF9SoPaP";
				conn = DriverManager.getConnection(url, user, pass);
			}
			if (conn.isValid(3)){
				return true;
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return false;
	}
	static void main(String[] args) throws Exception {
		DBConnection conn = new DBConnection();
//		DBConnection.connect();
//		DBConnection.execute("SELECT fornavn FROM bruker", null);
		PreparedStatement prep = DBConnection.conn.prepareStatement("SELECT fornavn FROM bruker");
		ResultSet res = prep.executeQuery();
		if (res.next()) {
			System.out.println(res.getString(1));
		}
//		Array arr = res.getString(1);
//		System.out.println(Arrays.toString((String[])arr.getArray()));
	}
}
