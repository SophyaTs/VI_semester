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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.DevelopingDAO;
import dao.TasksDAO;
import dbconnection.ConnectionPool;
import entities.Task;

/**
 * Servlet implementation class DeveloperServlet
 */
@WebServlet("/dev")
public class DeveloperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.setCharacterEncoding("UTF-8");			
	    //response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");	    
		
		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
		long employee_id = 0;
		long task_id = 0;
		Long hrs = (long) 0;
		switch(data.get("action").getAsString()){
		case "getlist":
			response.setContentType("application/json");	    
			employee_id = data.get("employee_id").getAsInt();
			List<Task> list = TasksDAO.getTasksByEmpId(employee_id);
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
			break;
		case "get":
			response.setContentType("text/plain");
			task_id = data.get("task_id").getAsLong();
			employee_id = data.get("employee_id").getAsLong();	
			hrs = DevelopingDAO.getHours(employee_id, task_id);
			response.getWriter().write(hrs.toString());
			break;
		case "save":
			task_id = data.get("task_id").getAsLong();
			employee_id = data.get("employee_id").getAsLong();
			hrs = data.get("hrs").getAsLong();
			DevelopingDAO.updateHours(employee_id, task_id, hrs);
			break;
		};

	}
	
//	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
//		if (!SessionValidation.validateSession(request, response))
//			return;
//		
//		data = new Gson().fromJson(request.getReader(), JsonObject.class);
//		
//	}

}
