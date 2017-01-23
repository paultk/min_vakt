package com.example.database_classes;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * Created by paul on 10.01.17.
 */
public class Overtid {
    private int overtidId, brukerVaktId;
    private double antTimer;
    private String kommentar;

    public Overtid(int overtidId, int brukerVaktId, double antTimer, String kommentar) {
      this.overtidId = overtidId;
      this.brukerVaktId = brukerVaktId;
      this.antTimer = antTimer;
      this.kommentar = kommentar;
    }

    public Overtid() {}

    public int getOvertidId() {
        return overtidId;
    }

    public void setOvertidId(int id) {
        this.overtidId = id;
    }

    public int getBrukerVaktId() {
        return brukerVaktId;
    }

    public void setBrukerVaktId(int brukerId) {
        this.brukerVaktId = brukerId;
    }

    public double getAntTimer() {
        return antTimer;
    }

    public void setAntTimer(double antTimer) {
        this.antTimer = antTimer;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}
