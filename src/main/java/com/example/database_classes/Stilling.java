package com.example.database_classes;

/**
 * Created by paul on 10.01.17.
 */
public class Stilling {
    private String beskrivelse;

    public Stilling(String beskrivelse){
        this.beskrivelse = beskrivelse;
    }

    public Stilling(){}


    public String getBeskrivelse(){
        return beskrivelse;
    }


    public void setBeskrivelse(String beskrivelse){
        this.beskrivelse = beskrivelse;
    }

    @Override
    public String toString(){ return "Stillingbeskrivelse: " + beskrivelse; }
}
