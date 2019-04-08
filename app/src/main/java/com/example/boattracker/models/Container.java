package com.example.boattracker.models;

import com.example.boattracker.documents.BaseDocument;
import com.example.boattracker.models.traits.HasId;
import com.example.boattracker.models.traits.HasVolume;

import java.util.HashMap;
import java.util.Map;

public class Container extends BaseDocument implements HasId, HasVolume {

    public final static String COLLECTION_NAME = "containers";

    private Containership containership;

    public Container(String id, int length, int height, int width) {

        setId(id);
        setVolume(length, height, width);
    }

    /**
     * Get Firebase document path
     *
     * @return Document path
     */
    public static String getDocumentPath(String id) {

        return "/" + COLLECTION_NAME + "/" + id;
    }

    public Map<String, Object> getData() {

        final Map<String, Object> data = new HashMap<>();

        data.put("length", getLength());
        data.put("width", getWidth());
        data.put("height", getHeight());

        if (containership != null) {
            data.put("containership", containership.getDocumentReference());
        }

        return data;
    }

    public Containership getContainership() {

        return containership;
    }

    public void setContainership(Containership containership) {

        this.containership = containership;
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
