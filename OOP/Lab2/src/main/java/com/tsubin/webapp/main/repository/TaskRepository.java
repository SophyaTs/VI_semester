package com.tsubin.webapp.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tsubin.webapp.main.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	Optional<Task> findById(long id);
	
	List<Task> findByProjectId(long project_id);
	
	@Query("select d.task from Developing d inner join d.task where d.employee.id=:empid")
	List<Task> findByEmployeeId(@Param("empid") long employee_id);
}
