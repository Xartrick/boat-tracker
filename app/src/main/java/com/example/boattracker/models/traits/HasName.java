package com.example.boattracker.models.traits;

import com.example.boattracker.documents.IDocument;

public interface HasName extends IDocument {

    String NAME = "name";

    default String getName() {
        return (String) get(NAME);
    }

    default void setName(String name) {
        put(NAME, name);
    }
}
