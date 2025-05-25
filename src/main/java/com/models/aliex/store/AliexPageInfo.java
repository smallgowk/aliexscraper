/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.store;

/**
 *
 * @author duyuno
 */
public class AliexPageInfo {
    
    public String storeSign;
    
    public int pageIndex;
    public int totalProduct;
//    public ArrayList<AliexProductFull> listItems;

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    
    
    public String getStoreSign() {
        return storeSign;
    }

    public void setStoreSign(String storeSign) {
        this.storeSign = storeSign;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

//    public ArrayList<AliexProductFull> getListItems() {
//        return listItems;
//    }
//
//    public void setListItems(ArrayList<AliexProductFull> listItems) {
//        this.listItems = listItems;
//    }
//    
    
}
