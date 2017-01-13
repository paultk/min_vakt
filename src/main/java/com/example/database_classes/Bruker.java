package com.example.database_classes;

import com.example.security.PasswordEncoderGenerator;

/**
 * Created by paul on 10.01.17.
 */
public class Bruker {

    private int brukerId, passordId, stillingsId, avdelingId, telefonNr, stillingsProsent;
    private double timelonn;
    private boolean admin;
    private String fornavn, etternavn, epost;
    private String plaintextPassord;
    private String hash;
    private String salt;

    public Bruker(int brukerId, int passordId, int stillingsId, int avdelingId, int telefonNr, int stillingsProsent, double timelonn,
                  boolean admin, String fornavn, String etternavn, String epost, String plaintextPassord) {
        this.brukerId = brukerId;
        this.passordId = passordId;
        this.stillingsId = stillingsId;
        this.telefonNr = telefonNr;
        this.stillingsProsent = stillingsProsent;
        this.timelonn = timelonn;
        this.admin = admin;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.epost = epost;
        this.avdelingId = avdelingId;
        this.plaintextPassord = plaintextPassord;
    }

    //Empty constructor for Spring REST
    public Bruker(){}

    public int getBrukerId() {
        return brukerId;
    }

    public void setBrukerId(int brukerId) {
        this.brukerId = brukerId;
    }

    public int getPassordId() {
        return passordId;
    }

    public void setPassordId(int passordId) {
        this.passordId = passordId;
    }

    public int getStillingsId() {
        return stillingsId;
    }

    public void setStillingsId(int stillingsId) {
        this.stillingsId = stillingsId;
    }

    public int getAvdelingId() {
        return avdelingId;
    }

    public void setAvdelingId(int avdelingId) {
        this.avdelingId = avdelingId;
    }

    public int getTelefonNr() {
        return telefonNr;
    }

    public void setTelefonNr(int telefonNr) {
        this.telefonNr = telefonNr;
    }

    public int getStillingsProsent() {
        return stillingsProsent;
    }

    public void setStillingsProsent(int stillingsProsent) {
        this.stillingsProsent = stillingsProsent;
    }

    public double getTimelonn() {
        return timelonn;
    }

    public void setTimelonn(double timelonn) {
        this.timelonn = timelonn;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public String getEpost() {
        return epost;
    }

    public void setEpost(String epost) {
        this.epost = epost;
    }

    public void setPassord(String newPassord) {
        plaintextPassord = newPassord;
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }

    public void hashPassord() {
        PasswordEncoderGenerator passwordEncoder = new PasswordEncoderGenerator();
        byte[] saltBytes = passwordEncoder.generateSalt();
        byte[] hashBytes = passwordEncoder.generateHash(plaintextPassord, saltBytes);

        salt = passwordEncoder.bytesToHex(saltBytes);
        hash = passwordEncoder.bytesToHex(hashBytes);
    }

    @Override
    public String toString() {
        return "Bruker{" +
                "brukerId=" + brukerId +
                ", passordId=" + passordId +
                ", stillingsId=" + stillingsId +
                ", avdelingId=" + avdelingId +
                ", telefonNr=" + telefonNr +
                ", stillingsProsent=" + stillingsProsent +
                ", timelonn=" + timelonn +
                ", admin=" + admin +
                ", fornavn='" + fornavn + '\'' +
                ", etternavn='" + etternavn + '\'' +
                ", epost='" + epost + '\'' +
                '}';
    }
}
