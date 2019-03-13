package com.example.boattracker.models;

import java.util.ArrayList;
import java.util.List;

public class Containership {
    private String id;
    private String name;
    private String captainName;
    private double latitude;
    private double longitude;
    private ContainershipType type;
    private List<Container> containers;

    public Containership(String id, String name, String captainName, double latitude, double longitude, ContainershipType type) {
        this.id = id;
        this.name = name;
        this.captainName = captainName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.containers = new ArrayList<>();
    }

    public void addContainer(Container container){
        this.containers.add(container);
    }

    public void removeContainer(Container container){
        this.containers.remove(container);
    }

    public String getName() {
        return name;
    }

    public String getCaptainName() {
        return captainName;
    }
}
