package com.example.boattracker.models;

import com.example.boattracker.documents.BaseDocument;
import com.example.boattracker.models.traits.HasId;
import com.example.boattracker.models.traits.HasVolume;

public class Container extends BaseDocument implements HasId, HasVolume {

    public static String COLLECTION_NAME = "containers";

    public Container(String id, int length, int height, int width) {
        this.setId(id);
        this.setVolume(length, height, width);
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
