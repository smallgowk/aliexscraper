/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Variation {
    public ArrayList<VariationProperty> propertyIdentifiers;
    public ArrayList<String> propertyValueIds;
    public String productId;
    public PriceUnit price;
    public PriceUnit discountedPrice;
    public PriceUnit bulkPrice;
    public String imageUrl;

    public ArrayList<VariationProperty> getPropertyIdentifiers() {
        return propertyIdentifiers;
    }

    public void setPropertyIdentifiers(ArrayList<VariationProperty> propertyIdentifiers) {
        this.propertyIdentifiers = propertyIdentifiers;
    }

    public ArrayList<String> getPropertyValueIds() {
        return propertyValueIds;
    }

    public void setPropertyValueIds(ArrayList<String> propertyValueIds) {
        this.propertyValueIds = propertyValueIds;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public PriceUnit getPrice() {
        return price;
    }

    public void setPrice(PriceUnit price) {
        this.price = price;
    }

    public PriceUnit getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(PriceUnit discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public PriceUnit getBulkPrice() {
        return bulkPrice;
    }

    public void setBulkPrice(PriceUnit bulkPrice) {
        this.bulkPrice = bulkPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public float getPriceValue() {
        return Float.parseFloat(price.getValue());
    }
    
    public int getColorPropertyValueId(AliexProductDetail aliexProductDetail) {
        if(propertyIdentifiers == null || propertyIdentifiers.isEmpty()) return -1;
        
        for(VariationProperty variationProperty : propertyIdentifiers) {
            
            if(aliexProductDetail.isColor(variationProperty.getPropertyId())) {
                return variationProperty.getPropertyValueId();
            }
        }
        
        return -1;
    }
}
