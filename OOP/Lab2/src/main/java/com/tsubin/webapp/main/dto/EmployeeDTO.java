package com.tsubin.webapp.main.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmployeeDTO {
	private long id;
	
	private String name;
	
	private String login;
	
	private RoleDTO position;
	
	private long salary;

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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public RoleDTO getPosition() {
		return position;
	}

	public void setPosition(RoleDTO position) {
		this.position = position;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}
	
	
}
