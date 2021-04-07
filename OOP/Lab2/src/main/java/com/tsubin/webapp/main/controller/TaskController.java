package com.tsubin.webapp.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tsubin.webapp.main.converter.TaskConverter;
import com.tsubin.webapp.main.dto.TaskDTO;
import com.tsubin.webapp.main.service.TaskService;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class TaskController {
	@Autowired
	private TaskService ts;
	@Autowired
	private TaskConverter tc;
	
	@GetMapping(value = "/projects/{prId}/tasks")
	public List<TaskDTO> getTasksMng(@PathVariable Long prId){
		return tc.convertListToDTO(ts.findByProjectId(prId));
	}
	
	@GetMapping(value = "/dev/{empId}")
	public List<TaskDTO> getTasksDev(@PathVariable Long empId){
		return tc.convertListToDTO(ts.findByEmployeeId(empId));
	}
	
	@GetMapping(value = "/projects/{prId}/tasks/{taskId}/req")
	public Long getRequired(@PathVariable Long taskId){
		return ts.findById(taskId).get().getWorkers_num();
	}
}
