package com.example.database_classes;

/**
 * Created by paul on 10.01.17.
 */
public class Stilling {
    private int stillingId;
    private String beskrivelse;

    public Stilling(int stillingId, String beskrivelse) {
        this.stillingId = stillingId;
        this.beskrivelse = beskrivelse;
    }

    public Stilling(String beskrivelse){
        this.beskrivelse = beskrivelse;
    }

    public Stilling(){}

    public int getStillingId(){
        return stillingId;
    }

    public String getBeskrivelse(){
        return beskrivelse;
    }

    public void setStillingId(int stillingId){
        this.stillingId = stillingId;
    }

    public void setBeskrivelse(String beskrivelse){
        this.beskrivelse = beskrivelse;
    }

    @Override
    public String toString(){ return "Stilling ID: " + stillingId + "\nBeskrivelse: " + beskrivelse; }
}
