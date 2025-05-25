/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex;

import com.models.amazon.DataStore;
import com.utils.MarketUtil;
import com.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class AliexProductFull {

    public String id;
    public String categoryId;
    public String title;
    public ArrayList<String> productImages;
    public ArrayList<ProductAttribute> attributes;
    public ArrayList<PriceFull> prices;
    public String htmlDescription;
    
    private float promotionRate;
    private float shippingPrice;
    private float originPrice;
    
    private boolean isHasShipFrom;
    private boolean isHasShipFromUS;
    
    public int pageIndex;
    public String storeSign;
    public String shipingMethod;
    public ArrayList<String> listSearchTerms;
    public String descriptionUrl;

    public String generic_keywords;
    public String description;
    public String item_sku;
    public String material_type;
    public String target_audience_keywords;
    public String target_audience_keywords1;
    
    public String bullet_point1;
    public String bullet_point2;
    public String bullet_point3;
    public String bullet_point4;
    public String bullet_point5;

    public AliexProductFull() {
    }

    public String getStoreSign() {
        return storeSign;
    }

    public void setStoreSign(String storeSign) {
        this.storeSign = storeSign;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getProductImages() {
        return productImages;
    }

    public void setProductImages(ArrayList<String> productImages) {
        this.productImages = productImages;
    }

    public ArrayList<ProductAttribute> getAttributes() {
        return attributes;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public float getPromotionRate() {
        return promotionRate;
    }

    public ArrayList<PriceFull> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<PriceFull> prices) {
        this.prices = prices;
    }

    public ArrayList<String> getListSearchTerms() {
        return listSearchTerms;
    }

    public void setListSearchTerms(ArrayList<String> listSearchTerms) {
        this.listSearchTerms = listSearchTerms;
    }

    

    public String getHtmlDescription() {
        return htmlDescription;
    }

    public void setHtmlDescription(String htmlDescription) {
        this.htmlDescription = htmlDescription;
    }
    
    public String getBranName() {
        if(attributes == null || attributes.isEmpty()) return null;
        
        for(ProductAttribute productAttribute : attributes) {
            if(productAttribute.isBrandName()) {
                return productAttribute.getValue();
            }
        }
        
        return null;
    }
    
    public void addBrandName(String brandName) {
        if(attributes == null) {
            attributes = new ArrayList<>();
        }
        
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setName("Brand Name");
        productAttribute.setValue(brandName);
        attributes.add(productAttribute);
    }
    
    public void repairVariationImage(HashMap<String, String> hashmapProperties) {
        
        if(prices == null || prices.isEmpty() || hashmapProperties == null) return;
        
        for(PriceFull priceFull : prices) {
            if(priceFull.getProperties() != null) {
                for(PropertyFull propertyFull : priceFull.getProperties()) {
                    if(hashmapProperties.containsKey("" + propertyFull.getValueId())) {
                        priceFull.setSkuImage(MarketUtil.getVariationImageUrl(hashmapProperties.get("" + propertyFull.getValueId())));
                    } else if(propertyFull.getValueDisplayName() != null && hashmapProperties.containsKey("" + propertyFull.getValueDisplayName().toLowerCase())){
                        priceFull.setSkuImage(MarketUtil.getVariationImageUrl(hashmapProperties.get("" + propertyFull.getValueDisplayName().toLowerCase())));
                    }
                }
            }
        }
    }

    public float getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(float shippingPrice) {
        this.shippingPrice = shippingPrice;
    }
    
    public boolean isHasVarition() {
        if (prices == null || prices.isEmpty()) {
            return false;
        }

        if (prices.size() == 1) {
            PriceFull priceFull = prices.get(0);
            return priceFull.isHasVariation();
        }

        for (PriceFull priceFull : prices) {
            if (priceFull.isHasVariation()) {
                return true;
            }
        }

        return false;
    }
    
    public float getFirstPrice() {
        return originPrice;
//        if (prices == null || prices.isEmpty()) {
//            return -1;
//        }
//
//        String priceStr = prices.get(0).getOriginalPrice().getValue();
//
//        return Float.parseFloat(priceStr);
    }
    
    public boolean isHasShipFrom() {
        return isHasShipFrom;
    }

    public boolean isHasShipFromUS() {
        return isHasShipFromUS;
    }
    
    public float getProductPrice(float price, float priceRate) {
//        float promotionRate = getPromotionRate();
//        return (price * (1 - promotionRate) + getShippingPrice()) * (priceRate - promotionRate);
//        System.out.println("============");
//        System.out.println("Price for " + id);
//        System.out.println("ori_price: " + price + " promotionRate: " + promotionRate + " => Price after discount: " + (price * (1 - promotionRate)));
//        System.out.println("shipping: " + shippingPrice);
//        System.out.println("priceRate: " + priceRate);
//        System.out.println("Lastest price ((price * (1 - promotionRate) + shippingPrice) * (priceRate)): " + (price * (1 - promotionRate) + shippingPrice) * (priceRate));
//        System.out.println("=============");
        return (price * (1 - promotionRate) + shippingPrice) * (priceRate);
    }
    
    public void addImageUrls(ArrayList<String> images) {
        if (images == null || images.isEmpty()) return;
        if (productImages == null) {
            productImages = new ArrayList<>();
        }
        
        if (productImages.size() == 9) return;
        
        for (String image : images) {
            if (!StringUtils.isEmpty(image)) {
                productImages.add(image);
                if (productImages.size() == 9) {
                    return;
                }
            }
        }
    }

    public void setMaterial_type(String material_type) {
        this.material_type = material_type;
        DataStore.putProductData(item_sku, "material_type", material_type);
    }
    
    public void setTarget_audience_keywords(String target_audience_keywords) {
        this.target_audience_keywords = target_audience_keywords;
        DataStore.putProductData(item_sku, "target_audience_keywords", target_audience_keywords);
    }
    
    public void setTarget_audience_keywords1(String target_audience_keywords1) {
        this.target_audience_keywords1 = target_audience_keywords1;
        DataStore.putProductData(item_sku, "target_audience_keywords1", target_audience_keywords1);
    }
    
    public void setBulletPoints(ArrayList<String> listBulletPoints) {
        if (listBulletPoints != null && !listBulletPoints.isEmpty()) {
            if (listBulletPoints.size() > 0) {
                setBullet_point1(listBulletPoints.get(0));
                if (listBulletPoints.size() > 1) {
                    setBullet_point2(listBulletPoints.get(1));
                    if (listBulletPoints.size() > 2) {
                        setBullet_point3(listBulletPoints.get(2));
                        if (listBulletPoints.size() > 3) {
                            setBullet_point4(listBulletPoints.get(3));
                            if (listBulletPoints.size() > 4) {
                                setBullet_point5(listBulletPoints.get(4));
                            }
                        }
                    }
                }
            }
        }
    }
    
    public String getBullet_point1() {
        return bullet_point1;
    }

    public void setBullet_point1(String bullet_point1) {
        this.bullet_point1 = bullet_point1;
        DataStore.putProductData(item_sku, "bullet_point1", bullet_point1);
    }

    public String getBullet_point2() {
        return bullet_point2;
    }

    public void setBullet_point2(String bullet_point2) {
        this.bullet_point2 = bullet_point2;
        DataStore.putProductData(item_sku, "bullet_point2", bullet_point2);
    }

    public String getBullet_point3() {
        return bullet_point3;
    }

    public void setBullet_point3(String bullet_point3) {
        this.bullet_point3 = bullet_point3;
        DataStore.putProductData(item_sku, "bullet_point3", bullet_point3);
    }

    public String getBullet_point4() {
        return bullet_point4;
    }

    public void setBullet_point4(String bullet_point4) {
        this.bullet_point4 = bullet_point4;
        DataStore.putProductData(item_sku, "bullet_point4", bullet_point4);
    }

    public String getBullet_point5() {
        return bullet_point5;
    }

    public void setBullet_point5(String bullet_point5) {
        this.bullet_point5 = bullet_point5;
        DataStore.putProductData(item_sku, "bullet_point5", bullet_point5);
    }
    
    public void removeBrandNameInfo() {
        String brandName = getBranName();
        title = StringUtils.removeWord(title, brandName);
        generic_keywords = StringUtils.removeWord(generic_keywords, brandName);
        description = StringUtils.removeWord(description, brandName);
        bullet_point1 = StringUtils.removeWord(bullet_point1, brandName);
        bullet_point2 = StringUtils.removeWord(bullet_point2, brandName);
        bullet_point3 = StringUtils.removeWord(bullet_point3, brandName);
        bullet_point4 = StringUtils.removeWord(bullet_point4, brandName);
        bullet_point5 = StringUtils.removeWord(bullet_point5, brandName);
        material_type = StringUtils.removeWord(material_type, brandName);
        
        DataStore.putProductData(item_sku, "item_name", title);
        DataStore.putProductData(item_sku, "generic_keywords", generic_keywords);
        DataStore.putProductData(item_sku, "product_description", description);
        DataStore.putProductData(item_sku, "bullet_point1", bullet_point1);
        DataStore.putProductData(item_sku, "bullet_point2", bullet_point2);
        DataStore.putProductData(item_sku, "bullet_point3", bullet_point3);
        DataStore.putProductData(item_sku, "bullet_point4", bullet_point4);
        DataStore.putProductData(item_sku, "bullet_point5", bullet_point5);
        DataStore.putProductData(item_sku, "material_type", material_type);
    }
}
