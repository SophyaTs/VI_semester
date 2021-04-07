package com.tsubin.webapp.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tsubin.webapp.main.BusinessLogic;
import com.tsubin.webapp.main.converter.ProjectConverter;
import com.tsubin.webapp.main.converter.TaskConverter;
import com.tsubin.webapp.main.dto.ProjectDTO;
import com.tsubin.webapp.main.dto.TaskDTO;
import com.tsubin.webapp.main.service.ProjectService;
import com.tsubin.webapp.main.service.TaskService;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class ProjectController {
	@Autowired
	private ProjectService ps;
	@Autowired
	private ProjectConverter pc;
	@Autowired
	private BusinessLogic bl;
	
	@GetMapping(value = "/projects")
	public List<ProjectDTO> getProjects(){
		return pc.convertListToDTO(ps.findAll());
	}
	
	@GetMapping(value = "/calc/{prId}")
	public long calculateProjectCost(@PathVariable Long prId){
		return bl.calculateCost(prId);
	}
	
	
}
