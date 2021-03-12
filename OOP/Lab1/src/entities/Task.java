package entities;

public class Task {
	private long id;
	private String name;
	private long project_id;
	private Role qualification;
	private long workers_num;
	
	public Task(long id, String name, long project_id, Role qualification, long workers_num) {
		super();
		this.id = id;
		this.name = name;
		this.project_id = project_id;
		this.qualification = qualification;
		this.workers_num = workers_num;
	}
	
	public Task(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Task() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getProject_id() {
		return project_id;
	}

	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}

	public Role getQualification() {
		return qualification;
	}

	public void setQualification(Role qualification) {
		this.qualification = qualification;
	}

	public long getWorkers_num() {
		return workers_num;
	}

	public void setWorkers_num(long workers_num) {
		this.workers_num = workers_num;
	}
	
	
}
