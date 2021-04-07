package com.tsubin.webapp.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tsubin.webapp.main.entity.Developing;
import com.tsubin.webapp.main.service.DevelopingService;

@Component
public class BusinessLogic {
	@Autowired
	private DevelopingService ds;
	
	public long calculateCost(long projectId) {
		var list = ds.listAllDevelopers(projectId);
		long cost = 0;
		for(Developing entry: list) {
			cost += entry.getEmployee().getSalary() * entry.getHrs();
		}
		return cost;
	}
}
