/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex;

import com.models.aliex.crawl.AliexScriptSpecProp;

/**
 *
 * @author Admin
 */
public class ProductAttribute {
    
    public static final String BRAND_NAME = "Brand Name";
    public static final String MATERIAL = "Material";
    public static final String STYLE = "Style";
    public static final String FABRIC_STYLE = "Fabric Type";
    
    public String name;
    public int id;
    public String value;
    public String valueId;

    public ProductAttribute() {
    }
    
    public ProductAttribute(AliexScriptSpecProp aliexScriptSpecProp) {
        this.name = aliexScriptSpecProp.attrName;
        this.value = aliexScriptSpecProp.attrValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }
    
    public boolean isBrandName() {
        if(name == null) return false;
        
        String textCheck = name.trim().toLowerCase();
        return textCheck.contains("brandname") || textCheck.contains("brand name");
    }
    
    public void mergeValue(String val) {
        if(value == null) {
            value = val;
        } else {
            value = value + ", " + val;
        }
        
    }
}
