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
	private JsonObject data;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");	    
		long employee_id = data.get("employee_id").getAsInt();
		List<Task> list = TasksDAO.getTasksByEmpId(employee_id);
		String json = new Gson().toJson(list);
		response.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");			
	    response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");	    
						
		data = new Gson().fromJson(request.getReader(), JsonObject.class);
		
		if(data.get("doGet") != null) {
			doGet(request,response);
			return;
		}
		
		response.setContentType("text/plain");
		long task_id = data.get("task_id").getAsLong();
		long employee_id = data.get("employee_id").getAsLong();	
		Long hrs = DevelopingDAO.getHours(employee_id, task_id);
		response.getWriter().write(hrs.toString());
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		data = new Gson().fromJson(request.getReader(), JsonObject.class);
		long task_id = data.get("task_id").getAsLong();
		long employee_id = data.get("employee_id").getAsLong();
		long hrs = data.get("hrs").getAsLong();
		DevelopingDAO.updateHours(employee_id, task_id, hrs);
	}

}
