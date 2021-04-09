package com.tsubin.webapp.main.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tsubin.webapp.main.converter.EmployeeConverter;
import com.tsubin.webapp.main.dto.EmployeeDTO;
import com.tsubin.webapp.main.entity.Employee;
import com.tsubin.webapp.main.service.EmployeeService;
import com.tsubin.webapp.main.service.TaskService;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTests {
	@InjectMocks
	private EmployeeController controller;
	
	@Mock
	private EmployeeService es;
	
	@Mock
	private TaskService ts;
	
	@Mock
	private EmployeeConverter ec;
	
	@Mock
	private Employee emp;
	
	@Mock
	private EmployeeDTO empDTO;
	
	@Test
	public void testGetEmployee() {
		when(es.findByLogin("a")).thenReturn(emp);
		when(ec.convertToDTO(emp)).thenReturn(empDTO);
		assertEquals(controller.getEmployee("a"),empDTO);
	}
}
