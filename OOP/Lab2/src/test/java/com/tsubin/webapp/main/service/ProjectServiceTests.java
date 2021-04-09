package com.tsubin.webapp.main.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tsubin.webapp.main.entity.Project;
import com.tsubin.webapp.main.repository.ProjectRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTests {
	@InjectMocks
	private ProjectService service;
	
	@Mock
	private ProjectRepository pr;
	
	@Mock
	private List<Project> list;
	
	@Test
	public void testFindAll() {
		when(pr.findAll()).thenReturn(list);
		assertEquals(service.findAll(),list);
	}
}
