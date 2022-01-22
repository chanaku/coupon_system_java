package com.chana.coupons.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



public class ConnectionPool {
	private static ConnectionPool instance = new ConnectionPool();
	private static Set<Connection> connections;
	private static final int MAX_CONNECTIONS = 10;
	private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/coupon_system";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "dontforget";
	
	private ConnectionPool() {
		connections = new HashSet<Connection>();
		for (int i=0; i<MAX_CONNECTIONS; i++) {
			Connection connection;
			try {
				connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
			connections.add(connection);
			} catch (SQLException e) {
				e.printStackTrace();
			
			}
			
		}
	}
	public static ConnectionPool getInstance() {
		
		return instance;
	}
	public synchronized Connection getConnection() {
		while(connections.isEmpty()) {
			try {
				wait();
			}catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			}
		}
		Iterator<Connection> cursor = connections.iterator();
		Connection connection = cursor.next();
		cursor.remove();
		return connection;
	}
	
	public synchronized  void restoreConnection(Connection connection) {
		connections.add(connection);
		notifyAll();  
	}
	public synchronized void closeAllConnections() {
		int closeConnectionCounter = 0;
		while (closeConnectionCounter < MAX_CONNECTIONS) {
			while (connections.isEmpty()) {
				try {
					wait();
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
			}Iterator<Connection> cursor = connections.iterator();
			while (cursor.hasNext()) {
				Connection currentConnection = cursor.next();
				try {
					currentConnection.close();
					cursor.remove();
					closeConnectionCounter++;
				}catch (SQLException e) {
					
				}
			}
		}
		
	}
	public static void closeResources(PreparedStatement preparedStatement) {
		try {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
		}catch (SQLException e) {
			
		}
		
	}
	public static void closeResources(PreparedStatement preparedStatement, ResultSet resultSet) {
		closeResources(preparedStatement);
		try {
			if(resultSet != null) {
				resultSet.close();
			}
		}catch (SQLException e) {
		}
	}
	}

