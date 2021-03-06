package com.example.boattracker.models;

import com.example.boattracker.documents.BaseDocument;
import com.example.boattracker.models.traits.HasId;
import com.example.boattracker.models.traits.HasName;
import com.example.boattracker.models.traits.HasPosition;
import com.example.boattracker.store.ContainerStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Containership
        extends BaseDocument
        implements HasId, HasName, HasPosition {

    public final static String COLLECTION_NAME = "containerships";

    private String captainName;
    private Port port;
    private ContainershipType type;

    public Containership(String id, String name, String captainName, double latitude, double longitude, Port port, ContainershipType type) {

        setId(id);
        setName(name);

        this.captainName = captainName;

        setPosition(latitude, longitude);

        this.port = port;
        this.type = type;
    }

    /**
     * Get Firebase document path
     *
     * @return Document path
     */
    public static String getDocumentPath(String id) {

        return "/" + COLLECTION_NAME + "/" + id;
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

        if (hasContainer(container)) {
            return false;
        }

        if (!canContainContainer(container)) {
            return false;
        }

        container.setContainership(this);

        return true;
    }

    /**
     * Move a Container from a Containership
     *
     * @param containership Source Containership
     * @param container     Container to move
     * @return True if Container has been moved, false otherwise
     */
    public boolean moveContainerFromContainership(Containership containership, Container container) {

        if (!containership.hasContainer(container)) {
            return false;
        }

        if (!isContainershipCloseEnough(containership)) {
            return false;
        }

        return addContainer(container);
    }

    /**
     * Check if Container is in Container list
     *
     * @param container Container
     * @return True if Container is in Container list, false otherwise
     */
    private boolean hasContainer(Container container) {

        final List<Container> containers = getContainers();

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

        final List<Container> containers = getContainers();

        for (Container container : containers) {
            volume += container.getVolume();
        }

        return volume;
    }

    /**
     * Get free volume.
     *
     * @return Free volume
     */
    public int getFreeVolume() {

        return getType().getVolume() - getContainersVolume();
    }

    public boolean canContainContainer(Container container) {

        final int volume = container.getVolume();
        final int max_volume = getType().getVolume();
        final int used_volume = getContainersVolume();

        return (used_volume + volume <= max_volume);
    }

    public boolean isContainershipCloseEnough(Containership containership) {

        return getDistance(containership) <= 300;
    }

    public boolean canMoveContainerTo(Container container, Containership containership) {

        return containership.canContainContainer(container) && isContainershipCloseEnough(containership);
    }

    public Map<String, Object> getData() {

        final Map<String, Object> data = new HashMap<>();

        data.put("captainName", getCaptainName());
        data.put("name", getName());
        data.put("position", getPosition());
        data.put("port", getPort().getDocumentReference());
        data.put("type", getType().getDocumentReference());

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

        return ContainerStore.allOfContainership(this);
    }

    /**
     * Get Firebase document path
     *
     * @return Document path
     */
    public String getDocumentPath() {

        return getDocumentPath(getId());
    }
}
