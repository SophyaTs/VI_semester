package com.tsubin.webapp.main.dto;

import org.junit.Assert;
import org.junit.Test;

public class DevelopingDTOTests {
	@Test
	public void testGettersSetters() {
		var dto = new DevelopingDTO();
		
		dto.setHrs(1);
		Assert.assertEquals((long)1,dto.getHrs());
		
		dto.setTask(new TaskDTO());
		Assert.assertEquals(new TaskDTO(), dto.getTask());
		
		dto.setEmployee(new EmployeeDTO());
		Assert.assertEquals(new EmployeeDTO(), dto.getEmployee());
		
		dto.setActive(true);
		Assert.assertTrue(dto.getActive());
	}
	
	@Test
	public void testEqual() {
		var dto1 = new DevelopingDTO();		
		Assert.assertTrue(dto1.canEqual(dto1));
		Assert.assertTrue(dto1.equals(dto1));
		
		var dto2 = new DevelopingDTO();
		dto2.setHrs(5);
		Assert.assertFalse(dto1.equals(dto2));
		
		dto1.setHrs(5);
		Assert.assertTrue(dto1.equals(dto2));
		
		var o = new Object();
		Assert.assertFalse(dto1.equals(o));
	}
	
	@Test
	public void testHashCode() {
		var dto1 = new DevelopingDTO();
		var dto2 = new DevelopingDTO();
		var dto3 = new DevelopingDTO();
		dto3.setHrs(5);
		
		Assert.assertTrue(dto1.hashCode()==dto1.hashCode());
		Assert.assertTrue(dto1.hashCode()==dto2.hashCode());
		Assert.assertTrue(dto1.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto2.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto3.hashCode()==dto3.hashCode());
	}
	
	@Test
	public void testToString() {
		var dto = new DevelopingDTO();
		Assert.assertTrue(dto.toString() instanceof String);
		Assert.assertTrue(dto.toString() != null);
	}
}
