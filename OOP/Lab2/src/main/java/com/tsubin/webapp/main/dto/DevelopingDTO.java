package com.tsubin.webapp.main.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DevelopingDTO {
	private EmployeeDTO employee;

	private TaskDTO task;
	
	private long hrs;
	
	private Boolean active;
	
}
