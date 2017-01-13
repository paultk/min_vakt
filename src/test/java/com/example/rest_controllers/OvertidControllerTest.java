package com.example.rest_controllers;

import com.example.database_classes.Overtid;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Savepoint;

import static org.junit.Assert.*;

/**
 * Created by Jens on 13-Jan-17.
 */
public class OvertidControllerTest {
	Savepoint savepoint;
	OvertidController controller = new OvertidController();
	@Before
	public void first() {
		DBConnection.beforeTest();
	}
	@Test
	public void overtidControllerTest() throws Exception {
		Overtid[] overtider = controller.getOvertider();
		assertNotNull(overtider);
		assertTrue("Kan ikke slette overtid fra DB", controller.deleteOvertid(overtider[overtider.length - 1]));
		assertTrue("Kan ikke legge til overtid i DB", controller.addOvertid(overtider[overtider.length - 1]));
	}
	@After
	public void after() {
		DBConnection.afterTest();
	}
}