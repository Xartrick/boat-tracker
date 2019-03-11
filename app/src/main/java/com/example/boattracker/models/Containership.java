package com.example.boattracker.models;

public class Containership {
    private String name;
    private String captainName;

    public Containership(String name, String captainName) {
        this.name = name;
        this.captainName = captainName;
    }

    public String getName() {
        return name;
    }

    public String getCaptainName() {
        return captainName;
    }
}
