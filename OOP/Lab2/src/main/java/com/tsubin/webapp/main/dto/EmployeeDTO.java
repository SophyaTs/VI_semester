package com.tsubin.webapp.main.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDTO {
	private long id;
	
	private String name;
	
	private String login;
	
	private RoleDTO position;
	
	private long salary;

	
	
}
