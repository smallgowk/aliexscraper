package com.phanduy.aliexscrap.model.response;

import com.phanduy.aliexscrap.config.Configs;
import com.phanduy.aliexscrap.controller.DownloadManager;
import com.phanduy.aliexscrap.model.amazon.NewProduct;
import com.phanduy.aliexscrap.model.amazon.ProductAmz;
import com.phanduy.aliexscrap.utils.StringUtils;
import java.util.ArrayList;

public class TransformCrawlResponse {
    public String productId;
    public ProductAmz baseAmzProduct;
    public float origiPrice;
    public float promotionRate;
    public float shippingPrice;
    public ArrayList<ProductAmz> childs;
    public String htmlDescription;
    public ArrayList<String> bulletPoints;
    public String htmlSpecifics;
    public boolean showReason;
    public boolean showTip;
    public BulletPointSearchTerm bulletPointSearchTerm;
    public ArrayList<String> imagePaths;
    
    public String id;
    public String brand;
    public String product_name;
    public String dealType;
    public String shipFrom;
    public String mainImage;
    public String main_image;
    public String image_2;
    public String image_3;
    public String image_4;
    public String image_5;
    public String image_6;
    public String image_7;
    public String image_8;
    public String image_9;
    public ArrayList<NewProduct> listProducts;

    public TransformCrawlResponse() {
    }
    
    public TransformCrawlResponse(String productId) {
        this.productId = productId;
    }

    public ProductAmz getBaseAmzProduct() {
        return baseAmzProduct;
    }

    public void setBaseAmzProduct(ProductAmz baseAmzProduct) {
        this.baseAmzProduct = baseAmzProduct;
    }

    public float getOrigiPrice() {
        return origiPrice;
    }

    public void setOrigiPrice(float origiPrice) {
        this.origiPrice = origiPrice;
    }

    public float getPromotionRate() {
        return promotionRate;
    }

    public void setPromotionRate(float promotionRate) {
        this.promotionRate = promotionRate;
    }

    public float getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(float shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public ArrayList<ProductAmz> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<ProductAmz> childs) {
        this.childs = childs;
    }

    public String getHtmlDescription() {
        return htmlDescription;
    }

    public void setHtmlDescription(String htmlDescription) {
        this.htmlDescription = htmlDescription;
    }

    public String getHtmlSpecifics() {
        return htmlSpecifics;
    }

    public void setHtmlSpecifics(String htmlSpecifics) {
        this.htmlSpecifics = htmlSpecifics;
    }

    public boolean isShowReason() {
        return showReason;
    }

    public void setShowReason(boolean showReason) {
        this.showReason = showReason;
    }

    public boolean isShowTip() {
        return showTip;
    }

    public void setShowTip(boolean showTip) {
        this.showTip = showTip;
    }

    public BulletPointSearchTerm getBulletPointSearchTerm() {
        return bulletPointSearchTerm;
    }

    public void setBulletPointSearchTerm(BulletPointSearchTerm bulletPointSearchTerm) {
        this.bulletPointSearchTerm = bulletPointSearchTerm;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public void updateImageUrl(String field, String url) {
        if (url == null || url.isEmpty()) return;
        DownloadManager.getInstance().put(getKeyForImage(productId, field, url), url);
    }
    
    public String getKeyForImage(String productId, String field, String url) {
        return productId + field + Math.abs(url.hashCode());
    }
    
    public void updateImageDownloads() {
        if (Configs.downloadAllImage != 1) {
            return;
        }
        updateImageUrl("Main", main_image);
        updateImageUrl("1",image_2);
        updateImageUrl("2",image_3);
        updateImageUrl("3",image_4);
        updateImageUrl("4",image_5);
        updateImageUrl("5",image_6);
        updateImageUrl("6",image_7);
        updateImageUrl("7",image_8);
        updateImageUrl("8",image_9);
        
        if (listProducts != null) {
            for (NewProduct newProduct : listProducts) {
                if (!StringUtils.isEmpty(newProduct.getImageName())) {
                    updateImageUrl(newProduct.getImageName(), newProduct.property_value_1_image);
                }
            }
        }
    }
    
    public boolean hasData() {
        return (baseAmzProduct != null && baseAmzProduct.aliexId != null) ||
                (listProducts != null && !listProducts.isEmpty());
    }
}
