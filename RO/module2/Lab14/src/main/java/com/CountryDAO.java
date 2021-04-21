package com;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {
	public static Country findById(long id) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM country "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();	
			Country country = null;
			if(rs.next()) {	
				country = new Country();
				country.setId(rs.getLong(1));
				country.setName(rs.getString(2));
			}
			st.close();
			return country;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static Country findByName(String name) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM country "
					+ "WHERE name = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, name);
			ResultSet rs = st.executeQuery();	
			Country country = null;
			if(rs.next()) {	
				country = new Country();
				country.setId(rs.getLong(1));
				country.setName(rs.getString(2));
			}
			st.close();
			return country;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static boolean update(Country country) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"UPDATE country "
					+ "SET name = ? "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, country.getName());
			st.setLong(2, country.getId());
			var result = st.executeUpdate();
			st.close();
			if(result>0)
				return true;
			else
				return false;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static boolean insert(Country country) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"INSERT INTO country (name) "
					+ "VALUES (?) "
					+ "RETURNING id";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, country.getName());
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				country.setId(rs.getLong(1));
			} else
				return false;
			st.close();	
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static boolean delete(Country country) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"DELETE FROM country "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, country.getId());
			var result = st.executeUpdate();
			st.close();
			if(result>0)
				return true;
			else
				return false;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static List<Country> findAll(){
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM country";		
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();			
			List<Country> list = new ArrayList<>();
			while(rs.next()) {
				var country = new Country();
				country.setId(rs.getLong(1));
				country.setName(rs.getString(2));
				list.add(country);
			}
			st.close();
			return list;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
