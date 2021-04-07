package com.tsubin.webapp.main.converter;

import org.springframework.stereotype.Component;

import com.tsubin.webapp.main.dto.RoleDTO;
import com.tsubin.webapp.main.entity.Role;

@Component
public class RoleConverter {
	public RoleDTO convertToDTO(Role entity) {
		var dto = new RoleDTO();
		dto.setId(entity.getId());
		dto.setTitle(entity.getTitle());
		return dto;
	}
	
	public Role convertToEntity(RoleDTO dto) {
		var entity = new Role();
		entity.setId(dto.getId());
		entity.setTitle(dto.getTitle());
		return entity;
	}
}
