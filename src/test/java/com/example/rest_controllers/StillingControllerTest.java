package com.example.rest_controllers;

import com.example.database_classes.Stilling;
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

    Stilling stilling = new Stilling(3, "Lege");
    Stilling updateStilling = new Stilling(8, "Hjelpepleier");


    @Test
    public void testGetStilling() throws Exception {
        Assert.assertNotNull("Can't get Stilling from DB", stillingController.getStilling(2));
    }

    @Test
    public void testDeleteStilling() throws Exception {
        Assert.assertTrue("Can't delete Stilling from DB", stillingController.deleteStilling(stillingController.getStilling(8)));
    }

    @Test
    public void testInsertStilling() throws Exception {
        Assert.assertTrue("Cant add Stilling to database", stillingController.insertStilling(stilling));
    }

    @Test
    public void testUpdateStilling() throws Exception {
        Assert.assertTrue("Can't edit Stilling in DB", stillingController.updateStilling(updateStilling));
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