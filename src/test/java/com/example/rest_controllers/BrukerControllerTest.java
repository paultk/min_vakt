package com.example.rest_controllers;

import com.example.database_classes.Bruker;
import com.example.security.TokenManager;
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
	private BrukerController controller = new BrukerController();
	private String token;
	Bruker bruker = new Bruker(1, 1, "Assistent", 1, 1, 1, 1, true, "test", "test", "testFyrDawg", "#@Aa1234");

	@Test
	public void brukerControllerTest() throws Exception {
		Assert.assertTrue("Can't add bruker to database", controller.addBruker(token, bruker));
		Bruker[] brukere = controller.getBrukere(token);
		Assert.assertNotNull("Can't get brukere from database", brukere);
		Assert.assertNotNull("Can't get single bruker from DB", controller.getBruker(token, brukere[1].getBrukerId()));
		Bruker editBruker = new Bruker(brukere[brukere.length - 1].getBrukerId(), 1, "Assistent", 1, 1, 1, 1, false, "mod", "mod", "mod");
		assertTrue("Can't edit bruker in DB", controller.updateBruker(token, editBruker));
	//	Assert.assertTrue("Can't delete bruker from DB", controller.deleteBruker(token, brukere[brukere.length - 1]));
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