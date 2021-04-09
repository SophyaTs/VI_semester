package com.tsubin.webapp.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsubin.webapp.main.entity.Employee;
import com.tsubin.webapp.main.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository er;

	public List<Employee> findByPosition(long role_id){
		return er.findByPositionId(role_id);
	}
	
	public Employee findByLogin(String login){
		var list = er.findByLogin(login);
		if (list.size() != 1)
			return null;
		return list.get(0);
	}
}
