/*
package com.example.rest_controllers;

import com.example.database_classes.Passord;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

*/
/**
 * Created by Jens on 13-Jan-17.
 *//*

public class PassordControllerTest {
	PassordController controller = new PassordController();
	@Test
	public void passordControllerTest() throws Exception {
		Passord[] passorder = controller.getPassordene();
		assertNotNull("Can't get passords from DB", passorder);
		assertNotNull("Can't get single pass from DB", controller.getPassord(passorder[0].getId()));
		assertTrue("Can't add pass to DB", controller.addPassord(passorder[0]));
		passorder = controller.getPassordene();
		assertTrue("Can't delete passord from DB", controller.deletePassord(passorder[passorder.length - 1]));
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
