package com.example.rest_controllers;

import com.example.database_classes.Bruker;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jens on 13-Jan-17.
 */
public class BrukerControllerTest {
	/*private BrukerController controller = new BrukerController();
	Bruker bruker = new Bruker(1, 1, 1, 1, 1, 1, 1, true, "test", "test", "test", "#@Aa1234");

	@Test
	public void brukerControllerTest() throws Exception {
		Assert.assertTrue("Can't add bruker to database", controller.addBruker(bruker));
		Bruker[] brukere = controller.getBrukere();
		Assert.assertNotNull("Can't get brukere from database", brukere);
		Assert.assertNotNull("Can't get single bruker from DB", controller.getBruker(brukere[1].getBrukerId()));
		Bruker editBruker = new Bruker(brukere[brukere.length - 1].getBrukerId(), 1, 1, 1, 1, 1, 1, false, "mod", "mod", "mod");
		assertTrue("Can't edit bruker in DB", controller.updateBruker(editBruker));
		Assert.assertTrue("Can't delete bruker from DB", controller.deleteBruker(brukere[brukere.length - 1]));
	}
	@Before
	public void first() {
		DBConnection.beforeTest();
	}
	@After
	public void after() {
		DBConnection.afterTest();
	}*/
}