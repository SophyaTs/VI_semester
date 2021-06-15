package test.entities;

import org.junit.Assert;
import org.junit.Test;

import main.entities.Project;

public class ProjectTest {
	@Test
	public void testGettersSetters() {
		var dto = new Project();
		
		dto.setId(1);
		Assert.assertEquals((long)1,dto.getId());
		
		dto.setName("Title");
		Assert.assertEquals("Title",dto.getName());
	}
	
	@Test
	public void testEqual() {
		var dto1 = new Project();		
		Assert.assertTrue(dto1.equals(dto1));
		
		var dto2 = new Project();
		dto2.setId(5);
		Assert.assertFalse(dto1.equals(dto2));
		
		dto1.setId(5);
		Assert.assertTrue(dto1.equals(dto2));
		
		var o = new Object();
		Assert.assertFalse(dto1.equals(o));
	}
	
	@Test
	public void testHashCode() {
		var dto1 = new Project();
		var dto2 = new Project();
		var dto3 = new Project();
		dto3.setId(5);
		
		Assert.assertTrue(dto1.hashCode()==dto1.hashCode());
		Assert.assertTrue(dto1.hashCode()==dto2.hashCode());
		Assert.assertTrue(dto1.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto2.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto3.hashCode()==dto3.hashCode());
	}
	
	@Test
	public void testToString() {
		var dto = new Project();
		Assert.assertTrue(dto.toString() instanceof String);
		Assert.assertTrue(dto.toString() != null);
	}
}
