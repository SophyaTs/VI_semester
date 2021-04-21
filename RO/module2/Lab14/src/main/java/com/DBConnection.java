package com;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection connection = null;
	
	private final static String url = "jdbc:postgresql://localhost:5432/WorldMap";
	private final static String user = "postgres";
	private final static String password = "password";
	
	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url,user,password);
			}
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}

}
