package com.tsubin.webapp.main.dto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class RoleDTOTests {
	@Test
	public void testGettersSetters() {
		var dto = new RoleDTO();
		
		dto.setId(1);
		Assert.assertEquals((long)1,dto.getId());
		
		dto.setTitle("Title");
		Assert.assertEquals("Title",dto.getTitle());
	}
	
	@Test
	public void testEqual() {
		var dto1 = new RoleDTO();		
		Assert.assertTrue(dto1.canEqual(dto1));
		Assert.assertTrue(dto1.equals(dto1));
		
		var dto2 = new RoleDTO();
		dto2.setId(5);
		Assert.assertFalse(dto1.equals(dto2));
		
		dto1.setId(5);
		Assert.assertTrue(dto1.equals(dto2));
		
		var o = new Object();
		Assert.assertFalse(dto1.equals(o));
		
		Object cast1 = new RoleDTO();
		Assert.assertFalse(dto1.equals(cast1));
		
		Object cast2 = dto2;
		Assert.assertTrue(dto1.equals(cast2));
		
		Assert.assertFalse(dto1.equals(null));
		
		dto1 = null;
		
		Assertions.assertThrows(java.lang.NullPointerException.class, () -> { 
				RoleDTO dto = null;
				dto.equals(null);
			});
	}
	
	@Test
	public void testHashCode() {
		var dto1 = new RoleDTO();
		var dto2 = new RoleDTO();
		var dto3 = new RoleDTO();
		dto3.setId(5);
		
		Assert.assertTrue(dto1.hashCode()==dto1.hashCode());
		Assert.assertTrue(dto1.hashCode()==dto2.hashCode());
		Assert.assertTrue(dto1.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto2.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto3.hashCode()==dto3.hashCode());
	}
	
	@Test
	public void testToString() {
		var dto = new RoleDTO();
		Assert.assertTrue(dto.toString() instanceof String);
		Assert.assertTrue(dto.toString() != null);
	}
}
