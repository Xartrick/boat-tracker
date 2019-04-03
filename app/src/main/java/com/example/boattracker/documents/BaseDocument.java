package com.example.boattracker.documents;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseDocument implements IDocument, Serializable {

    private final Map<String, Object> entries;

    protected BaseDocument() {
        this.entries = new HashMap<>();
    }

    @Override
    public final void put(String key, Object value) {
        this.entries.put(key, value);
    }

    @Override
    public final Object get(String key) {
        return this.entries.get(key);
    }
}
