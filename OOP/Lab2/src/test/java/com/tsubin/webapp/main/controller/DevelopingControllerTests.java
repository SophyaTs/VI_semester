package com.tsubin.webapp.main.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tsubin.webapp.main.converter.DevelopingConverter;
import com.tsubin.webapp.main.dto.DevelopingDTO;
import com.tsubin.webapp.main.entity.Developing;
import com.tsubin.webapp.main.service.DevelopingService;

@RunWith(MockitoJUnitRunner.class)
public class DevelopingControllerTests {
	@InjectMocks
	private DevelopingController controller;
	
	@Mock
	private DevelopingService ds;
	
	@Mock
	private DevelopingConverter dc;
	
	@Mock
	private List<DevelopingDTO> listDTO;
	
	@Mock
	private List<Developing> listE;
	
//	@Mock
//	private Developing.DevelopingId devId;
	
	@Mock
	private Developing developing;
	
	@Test
	public void testGetDevs() {
		when(ds.findByTask_id(1)).thenReturn(listE);
		when(dc.convertListToDTO(listE)).thenReturn(listDTO);
		assertEquals(controller.getDevelopers((long) 1),listDTO);
	}
	
	@Test
	public void testGetHrs() {
		Developing.DevelopingId devId = new Developing.DevelopingId(1,1);
		when(ds.findById(devId)).thenReturn(Optional.of(developing));
		when(developing.getHrs()).thenReturn((long) 1);
		assertEquals(controller.getHrs(1,1),1);
	}
}
