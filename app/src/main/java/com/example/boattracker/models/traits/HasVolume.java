package com.example.boattracker.models.traits;

import com.example.boattracker.documents.IDocument;

public interface HasVolume extends IDocument {

    String LENGTH = "length";
    String HEIGHT = "height";
    String WIDTH = "width";

    default int getLength() {
        final Integer length = (Integer) get(LENGTH);

        return length == null ? 0 : length;
    }

    default void setLength(int length) {
        put(LENGTH, length);
    }

    default int getHeight() {
        final Integer height = (Integer) get(HEIGHT);

        return height == null ? 0 : height;
    }

    default void setHeight(int height) {
        put(HEIGHT, height);
    }

    default int getWidth() {
        final Integer width = (Integer) get(WIDTH);

        return width == null ? 0 : width;
    }

    default void setWidth(int width) {
        put(WIDTH, width);
    }

    default int getVolume() {
        return this.getLength() * this.getHeight() * this.getWidth();
    }

    default void setVolume(int length, int height, int width) {
        this.setLength(length);
        this.setHeight(height);
        this.setWidth(width);
    }
}
