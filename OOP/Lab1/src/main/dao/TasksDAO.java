package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.dbconnection.ConnectionPool;
import main.entities.Role;
import main.entities.Task;

public class TasksDAO {
	public static List<Task> getTasksByEmpId(final long empId){
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"SELECT id,name,project_id,qualification,workers_num "
					+ "FROM developing INNER JOIN tasks ON task_id = id "
					+ "WHERE employee_id = ? AND active='true'";		
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
			st.close();
			cp.releaseConnection(connection);
			return tasks;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Task> getTasksByProjectId(final long projectId){
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM tasks "
					+ "WHERE project_id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, projectId);
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
			st.close();		
			cp.releaseConnection(connection);
			return tasks;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Task getTask(final long taskId) {
		ConnectionPool cp = ConnectionPool.getConnectionPool();		
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"SELECT * FROM tasks WHERE id = ?";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, taskId);
			ResultSet rs = st.executeQuery();			
			Task task = new Task();
			if(rs.next()) {				
				task.setId(rs.getLong(1));
				task.setName(rs.getString(2));
				task.setProject_id(rs.getLong(3));
				task.setQualification(Role.roles.get((int)rs.getLong(4)-1));
				task.setWorkers_num(rs.getLong(5));
			} else {
				System.out.println("Warning: no task with this id");
				return null;
			}
			st.close();
			cp.releaseConnection(connection);
			return task;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
