package com.example.boattracker.models.traits;

import com.example.boattracker.documents.IDocument;

public interface HasId extends IDocument {

    String ID = "id";

    default String getId() {
        return (String) get(ID);
    }

    default void setId(String id) {
        put(ID, id);
    }
}
