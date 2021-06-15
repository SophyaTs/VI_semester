package test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import main.dao.DevelopingDAO;

public class DevelopingDAOTest {
	
	@Test
	public void testGetHours(){
		long hours = DevelopingDAO.getHours(0, 0);
		assertEquals(0,hours);
	}

	@Test
	public void testUpdateActive(){
		int res = DevelopingDAO.updateActive(0, 0, false);
		assertEquals(0,res);
	}
	
	@Test
	public void testGetEmployeeIdsAndHoursByTaskId(){
		var res = DevelopingDAO.getEmployeeIdsAndHoursByTaskId(0);
		assertTrue(res instanceof Map<?,?>);
	}
	
	@Test
	public void testGetCostAndHoursByProjectId(){
		var res = DevelopingDAO.getCostAndHoursByProjectId(0);
		assertTrue(res instanceof List<?>);
	}
	
	@Test
	public void testVoid() {
		DevelopingDAO.updateActiveDevelopers(0, new ArrayList<Long>());
		DevelopingDAO.updateHours(0, 0, 0);
		DevelopingDAO.insert(0, 0, 0, true);
	}
}
