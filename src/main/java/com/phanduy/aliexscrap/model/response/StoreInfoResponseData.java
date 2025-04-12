package com.phanduy.aliexscrap.model.response;

import com.phanduy.aliexscrap.model.Category;
import com.phanduy.aliexscrap.model.StoreInfo;

import java.util.ArrayList;

public class StoreInfoResponseData {
    public StoreInfo storeInfo;
    public ArrayList<Category> categoryList;

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
