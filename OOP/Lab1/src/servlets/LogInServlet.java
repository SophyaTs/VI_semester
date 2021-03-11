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
import entities.Employee;
import entities.Role;
import dao.EmployeeDAO;

@WebServlet("/index")
public class LogInServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");	
	    response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");	    

		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
		
		String login = data.get("login").getAsString();
		String password = data.get("password").getAsString();

		Employee emp = EmployeeDAO.getEmployeeByLogin(login);
		Response r;
		if(emp != null) {
			if(emp.getPassword().equals(password))
				r = new Response(true,emp.getId(),emp.getName(),emp.getPosition());
			else
				r = new Response(false);
		}
		else
			r = new Response(false);
		response.getWriter().write(new Gson().toJson(r));			
	}
	
	private static class Response{
		public boolean status;
		public long id;
		public String name;
		public String role;
		
		public Response(boolean status, long id, String name, Role role) {
			super();
			this.status = status;
			this.id = id;
			this.name = name;
			this.role = role.getTitle().equals("manager") ? "m" : "d";
		}

		public Response(boolean status) {
			super();
			this.status = status;
		}	
		
	}
}
