package com.tsubin.webapp.main.dto;

import org.junit.Assert;
import org.junit.Test;

public class EmployeeDTOTests {
	@Test
	public void testGettersSetters() {
		var dto = new EmployeeDTO();
		
		dto.setId(1);
		Assert.assertEquals((long)1,dto.getId());
		
		dto.setLogin("Name");
		Assert.assertEquals("Name",dto.getLogin());
		
		dto.setName("Name");
		Assert.assertEquals("Name",dto.getName());
		
		dto.setPosition(new RoleDTO());
		Assert.assertEquals(new RoleDTO(), dto.getPosition());
		
		dto.setSalary(1);
		Assert.assertEquals((long)1,dto.getSalary());
	}
	
	@Test
	public void testEqual() {
		var dto1 = new EmployeeDTO();		
		Assert.assertTrue(dto1.canEqual(dto1));
		Assert.assertTrue(dto1.equals(dto1));
		
		var dto2 = new EmployeeDTO();
		dto2.setId(5);
		Assert.assertFalse(dto1.equals(dto2));
		
		dto1.setId(5);
		Assert.assertTrue(dto1.equals(dto2));
		
		var o = new Object();
		Assert.assertFalse(dto1.equals(o));
	}
	
	@Test
	public void testHashCode() {
		var dto1 = new EmployeeDTO();
		var dto2 = new EmployeeDTO();
		var dto3 = new EmployeeDTO();
		dto3.setId(5);
		
		Assert.assertTrue(dto1.hashCode()==dto1.hashCode());
		Assert.assertTrue(dto1.hashCode()==dto2.hashCode());
		Assert.assertTrue(dto1.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto2.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto3.hashCode()==dto3.hashCode());
	}
	
	@Test
	public void testToString() {
		var dto = new EmployeeDTO();
		Assert.assertTrue(dto.toString() instanceof String);
		Assert.assertTrue(dto.toString() != null);
	}
}
