package com.chana.coupons.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
	public static String jdbcUrl ="jdbc:mysql://127.0.0.1:3306/coupon_system";
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(jdbcUrl, "root", "dontforget");
	}
}
