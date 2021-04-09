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
	
	public Optional<Developing> findById(Developing.DevelopingId id){
		return dr.findById(id);
	}
	
	//@Transactional
	public Developing updateHrs(Developing.DevelopingId id, long hrs) {
		Developing d = findById(id).get();
		d.setHrs(hrs);
		dr.save(d);
		return d;
	}
	
	public Developing updateActive(Developing.DevelopingId id, boolean active) {
		Optional<Developing> result = findById(id);
		Developing d;
		if (result.isPresent()) {
			d = result.get();
			d.setActive(active);			
		}
		else {			
			Employee e = er.getOne(id.getEmployee_id());
			Task t = tr.getOne(id.getTask_id());
			d = new Developing(new Developing.DevelopingId(e.getId(), t.getId()),
								e,
								t,
								0,
								true);
		}
		dr.save(d);
		return d;
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
			updateActive(new Developing.DevelopingId(id,taskId),true);
		for(long id : deleted)
			updateActive(new Developing.DevelopingId(id,taskId),false);
	}
	
	public List<Developing> listAllDevelopers(long projectId){
		return dr.findByProjectId(projectId);
	}
}
