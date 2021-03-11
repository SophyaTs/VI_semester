package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbconnection.ConnectionPool;
import entities.Role;
import entities.Task;

public class DevelopingDAO {
	public static List<Task> getTasksByEmpId(final long empId){
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"SELECT id,name,project_id,qualification,workers_num "
					+ "FROM developing INNER JOIN tasks ON task_id = id "
					+ "WHERE employee_id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, empId);
			ResultSet rs = st.executeQuery();
			List<Task> tasks = new ArrayList<Task>();
			while(rs.next()) {				
				long id = rs.getLong(1);
				String name = rs.getString(2);
				long project_id = rs.getLong(3);
				Role qualification = Role.roles.get((int)rs.getLong(4)-1);
				long workers_num = rs.getLong(5);
				tasks.add(new Task(id,name,project_id,qualification,workers_num));
			}
			cp.releaseConnection(connection);
			return tasks;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
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
			cp.releaseConnection(connection);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
