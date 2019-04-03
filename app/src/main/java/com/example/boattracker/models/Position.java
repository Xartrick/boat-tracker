package com.example.boattracker.models;

import java.io.Serializable;

public class Position implements Serializable {

    private double latitude;
    private double longitude;

    public Position() {
        this.setLatitude(0.0);
        this.setLongitude(0.0);
    }

    public Position(double latitude, double longitude) {
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
