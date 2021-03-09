package entities;

public class Task {
	private int id;
	private String name;
	private int project_id;
	private Role qualification;
	private int workers_num;
	
	public Task(int id, String name, int project_id, Role qualification, int workers_num) {
		super();
		this.id = id;
		this.name = name;
		this.project_id = project_id;
		this.qualification = qualification;
		this.workers_num = workers_num;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public Role getQualification() {
		return qualification;
	}

	public void setQualification(Role qualification) {
		this.qualification = qualification;
	}

	public int getWorkers_num() {
		return workers_num;
	}

	public void setWorkers_num(int workers_num) {
		this.workers_num = workers_num;
	}
	
	
}
