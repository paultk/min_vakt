package com.example.database_classes;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * Created by paul on 10.01.17.
 */
public class Overtid {
    private int overtidId, brukerId;
    private double antTimer;
    private Date dato;
    private String kommentar;


    public Overtid(int overtidId, int brukerId, double antTimer, Date dato, String kommentar) {
      this.overtidId = overtidId;
      this.brukerId = brukerId;
      this.antTimer = antTimer;
      this.dato = dato;
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

    public Date getDato() {
        return dato;
    }

    public void setDate(LocalDateTime date) {
        this.dato = dato;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}
