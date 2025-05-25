/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex;

import com.config.Configs;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class AliexOriginalInfo {
    public String id;
    public String status;
    public float shippingPrice;
    public String descriptionHtml;
    public ArrayList<Variation> variation;
    public AliexProductDetail detail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getProductDetailUrl() {
        return detail.getDetailUrl();
    }

    public float getProductPrice() {
       float promotionRate = getPromotionRate();
       return (detail.getProductPrice() * (1- promotionRate) + getShippingPrice()) * (Configs.priceRate - promotionRate);
    }
    
    public float getPromotionRate() {
        return detail.getPromotionRate();
    }
    
    public float getShippingPrice() {
        return shippingPrice;
    }
    
    public float getUPSShippringPrice() {
//        for(Variation var : variation) {
//            if(var.)
//        }
        return 5;
    }

    public void setShippingPrice(float shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    public ArrayList<Variation> getVariation() {
        return variation;
    }

    public void setVariation(ArrayList<Variation> variation) {
        this.variation = variation;
    }

    public AliexProductDetail getDetail() {
        return detail;
    }

    public void setDetail(AliexProductDetail detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    public boolean isHasVariation() {
        for(Variation var : variation) {
            if(!var.getPropertyIdentifiers().isEmpty())  {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isHasInfo() {
        if(detail == null) return false;
        
        return detail.isHasInfo();
    }
    
    public float getProductPrice(float priceRate) {
       float promotionRate = getPromotionRate();
       return (detail.getProductPrice() * (1- promotionRate) + getShippingPrice()) * (priceRate - promotionRate);
    }
}
