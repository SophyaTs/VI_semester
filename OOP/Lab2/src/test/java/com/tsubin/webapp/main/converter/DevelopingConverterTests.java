package com.tsubin.webapp.main.converter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tsubin.webapp.main.dto.DevelopingDTO;
import com.tsubin.webapp.main.entity.Developing;
import com.tsubin.webapp.main.entity.Employee;
import com.tsubin.webapp.main.entity.Task;

@RunWith(MockitoJUnitRunner.class)
public class DevelopingConverterTests {
	@InjectMocks
	private DevelopingConverter converter;
	
	@Mock
	private Employee emp;
	
	@Mock
	private EmployeeConverter ec;
	
	@Mock
	private Task task;
	
	@Mock
	private TaskConverter tc;
	
	@Test 
	public void testToDTO() {
		var entity = new Developing();
		entity.setEmployee(emp);
		entity.setTask(task);
		
		var dto = new DevelopingDTO();
		dto.setEmployee(ec.convertToDTO(emp));
		dto.setTask(tc.convertToDTO(task));
		
		var dtoC = converter.convertToDTO(entity);
		
		assertEquals(dto,dtoC);
	}
	
	@Test 
	public void testToEntity() {	
		var entity = new Developing();
		
		var dto = new DevelopingDTO();
		
		var entityC = converter.convertToEntity(dto);
		
		assertEquals(entity,entityC);
	}
	
	@Test 
	public void testConvertList() {
		var list = new ArrayList<Developing>();
		var entity = new Developing();
		entity.setEmployee(emp);
		entity.setTask(task);
		
		var dto = new DevelopingDTO();
		dto.setEmployee(ec.convertToDTO(emp));
		dto.setTask(tc.convertToDTO(task));
		list.add(entity);
		
		var listC = converter.convertListToDTO(list);
		
		assertEquals(list.get(0),list.get(0));
	}
}
