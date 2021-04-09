package com.tsubin.webapp.main.dto;

import org.junit.Assert;
import org.junit.Test;

public class TaskDTOTests {
	@Test
	public void testGettersSetters() {
		var dto = new TaskDTO();
		
		dto.setId(1);
		Assert.assertEquals((long)1,dto.getId());
		
		dto.setName("Name");
		Assert.assertEquals("Name",dto.getName());
		
		dto.setProject(new ProjectDTO());
		Assert.assertEquals(new ProjectDTO(),dto.getProject());
		
		dto.setQualification(new RoleDTO());
		Assert.assertEquals(new RoleDTO(),dto.getQualification());
		
		dto.setWorkers_num(1);
		Assert.assertEquals((long)1,dto.getWorkers_num());
	}

	@Test
	public void testEqual() {
		var dto1 = new TaskDTO();		
		Assert.assertTrue(dto1.canEqual(dto1));
		Assert.assertTrue(dto1.equals(dto1));
		
		var dto2 = new TaskDTO();
		dto2.setId(5);
		Assert.assertFalse(dto1.equals(dto2));
		
		dto1.setId(5);
		Assert.assertTrue(dto1.equals(dto2));
		
		var o = new Object();
		Assert.assertFalse(dto1.equals(o));
	}
	
	@Test
	public void testHashCode() {
		var dto1 = new TaskDTO();
		var dto2 = new TaskDTO();
		var dto3 = new TaskDTO();
		dto3.setId(5);
		
		Assert.assertTrue(dto1.hashCode()==dto1.hashCode());
		Assert.assertTrue(dto1.hashCode()==dto2.hashCode());
		Assert.assertTrue(dto1.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto2.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto3.hashCode()==dto3.hashCode());
	}
	
	@Test
	public void testToString() {
		var dto = new TaskDTO();
		Assert.assertTrue(dto.toString() instanceof String);
		Assert.assertTrue(dto.toString() != null);
	}
	
}
