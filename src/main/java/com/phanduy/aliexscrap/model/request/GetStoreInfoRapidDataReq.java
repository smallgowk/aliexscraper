package com.phanduy.aliexscrap.model.request;

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
