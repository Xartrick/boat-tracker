package com.example.boattracker.models;

import com.example.boattracker.documents.BaseDocument;
import com.example.boattracker.models.traits.HasId;
import com.example.boattracker.models.traits.HasLocalization;
import com.example.boattracker.models.traits.HasName;

public class Port extends BaseDocument implements HasId, HasName, HasLocalization {

    public Port(String id, String name, double latitude, double longitude) {
        this.setId(id);
        this.setName(name);
        this.setLocalization(latitude, longitude);
    }
}
