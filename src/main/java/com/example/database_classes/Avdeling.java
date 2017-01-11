package com.example.database_classes;

/**
 * Created by paul on 10.01.17.
 */
public class Avdeling {
    private int avdelingId;
    private String navn;

    public Avdeling(int avdelingId, String navn) {
        this.avdelingId = avdelingId;
        this.navn = navn;
    }


    @Override
    public String toString() {
        return "Avdeling ID: " + avdelingId + "\nNavn: " + navn;
    }
}
