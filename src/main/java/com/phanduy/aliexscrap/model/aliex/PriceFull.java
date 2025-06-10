/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.model.aliex;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class PriceFull {
    public String id;
    public PriceUnit originalPrice;
//    public PriceUnit discountedPrice;
//    public PriceUnit bulkPrice;
    
    public String skuImage;
    
    public ArrayList<PropertyFull> properties;
    
    public boolean hasShipFrom;
    public boolean hasVariation;
    public boolean shipFromUS;

    public PriceUnit getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(PriceUnit originalPrice) {
        this.originalPrice = originalPrice;
    }

    public ArrayList<PropertyFull> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<PropertyFull> properties) {
        this.properties = properties;
    }
    
    public float getPriceStandard() {
        String priceStr = originalPrice.getValue();
        return Float.parseFloat(priceStr);
    }

    public String getVariationName(int id, int valueId) {
        if (properties == null || properties.isEmpty()) {
            return null;
        }


        return null;
    }

    public String getSkuImage() {
        return skuImage;
    }

    public void setSkuImage(String skuImage) {
        this.skuImage = skuImage;
    }
    
    public boolean isHasVariation() {
        return hasVariation;
    }
}
