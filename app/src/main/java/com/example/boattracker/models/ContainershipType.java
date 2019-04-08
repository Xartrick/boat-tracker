package com.example.boattracker.models;

import com.example.boattracker.documents.BaseDocument;
import com.example.boattracker.models.traits.HasId;
import com.example.boattracker.models.traits.HasName;
import com.example.boattracker.models.traits.HasVolume;

public class ContainershipType extends BaseDocument implements HasId, HasName, HasVolume {

    public final static String COLLECTION_NAME = "containership-types";

    public ContainershipType(String id, String name, int length, int height, int width) {

        setId(id);
        setName(name);
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

    /**
     * Get Firebase document path
     *
     * @return Document path
     */
    public String getDocumentPath() {

        return getDocumentPath(getId());
    }
}
