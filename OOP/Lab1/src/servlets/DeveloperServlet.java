package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dbconnection.ConnectionPool;
import entities.Task;

/**
 * Servlet implementation class DeveloperServlet
 */
@WebServlet("/dev")
public class DeveloperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JsonObject data;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");	    
		
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		Connection connection = null;		
		Statement st = null;
		
		try {
			connection = cp.getConnection();
			st = connection.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
		int employee_id = data.get("employee_id").getAsInt();
		
		String sql = 
				"SELECT task_id,name "
				+ "FROM developing INNER JOIN tasks ON task_id = id "
				+ "WHERE employee_id = '" + employee_id + "'";					
		ResultSet rs;
		try {
			rs = st.executeQuery(sql);
			List<Task> list = new ArrayList<Task>();
			while(rs.next()) {
				list.add(new Task(rs.getInt(1),rs.getString(2)));
			}
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			cp.releaseConnection(connection);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");	
	    response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");	    
						
		data = new Gson().fromJson(request.getReader(), JsonObject.class);
		if(data.get("doGet") != null) {
			doGet(request,response);
			return;
		}
		
		int task_id = data.get("task_id").getAsInt();
		int employee_id = data.get("employee_id").getAsInt();
		
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		Connection connection = null;		
		Statement st = null;
		
		try {
			connection = cp.getConnection();
			st = connection.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String sql = "SELECT hrs FROM developing WHERE task_id = '" + task_id + "'"+" AND employee_id = '" + employee_id + "'";					
		ResultSet rs;
		
		try {
			rs = st.executeQuery(sql);
			if(rs.next()) {
				Integer hrs = rs.getInt(1);
				System.out.println(hrs);
				response.getWriter().write(hrs.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			cp.releaseConnection(connection);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		Connection connection = null;		
		Statement st = null;
		
		try {
			connection = cp.getConnection();
			st = connection.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		data = new Gson().fromJson(request.getReader(), JsonObject.class);
		int task_id = data.get("task_id").getAsInt();
		int employee_id = data.get("employee_id").getAsInt();
		int hrs = data.get("hrs").getAsInt();
		
		String sql = 
				"UPDATE developing "
				+ "SET hrs = '"+ hrs +"' "
				+ "WHERE task_id = '" + task_id + "' AND employee_id = '" + employee_id +"'" ;
		
	//	String sql = "UPDATE developing SET hrs='11' WHERE task_id='1' AND employee_id='1'";
		
		try {
			st.execute(sql);
			cp.releaseConnection(connection);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
