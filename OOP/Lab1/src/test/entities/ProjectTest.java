package test.entities;

import org.junit.Assert;
import org.junit.Test;

import main.entities.Project;

public class ProjectTest {
	@Test
	public void testGettersSetters() {
		Project pr = new Project((long)1,"Project");
		Assert.assertEquals((long)1,pr.getId());
		Assert.assertEquals("Project",pr.getName());
		
		pr.setId(2);
		Assert.assertEquals(2,pr.getId());
		
		pr.setName("Hello");
		Assert.assertEquals("Hello",pr.getName());
	}
}
