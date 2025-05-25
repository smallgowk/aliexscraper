/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.request;

/**
 *
 * @author PhanDuy
 */
public class GetStoreInfoRapidDataReq {
    public String productId;
    public String diskSerialNumber;
    public String currency;
    public String region;
    public String locale;

    public GetStoreInfoRapidDataReq(String productId, String diskSerialNumber, String currency, String region, String locale) {
        this.productId = productId;
        this.diskSerialNumber = diskSerialNumber;
        this.currency = currency;
        this.region = region;
        this.locale = locale;
    }
}
