package com.example.rest_controllers;

import com.example.database_classes.Overtid;
import com.example.security.TokenManager;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//*
// * Created by Jens on 13-Jan-17.


public class OvertidControllerTest {
	private OvertidController controller = new OvertidController();
	private String token;
	@Test
	public void overtidControllerTest() throws Exception {
		Overtid[] overtider = controller.getOvertider(token);
		assertNotNull("Can't get overtider from DB", overtider);
		assertNotNull("Can't get single overtid from DB", controller.getOvertid(overtider[0].getOvertidId(), token));
		assertTrue("Can't delete overtid from DB", controller.deleteOvertid(overtider[overtider.length - 1], token));
		assertTrue("Can't add overtid to DB", controller.addOvertid(overtider[overtider.length - 1], token));
		//TODO this test is dependent on vaktid 1 existing
		Overtid ny = new Overtid(overtider[0].getOvertidId(), 3, 10, "test");
		assertTrue("Can't update overtid in DB", controller.updateOvertid(ny, token));
	}
	@Before
	public void first() throws Exception {
		DBConnection.beforeTest();
		this.token = TokenManager.lagToken("test", true);
	}
	@After
	public void after() {
		DBConnection.afterTest();
	}
}
