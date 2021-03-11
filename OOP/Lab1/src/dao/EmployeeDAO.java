package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbconnection.ConnectionPool;
import entities.Employee;
import entities.Role;

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
			cp.releaseConnection(connection);
			return emp;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
