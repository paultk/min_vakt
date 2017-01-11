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

    @Override
    public String toString(){ return "ID: " + id + "\nSalt: " + salt + "\nHash" + hash; }
}
