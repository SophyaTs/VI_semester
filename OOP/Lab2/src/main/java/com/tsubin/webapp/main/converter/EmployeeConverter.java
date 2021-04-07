package com.tsubin.webapp.main.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tsubin.webapp.main.dto.EmployeeDTO;
import com.tsubin.webapp.main.entity.Employee;
import com.tsubin.webapp.main.repository.RoleRepository;


@Component
public class EmployeeConverter {
	@Autowired
	private RoleConverter rc;
	
	public EmployeeDTO convertToDTO(Employee entity) {
		var dto = new EmployeeDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setLogin(entity.getLogin());
		dto.setPosition(rc.convertToDTO(entity.getPosition()));
		dto.setSalary(entity.getSalary());
		return dto;
	}
	
	public Employee convertToEntity(EmployeeDTO dto) {
		var entity = new Employee();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setLogin(dto.getLogin());
		entity.setPosition(rc.convertToEntity(dto.getPosition()));
		entity.setSalary(dto.getSalary());
		return entity;
	}
	
	public List<EmployeeDTO> convertListToDTO(List<Employee> list){
		var result = new ArrayList<EmployeeDTO>();
		for(Employee p : list) {
			result.add(convertToDTO(p));
		}
		return result;
	}
}
