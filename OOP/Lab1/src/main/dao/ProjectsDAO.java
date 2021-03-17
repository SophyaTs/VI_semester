package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.dbconnection.ConnectionPool;
import main.entities.Project;

public class ProjectsDAO {
	public static List<Project> getProjectsAll(){
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		try(Connection connection = cp.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM projects";		
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();			
			List<Project> projects = new ArrayList<Project>();
			while(rs.next()) {				
				long id = rs.getLong(1);
				String name = rs.getString(2);
				projects.add(new Project(id,name));
			}
			st.close();
			cp.releaseConnection(connection);
			return projects;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
