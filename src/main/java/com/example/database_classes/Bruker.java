package com.example.database_classes;

import com.example.security.PasswordSystemManager;


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
    private static final String INVALID_PASSWORD_FORMAT = "Invalid password format: passwords must be at least 8 characters long, containing at least one lowercase character, one uppercase character, and two special characters (@#$%!^&+=).";

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

    public Bruker(int passordId, int stillingsId, int avdelingId, int telefonNr, int stillingsProsent, double timelonn,
                  boolean admin, String fornavn, String etternavn, String epost, String plaintextPassord) {
        this.passordId = passordId;
        this.stillingsId = stillingsId;
        this.avdelingId = avdelingId;
        this.telefonNr = telefonNr;
        this.stillingsProsent = stillingsProsent;
        this.timelonn = timelonn;
        this.admin = admin;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.epost = epost;
        this.plaintextPassord = plaintextPassord;
    }

    public Bruker(int brukerId, int passordId, int stillingsId, int avdelingId, int telefonNr, int stillingsProsent, double timelonn,
                  boolean admin, String fornavn, String etternavn, String epost) {
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
    }

    //Empty constructor for Spring REST
    public Bruker(){}

    // Constructor for testing in SqlQueries.java
    public Bruker(int stillingsId, int avdelingId, int telefonNr, int stillingsProsent, double timelonn,
                  boolean admin, String fornavn, String etternavn, String epost, String plaintextPassord) {
        if (!PasswordSystemManager.checkPasswordValidity(plaintextPassord)) throw new IllegalArgumentException(INVALID_PASSWORD_FORMAT);
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

    /**
     * Returns user ID
     *
     * @return brukerId
     */
    public int getBrukerId() {
        return brukerId;
    }

    /**
     * Sets user ID
     *
     * @param brukerId user ID
     */
    public void setBrukerId(int brukerId) {
        this.brukerId = brukerId;
    }

    /**
     * Returns password ID
     *
     * @return passordId
     */
    public int getPassordId() {
        return passordId;
    }

    /**
     * Sets password ID
     *
     * @param passordId password ID
     */
    public void setPassordId(int passordId) {
        this.passordId = passordId;
    }

    /**
     * Returns position ID
     *
     * @return stillingsId
     */
    public int getStillingsId() {
        return stillingsId;
    }

    /**
     * Sets position ID
     *
     * @param stillingsId position ID
     */
    public void setStillingsId(int stillingsId) {
        this.stillingsId = stillingsId;
    }

    /**
     * Returns department ID
     *
     * @return avdelingId
     */
    public int getAvdelingId() {
        return avdelingId;
    }

    /**
     * Sets department ID
     *
     * @param avdelingId department ID
     */
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

    public void setPlaintextPassord(String plaintextPassord) {
        this.plaintextPassord = plaintextPassord;
        hashPassord();
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }

    public void hashPassord() throws IllegalArgumentException {
        if (!PasswordSystemManager.checkPasswordValidity(plaintextPassord)) {
            System.out.println("Attempted password: " + plaintextPassord);
            throw new IllegalArgumentException(INVALID_PASSWORD_FORMAT);
        }
        PasswordSystemManager passwordEncoder = new PasswordSystemManager();
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
