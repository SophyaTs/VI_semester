package com.tsubin.webapp.main.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskDTO {
	private long id;

	private String name;

	private ProjectDTO project;
	
	private RoleDTO qualification;
	
	private long workers_num;
	
}
