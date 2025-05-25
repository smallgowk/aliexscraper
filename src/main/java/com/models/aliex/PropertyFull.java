/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex;

/**
 *
 * @author duyuno
 */
public class PropertyFull {

    private long propertyId;
    private String skuPropertyName;
    private long valueId;
//    private String valueName;
    private String valueDisplayName;
//    private String sku;
    private String shipFromCountry;
    private String imageUrl;
    public boolean showTypeColor;
    public boolean sizeProperty;
    public boolean color;
    public boolean size;
    public boolean shipFrom;
    public boolean shipFromUS;
    
    public PropertyFull() {
        
    }

    public long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    public long getValueId() {
        return valueId;
    }

    public void setValueId(long valueId) {
        this.valueId = valueId;
    }

    public String getValueDisplayName() {
        return valueDisplayName;
    }

    public void setValueDisplayName(String valueDisplayName) {
        this.valueDisplayName = valueDisplayName;
    }

//    public String getSku() {
//        return sku;
//    }
//
//    public void setSku(String sku) {
//        this.sku = sku;
//    }

    public String getShipFromCountry() {
        return shipFromCountry;
    }

    public void setShipFromCountry(String shipFromCountry) {
        this.shipFromCountry = shipFromCountry;
    }

    public String getPropertyName() {
        return skuPropertyName;
    }

    public void setPropertyName(String propertyName) {
        this.skuPropertyName = propertyName;
    }

//    public String getValueName() {
//        return valueName;
//    }
//
//    public void setValueName(String valueName) {
//        this.valueName = valueName;
//    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public boolean isColor() {
//        if (skuPropertyName != null) {
//            String name = skuPropertyName.toLowerCase();
//            if (name.contains("color") || name.contains("colour") || name.contains("plug") || name.contains("اللون")) {
//                return true;
//            }
//        }
        return color;
    }
    
    public boolean isSize() {
//        if (skuPropertyName != null) {
//            String name = skuPropertyName.toLowerCase();
//            if (name.contains("size")) {
//                return true;
//            }
//        }
        return size;
    }
    
    public boolean isShipFrom() {
//        if (skuPropertyName != null) {
//            String name = skuPropertyName.toLowerCase();
//            return name.contains("ships from") || name.contains("ship") || name.contains("سفينة");
//        }
        return shipFrom;
    }
    
    public boolean isShipFromUS() {
        return shipFromUS;
    }
}
