package com.example.database_classes;

/**
 * Created by paul on 10.01.17.
 */
public class Fravaer {

    private int brukerId, vaktId;
    private double antTimer;
    private String kommentar;

    public Fravaer(int brukerId, int vaktId, double antTimer, String kommentar) {
        this.brukerId = brukerId;
        this.vaktId = vaktId;
        this.antTimer = antTimer;
        this.kommentar = kommentar;

    }
    public Fravaer(){}

    public int getBrukerId() {
        return brukerId;
    }

    public void setBrukerId(int brukerId) {
        this.brukerId = brukerId;
    }

    public int getVaktId() {
        return vaktId;
    }

    public void setVaktId(int vaktId) {
        this.vaktId = vaktId;
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

    @Override
    public String toString() {
        return "Bruker ID: " + brukerId + "\nVaktId: " + vaktId + "\nAntall timer: " + antTimer + "\nKommentar: " + kommentar;
    }
}
