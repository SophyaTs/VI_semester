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

@Data
@Entity
@Table(name = "role", schema = "public")
@NoArgsConstructor
public class Role {
	@Id
    @GeneratedValue(generator = "roles_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "roles_id_seq", sequenceName = "roles_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
	private long id;
	
	@Column(name = "title", updatable = true)
	private String title;
}
