package com.tsubin.webapp.main.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tsubin.webapp.main.dto.DevelopingDTO;
import com.tsubin.webapp.main.entity.Developing;
import com.tsubin.webapp.main.repository.EmployeeRepository;
import com.tsubin.webapp.main.repository.TaskRepository;


@Component
public class DevelopingConverter {
	@Autowired
	private EmployeeConverter ec;
	@Autowired
	private TaskConverter tc;
	
	public DevelopingDTO convertToDTO(Developing entity) {
		var dto = new DevelopingDTO();
		dto.setEmployee(ec.convertToDTO(entity.getEmployee()));
		dto.setTask(tc.convertToDTO(entity.getTask()));
		dto.setHrs(entity.getHrs());
		dto.setActive(entity.getActive());
		return dto;
	}
	
	public Developing convertToEntity(DevelopingDTO dto) {
		var entity = new Developing();
		entity.setEmployee(ec.convertToEntity(dto.getEmployee()));
		entity.setTask(tc.convertToEntity(dto.getTask()));
		entity.setHrs(dto.getHrs());
		entity.setActive(dto.getActive());
		return entity;
	}
	
	public List<DevelopingDTO> convertListToDTO(List<Developing> list){
		var result = new ArrayList<DevelopingDTO>();
		for(Developing p : list) {
			result.add(convertToDTO(p));
		}
		return result;
	}
}
