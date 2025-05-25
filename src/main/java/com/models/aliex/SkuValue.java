/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex;

/**
 *
 * @author Admin
 */
public class SkuValue {
    public String propertyValueName;
    public String propertyValueDisplayName;
    public int propertyValueId;
    public String imageUrl;

    public String getPropertyValueName() {
        return propertyValueName;
    }

    public void setPropertyValueName(String propertyValueName) {
        this.propertyValueName = propertyValueName;
    }

    public String getPropertyValueDisplayName() {
        return propertyValueDisplayName;
    }

    public void setPropertyValueDisplayName(String propertyValueDisplayName) {
        this.propertyValueDisplayName = propertyValueDisplayName;
    }

    public int getPropertyValueId() {
        return propertyValueId;
    }

    public void setPropertyValueId(int propertyValueId) {
        this.propertyValueId = propertyValueId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    
}
