package com.example.rest_controllers;

import com.example.database_classes.BrukerVakt;
import com.example.security.TokenManager;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


// * Created by Jens on 13-Jan-17.


public class BrukerVaktControllerTest {
	private BrukerVaktController controller = new BrukerVaktController();
	private String token;
	@Test
	public void brukerVaktControllerTest() throws Exception {
		BrukerVakt[] brukervakter = controller.getBrukerVakter(token);
		assertNotNull("Can't get brukervakter from DB", brukervakter);
		assertTrue("Can't delete brukervakt from DB", controller.deleteBrukerVakt(brukervakter[brukervakter.length - 1], token));
		assertTrue("Can't add to DB", controller.addBrukerVakt(brukervakter[brukervakter.length - 1], token));
	}
	@Before
	public void first() throws Exception {
		DBConnection.beforeTest();
		this.token = TokenManager.lagToken("test");
	}
	@After
	public void after() {
		DBConnection.afterTest();
	}
}
