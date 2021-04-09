package com.tsubin.webapp.main.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.tsubin.webapp.main.dto.RoleDTO;
import com.tsubin.webapp.main.entity.Role;

@RunWith(MockitoJUnitRunner.class)
public class RoleConverterTests {
	@InjectMocks
	private RoleConverter converter;
	
	@Test 
	public void testToDTO() {
		var entity = new Role();
		entity.setId(1);
		entity.setTitle("Title");
		
		var dto = new RoleDTO();
		dto.setId(1);
		dto.setTitle("Title");
		
		var dtoC = converter.convertToDTO(entity);
		
		assertEquals(dto,dtoC);
	}
	
	@Test 
	public void testToEntity() {
		var entity = new Role();
		entity.setId(1);
		entity.setTitle("Title");
		
		var dto = new RoleDTO();
		dto.setId(1);
		dto.setTitle("Title");
		
		var entityC = converter.convertToEntity(dto);
		
		assertEquals(entity,entityC);
	}
}
