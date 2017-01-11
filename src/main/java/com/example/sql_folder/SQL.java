package com.example.sql_folder;

import java.sql.*;

/**
 * Created by Jens on 11-Jan-17.
 */
public class SQL {

	static Connection conn;
	static boolean connect() {
		String url = "jdbc:mysql://mysql.stud.iie.ntnu.no/g_scrum06";
		String user = "g_scrum06";
		String pass = "aF9SoPaP";
		try {
			conn = DriverManager.getConnection(url, user, pass);
			if (conn.isValid(1)) {
				return true;
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		SQL.connect();
		PreparedStatement prep = SQL.conn.prepareStatement("SELECT fornavn FROM bruker");
		ResultSet res = prep.executeQuery();
		if (res.next()) {
			System.out.println(res.getString(1));
		}
//		Array arr = res.getString(1);
//		System.out.println(Arrays.toString((String[])arr.getArray()));
	}
}
