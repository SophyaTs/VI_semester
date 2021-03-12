package entities;

public class DevelopingRelation {
	private long emplyee_id;
	private long task_id;
	private long hrs;
	private boolean active;
	public DevelopingRelation(long emplyee_id, long task_id, long hrs, boolean active) {
		super();
		this.emplyee_id = emplyee_id;
		this.task_id = task_id;
		this.hrs = hrs;
		this.active = active;
	}
	public long getEmplyee_id() {
		return emplyee_id;
	}
	public void setEmplyee_id(long emplyee_id) {
		this.emplyee_id = emplyee_id;
	}
	public long getTask_id() {
		return task_id;
	}
	public void setTask_id(long task_id) {
		this.task_id = task_id;
	}
	public long getHrs() {
		return hrs;
	}
	public void setHrs(long hrs) {
		this.hrs = hrs;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
