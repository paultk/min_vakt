package com.example.database_classes;

/**
 * Created by paul on 10.01.17.
 */
public class Passord {
    private int id;
    private String salt, hash;

    public Passord(int id, String salt, String hash) {
        this.id = id;
        this.salt = salt;
        this.hash = hash;
    }

    public Passord(String salt, String hash){
        this.salt = salt;
        this.hash = hash;
    }

    public Passord(){}

    public int getId(){
        return id;
    }

    public String getSalt(){
        return salt;
    }

    public String getHash(){
        return hash;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setSalt(String salt){
        this.salt = salt;
    }

    public void setHash(String hash){
        this.hash = hash;
    }

    @Override
    public String toString(){ return "ID: " + id + "\nSalt: " + salt + "\nHash" + hash; }
}
