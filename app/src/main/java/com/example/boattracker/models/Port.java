package com.example.boattracker.models;

import com.example.boattracker.documents.BaseDocument;
import com.example.boattracker.models.traits.HasId;
import com.example.boattracker.models.traits.HasPosition;
import com.example.boattracker.models.traits.HasName;

public class Port extends BaseDocument implements HasId, HasName, HasPosition {

    public Port(String id, String name, double latitude, double longitude) {
        this.setId(id);
        this.setName(name);
        this.setPosition(latitude, longitude);
    }

    /**
     * Get Firebase document path
     *
     * @return Document path
     */
    public String getDocumentPath() {
        return "/ports/" + this.getId();
    }
}
