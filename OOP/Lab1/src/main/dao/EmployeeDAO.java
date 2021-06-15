package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.dbconnection.ConnectionPool;
import main.entities.Employee;
import main.entities.Role;
import main.entities.Task;

public class EmployeeDAO {
	public static Employee getEmployeeByLogin(final String login) {
		ConnectionPool cp = ConnectionPool.getConnectionPool();		
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"SELECT * FROM employees WHERE login = ?";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, login);
			ResultSet rs = st.executeQuery();			
			Employee emp = new Employee();
			if(rs.next()) {				
				emp.setId(rs.getInt(1));
				emp.setName(rs.getString(2));
				emp.setLogin(rs.getString(3));
				emp.setPassword(rs.getString(4));
				emp.setPosition(Role.roles.get((int)rs.getLong(5)-1));
				emp.setSalary(rs.getInt(6));
			}
			if(rs.next()) {
				System.out.println("Error: multiple users with same login");
				cp.releaseConnection(connection);
				return null;
			}
			st.close();
			cp.releaseConnection(connection);
			return emp;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Employee> getEmployeesByQualification(final Role role){
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"SELECT id,name,position,salary "
					+ "FROM employees "
					+ "WHERE position = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, role.getId());
			ResultSet rs = st.executeQuery();			
			List<Employee> employees = new ArrayList<Employee>();
			while(rs.next()) {				
				Employee emp = new Employee();
				emp.setId(rs.getLong(1));
				emp.setName(rs.getString(2));
				emp.setPosition(Role.roles.get((int)rs.getLong(3)-1));
				emp.setSalary(rs.getLong(4));
				employees.add(emp);
			}
			st.close();
			cp.releaseConnection(connection);
			return employees;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Long> getActiveEmployeeIdsByTaskId(final long taskId){
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"SELECT id "
					+ "FROM employees INNER JOIN developing ON id=employee_id "
					+ "WHERE task_id = ? AND active='true'";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, taskId);
			ResultSet rs = st.executeQuery();			
			List<Long> employees = new ArrayList<Long>();
			while(rs.next()) {				
				employees.add(rs.getLong(1));
			}	
			st.close();
			cp.releaseConnection(connection);
			return employees;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
