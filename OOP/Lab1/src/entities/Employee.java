package entities;

public class Employee {
	private long id;
	private String name;
	private String login;
	private String password;
	private Role position;
	private long salary;
	
	public Employee(long id, String name, String login, String password, Role position, long salary) {
		super();
		this.id = id;
		this.name = name;
		this.login = login;
		this.password = password;
		this.position = position;
		this.salary = salary;
	}

	public Employee() {}

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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getPosition() {
		return position;
	}

	public void setPosition(Role position) {
		this.position = position;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}
	
	
}
