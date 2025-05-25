/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.store.inputdata;

import com.utils.StringUtils;
import java.net.URI;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 *
 * @author duyuno
 */
public class BaseStoreOrderInfo {
    public String link;
    public String product_type;
    public String brand_name;
    public String bullet_points;
    public String category;
    public String acc_no;
    public String status;
    public String storeSign;
    public int pageTotal;
    public String region;
    public String productId = null;
    public String storeId = null;
    public String query = null;

    public String main_key;
    public String tip;
    public String reasons;
    public String description;
    
    public boolean isCrawled;
    
    public boolean hasInfo() {
        return link != null;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBullet_points() {
        return bullet_points;
    }

    public void setBullet_points(String bullet_points) {
        this.bullet_points = bullet_points;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStoreSign() {
        return storeSign;
    }

    public void setStoreSign(String storeSign) {
        this.storeSign = storeSign;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setStoreId(String storeId) {
        if (!StringUtils.isEmpty(storeId)) {
            this.storeId = storeId;
            this.storeSign = storeId;
        }
    }
    
    public void setQuery(String query) {
        if (query == null) return;
        this.query = query;
        this.storeSign = query.replaceAll(Pattern.quote(" "), "_");
    }
    
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void initStoreSign() {
        if (!StringUtils.isEmpty(storeSign) || StringUtils.isEmpty(link) || !StringUtils.isEmpty(query)) return;
              
        URI uri = URI.create(link);

        String path = uri.getPath();

        String pageId = getStoreIdFromPath(path);
        
        String searchKey = null;
        String query = uri.getQuery();

        if (query != null && !query.isEmpty()) {
            String[] queryParts = query.split(Pattern.quote("&"));
            for (String s : queryParts) {
                String[] paramsParts = s.split(Pattern.quote("="));
                if (paramsParts[0].equals("SearchText") && paramsParts.length == 2) {
                    searchKey = paramsParts[1].trim();
                }
            }
        }

        if (searchKey != null) {
            searchKey = searchKey.replaceAll(Pattern.quote(" "), "_");
        }
        
        storeSign = searchKey != null ? pageId +"_" + searchKey : pageId;
    }
    
    private String getStoreIdFromPath(String path) {
        if (path.contains(".html")) {
            path = path.substring(0, path.indexOf(".html"));
        }
        String[] parts = path.split(Pattern.quote("/"));
        for (String part : parts) {
            if (StringUtils.isOnlyNumberWord(part)) {
                return part;
            }
        }
        return null;
    }

    public String getMain_key() {
        return main_key;
    }

    public void setMain_key(String main_key) {
        this.main_key = main_key;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static BaseStoreOrderInfo buildTestData() {
        BaseStoreOrderInfo store = new BaseStoreOrderInfo();
        store.setAcc_no("ShangDeLi");
        store.setCategory("tools");
        store.setProduct_type("tools");
        store.setDescription("Here is all for your need- Best Price and Quality for a \"{tittle}\" <br />\n" +
                "{Tips}<br />\n" +
                "{Productdescription} <br />\n" +
                "{Productspecific}<br />\n" +
                "{reason}<br />\n" +
                "</br>★★★Don't hesitate, Scroll to the top now and click Add to Cart to take this amazing product today. !");
        store.setStoreSign("TestStore");
//        store.setBullet_points("✅ Bullet Point 01 [{searchterm1}]\n" +
//"\n" +
//"✅ Bullet Point 02 [{searchterm2}]\n" +
//"\n" +
//"✅ Bullet Point 03 [{searchterm3}]\n" +
//"\n" +
//"✅ Bullet Point 04 [{searchterm4}]\n" +
//"\n" +
//"✅ Bullet Point 05 [{searchterm5}]\"");

        store.setBullet_points("THE BEST PRICE & QUALITY: Reasonable price with excellent quality, saves you hundreds of dollars from car dealership.{tittle} \n" +
                "\n" +
                "The car & motorcycle accessorries category include: Protective Gear, Frames & Fittings, Brakes, Engines & Engine Parts, Exhaust & Exhaust Systems, Fuel Supply, Ingition, Seat Covers, Electrical System, Wheels & Rims, Bumpers & Chassis Filters, Bags & Luggage.\n" +
                "\n" +
                "FREE SHIPPING: 90% conventional orders will be delivered within 10-15 days. Please make sure to buy this product from our store. Other brand is not reliable.\n" +
                "\n" +
                "LIFETIME WARRENTY: Place order with 100% confidence, we provide 1 click refund for our valued customers in 3month. Please feel free to contact us if you have any questions and we are always stand by your side to issue your problems \n" +
                "\n" +
                "Don't hesitate, Scroll to the top now and click Add to Cart to take this amazing product today!!! ");
        store.setReasons("<br />Why should you choose our products: <br />" +
                "<br />Best Feedback from Customer<br />Best Customer Service for our product<br />Free shipping and returns<br />100% SATISFACTION GUARANTEE- If any quality problems with the our product, please feel free to contact us, we will FULLY REFUND or provide a new product, we always ensure 100% customer's satisfaction !<br />");
        store.setBrand_name("NHI");
        return store;
    }
}
