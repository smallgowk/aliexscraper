package com.phanduy.aliexscrap.model;

import java.util.ArrayList;

public class Category {
    private String id;
    private String name;
    private int index;
    private ArrayList<SubCategory> subList;

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

    public ArrayList<SubCategory> getSubList() {
        return subList;
    }

    public void setSubList(ArrayList<SubCategory> subList) {
        this.subList = subList;
    }

    // toString method
    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", index=" + index +
                '}';
    }
}