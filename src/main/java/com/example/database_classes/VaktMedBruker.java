package com.example.database_classes;

/**
 * Created by Knut on 23.01.2017.
 */
public class VaktMedBruker {
    private Vakt vakt;
    private int brukerId;

    public VaktMedBruker(Vakt vakt, int brukerId) {
        this.vakt = vakt;
        this.brukerId = brukerId;
    }

    public VaktMedBruker() {
    }

    public Vakt getVakt() {
        return vakt;
    }

    public void setVakt(Vakt vakt) {
        this.vakt = vakt;
    }

    public int getBrukerId() {
        return brukerId;
    }

    public void setBrukerId(int brukerId) {
        this.brukerId = brukerId;
    }

    @Override
    public String toString() {
        return "VaktMedBruker{" +
                "vakt=" + vakt +
                ", brukerId=" + brukerId +
                '}';
    }
}
