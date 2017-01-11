package com.example.database_classes;

import java.time.LocalDateTime;

/**
 * Created by paul on 10.01.17.
 */
public class Overtid {
    private int id, brukerId;
    private double antTimer;
    private LocalDateTime date;
    private String kommentar;


    public Overtid(int id, int brukerId, double antTimer, LocalDateTime date, String kommentar) {
      this.id = id;
      this.brukerId = brukerId;
      this.antTimer = antTimer;
      this.date = date;
      this.kommentar = kommentar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}
