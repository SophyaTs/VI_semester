package servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.DevelopingDAO;
import dao.EmployeesDAO;
import dao.ProjectsDAO;
import dao.TasksDAO;
import entities.Employee;
import entities.Project;
import entities.Task;

/**
 * Servlet implementation class ManagerServlet
 */
@WebServlet("/mng")
public class ManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JsonObject data;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get list of projects
		if(data.get("project").getAsBoolean() == true) {
			List<Project> list = ProjectsDAO.getProjectsAll();
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
		}
		// get list of tasks
		else {
			long prId = data.get("projectId").getAsLong();
			List<Task> list = TasksDAO.getTasksByProjectId(prId);
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		data = new Gson().fromJson(request.getReader(), JsonObject.class);
		
		// just getting lists of projects or tasks, nothing too crazy
		if(data.get("doGet").getAsBoolean() == true) {
			doGet(request,response);
			return;
		}
		
		if(data.get("forTask").getAsBoolean() == true) {
			long taskId = data.get("taskId").getAsLong();
			Task task = TasksDAO.getTask(taskId);
			Response r = new Response(
					EmployeesDAO.getEmployeesByQualification(task.getQualification()),
					EmployeesDAO.getActiveEmployeeIdsByTaskId(taskId),
					DevelopingDAO.getEmployeeIdsAndHoursByTaskId(taskId),
					task.getWorkers_num()
					);
			String json = new Gson().toJson(r);
			response.getWriter().write(json);
		}
	}
	
	private static class Response{
		public List<Employee> allDevs;
		public List<Long> chDevs;
		public Map<Long,Long> hours;
		public long required;
		public Response(List<Employee> allDevs, List<Long> chDevs, Map<Long, Long> hours, long required) {
			super();
			this.allDevs = allDevs;
			this.chDevs = chDevs;
			this.hours = hours;
			this.required = required;
		}
	}

}
