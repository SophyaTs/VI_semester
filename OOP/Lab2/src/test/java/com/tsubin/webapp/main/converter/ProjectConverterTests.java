package com.tsubin.webapp.main.converter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.tsubin.webapp.main.dto.ProjectDTO;
import com.tsubin.webapp.main.entity.Project;

@RunWith(MockitoJUnitRunner.class)
public class ProjectConverterTests {
	@InjectMocks
	private ProjectConverter converter;
	
	@Test 
	public void testToDTO() {
		var entity = new Project();
		entity.setId(1);
		entity.setName("Title");
		
		var dto = new ProjectDTO();
		dto.setId(1);
		dto.setName("Title");
		
		var dtoC = converter.convertToDTO(entity);
		
		assertEquals(dto,dtoC);
	}
	
	@Test 
	public void testToEntity() {
		var entity = new Project();
		entity.setId(1);
		entity.setName("Title");
		
		var dto = new ProjectDTO();
		dto.setId(1);
		dto.setName("Title");
		
		var entityC = converter.convertToEntity(dto);
		
		assertEquals(entity,entityC);
	}
	
	@Test 
	public void testConvertList() {
		var list = new ArrayList<Project>();
		
		var entity = new Project();
		entity.setId(1);
		entity.setName("Title");
		var dto = new ProjectDTO();
		dto.setId(1);
		dto.setName("Title");
		
		list.add(entity);
		
		var listC = converter.convertListToDTO(list);
		
		assertEquals(list.get(0),list.get(0));
	}
}
