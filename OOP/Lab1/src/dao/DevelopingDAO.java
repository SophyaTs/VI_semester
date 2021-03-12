package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dbconnection.ConnectionPool;
import entities.Role;
import entities.Task;

public class DevelopingDAO {
	public static long getHours(final long empId, final long taskId) {
		long hours = 0;
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"SELECT hrs "
					+ "FROM developing "
					+ "WHERE task_id = ? AND employee_id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(2, empId);
			st.setLong(1, taskId);
			ResultSet rs = st.executeQuery();			
			if(rs.next()) {				
				hours = rs.getInt(1);
			}
			st.close();
			cp.releaseConnection(connection);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return hours;
	}
	
	public static void updateHours(final long empId, final long taskId, final long hours) {
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"UPDATE developing "
					+ "SET hrs = ?"
					+ "WHERE task_id = ? AND employee_id = ?" ;		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(3, empId);
			st.setLong(2, taskId);
			st.setLong(1, hours);
			st.executeQuery();
			st.close();
			cp.releaseConnection(connection);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateActive(final long empId, final long taskId, final boolean active) {
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"UPDATE developing "
					+ "SET active = ?"
					+ "WHERE task_id = ? AND employee_id = ?" ;		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(3, empId);
			st.setLong(2, taskId);
			st.setBoolean(1, active);
			st.executeQuery();
			st.close();
			cp.releaseConnection(connection);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<Long,Long> getEmployeeIdsAndHoursByTaskId(final long taskId){
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"SELECT id,hrs "
					+ "FROM employees INNER JOIN developing ON id=employee_id "
					+ "WHERE task_id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, taskId);
			ResultSet rs = st.executeQuery();			
			Map<Long,Long> result = new LinkedHashMap<>();
			while(rs.next()) {				
				result.put(rs.getLong(1),rs.getLong(2));
			}
			st.close();
			cp.releaseConnection(connection);
			return result;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
