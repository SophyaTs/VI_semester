package com.tsubin.webapp.main.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DevelopingDTO {
	private EmployeeDTO employee;

	private TaskDTO task;
	
	private long hrs;
	
	private Boolean active;

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}

	public TaskDTO getTask() {
		return task;
	}

	public void setTask(TaskDTO task) {
		this.task = task;
	}

	public long getHrs() {
		return hrs;
	}

	public void setHrs(long hrs) {
		this.hrs = hrs;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}
