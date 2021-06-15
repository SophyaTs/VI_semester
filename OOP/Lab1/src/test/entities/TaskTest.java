package test.entities;

import org.junit.Assert;
import org.junit.Test;

import main.entities.Role;
import main.entities.Task;

public class TaskTest {
	@Test
	public void testGettersSetters() {
		var dto = new Task();
		
		dto.setId(1);
		Assert.assertEquals((long)1,dto.getId());
		
		dto.setName("Name");
		Assert.assertEquals("Name",dto.getName());
		
		dto.setProject_id(0);
		Assert.assertEquals(0,dto.getProject_id());
		
		dto.setQualification(new Role());
		Assert.assertEquals(new Role(),dto.getQualification());
		
		dto.setWorkers_num(1);
		Assert.assertEquals((long)1,dto.getWorkers_num());
	}

	@Test
	public void testEqual() {
		var dto1 = new Task();		
		Assert.assertTrue(dto1.equals(dto1));
		
		var dto2 = new Task();
		dto2.setId(5);
		Assert.assertFalse(dto1.equals(dto2));
		
		dto1.setId(5);
		Assert.assertTrue(dto1.equals(dto2));
		
		var o = new Object();
		Assert.assertFalse(dto1.equals(o));
	}
	
	@Test
	public void testHashCode() {
		var dto1 = new Task();
		var dto2 = new Task();
		var dto3 = new Task();
		dto3.setId(5);
		
		Assert.assertTrue(dto1.hashCode()==dto1.hashCode());
		Assert.assertTrue(dto1.hashCode()==dto2.hashCode());
		Assert.assertTrue(dto1.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto2.hashCode()!=dto3.hashCode());
		Assert.assertTrue(dto3.hashCode()==dto3.hashCode());
	}
	
	@Test
	public void testToString() {
		var dto = new Task();
		Assert.assertTrue(dto.toString() instanceof String);
		Assert.assertTrue(dto.toString() != null);
	}
}
