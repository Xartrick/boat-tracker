package com.example.boattracker.models.traits;

import com.example.boattracker.documents.IDocument;

public interface HasName extends IDocument {

    String NAME = "name";

    /**
     * Get name
     *
     * @return Name
     */
    default String getName() {
        return (String) get(NAME);
    }

    /**
     * Set name
     *
     * @param name Name
     */
    default void setName(String name) {
        put(NAME, name);
    }
}
