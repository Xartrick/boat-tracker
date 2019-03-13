package com.example.boattracker.models;

import java.util.ArrayList;
import java.util.List;

public class Containership {
    private String id;
    private String name;
    private String captainName;
    private double latitude;
    private double longitude;
    private Port port;
    private ContainershipType type;
    private List<Container> containers;

    public Containership(String id, String name, String captainName, double latitude, double longitude, Port port, ContainershipType type) {
        this.id = id;
        this.name = name;
        this.captainName = captainName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.port = port;
        this.type = type;
        this.containers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getCaptainName() {
        return captainName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
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

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public ContainershipType getType() {
        return type;
    }

    public void setType(ContainershipType type) {
        this.type = type;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }
}
