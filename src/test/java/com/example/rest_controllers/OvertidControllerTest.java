/*
package com.example.rest_controllers;

import com.example.database_classes.Overtid;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.Savepoint;

import static org.junit.Assert.*;

*/
/**
 * Created by Jens on 13-Jan-17.
 *//*

public class OvertidControllerTest {
	OvertidController controller = new OvertidController();
	@Test
	public void overtidControllerTest() throws Exception {
		Overtid[] overtider = controller.getOvertider();
		assertNotNull("Can't get overtider from DB", overtider);
		assertNotNull("Can't get single overtid from DB", controller.getOvertid(overtider[0].getOvertidId()));
		assertTrue("Can't delete overtid from DB", controller.deleteOvertid(overtider[overtider.length - 1]));
		assertTrue("Can't add overtid to DB", controller.addOvertid(overtider[overtider.length - 1]));
		//TODO this test is dependent on vaktid 1 existing
		Overtid ny = new Overtid(overtider[0].getOvertidId(), 1, 10, "test");
		assertTrue("Can't update overtid in DB", controller.updateOvertid(ny));
	}
	@Before
	public void first() {
		DBConnection.beforeTest();
	}
	@After
	public void after() {
		DBConnection.afterTest();
	}
}*/
