package test.entities;

import org.junit.Assert;
import org.junit.Test;

import main.entities.Task;

public class TaskTest {
	@Test
	public void testGettersSetters() {
		Task task = new Task((long)1,"Tests");
		
		Assert.assertEquals((long)1,task.getId());
		Assert.assertEquals("Tests",task.getName());
		
		task.setProject_id((long)1);
		Assert.assertEquals((long)1,task.getProject_id());
		
		task.setWorkers_num((long)1);
		Assert.assertEquals((long)1,task.getWorkers_num());
	}
}
