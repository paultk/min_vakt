package com.example.rest_controllers;

import com.example.database_classes.Stilling;
import com.example.security.TokenManager;
import com.example.sql_folder.DBConnection;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Håkon on 13.01.2017.
 */
public class StillingControllerTest {

    private StillingController stillingController = new StillingController();
	private String stilling1 = "Assistent";
	private String stilling2 = "Test";
    private Stilling stilling = new Stilling(stilling1);
    private Stilling updateStilling = new Stilling(stilling2);
    private String token;


    @Test
    public void testGetStilling() throws Exception {
        Assert.assertNotNull("Can't get Stilling from DB", stillingController.getStilling(stilling1, token));
    }

    /*@Test
    public void testDeleteStilling() throws Exception {
        Assert.assertTrue("Can't delete Stilling from DB", stillingController.deleteStilling(stillingController.getStilling(stilling1, token), token));
    }*/

    @Test
    public void testInsertStilling() throws Exception {
        Assert.assertTrue("Cant add Stilling to database", stillingController.insertStilling(updateStilling, token));
    }

    /*@Test
    public void testUpdateStilling() throws Exception {
        Assert.assertTrue("Can't edit Stilling in DB", stillingController.updateStilling(updateStilling, token));
    }*/

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