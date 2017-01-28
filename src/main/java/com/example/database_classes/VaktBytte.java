package com.example.database_classes;

/**
 * Created by Knut on 28.01.2017.
 */
public class VaktBytte {

    private int brukerId1, vaktId, brukerId2;

    public VaktBytte(int brukerId1, int vaktId, int brukerId2) {
        this.brukerId1 = brukerId1;
        this.vaktId = vaktId;
        this.brukerId2 = brukerId2;
    }
    public VaktBytte() {}

    public int getBrukerId1() {
        return brukerId1;
    }

    public void setBrukerId1(int brukerId1) {
        this.brukerId1 = brukerId1;
    }

    public int getVaktId() {
        return vaktId;
    }

    public void setVaktId(int vaktId) {
        this.vaktId = vaktId;
    }

    public int getBrukerId2() {
        return brukerId2;
    }

    public void setBrukerId2(int brukerId2) {
        this.brukerId2 = brukerId2;
    }

    @Override
    public String toString() {
        return "VaktBytte{" +
                "brukerId1=" + brukerId1 +
                ", vaktId=" + vaktId +
                ", brukerId2=" + brukerId2 +
                '}';
    }
}
