package com.example.database_classes;

/**
 * Created by paul on 10.01.17.
 */
public class Stilling {
    private int stillingId;
    private String stillingNavn;

    public Stilling(int stillingId, String stillingNavn) {
        this.stillingId = stillingId;
        this.stillingNavn = stillingNavn;
    }

    @Override
    public String toString(){ return "Stilling ID: " + stillingId + "\nStilling navn: " + stillingNavn; }
}
