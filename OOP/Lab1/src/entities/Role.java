package entities;

import java.util.List;

import dao.RolesDAO;

public class Role {
	private long id;
	private String title;
	
	public static List<Role> roles = RolesDAO.getRolesAll();
	
	public Role(long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public static List<Role> getRoles() {
		return roles;
	}

	public static void setRoles(List<Role> roles) {
		Role.roles = roles;
	}

	
}
