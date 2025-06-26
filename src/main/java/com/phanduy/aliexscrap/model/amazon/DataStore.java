/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.model.amazon;

import java.util.HashMap;

/**
 *
 * @author PhanDuy
 */
public class DataStore {
    public static HashMap<String, HashMap<String, String>> mapProductData = new HashMap<String, HashMap<String, String>>();
    
    public static void putProductData(String sku, String fieldName, String value) {
        HashMap<String, String> mapProduct = mapProductData.get(sku);
        if (mapProduct == null) {
            mapProduct = new HashMap<>();
            mapProduct.put(fieldName, value);
            mapProductData.put(sku, mapProduct);
        } else {
            mapProduct.put(fieldName, value);
        }
    }
    
    public static String getValue(String sku, String fieldName) {
        HashMap<String, String> mapProduct = mapProductData.get(sku);
        if (mapProduct != null) {
            return mapProduct.get(fieldName);
        } else {
            return null;
        }
    }
    
    public static void clearData() {
        mapProductData.clear();
    }
}
