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

    @Override
    public String toString() {
        return "Bruker ID: " + brukerId + "\nVaktId: " + vaktId + "\nAntall timer: " + antTimer + "\nKommentar: " + kommentar;
    }
}
