package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import main.dbconnection.ConnectionPool;
import main.entities.Role;
import main.entities.Task;

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
			st.executeUpdate();
			st.close();
			cp.releaseConnection(connection);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static int updateActive(final long empId, final long taskId, final boolean active) {
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
			int count = st.executeUpdate();
			st.close();
			cp.releaseConnection(connection);
			return count;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void insert(final long empId, final long taskId, final long hrs, final boolean active) {
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"INSERT INTO developing (employee_id,task_id,hrs,active) "
					+ "VALUES (?,?,?,?)";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, empId);
			st.setLong(2, taskId);
			st.setLong(3, hrs);
			st.setBoolean(4, active);
			int count = st.executeUpdate();
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
	
	public static List<List<Long>> getCostAndHoursByProjectId(final long projectId){
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"SELECT salary,hrs "
					+ "FROM (employees INNER JOIN developing ON id=employee_id) INNER JOIN tasks ON developing.task_id=tasks.id "
					+ "WHERE project_id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, projectId);
			ResultSet rs = st.executeQuery();			
			List<List<Long>>  result = new ArrayList<>();
			while(rs.next()) {	
				result.add(new ArrayList<>());
				result.get(result.size()-1).add(rs.getLong(1));
				result.get(result.size()-1).add(rs.getLong(2));
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
	
	public static void updateActiveDevelopers(final long taskId, final List<Long> empIds) {
		List<Long> oldIds = EmployeesDAO.getActiveEmployeeIdsByTaskId(taskId);
		List<Long> added = new ArrayList<>();
		List<Long> deleted = new ArrayList<>();
		for(long id : empIds)
			if(!oldIds.contains(id))
				added.add(id);
		for(long id : oldIds)
			if(!empIds.contains(id))
				deleted.add(id);
		
		for(long id : added)
			if(updateActive(id,taskId,true) == 0)
				insert(id,taskId,0,true);
		for(long id : deleted)
			updateActive(id,taskId,false);
	}
}
