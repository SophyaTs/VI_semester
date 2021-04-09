package com.tsubin.webapp.main.entity;

import org.junit.Assert;
import org.junit.Test;


public class EmployeeTests {
	@Test
	public void testGettersSetters() {
		var dto = new Employee();
		
		dto.setId(1);
		Assert.assertEquals((long)1,dto.getId());
		
		dto.setLogin("Name");
		Assert.assertEquals("Name",dto.getLogin());
		
		dto.setName("Name");
		Assert.assertEquals("Name",dto.getName());
		
		dto.setPosition(new Role());
		Assert.assertEquals(new Role(), dto.getPosition());
		
		dto.setSalary(1);
		Assert.assertEquals((long)1,dto.getSalary());
	}
	
	@Test
	public void testEqual() {
		var dto1 = new Employee();		
		Assert.assertTrue(dto1.canEqual(dto1));
		Assert.assertTrue(dto1.equals(dto1));
		
		var dto2 = new Employee();
		dto2.setId(5);
		Assert.assertFalse(dto1.equals(dto2));
		
		dto1.setId(5);
		Assert.assertTrue(dto1.equals(dto2));
		
		var o = new Object();
		Assert.assertFalse(dto1.equals(o));
	}
	
	@Test
	public void testHashCode() {
		var dto1 = new Employee();
		var dto2 = new Employee();
		var dto3 = new Employee();
		dto3.setId(5);
		
		Assert.assertTrue(dto1.hashCode()==dto1.hashCode());
		Assert.assertTrue(dto1.hashCode()==dto2.hashCode());
		Assert.assertTrue(dto1.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto2.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto3.hashCode()==dto3.hashCode());
	}
	
	@Test
	public void testToString() {
		var dto = new Employee();
		Assert.assertTrue(dto.toString() instanceof String);
		Assert.assertTrue(dto.toString() != null);
	}
}
