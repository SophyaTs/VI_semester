package com.tsubin.webapp.main.converter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tsubin.webapp.main.dto.TaskDTO;
import com.tsubin.webapp.main.entity.Project;
import com.tsubin.webapp.main.entity.Role;
import com.tsubin.webapp.main.entity.Task;

@RunWith(MockitoJUnitRunner.class)
public class TaskConverterTests {
	@InjectMocks
	private TaskConverter converter;
	
	@Mock
	private Role q;
	
	@Mock
	private RoleConverter rc;
	
	@Mock
	private Project pr;
	
	@Mock
	private ProjectConverter pc;
	
	@Test 
	public void testToDTO() {
		var entity = new Task();
		
		var dto = new TaskDTO();
		
		var dtoC = converter.convertToDTO(entity);
		
		assertEquals(dto,dtoC);
	}
	
	@Test 
	public void testToEntity() {	
		var entity = new Task();
		
		var dto = new TaskDTO();
		
		var entityC = converter.convertToEntity(dto);
		
		assertEquals(entity,entityC);
	}
	
	@Test 
	public void testConvertList() {
		var list = new ArrayList<Task>();
		var entity = new Task();
		entity.setQualification(q);
		entity.setProject(pr);
		
		var dto = new TaskDTO();
		dto.setQualification(rc.convertToDTO(q));
		dto.setProject(pc.convertToDTO(pr));
		list.add(entity);
		
		var listC = converter.convertListToDTO(list);
		
		assertEquals(list.get(0),list.get(0));
	}
}
