package com.example.database_classes;

/**
 * Created by paul on 10.01.17.
 */
public class BrukerVakt {

    private int brukerVaktId, brukerId, vaktId;

    public BrukerVakt(int brukerVaktId, int brukerId, int vaktId) {
        this.brukerVaktId = brukerVaktId;
        this.brukerId = brukerId;
        this.vaktId = vaktId;
    }

    public BrukerVakt() {}

    public int getBrukerVaktId() {
        return brukerVaktId;
    }

    public void setBrukerVaktId(int brukerVaktId) {
        this.brukerVaktId = brukerVaktId;
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

    @Override
    public String toString() {
        return "BrukerVakt{" +
                "brukerVaktId=" + brukerVaktId +
                ", brukerId=" + brukerId +
                ", vaktId=" + vaktId +
                '}';
    }
}
