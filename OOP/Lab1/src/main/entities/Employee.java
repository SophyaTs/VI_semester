package main.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	private long id;
	private String name;
	private String login;
	private String password;
	private Role position;
	private long salary;
	
}
