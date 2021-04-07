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
@Table(name = "tasks", schema = "public")
@NoArgsConstructor
public class Task {
	@Id
    @GeneratedValue(generator = "tasks_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "tasks_id_seq", sequenceName = "tasks_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
	private long id;
	
	@Column(name = "name", updatable = true)
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName="id", nullable = false)
	private Project project;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "qualification", referencedColumnName="id", nullable = false)
	private Role qualification;
	
	@Column(name = "workers_num", updatable = true)
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

	public Role getQualification() {
		return qualification;
	}

	public void setQualification(Role qualification) {
		this.qualification = qualification;
	}

	public long getWorkers_num() {
		return workers_num;
	}

	public void setWorkers_num(long workers_num) {
		this.workers_num = workers_num;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	
}
