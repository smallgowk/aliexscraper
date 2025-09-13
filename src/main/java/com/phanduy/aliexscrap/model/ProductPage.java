package com.phanduy.aliexscrap.model;

import java.util.ArrayList;
import java.util.List;

public class ProductPage {
    private int pageNumber;
    private ArrayList<String> ids;
    
    public ProductPage() {}
    
    public ProductPage(int pageNumber, ArrayList<String> ids) {
        this.pageNumber = pageNumber;
        this.ids = ids;
    }
    
    public String getPageNumber() {
        return String.valueOf(pageNumber);
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
    
    @Override
    public String toString() {
        return "ProductPage{" +
                "pageNumber=" + pageNumber +
                ", ids=" + ids +
                '}';
    }
}
