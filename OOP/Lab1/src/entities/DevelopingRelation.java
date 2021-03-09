package entities;

public class DevelopingRelation {
	private int emplyee_id;
	private int task_id;
	private int hrs;
	
	public DevelopingRelation(int emplyee_id, int task_id, int hoursSpent) {
		super();
		this.emplyee_id = emplyee_id;
		this.task_id = task_id;
		this.hrs = hoursSpent;
	}

	public int getEmplyee_id() {
		return emplyee_id;
	}

	public void setEmplyee_id(int emplyee_id) {
		this.emplyee_id = emplyee_id;
	}

	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public int getHrs() {
		return hrs;
	}

	public void setHrs(int hrs) {
		this.hrs = hrs;
	}
	
	
}
