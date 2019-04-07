package com.example.boattracker.models;

import com.example.boattracker.documents.BaseDocument;
import com.example.boattracker.models.traits.HasId;
import com.example.boattracker.models.traits.HasPosition;
import com.example.boattracker.models.traits.HasName;
import com.example.boattracker.store.ContainerStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Containership
        extends BaseDocument
        implements HasId, HasName, HasPosition {

    public static String COLLECTION_NAME = "containerships";

    private String captainName;
    private Port port;
    private ContainershipType type;

    public Containership(String id, String name, String captainName, double latitude, double longitude, Port port, ContainershipType type) {
        this.setId(id);
        this.setName(name);

        this.captainName = captainName;

        this.setPosition(latitude, longitude);

        this.port = port;
        this.type = type;
    }

    public void replace(Containership containership) {
        setId(containership.getId());
        setName(containership.getName());
        setCaptainName(containership.getCaptainName());
        setPosition(containership.getLatitude(), containership.getLongitude());
        setPort(containership.getPort());
        setType(containership.getType());
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

        container.setContainership(this);

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

        return this.addContainer(container);
    }

    /**
     * Check if Container is in Container list
     * @param container Container
     * @return True if Container is in Container list, false otherwise
     */
    public boolean hasContainer(Container container) {
        final List<Container> containers = this.getContainers();

        for (Container c : containers) {
            if (c.getId().equals(container.getId())) {
                return true;
            }
        }

        return false;
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

    /**
     * Get free volume.
     * @return Free volume
     */
    public int getFreeVolume() {

        return this.getType().getVolume() - this.getContainersVolume();
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

    public boolean canMoveContainerTo(Container container, Containership containership) {
        return containership.canContainContainer(container) && isContainershipCloseEnough(containership);
    }

    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();

        data.put("captainName", this.getCaptainName());
        data.put("name", this.getName());
        data.put("position", this.getPosition());
        data.put("port", this.port.getDocumentReference());
        data.put("type", this.type.getDocumentReference());

        return data;
    }

    /*
     * Getters and setters
     */

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
        final List<Container> containers = new ArrayList<>();
        final List<Container> containership_containers = ContainerStore.all();

        for (Container container : containership_containers) {
            if (container.getContainership().getId().equals(this.getId())) {
                containers.add(container);
            }
        }

        return containers;
    }

    /**
     * Get Firebase document path
     *
     * @return Document path
     */
    public String getDocumentPath() {
        return getDocumentPath(this.getId());
    }

    /**
     * Get Firebase document path
     *
     * @return Document path
     */
    public static String getDocumentPath(String id) {
        return "/" + COLLECTION_NAME + "/" + id;
    }
}
