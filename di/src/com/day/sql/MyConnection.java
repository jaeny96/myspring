package com.day.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection {
//	public static Connection getConnection() {
//		String url = "jdbc:oracle:thin:@localhost:1521:XE";
//		String user = "hr";
//		String pwd = "hr";
//		try {
//			return DriverManager.getConnection(url, user, pwd);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "hr";
		String pwd = "hr";

		return DriverManager.getConnection(url, user, pwd);
	}

	public static void close(Connection con, Statement stmt, ResultSet rs) {
		// DB와 연결해제
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 각각 try~catch문 사용하여 close 해줘야 함! 어디서 오류날지 모름
		// close 안해주면 메모리 누수(leak)가 생김
	}
}