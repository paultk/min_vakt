package com.example.database_classes;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * Created by paul on 10.01.17.
 */
public class Overtid {
    private int overtidId, brukerId, vaktId;
    private double antTimer;
    private String kommentar;

    public Overtid(int overtidId, int brukerId, double antTimer, int vaktId, String kommentar) {
      this.overtidId = overtidId;
      this.brukerId = brukerId;
      this.antTimer = antTimer;
      this.vaktId = vaktId;
      this.kommentar = kommentar;
    }

    public Overtid() {}

    public int getOvertidId() {
        return overtidId;
    }

    public void setOvertidId(int id) {
        this.overtidId = id;
    }

    public int getBrukerId() {
        return brukerId;
    }

    public void setBrukerId(int brukerId) {
        this.brukerId = brukerId;
    }

    public double getAntTimer() {
        return antTimer;
    }

    public void setAntTimer(double antTimer) {
        this.antTimer = antTimer;
    }

    public int getVaktId() {
        return vaktId;
    }

    public void setVaktId(int vaktId) {
        this.vaktId = vaktId;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}
