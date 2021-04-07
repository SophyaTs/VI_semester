package com.tsubin.webapp.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsubin.webapp.main.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{
	List<Project> findAll();
}
