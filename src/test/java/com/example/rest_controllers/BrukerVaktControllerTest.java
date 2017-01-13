package com.example.rest_controllers;

import com.example.database_classes.BrukerVakt;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jens on 13-Jan-17.
 */
public class BrukerVaktControllerTest {
	BrukerVaktController controller = new BrukerVaktController();
	@Test
	public void brukerVaktControllerTest() throws Exception {
		BrukerVakt[] brukervakter = controller.getBrukerVakter();
		assertNotNull("Can't get brukervakter from DB", brukervakter);
		assertTrue("Can't delete brukervakt from DB", controller.deleteBrukerVakt(brukervakter[brukervakter.length - 1]));
		assertTrue("Can't add to DB", controller.addBrukerVakt(brukervakter[brukervakter.length - 1]));
	}
	@Before
	public void first() {
		DBConnection.beforeTest();
	}
	@After
	public void after() {
		DBConnection.afterTest();
	}
}