package com.example.boattracker.models;

import com.example.boattracker.documents.BaseDocument;
import com.example.boattracker.models.traits.HasId;
import com.example.boattracker.models.traits.HasName;
import com.example.boattracker.models.traits.HasVolume;

public class ContainershipType extends BaseDocument implements HasId, HasName, HasVolume {

    public ContainershipType(String id, String name, int length, int height, int width) {
        this.setId(id);
        this.setName(name);
        this.setVolume(length, height, width);
    }

    /**
     * Get Firebase document path
     *
     * @return Document path
     */
    public String getDocumentPath() {
        return "/containership-types/" + this.getId();
    }
}
