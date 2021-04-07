package com.tsubin.webapp.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees", schema = "public")
@NoArgsConstructor
public class Employee {
	@Id
    @GeneratedValue(generator = "employees_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "employees_id_seq", sequenceName = "employees_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
	private long id;
	
	@Column(name = "name", updatable = true)
	private String name;
	
	@Column(name = "login", updatable = true)
	private String login;
	
	@Column(name = "password", updatable = true)
	private String password;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position", referencedColumnName="id", nullable = false)
	//@Column(name = "position", updatable = true)
	private Role position;
	
	@Column(name = "salary", updatable = true)
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	public Role getPosition() {
		return position;
	}

	public void setPosition(Role position) {
		this.position = position;
	}
	
	
}
