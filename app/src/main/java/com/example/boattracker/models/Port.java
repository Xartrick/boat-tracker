package com.example.boattracker.models;

import com.example.boattracker.documents.BaseDocument;
import com.example.boattracker.models.traits.HasId;
import com.example.boattracker.models.traits.HasName;
import com.example.boattracker.models.traits.HasPosition;

public class Port extends BaseDocument implements HasId, HasName, HasPosition {

    public final static String COLLECTION_NAME = "ports";

    public Port(String id, String name, double latitude, double longitude) {

        setId(id);
        setName(name);
        setPosition(latitude, longitude);
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
