package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
	private static ConnectionPool cp = new ConnectionPool();
	
	private final String url = "jdbc:postgresql://localhost:5432/development_team";
	private final String user = "postgres";
	private final String password = "password";
	private final int MAX_CONNECTIONS = 5;
	
	private BlockingQueue<Connection> connections;
	
	private ConnectionPool() {
		try {
			 Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			 System.out.println("JDBC Driver not found");
			 e.printStackTrace();
		}
		
		connections = new LinkedBlockingQueue<Connection>(MAX_CONNECTIONS);		
		
		try {
			for(int i = 0; i < MAX_CONNECTIONS; ++i) {
				connections.put(DriverManager.getConnection(url,user,password));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static ConnectionPool getConnectionPool() {
		return cp;
	}
	
	public Connection getConnection() throws InterruptedException {
		return connections.take();
	}
	
	public void releaseConnection(Connection c) throws InterruptedException {
		connections.put(c);
	}
}
