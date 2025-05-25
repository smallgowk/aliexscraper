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
public class SkuProperty {
    
    public static final int TYPE_COLOR = 1;
    public static final int TYPE_SIZE = 2;
    
    public int type;
    
    public int propertyId;
    public String propertyName;
    public ArrayList<SkuValue> values;
    
    public boolean isShipFrom() {
        return propertyName != null && propertyName.toLowerCase().contains("ship");
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public ArrayList<SkuValue> getValues() {
        return values;
    }

    public void setValues(ArrayList<SkuValue> values) {
        this.values = values;
    }
    
    
    
}
