package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.entity.Product;


public class ProductDAO {
	public static Product findById(long id) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM product "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();	
			Product e = null;
			if(rs.next()) {	
				e = new Product();
				e.setId(rs.getLong(1));
				e.setName(rs.getString(2));
				e.setDescription(rs.getString(3));
				e.setPrice(rs.getLong(4));
			}
			st.close();
			return e;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static boolean update(Product e) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"UPDATE product "
					+ "SET name = ?, description = ?, price = ? "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, e.getName());
			st.setString(2, e.getDescription());
			st.setLong(3, e.getPrice());
			st.setLong(4, e.getId());
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
	
	public static boolean insert(Product e) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"INSERT INTO product (name,description,price) "
					+ "VALUES (?,?,?) "
					+ "RETURNING id";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, e.getName());
			st.setString(2, e.getDescription());
			st.setLong(3, e.getPrice());
			ResultSet rs = st.executeQuery();	
			if(rs.next()) {
				e.setId(rs.getLong(1));
			} else
				return false;
			st.close();
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static boolean delete(long id) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"DELETE FROM product "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
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
}
