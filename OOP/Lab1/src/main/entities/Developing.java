package main.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Developing {
	private long employee_id;
	private long task_id;
	private long hrs;
	private boolean active;	
}
