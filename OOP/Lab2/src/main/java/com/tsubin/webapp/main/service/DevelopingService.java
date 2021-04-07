package com.tsubin.webapp.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsubin.webapp.main.entity.Developing;
import com.tsubin.webapp.main.entity.Employee;
import com.tsubin.webapp.main.entity.Task;
import com.tsubin.webapp.main.repository.DevelopingRepository;
import com.tsubin.webapp.main.repository.EmployeeRepository;
import com.tsubin.webapp.main.repository.TaskRepository;

@Service
public class DevelopingService {
	@Autowired
	private DevelopingRepository dr;
	@Autowired
	private TaskRepository tr;
	@Autowired
	private EmployeeRepository er;
	
	public List<Developing> findByTask_id(long taskId){
		return dr.findByTaskId(taskId);
	}
	
	public Optional<Developing> findById(long employeeId, long taskId){
		return dr.findById(new Developing.DevelopingId(employeeId,taskId));
	}
	
	//@Transactional
	public void updateHrs(long employeeId, long taskId, long hrs) {
		Developing d = findById(employeeId,taskId).get();
		d.setHrs(hrs);
		dr.save(d);
	}
	
	public void updateActive(long employeeId, long taskId, boolean active) {
		Optional<Developing> result = findById(employeeId,taskId);
		Developing d;
		if (result.isPresent()) {
			d = result.get();
			d.setActive(active);			
		}
		else {			
			Employee e = er.getOne(employeeId);
			Task t = tr.getOne(taskId);
			d = new Developing(new Developing.DevelopingId(e.getId(), t.getId()),
								e,
								t,
								0,
								true);
		}
		dr.save(d);
	}
	
	public void updateDevelopers(Long taskId, List<Long> empIds) {
		var oldList = dr.findByTaskId(taskId);
		var oldIds = new ArrayList<Long>();
		for(Developing d: oldList) {
			oldIds.add(d.getEmployee().getId());
		}
		List<Long> added = new ArrayList<>();
		List<Long> deleted = new ArrayList<>();
		for(long id : empIds)
			if(!oldIds.contains(id))
				added.add(id);
		for(long id : oldIds)
			if(!empIds.contains(id))
				deleted.add(id);
		
		for(long id : added)
			updateActive(id,taskId,true);
		for(long id : deleted)
			updateActive(id,taskId,false);
	}
	
	public List<Developing> listAllDevelopers(long projectId){
		return dr.findByProjectId(projectId);
	}
}
