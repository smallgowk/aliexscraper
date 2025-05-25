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
public class VariationProperty {
    public int propertyId;
    public int propertyValueId;
    public String propertyValueName;

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getPropertyValueId() {
        return propertyValueId;
    }

    public void setPropertyValueId(int propertyValueId) {
        this.propertyValueId = propertyValueId;
    }

    public String getPropertyValueName() {
        return propertyValueName;
    }

    public void setPropertyValueName(String propertyValueName) {
        this.propertyValueName = propertyValueName;
    }
    
    public boolean isColor() {
        return propertyValueName.contains("color") || propertyValueName.contains("plug");
    }
    
    public boolean isShipFrom() {
        return propertyValueName != null && propertyValueName.contains("ship");
    }
}
