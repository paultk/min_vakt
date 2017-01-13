package com.example.rest_controllers;

import com.example.database_classes.BrukerVakt;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jens on 13-Jan-17.
 */
public class BrukerVaktControllerTest {
	BrukerVaktController controller = new BrukerVaktController();
	@Test
	public void brukerVaktControllerTest() throws Exception {
		try {
			controller.deleteBrukerVakt(new BrukerVakt(1, 1));
		}
		catch (Exception e) {}
		assertTrue("Can't add brukervakt to DB", controller.addBrukerVakt(new BrukerVakt(1, 1)));
		BrukerVakt[] brukervakter = controller.getBrukerVakter();
		assertNotNull("Can't get brukervakter from DB", brukervakter);
		assertTrue("Can't delete brukervakt from DB", controller.deleteBrukerVakt(brukervakter[brukervakter.length - 1]));
	}
}