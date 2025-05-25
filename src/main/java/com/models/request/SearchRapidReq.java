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
public class SearchRapidReq {
    public String query;
    public String diskSerialNumber;
    public String currency;
    public String region;
    public String locale;
    public int page;

    public SearchRapidReq(String query, String diskSerialNumber, String currency, String region, String locale, int page) {
        this.query = query;
        this.diskSerialNumber = diskSerialNumber;
        this.currency = currency;
        this.region = region;
        this.locale = locale;
        this.page = page;
    }
}
