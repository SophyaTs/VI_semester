

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDAO {
	public static City findById(long id) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM city "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();	
			City city = null;
			if(rs.next()) {	
				city = new City();
				city.setId(rs.getLong(1));
				city.setName(rs.getString(2));
				city.setCountryid(rs.getLong(3));
				city.setPopulation(rs.getLong(4));
			}
			st.close();
			return city;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static City findByName(String name) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM city "
					+ "WHERE name = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, name);
			ResultSet rs = st.executeQuery();	
			City city = null;
			if(rs.next()) {	
				city = new City();
				city.setId(rs.getLong(1));
				city.setName(rs.getString(2));
				city.setCountryid(rs.getLong(3));
				city.setPopulation(rs.getLong(4));
			}
			st.close();
			return city;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static boolean update(City city) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"UPDATE city "
					+ "SET name = ?, countryid = ?, population = ? "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, city.getName());
			st.setLong(2, city.getCountryid());
			st.setLong(3, city.getPopulation());
			st.setLong(4, city.getId());
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
	
	public static boolean insert(City city) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"INSERT INTO city (name,countryid,population) "
					+ "VALUES (?,?,?) "
					+ "RETURNING id";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, city.getName());
			st.setLong(2, city.getCountryid());
			st.setLong(3, city.getPopulation());
			ResultSet rs = st.executeQuery();	
			if(rs.next()) {
				city.setId(rs.getLong(1));
			} else
				return false;
			st.close();
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static boolean delete(City city) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"DELETE FROM city "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, city.getId());
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
	
	public static List<City> findAll(){
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM city";		
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();			
			List<City> list = new ArrayList<>();
			while(rs.next()) {
				var city = new City();
				city.setId(rs.getLong(1));
				city.setName(rs.getString(2));
				city.setCountryid(rs.getLong(3));
				city.setPopulation(rs.getLong(4));
				list.add(city);
			}
			st.close();
			return list;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static List<City> findByCountryId(Long id){
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM city "
					+ "WHERE countryid = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			List<City> list = new ArrayList<>();
			while(rs.next()) {
				var city = new City();
				city.setId(rs.getLong(1));
				city.setName(rs.getString(2));
				city.setCountryid(rs.getLong(3));
				city.setPopulation(rs.getLong(4));
				list.add(city);
			}
			st.close();
			return list;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
