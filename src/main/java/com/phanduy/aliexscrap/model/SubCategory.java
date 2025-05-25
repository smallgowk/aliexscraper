package com.phanduy.aliexscrap.model;

import java.util.ArrayList;

public class SubCategory {
    private String id;
    private String name;
    private int index;
    public ArrayList<String> items;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    // toString method
    @Override
    public String toString() {
        return "SubCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", index=" + index +
                '}';
    }
}