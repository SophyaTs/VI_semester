package com.tsubin.webapp.main.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TaskDTO {
	private long id;

	private String name;

	private ProjectDTO project;
	
	private RoleDTO qualification;
	
	private long workers_num;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}

	public RoleDTO getQualification() {
		return qualification;
	}

	public void setQualification(RoleDTO qualification) {
		this.qualification = qualification;
	}

	public long getWorkers_num() {
		return workers_num;
	}

	public void setWorkers_num(long workers_num) {
		this.workers_num = workers_num;
	}
	
	
}
