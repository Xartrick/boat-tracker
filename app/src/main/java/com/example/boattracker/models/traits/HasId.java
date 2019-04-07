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

    /**
     * Check if model is model
     *
     * @param model Model
     * @return True if model is model, False otherwise
     */
    default boolean is(HasId model) {
        return this.getId().equals(model.getId());
    }
}
