package com.tsubin.webapp.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsubin.webapp.main.entity.Project;
import com.tsubin.webapp.main.repository.ProjectRepository;


@Service
public class ProjectService {
	@Autowired
	private ProjectRepository pr;
	
	public List<Project> findAll(){
		return pr.findAll();
	}
}
