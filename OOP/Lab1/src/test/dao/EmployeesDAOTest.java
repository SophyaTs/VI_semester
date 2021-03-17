package test.dao;

import static org.mockito.Mockito.mock;

import java.sql.Connection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.*;
import org.powermock.core.classloader.annotations.PrepareForTest;

import main.dao.EmployeesDAO;
import main.dbconnection.ConnectionPool;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Connection.class, ConnectionPool.class})
public class EmployeesDAOTest {
	
	@Test
	public void test() {
		mockStatic(Connection.class); 
		mockStatic(ConnectionPool.class);
	}


}
