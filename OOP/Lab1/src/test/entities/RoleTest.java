package test.entities;

import org.junit.Assert;
import org.junit.Test;

import main.entities.Role;

public class RoleTest {
	
	@Test
	public void testGettersSetters() {
		var dto = new Role();
		
		dto.setId(1);
		Assert.assertEquals((long)1,dto.getId());
		
		dto.setTitle("Title");
		Assert.assertEquals("Title",dto.getTitle());
	}
	
	@Test
	public void testEqual() {
		var dto1 = new Role();		
		Assert.assertTrue(dto1.equals(dto1));
		
		var dto2 = new Role();
		dto2.setId(5);
		Assert.assertFalse(dto1.equals(dto2));
		
		dto1.setId(5);
		Assert.assertTrue(dto1.equals(dto2));
		
		var o = new Object();
		Assert.assertFalse(dto1.equals(o));
	}
	
	@Test
	public void testHashCode() {
		var dto1 = new Role();
		var dto2 = new Role();
		var dto3 = new Role();
		dto3.setId(5);
		
		Assert.assertTrue(dto1.hashCode()==dto1.hashCode());
		Assert.assertTrue(dto1.hashCode()==dto2.hashCode());
		Assert.assertTrue(dto1.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto2.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto3.hashCode()==dto3.hashCode());
	}
	
	@Test
	public void testToString() {
		var dto = new Role();
		Assert.assertTrue(dto.toString() instanceof String);
		Assert.assertTrue(dto.toString() != null);
	}
}
