package com.tsubin.webapp.main.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tsubin.webapp.main.dto.TaskDTO;
import com.tsubin.webapp.main.entity.Task;
import com.tsubin.webapp.main.repository.ProjectRepository;
import com.tsubin.webapp.main.repository.RoleRepository;


@Component
public class TaskConverter {
	@Autowired
	private ProjectConverter pc;
	@Autowired
	private RoleConverter rc;
	
	public TaskDTO convertToDTO(Task entity) {
		var dto = new TaskDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setProject(pc.convertToDTO(entity.getProject()));
		dto.setQualification(rc.convertToDTO(entity.getQualification()));
		dto.setWorkers_num(entity.getWorkers_num());
		return dto;
	}
	
	public Task convertToEntity(TaskDTO dto) {
		var entity = new Task();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setProject(pc.convertToEntity(dto.getProject()));
		entity.setQualification(rc.convertToEntity(dto.getQualification()));
		entity.setWorkers_num(dto.getWorkers_num());
		return entity;
	}
	
	public List<TaskDTO> convertListToDTO(List<Task> list){
		var result = new ArrayList<TaskDTO>();
		for(Task t : list) {
			result.add(convertToDTO(t));
		}
		return result;
	}
}
