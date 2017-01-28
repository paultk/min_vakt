package com.example.rest_controllers;

import com.example.database_classes.Passord;
import com.example.security.TokenManager;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//*
// * Created by Jens on 13-Jan-17.


public class PassordControllerTest {
	private PassordController controller = new PassordController();
	private String token;
	@Test
	public void passordControllerTest() throws Exception {
		Passord[] passorder = controller.getPassordene(token);
		assertNotNull("Can't get passords from DB", passorder);
		assertNotNull("Can't get single pass from DB", controller.getPassord(passorder[0].getId(), token));
		assertTrue("Can't add pass to DB", controller.addPassord(passorder[0], token));
		passorder = controller.getPassordene(token);
		assertTrue("Can't delete passord from DB", controller.deletePassord(passorder[passorder.length - 1], token));
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
