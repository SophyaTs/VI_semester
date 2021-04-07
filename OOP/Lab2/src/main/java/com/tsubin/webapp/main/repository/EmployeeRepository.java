package com.tsubin.webapp.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsubin.webapp.main.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	Optional<Employee> findById(long id);
	
	List<Employee>  findByLogin(String login);
	
	List<Employee> findByPositionId(long role_id);
}
