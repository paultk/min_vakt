package com.example.database_classes;

/**
 * Created by Knut on 28.01.2017.
 */
public class VaktBytte {

    private int brukerId1, vaktId, brukerID2Id;

    public VaktBytte(int brukerId1, int vaktId, int brukerID2Id) {
        this.brukerId1 = brukerId1;
        this.vaktId = vaktId;
        this.brukerID2Id = brukerID2Id;
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

    public int getBrukerID2Id() {
        return brukerID2Id;
    }

    public void setBrukerID2Id(int brukerID2Id) {
        this.brukerID2Id = brukerID2Id;
    }

    @Override
    public String toString() {
        return "VaktBytte{" +
                "brukerId1=" + brukerId1 +
                ", vaktId=" + vaktId +
                ", brukerID2Id=" + brukerID2Id +
                '}';
    }
}
