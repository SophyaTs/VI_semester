package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.entity.Containing;

public class ContainingDAO {
	public static Containing findById(long orderid, long productid) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM containing "
					+ "WHERE orderid = ? AND productid = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, orderid);
			st.setLong(2, productid);
			ResultSet rs = st.executeQuery();	
			Containing e = null;
			if(rs.next()) {	
				e = new Containing();
				e.setOrderid(rs.getLong(1));
				e.setProductid(rs.getLong(2));
				e.setQuantity(rs.getLong(3));
			}
			st.close();
			return e;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static boolean update(Containing e) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"UPDATE containing "
					+ "SET quantity = ? "
					+ "WHERE orderid = ? AND productid = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, e.getQuantity());
			st.setLong(2, e.getOrderid());
			st.setLong(3, e.getProductid());
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
	
	public static boolean insert(Containing e) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"INSERT INTO containing (orderid,productid,quantity) "
					+ "VALUES (?,?,?) ";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(3, e.getQuantity());
			st.setLong(1, e.getOrderid());
			st.setLong(2, e.getProductid());
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
	
	public static boolean delete(long orderid, long productid) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"DELETE FROM containing "
					+ "WHERE orderid = ? AND productid = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, orderid);
			st.setLong(2, productid);
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
