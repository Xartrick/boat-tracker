package com.example.boattracker.documents;

public interface IDocument {

    void put(String key, Object value);
    Object get(String key);
}
