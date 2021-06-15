package test.entities;

import org.junit.Test;
import org.junit.Assert;
import main.entities.Developing;

public class DevelopingTest {
	@Test
	public void testGettersSetters() {
		var entity = new Developing();
		
		entity.setHrs(1);
		Assert.assertEquals((long)1,entity.getHrs());
		
		entity.setTask_id(0);
		Assert.assertEquals(0, entity.getTask_id());
		
		entity.setEmployee_id(0);
		Assert.assertEquals(0, entity.getEmployee_id());
		
		entity.setActive(true);
		Assert.assertTrue(entity.isActive());
	}
	
	@Test
	public void testEqual() {
		var entity1 = new Developing();		
		Assert.assertTrue(entity1.equals(entity1));
		
		var entity2 = new Developing();
		entity2.setHrs(5);
		Assert.assertFalse(entity1.equals(entity2));
		
		entity1.setHrs(5);
		Assert.assertTrue(entity1.equals(entity2));
		
		var o = new Object();
		Assert.assertFalse(entity1.equals(o));
	}
	
	@Test
	public void testHashCode() {
		var entity1 = new Developing();
		var entity2 = new Developing();
		var entity3 = new Developing();
		entity3.setHrs(5);
		
		Assert.assertTrue(entity1.hashCode()==entity1.hashCode());
		Assert.assertTrue(entity1.hashCode()==entity2.hashCode());
		Assert.assertTrue(entity1.hashCode()!=entity3.hashCode());
		Assert.assertTrue(entity2.hashCode()!=entity3.hashCode());
		Assert.assertTrue(entity3.hashCode()==entity3.hashCode());
	}
	
	@Test
	public void testToString() {
		var entity = new Developing();
		Assert.assertTrue(entity.toString() instanceof String);
		Assert.assertTrue(entity.toString() != null);
	}

}
