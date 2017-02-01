package com.example.rest_controllers;

import com.example.database_classes.Avdeling;
import com.example.security.TokenManager;
import com.example.sql_folder.DBConnection;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/// * Created by Håkon on 13.01.2017.*/


public class AvdelingControllerTest {

    private AvdelingController avdelingController = new AvdelingController();
    private String token;

    Avdeling avdeling = new Avdeling(4, "Kebab");
    Avdeling updateAvdeling = new Avdeling(1, "Falaffel");

    @Test
    public void testGetAvdeling() throws Exception {
        Assert.assertNotNull("Can't get Avdeling from DB", avdelingController.getAvdeling(2, token));

    }

    @Test
    public void testgetAllAvdeling() throws Exception {
        Assert.assertNotNull("Can't add Avdeling to DB", avdelingController.getAvdelinger(token));
    }

    /*@Test
    public void testDeleteAvdeling() throws Exception {
        Assert.assertTrue("Can't delete Avdeling from DB", avdelingController.deleteAvdeling(avdelingController.getAvdeling(3, token), token
		));
    }*/

    @Test
    public void testInsertAvdeling() throws Exception {
        Assert.assertTrue("Can't add Avdeling to DB", avdelingController.insertAvdeling(avdeling, token));
    }

    @Test
    public void testUpdateAvdeling() throws Exception {
        Assert.assertTrue("Can't edit avdeling in DB", avdelingController.updateAvdeling(updateAvdeling, token));

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
