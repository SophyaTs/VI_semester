package com.tsubin.webapp.main.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tsubin.webapp.main.entity.Developing;
import com.tsubin.webapp.main.entity.Employee;
import com.tsubin.webapp.main.entity.Task;
import com.tsubin.webapp.main.repository.DevelopingRepository;
import com.tsubin.webapp.main.repository.EmployeeRepository;
import com.tsubin.webapp.main.repository.TaskRepository;

@RunWith(MockitoJUnitRunner.class)
public class DevelopingServiceTests {
	@InjectMocks
	private DevelopingService service;
	
	@Mock
	private DevelopingRepository dr;
	
	@Mock
	private TaskRepository tr;
	
	@Mock
	private EmployeeRepository er;
	
	@Mock
	private Developing developing;
	
	@Mock
	private List<Developing> list;
	
	@Mock 
	private Developing.DevelopingId developingId;
	
	@Mock
	private Employee emp;
	
	@Mock
	private Task task;
	
	@Test
	public void testFindById() {
		when(dr.findById(developingId)).thenReturn(Optional.of(developing));
		assertEquals(service.findById(developingId),Optional.of(developing));
	}
	
	@Test
	public void testFindByTask_id() {
		when(dr.findByTaskId(1)).thenReturn(list);
		assertEquals(service.findByTask_id(1),list);
	}
	
	@Test
	public void testListDevelopers() {
		when(dr.findByProjectId(1)).thenReturn(list);
		assertEquals(service.listAllDevelopers(1), list);
	}
	
	@Test
	public void testUpdate() {
		Developing d = new Developing();
		d.setActive(false);
		when(service.findById(developingId)).thenReturn(Optional.of(d));
		when(dr.save(d)).thenReturn(d);
		d = service.updateActive(developingId, true);
		assertEquals(
				true,
				d.getActive()
				);
		
		d = service.updateHrs(developingId, 1);
		assertEquals(
				1,
				d.getHrs()
				);
		
		when(service.findById(developingId)).thenReturn(Optional.empty());
		when(er.getOne(any())).thenReturn(emp);
		when(tr.getOne(any())).thenReturn(task);
		d = service.updateActive(developingId, true);
		assertEquals(
				true,
				d.getActive()
				);
	}
	
	
}
