package com.tsubin.webapp.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tsubin.webapp.main.converter.EmployeeConverter;
import com.tsubin.webapp.main.dto.EmployeeDTO;
import com.tsubin.webapp.main.entity.Task;
import com.tsubin.webapp.main.service.EmployeeService;
import com.tsubin.webapp.main.service.TaskService;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class EmployeeController {
	@Autowired
	private EmployeeService es;
	@Autowired
	private TaskService ts;
	@Autowired
	private EmployeeConverter ec;
	
	@GetMapping(value = "/projects/{prId}/tasks/{taskId}/all")
	public List<EmployeeDTO> getTasks(@PathVariable Long taskId){
		Task task = ts.findById(taskId).get();
		return ec.convertListToDTO(es.findByPosition(task.getQualification().getId()));
	}
	
	@GetMapping(value = "/user/{login}")
	public EmployeeDTO getEmployee(@PathVariable String login){
		return ec.convertToDTO(es.findByLogin(login));
	}
}
