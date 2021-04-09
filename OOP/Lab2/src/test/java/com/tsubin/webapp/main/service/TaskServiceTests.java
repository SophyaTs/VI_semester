package com.tsubin.webapp.main.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tsubin.webapp.main.entity.Task;
import com.tsubin.webapp.main.repository.TaskRepository;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTests {
	@InjectMocks
	private TaskService service;
	
	@Mock
	private TaskRepository tr;
	
	@Mock
	private Task task;
	
	@Mock
	private List<Task> list;
	
	@Test
	public void testFindByProjectId() {
		when(tr.findByProjectId(1)).thenReturn(list);
		assertEquals(service.findByProjectId(1),list);
	}
	
	@Test
	public void testFindByEmployeeId() {
		when(tr.findByEmployeeId(1)).thenReturn(list);
		assertEquals(service.findByEmployeeId(1),list);
	}
	
	@Test
	public void testFindById() {
		when(tr.findById(1)).thenReturn(Optional.of(task));
		assertEquals(service.findById(1),Optional.of(task));
	}
}
