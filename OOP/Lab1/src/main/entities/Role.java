package main.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.dao.RoleDAO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	private long id;
	private String title;
	
	public static List<Role> roles = RoleDAO.getRolesAll();
}
