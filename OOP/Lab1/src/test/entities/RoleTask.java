package test.entities;

import org.junit.Assert;
import org.junit.Test;

import main.entities.Role;

public class RoleTask {
	
	@Test
	public void testGettersSetters() {
		Role pr = new Role((long)1,"Project");
		Assert.assertEquals((long)1,pr.getId());
		Assert.assertEquals("Project",pr.getTitle());
		
		pr.setId(2);
		Assert.assertEquals(2,pr.getId());
		
		pr.setTitle("Hello");
		Assert.assertEquals("Hello",pr.getTitle());
	}
}
