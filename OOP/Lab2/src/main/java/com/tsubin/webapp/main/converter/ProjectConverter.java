package com.tsubin.webapp.main.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tsubin.webapp.main.dto.ProjectDTO;
import com.tsubin.webapp.main.entity.Project;

@Component
public class ProjectConverter {
	public ProjectDTO convertToDTO(Project entity) {
		var dto = new ProjectDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}
	
	public Project convertToEntity(ProjectDTO dto) {
		var entity = new Project();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;
	}
	
	public List<ProjectDTO> convertListToDTO(List<Project> list){
		var result = new ArrayList<ProjectDTO>();
		for(Project p : list) {
			result.add(convertToDTO(p));
		}
		return result;
	}
}
