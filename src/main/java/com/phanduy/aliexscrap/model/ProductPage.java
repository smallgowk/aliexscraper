package com.phanduy.aliexscrap.model;

import java.util.ArrayList;

public class ProductPage {
    private String signature;
    private int pageNumber;
    private ArrayList<String> ids;
    
    public ProductPage() {}
    
    public ProductPage(int pageNumber, ArrayList<String> ids) {
        this.pageNumber = pageNumber;
        this.ids = ids;
    }

    public ProductPage(String signature, int pageNumber, ArrayList<String> ids) {
        this.signature = signature;
        this.pageNumber = pageNumber;
        this.ids = ids;
    }

    public String getPageNumber() {
        return String.valueOf(pageNumber);
    }
    
    public int getPageNumberInt() {
        return pageNumber;
    }
    
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    public ArrayList<String> getIds() {
        return ids;
    }
    
    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "ProductPage{" +
                "signature=" + signature +
                "pageNumber=" + pageNumber +
                ", ids=" + ids +
                '}';
    }
}
