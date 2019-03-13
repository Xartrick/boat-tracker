package com.example.boattracker.models;

public class Container {
    private String id;
    private int length;
    private int heigth;
    private int width;

    public Container(String id, int length, int heigth, int width) {
        this.id = id;
        this.length = length;
        this.heigth = heigth;
        this.width = width;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
