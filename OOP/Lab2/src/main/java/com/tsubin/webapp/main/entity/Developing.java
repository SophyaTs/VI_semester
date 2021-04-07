package com.tsubin.webapp.main.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "developing", schema = "public")
//@IdClass(DevelopingId.class)
@NoArgsConstructor
public class Developing {
//	@Id
//	@Column(name = "employee_id", updatable = false)
//	private long employee_id;
//	
//	@Id
//	@Column(name = "task_id", updatable = false)
//	private long task_id;
	
	@EmbeddedId
	private DevelopingId id;
		
	@AttributeOverride(name="id.employee_id", column=@Column(name="employee_id"))
	@MapsId("employee_id")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="employee_id", referencedColumnName="id")
	private Employee employee;
	
	@AttributeOverride(name="id.task_id", column=@Column(name="task_id"))
	@MapsId("task_id")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="task_id", referencedColumnName="id")
	private Task task;
	
	@Column(name = "hrs", updatable = true)
	private long hrs;
	
	@Column(name = "active", updatable = true)
	private Boolean active;
	
	@NoArgsConstructor
	@Embeddable
	public static class DevelopingId implements Serializable{
		@Column(name="employee_id") //, insertable=false, updatable = false
		private Long employee_id;
		
		@Column(name="task_id") //, insertable=false, updatable = false
		private Long task_id;
		
		@Override
		public boolean equals(Object o) {
			if (this == o) {  
		          return true;  
		      }  
		      if (o instanceof DevelopingId) {
		    	  var od = (DevelopingId) o;
		    	  if (od.employee_id == this.employee_id && od.task_id==this.task_id)
		    		  return true;
		    	  else
		    		  return false;
		      }
		      else
		    	  return false;
		}
		
		@Override
		public int hashCode() {
			return (int) ((Math.pow(employee_id, 73) + Math.pow(task_id, 59)) % 113);
		}

		public DevelopingId(long employee_id, long task_id) {
			super();
			this.employee_id = employee_id;
			this.task_id = task_id;
		}

		
		public Long getEmployee_id() {
			return employee_id;
		}

		public void setEmployee_id(Long employee_id) {
			this.employee_id = employee_id;
		}

		public Long getTask_id() {
			return task_id;
		}

		public void setTask_id(Long task_id) {
			this.task_id = task_id;
		}
		
		@Override
	    public String toString() {
	        return "DevelopingId{" +
	                "employee_id=" + employee_id +
	                ", task_id=" + task_id +
	                '}';
	    }
	}
	
	public long getHrs() {
		return hrs;
	}

	public void setHrs(long hrs) {
		this.hrs = hrs;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Developing(DevelopingId id, Employee employee, Task task, long hrs, Boolean active) {
		super();
		this.id = id;
		this.employee = employee;
		this.task = task;
		this.hrs = hrs;
		this.active = active;
	}
	
	
}
