package com.example.boattracker.models;

import java.io.Serializable;

public class Position implements Serializable {

    private double latitude;
    private double longitude;

    public Position() {

        setLatitude(0.0);
        setLongitude(0.0);
    }

    public Position(double latitude, double longitude) {

        setLatitude(latitude);
        setLongitude(longitude);
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
