package com.example.database_classes;

/**
 * Created by paul on 10.01.17.
 */
public class BrukerVakt {

    private int brukerId, vaktId;

    public BrukerVakt(int brukerId, int vaktId) {
        this.brukerId = brukerId;
        this.vaktId = vaktId;
    }

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
}
