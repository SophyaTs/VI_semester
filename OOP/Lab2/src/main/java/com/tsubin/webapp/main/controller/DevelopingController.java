package com.tsubin.webapp.main.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tsubin.webapp.main.converter.DevelopingConverter;
import com.tsubin.webapp.main.dto.DevelopingDTO;
import com.tsubin.webapp.main.entity.Developing;
import com.tsubin.webapp.main.service.DevelopingService;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class DevelopingController {
	@Autowired
	private DevelopingService ds;
	@Autowired
	private DevelopingConverter dc;
	
	@GetMapping(value = "/projects/{prId}/tasks/{taskId}/working")
	public List<DevelopingDTO> getDevelopers(@PathVariable Long taskId){
		return dc.convertListToDTO(ds.findByTask_id(taskId));
	}
	
	@PutMapping(value = "/projects/{prId}/tasks/{taskId}/save")
	public void saveDevelopers(@PathVariable Long taskId, @RequestBody List<Long> empIds){
		ds.updateDevelopers(taskId, empIds);
	}
	
	@GetMapping(value = "/dev/{empId}/task/{taskId}")
	public long getHrs(@PathVariable Long empId,@PathVariable Long taskId) {
		return ds.findById(empId, taskId).get().getHrs();
	}
	
	@PutMapping(value = "/dev/{empId}/task/{taskId}/update")
	public void updateHrs(@PathVariable Long empId,@PathVariable Long taskId, @RequestBody long hrs) {
		ds.updateHrs(empId, taskId, hrs);
	}
}
