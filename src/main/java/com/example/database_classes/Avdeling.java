package com.example.database_classes;

/**
 * Created by paul on 10.01.17.
 */
public class Avdeling {
    private int avdelingId;
    private String navn;


    /**
     * Creates a new Avdeling (department) object from the database
     *
     * @param avdelingId department ID
     * @param navn  Name of department
     */
    public Avdeling(int avdelingId, String navn) {
        this.avdelingId = avdelingId;
        this.navn = navn;
    }

    public Avdeling() {}

    /**
     * Returns the department ID
     *
     * @return int avdelingId
     */
    public int getAvdelingId() {
        return avdelingId;
    }


    /**
     * Sets the department ID
     *
     * @param avdelingId the deparment ID
     */
    public void setAvdelingId(int avdelingId) {
        this.avdelingId = avdelingId;
    }

    /**
     * Returns the name of the department
     *
     * @return String navn
     */
    public String getNavn() {
        return navn;
    }

    /**
     * Sets the name of the department
     *
     * @param navn the name of the department
     */
    public void setNavn(String navn) {
        this.navn = navn;
    }

    @Override
    public String toString() {
        return "Avdeling ID: " + avdelingId + "\nNavn: " + navn;
    }
}
