package test.entities;

import org.junit.Test;

import org.junit.Assert;
import main.entities.DevelopingRelation;

public class DevelopingRelationTest {
	@Test
	public void testGettersSetters() {
		DevelopingRelation r = new DevelopingRelation((long)1,(long)1,(long)10,true);
		Assert.assertEquals((long)1, r.getEmplyee_id());
		Assert.assertEquals((long)1, r.getTask_id());
		Assert.assertEquals((long)10, r.getHrs());
		Assert.assertEquals(true, r.isActive());
		
		r.setEmplyee_id(2);
		Assert.assertEquals((long)2, r.getEmplyee_id());
		
		r.setTask_id(2);
		Assert.assertEquals((long)2, r.getTask_id());
		
		r.setHrs(20);
		Assert.assertEquals((long)20, r.getHrs());
		
		r.setActive(false);
		Assert.assertEquals(false, r.isActive());
	}

}
