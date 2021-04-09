package com.tsubin.webapp.main.entity;

import org.junit.Assert;
import org.junit.Test;

public class DevelopingTests {
	@Test
	public void testGettersSetters() {
		var entity = new Developing();
		
		entity.setHrs(1);
		Assert.assertEquals((long)1,entity.getHrs());
		
		entity.setTask(new Task());
		Assert.assertEquals(new Task(), entity.getTask());
		
		entity.setEmployee(new Employee());
		Assert.assertEquals(new Employee(), entity.getEmployee());
		
		entity.setActive(true);
		Assert.assertTrue(entity.getActive());
	}
	
	@Test
	public void testEqual() {
		var entity1 = new Developing();		
		Assert.assertTrue(entity1.canEqual(entity1));
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
	
	@Test
	public void testGettersSettersId() {
		var id = new Developing.DevelopingId();
		
		id.setEmployee_id(1);
		Assert.assertEquals((long)1,id.getEmployee_id());
		
		id.setTask_id(1);
		Assert.assertEquals((long)1,id.getTask_id());
		
		id = new Developing.DevelopingId(1,1);
		Assert.assertEquals((long)1,id.getEmployee_id());
		Assert.assertEquals((long)1,id.getTask_id());
	}
	
	@Test
	public void testEqualId() {
		var id1 = new Developing.DevelopingId();		
		Assert.assertTrue(id1.canEqual(id1));
		Assert.assertTrue(id1.equals(id1));
		
		var id2 = new Developing.DevelopingId();

		id1.setEmployee_id(1);
		Assert.assertFalse(id1.equals(id2));
		
		id2.setEmployee_id(1);
		Assert.assertTrue(id1.equals(id2));
		
		var o = new Object();
		Assert.assertFalse(id1.equals(o));
	}
	
	@Test
	public void testHashCodeId() {
		var entity1 = new Developing.DevelopingId();
		var entity2 = new Developing.DevelopingId();
		var entity3 = new Developing.DevelopingId();
		entity3.setEmployee_id(5);
		
		Assert.assertTrue(entity1.hashCode()==entity1.hashCode());
		Assert.assertTrue(entity1.hashCode()==entity2.hashCode());
		Assert.assertTrue(entity1.hashCode()!=entity3.hashCode());
		Assert.assertTrue(entity2.hashCode()!=entity3.hashCode());
		Assert.assertTrue(entity3.hashCode()==entity3.hashCode());
	}
	
	@Test
	public void testToStringId() {
		var entity = new Developing.DevelopingId();
		Assert.assertTrue(entity.toString() instanceof String);
		Assert.assertTrue(entity.toString() != null);
	}
}
