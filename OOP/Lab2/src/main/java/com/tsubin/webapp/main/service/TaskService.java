package com.tsubin.webapp.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsubin.webapp.main.entity.Task;
import com.tsubin.webapp.main.repository.TaskRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository tr;

	public List<Task> findByProjectId(long project_id){
		return tr.findByProjectId(project_id);
	}
	
	public Optional<Task> findById(long id){
		return tr.findById(id);
	}
	
	public List<Task> findByEmployeeId(long employee_id){
		var list = tr.findByEmployeeId(employee_id);
		return list;
	}
}
