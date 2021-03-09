package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dbconnection.ConnectionPool;

@WebServlet("/index")
public class LogInServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
        //requestDispatcher.forward(request, response);
		
		//response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		try {
//			 Class.forName("org.postgresql.Driver");
//		} catch (ClassNotFoundException e) {
//			 System.out.println("JDBC Driver not found");
//			 e.printStackTrace();
//		}
//		 
//		try(Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/development_team", "postgres","password");) {
//			Statement st = connection.createStatement();
//
//			String login = request.getParameter("login");
//			String password = request.getParameter("password");
//			
//			String sql = "SELECT password FROM employees WHERE login = '" + login + "'";
//			
//			ResultSet rs = st.executeQuery(sql);
//			if(rs.next()) {
//				String expectedPassword = rs.getString(1);
//				if(expectedPassword.equals(password)) {
//					doGet(request,response);
//					System.out.println("Logged in");
//				} else
//					System.out.println("Wrong password");
//			} else
//				System.out.println("User not found");
//			
//			st.close();
//			connection.close();
//			
//		} catch (SQLException e) {
//			System.out.println("Database not found or SQL was not executed properly");
//			e.printStackTrace();
//		} 
		//response.setStatus(HttpServletResponse.SC_OK);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");	
	    //response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
	    //response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
	    response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");	    
		
		ConnectionPool cp = ConnectionPool.getConnectionPool();
		Connection connection = null;		
		Statement st = null;
		
		try {
			connection = cp.getConnection();
			st = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setCharacterEncoding("UTF-8");
		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
		
		String login = data.get("login").getAsString();
		String password = data.get("password").getAsString();
		System.out.println(login+" "+ password);
					
		String sql = "SELECT password,name FROM employees WHERE login = '" + login + "'";					
		ResultSet rs;
		response.setContentType("text/plain"); 
		
		try {
			rs = st.executeQuery(sql);		
			//response.setCharacterEncoding("UTF-8");response.setContentType("JSON");
			if(rs.next()) {
				String expectedPassword = rs.getString(1);
				if(expectedPassword.equals(password)) {
					System.out.println("ok");
					response.getWriter().write(new Gson().toJson(new Response(true,"dev",rs.getString(2))));
				} else
					System.out.println("error: wrong password");
					response.getWriter().write(new Gson().toJson(new Response(false,"","")));
			} else {
				System.out.println("error: no such user");
				response.getWriter().write(new Gson().toJson(new Response(false,"","")));
			}
			cp.releaseConnection(connection);

		} catch (SQLException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	private static class Response{
		public boolean status;
		public String role;
		public String name;
		public Response(boolean status, String role, String name) {
			super();
			this.status = status;
			this.role = role;
			this.name = name;
		}
	}
}
