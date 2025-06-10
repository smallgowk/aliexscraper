/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.model.request;

/**
 *
 * @author PhanDuy
 */
public class GetPageDataRapidDataReq {
    public String storeId;
    public String sellerId;
    public String diskSerialNumber;
    public String currency;
    public String region;
    public String locale;
    public int page;

    public GetPageDataRapidDataReq(String storeId, String sellerId, String diskSerialNumber, String currency, String region, String locale, int page) {
        this.storeId = storeId;
        this.sellerId = sellerId;
        this.diskSerialNumber = diskSerialNumber;
        this.currency = currency;
        this.region = region;
        this.locale = locale;
        this.page = page;
    }
}
