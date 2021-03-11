package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbconnection.ConnectionPool;
import entities.Role;

public class RoleDAO {
	public static List<Role> getRolesAll(){
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM role";		
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Role> roles = new ArrayList<Role>();
			while(rs.next()) {				
				long id = rs.getLong(1);
				String title = rs.getString(2);
				roles.add(new Role(id,title));
			}
			cp.releaseConnection(connection);
			return roles;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
