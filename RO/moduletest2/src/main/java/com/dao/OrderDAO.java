package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.entity.Containing;
import com.entity.Order;

public class OrderDAO {
	public static Order findById(long id) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM orders "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();	
			Order e = null;
			if(rs.next()) {	
				e = new Order();
				e.setId(rs.getLong(1));
				e.setDate(rs.getDate(2));
			}
			st.close();
			return e;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static boolean update(Order e) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"UPDATE orders "
					+ "SET date = ? "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setDate(1, e.getDate());
			st.setLong(2, e.getId());
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
	
	public static boolean insert(Order e) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"INSERT INTO orders (date) "
					+ "VALUES (?) "
					+ "RETURNING id";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setDate(1, e.getDate());
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
					"DELETE FROM orders "
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
	
	public static String getPrettyInfo(long id) {
		var e = findById(id);
		if(e == null)
			return "";
		
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT name,description,price,quantity "
					+ "FROM (orders INNER JOIN containing ON orders.id = orderid) "
					+ "INNER JOIN product ON productid = product.id "
					+ "WHERE orderid = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();	
			var str = new StringBuilder();
			
			str.append("Date: "+e.getDate().toString()+ "\n");
			while(rs.next()) {	
				str.append(rs.getString(1)+"\n\t"+ rs.getString(2)+"\nPrice: "+rs.getLong(3)
						+"\nQuantity: "+rs.getLong(4)+"\n");
			}
			st.close();
			return str.toString();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static List<Long> findByProductid(long id){
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT id "
					+ "FROM orders INNER JOIN containing ON id = orderid "
					+ "WHERE productid = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();			
			List<Long> list = new ArrayList<>();
			while(rs.next()) {
				list.add(rs.getLong(1));
			}
			st.close();
			return list;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static List<Long> findByNotProductidAndToday(long productid){
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT id "
					+ "FROM orders "
					+ "WHERE date = CURRENT_DATE AND id NOT IN ( "
					+"	SELECT id "
					+"	FROM orders INNER JOIN containing ON id=orderid "
					+"	WHERE productid = ? "
					+")";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, productid);
			ResultSet rs = st.executeQuery();			
			List<Long> list = new ArrayList<>();
			while(rs.next()) {
				list.add(rs.getLong(1));
			}
			st.close();
			return list;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static boolean placeTodayOrder() {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT DISTINCT productid "
					+ "FROM containing INNER JOIN orders ON orderid = id "
					+ "WHERE date = CURRENT_DATE";		
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();			
			List<Long> list = new ArrayList<>();
			while(rs.next()) {
				list.add(rs.getLong(1));
			}
			st.close();
			
			var order = new Order();
			order.setDate(new Date(System.currentTimeMillis()));
			if(!insert(order))
				return false;
			for(long id : list) {
				var c = new Containing(order.getId(),id,1);
				ContainingDAO.insert(c);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteSpecial(long productid, long quantity) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"DELETE FROM orders "
					+ "WHERE id IN ( "
					+ "SELECT id "
					+ "FROM orders INNER JOIN containing ON id = orderid "
					+ "WHERE productid = ? AND quantity = ?)";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, productid);
			st.setLong(2, quantity);
			var result = st.executeUpdate();
			st.close();
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static List<Long> filterByCostAndQuantity(long cost, long q){
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT orderid,price,quantity "
							+ "FROM product INNER JOIN containing ON id = productid "
							+ "ORDER BY orderid";		
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();			
			List<Long> list = new ArrayList<>();
			long currentCost = 0;
			long quantity = 0;
			long currentId = 0;
			while(rs.next()) {
				var id = rs.getLong(1);
				if(id != currentId) {
					if (quantity == q && currentCost <= cost) {
						list.add(currentId);
					}
					currentCost = 0;
					quantity = 0;
					currentId = id;
				}
				var newPr = rs.getLong(3);
				quantity += newPr;
				currentCost += newPr * rs.getLong(2);
			}
			st.close();
			return list;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
