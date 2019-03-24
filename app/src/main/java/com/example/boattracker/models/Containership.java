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
        return this.containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    /**
     * Add a Container
     *
     * @param container Container to add
     * @return True if Container has been added, false otherwise
     */
    public boolean addContainer(Container container) {
        if (this.hasContainer(container)) {
            return false;
        }

        if (!this.canContainContainer(container)) {
            return false;
        }

        this.containers.add(container);

        return true;
    }

    /**
     * Move a Container from a Containership
     *
     * @param containership Source Containership
     * @param container Container to move
     * @return True if Container has been moved, false otherwise
     */
    public boolean moveContainerFromContainership(Containership containership, Container container) {
        if (!containership.hasContainer(container)) {
            return false;
        }

        if (!this.isContainershipCloseEnough(containership)) {
            return false;
        }

        if (!this.addContainer(container)) {
            return false;
        }

        return containership.removeContainer(container);
    }

    /**
     * Remove a Container
     *
     * @param container Container
     * @return True if Container has been removed, false otherwise
     */
    public boolean removeContainer(Container container) {
        if (!this.hasContainer(container)) {
            return false;
        }

        this.containers.remove(container);

        return true;
    }

    /**
     * Check if Container is in Container list
     * @param container Container
     * @return True if Container is in Container list, false otherwise
     */
    public boolean hasContainer(Container container) {
        final List<Container> containers = this.getContainers();

        return containers.contains(container);
    }

    /**
     * Get used volume by containers
     *
     * @return Containers used volume
     */
    public int getContainersVolume() {
        int volume = 0;

        final List<Container> containers = this.getContainers();

        for (Container container : containers) {
            volume += container.getVolume();
        }

        return volume;
    }

    public boolean canContainContainer(Container container) {
        int volume = container.getVolume();
        int max_volume = this.getType().getVolume();
        int used_volume = this.getContainersVolume();

        return (used_volume + volume <= max_volume);
    }

    public boolean isContainershipCloseEnough(Containership containership) {
        return this.getDistance(containership) <= 300;
    }
}
