package com.example.boattracker.models.traits;

import com.example.boattracker.documents.IDocument;

public interface HasId extends IDocument {

    String ID = "id";

    /**
     * Get id
     *
     * @return Id
     */
    default String getId() {
        return (String) get(ID);
    }

    /**
     * Set id
     *
     * @param id Id
     */
    default void setId(String id) {
        put(ID, id);
    }
}
