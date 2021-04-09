package com.tsubin.webapp.main.service;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tsubin.webapp.main.entity.Employee;
import com.tsubin.webapp.main.repository.EmployeeRepository;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTests {
	@InjectMocks
	private EmployeeService service;
	
	@Mock
	private EmployeeRepository er;
	
	@Mock
	private Employee emp;
	
	@Test
	public void testFindById() {
		var list = new ArrayList<Employee>();
		when(er.findByLogin("a")).thenReturn(list);
		assertEquals(service.findByLogin("a"),null);
		
		list.add(emp);
		when(er.findByLogin("a")).thenReturn(list);
		assertEquals(service.findByLogin("a"),emp);
	}
	
	@Test
	public void testFindByPosition() {
		var list = new ArrayList<Employee>();
		when(er.findByPositionId(1)).thenReturn(list);
		assertEquals(service.findByPosition(1),list);
	}
}
