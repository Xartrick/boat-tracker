package com.example.boattracker.models;

import com.example.boattracker.documents.BaseDocument;
import com.example.boattracker.models.traits.HasId;
import com.example.boattracker.models.traits.HasVolume;

public class Container extends BaseDocument implements HasId, HasVolume {

    public static String COLLECTION_NAME = "containers";

    private Containership containership;

    public Container(String id, int length, int height, int width) {
        this.setId(id);
        this.setVolume(length, height, width);
    }

    public Containership getContainership() {
        return this.containership;
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
