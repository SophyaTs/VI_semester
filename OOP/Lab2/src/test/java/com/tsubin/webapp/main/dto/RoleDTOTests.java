package com.tsubin.webapp.main.dto;

import org.junit.Assert;
import org.junit.Test;

public class RoleDTOTests {
	@Test
	public void testGettersSetters() {
		var dto = new RoleDTO();
		
		dto.setId(1);
		Assert.assertEquals((long)1,dto.getId());
		
		dto.setTitle("Title");
		Assert.assertEquals("Title",dto.getTitle());
	}
}
