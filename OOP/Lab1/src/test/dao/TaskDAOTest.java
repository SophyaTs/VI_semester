package test.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import main.dao.ProjectDAO;
import main.dao.TaskDAO;
import main.entities.Task;

public class TaskDAOTest {
	@Test
	public void testGetTasksByEmpId(){
		var res = TaskDAO.getTasksByEmpId(0);
		assertTrue(res instanceof List<?>);
	}
	
	@Test
	public void testGetTasksByProjectId(){
		var res = TaskDAO.getTasksByProjectId(0);
		assertTrue(res instanceof List<?>);
	}
	
	@Test
	public void testGetTask(){
		var res = TaskDAO.getTask(0);
		Assert.assertEquals(null, res);
	}
}
