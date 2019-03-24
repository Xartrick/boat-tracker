package com.example.boattracker.models;

import com.example.boattracker.documents.BaseDocument;
import com.example.boattracker.models.traits.HasId;
import com.example.boattracker.models.traits.HasPosition;
import com.example.boattracker.models.traits.HasName;

import java.util.ArrayList;
import java.util.List;

public class Containership
        extends BaseDocument
        implements HasId, HasName, HasPosition {

    private String captainName;
    private Port port;
    private ContainershipType type;
    private List<Container> containers;

    public Containership(String id, String name, String captainName, double latitude, double longitude, Port port, ContainershipType type) {
        this.setId(id);
        this.setName(name);

        this.captainName = captainName;

        this.setPosition(latitude, longitude);

        this.port = port;
        this.type = type;
        this.containers = new ArrayList<>();
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
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
