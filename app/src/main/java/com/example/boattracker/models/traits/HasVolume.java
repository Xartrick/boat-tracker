package com.example.boattracker.models.traits;

import com.example.boattracker.documents.IDocument;

public interface HasVolume extends IDocument {

    String LENGTH = "length";
    String HEIGHT = "height";
    String WIDTH = "width";

    /**
     * Get length
     *
     * @return Length
     */
    default int getLength() {

        final Integer length = (Integer) get(LENGTH);

        return length == null ? 0 : length;
    }

    /**
     * Set length
     *
     * @param length Length
     */
    default void setLength(int length) {

        put(LENGTH, length);
    }

    /**
     * Get height
     *
     * @return Height
     */
    default int getHeight() {

        final Integer height = (Integer) get(HEIGHT);

        return height == null ? 0 : height;
    }

    /**
     * Set height
     *
     * @param height Height
     */
    default void setHeight(int height) {

        put(HEIGHT, height);
    }

    /**
     * Get width
     *
     * @return Width
     */
    default int getWidth() {

        final Integer width = (Integer) get(WIDTH);

        return width == null ? 0 : width;
    }

    /**
     * Set width
     *
     * @param width Width
     */
    default void setWidth(int width) {

        put(WIDTH, width);
    }

    /**
     * Get volume.
     *
     * @return Volume
     */
    default int getVolume() {

        return getLength() * getHeight() * getWidth();
    }

    /**
     * Set volume (length, height and width)
     *
     * @param length Length
     * @param height Height
     * @param width  Width
     */
    default void setVolume(int length, int height, int width) {

        setLength(length);
        setHeight(height);
        setWidth(width);
    }
}
