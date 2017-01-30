package com.example.rest_controllers;

import com.example.database_classes.Avdeling;
import com.example.database_classes.Bruker;
import com.example.database_classes.Vakt;
import com.example.security.TokenManager;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//*
// * Created by Knut on 13.01.2017.


public class VaktControllerTest {

    private VaktController controller = new VaktController();
	private DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
	private LocalDateTime m = LocalDateTime.parse("2010-12-18T14:30:40", aDateTimeFormatter);
    private LocalDateTime n = LocalDateTime.parse("2010-12-18T14:30:41", aDateTimeFormatter);
	private Vakt vakt = new Vakt(200, 1, 1 ,m , m, 350);
	private Vakt nyVakt = new Vakt(1, 1, 1 ,m , m, 400);
	private String t = "2008-12-18T14:30:40";
	private String s = "2018-12-18T14:30:41";
	private Avdeling avdeling = new Avdeling(1, "Test");
	private Bruker bruker = new Bruker(7, 11, "Assistent", 1, 1, 1, 1, true, "test", "test", "test", "Admin@@@");
	private String token;

    @Test
    public void vaktControllerTest() throws Exception {
        Assert.assertTrue("Can't add vakt to database", controller.insertVakt(vakt, token));
        Vakt[] vakter = controller.getAllVakter(token);
        Assert.assertNotNull("Can't get vakter from database", vakter);
        Assert.assertNotNull("Can't get vakter on brukerid from database", controller.getVakter(bruker, token));
        Assert.assertNotNull("Can't get vakter on a date from DB", controller.getVakterAvdeling(avdeling, token));
        Assert.assertNotNull("Can't get vakter on a user with month/year from DB", controller.getVakterBrukerCurMonth(1,12,2010, token));
        Assert.assertNotNull("Can't get vakter on a month and avdid from DB", controller.getAllVaktMonth(t,1, token));
        Assert.assertNotNull("Can't get vakter on a date from DB", controller.getAllVaktDate(t,token));
        vakter[0].setAntPers(999);
        Assert.assertTrue("Can't update vakt from DB", controller.updateVakt(vakter[0], token));
        //System.out.println(Arrays.toString(controller.getAllVaktDate(t,s)));
        Assert.assertTrue("Can't delete vakt from DB", controller.deleteVakt(vakter[vakter.length - 1], token));
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
