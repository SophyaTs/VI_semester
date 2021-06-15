package test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;


import main.dao.EmployeeDAO;
import main.entities.Employee;
import main.entities.Role;

public class EmployeeDAOTest {
	@Test
	public void testGetEmployeeByLogin(){
		var res = EmployeeDAO.getEmployeeByLogin("");
		assertTrue(res instanceof Employee);
	}
	
	@Test
	public void testGetEmployeesByQualification(){
		var res = EmployeeDAO.getEmployeesByQualification(new Role());
		assertTrue(res instanceof List<?>);
	}

}
