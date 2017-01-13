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

    // Constructor for testing in SqlQueries.java
    public Bruker(int stillingsId, int avdelingId, int telefonNr, int stillingsProsent, double timelonn,
                  boolean admin, String fornavn, String etternavn, String epost, String plaintextPassord) {
        if (!PasswordEncoderGenerator.checkPasswordValidity(plaintextPassord)) throw new IllegalArgumentException("Invalid password format: passwords must be at least 8 characters long, containing at least one lowercase character, one uppercase character, and two special characters (@#$%^&+=).");
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bruker bruker = (Bruker) o;

        if (brukerId != bruker.brukerId) return false;
        if (passordId != bruker.passordId) return false;
        if (stillingsId != bruker.stillingsId) return false;
        if (avdelingId != bruker.avdelingId) return false;
        if (telefonNr != bruker.telefonNr) return false;
        if (stillingsProsent != bruker.stillingsProsent) return false;
        if (Double.compare(bruker.timelonn, timelonn) != 0) return false;
        if (admin != bruker.admin) return false;
        if (fornavn != null ? !fornavn.equals(bruker.fornavn) : bruker.fornavn != null) return false;
        if (etternavn != null ? !etternavn.equals(bruker.etternavn) : bruker.etternavn != null) return false;
        return epost != null ? epost.equals(bruker.epost) : bruker.epost == null;
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
