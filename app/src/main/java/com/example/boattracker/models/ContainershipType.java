package com.example.boattracker.models;

public class ContainershipType {
    private String id;
    private String name;
    private int length;
    private int heigth;
    private int width;

    public ContainershipType(String id, String name, int length, int heigth, int width) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.heigth = heigth;
        this.width = width;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getHeigth() {
        return heigth;
    }

    public int getWidth() {
        return width;
    }
}
