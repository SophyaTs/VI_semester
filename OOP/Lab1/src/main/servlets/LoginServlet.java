package main.servlets;

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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import main.dao.EmployeesDAO;
import main.dbconnection.ConnectionPool;
import main.entities.Employee;
import main.entities.Role;

@WebServlet("/index")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		SessionValidation.invalidateSession(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		SessionValidation.invalidateSession(request, response);
		
		Cookie[] cookies = request.getCookies();
	    Cookie cookie = null;
	    for(Cookie c: cookies) {
	    	if("LOGIN".equals(c.getName())) {
	    		cookie = c;
	    		break;
	    	}	    			
	    }
	    String login = cookie.getValue();
	    for(Cookie c: cookies) {
	    	if("PASSWORD".equals(c.getName())) {
	    		cookie = c;
	    		break;
	    	}	    			
	    }
	    String password = cookie.getValue();
	    
	    Employee emp = EmployeesDAO.getEmployeeByLogin(login);
		if(emp != null) {
			if(emp.getPassword().equals(password)) {
				SessionValidation.createSession(request);
				cookie = new Cookie("ROLE","manager".equals(emp.getPosition().getTitle()) ? "m" : "d");
				cookie.setHttpOnly(false);
				response.addCookie(cookie);
				response.getWriter().write(new Gson().toJson(new Response(emp.getId(),emp.getName(), emp.getPosition())));
			}
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");	
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.setCharacterEncoding("UTF-8");
		
		if(!SessionValidation.validateSession(request, response)) {
			return;
		}
		
		Cookie[] cookies = request.getCookies();
	    Cookie cookie = null;
	    for(Cookie c: cookies) {
	    	if("ROLE".equals(c.getName())) {
	    		cookie = c;
	    		break;
	    	}	    			
	    }
		String role = cookie.getValue();
		if ("m".equals(role))
			new ManagerServlet().doPost(request,response);
			//response.sendRedirect("/mng");
		else
			new DeveloperServlet().doPost(request,response);
			//response.sendRedirect("/dev");
		
		//response.setContentType("application/json");	
	       	    
		//JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
		
		//String login = data.get("login").getAsString();
		//String password = data.get("password").getAsString();

				
	}
	
	private static class Response{
		public long id;
		public String name;
		public boolean manager;
		
		public Response(long id, String name, Role role) {
			super();
			this.id = id;
			this.name = name;
			this.manager = "manager".equals(role.getTitle()) ? true : false;
		}		
	}
}
