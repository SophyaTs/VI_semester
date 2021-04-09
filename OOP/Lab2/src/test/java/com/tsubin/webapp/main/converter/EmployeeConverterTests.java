package com.tsubin.webapp.main.converter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tsubin.webapp.main.dto.EmployeeDTO;
import com.tsubin.webapp.main.entity.Employee;
import com.tsubin.webapp.main.entity.Role;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeConverterTests {
	@InjectMocks
	private EmployeeConverter converter;
	
	@Mock
	private Role position;
	
	@Mock
	private RoleConverter rc;
	
	@Test 
	public void testToDTO() {
		var entity = new Employee();
		entity.setPosition(position);
		
		var dto = new EmployeeDTO();
		dto.setPosition(rc.convertToDTO(position));
		
		var dtoC = converter.convertToDTO(entity);
		
		assertEquals(dto,dtoC);
	}
	
	@Test 
	public void testToEntity() {	
		var entity = new Employee();
		
		var dto = new EmployeeDTO();

		var entityC = converter.convertToEntity(dto);
		
		assertEquals(entity,entityC);
	}
	
	@Test 
	public void testConvertList() {
		var list = new ArrayList<Employee>();
		var entity = new Employee();
		entity.setPosition(position);
		
		var dto = new EmployeeDTO();
		dto.setPosition(rc.convertToDTO(position));
		list.add(entity);
		
		var listC = converter.convertListToDTO(list);
		
		assertEquals(list.get(0),list.get(0));
	}
}
