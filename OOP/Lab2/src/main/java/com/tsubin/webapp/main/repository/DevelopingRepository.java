package com.tsubin.webapp.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tsubin.webapp.main.entity.Developing;
import com.tsubin.webapp.main.entity.Task;

@Repository
public interface DevelopingRepository extends JpaRepository<Developing, Developing.DevelopingId>{
	List<Developing> findByTaskId(long taskId);
	
	@Query("select d from Developing d inner join d.task where d.task.project.id=:prid")
	List<Developing> findByProjectId(@Param("prid") long project_id);
}
