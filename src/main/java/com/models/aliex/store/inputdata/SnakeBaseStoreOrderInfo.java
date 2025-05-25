/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.store.inputdata;

/**
 *
* @author duyuno
 */
public class SnakeBaseStoreOrderInfo extends BaseStoreOrderInfo{
    public String main_key;
    public String tip;
    public String reasons;
    public String description;
    
    public static SnakeBaseStoreOrderInfo buildTestData() {
        SnakeBaseStoreOrderInfo store = new SnakeBaseStoreOrderInfo();
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
    
    
    
}
