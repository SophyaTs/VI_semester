package test.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import main.dao.ProjectDAO;

public class ProjectDAOTest {
	@Test
	public void testGetProjectsAll(){
		var res = ProjectDAO.getProjectsAll();
		assertTrue(res instanceof List<?>);
	}
}
