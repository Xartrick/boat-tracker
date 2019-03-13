package com.example.boattracker.models;

public class Port {
    private String id;
    private String name;
    private double latitude;
    private double longitude;

    public Port(String id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
