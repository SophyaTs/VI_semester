package com.tsubin.webapp.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projects", schema = "public")
@NoArgsConstructor
public class Project {
	@Id
    @GeneratedValue(generator = "projects_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "projects_id_seq", sequenceName = "projects_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
	private long id;
	
	@Column(name = "name", updatable = true)
	private String name;

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
	
	
}
