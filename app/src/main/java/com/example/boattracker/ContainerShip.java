package com.example.boattracker;

public class ContainerShip {
    private String name;
    private String captainName;

    public ContainerShip(String name, String captainName) {
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
