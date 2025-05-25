package com.phanduy.aliexscrap.model.request;

public class GetListProductByPageReq {
    public String storeId;
    public String sellerId;
    public String diskSerialNumber;
    public String currency;
    public String region;
    public String locale;
    public int page;

    public GetListProductByPageReq(String storeId, String sellerId, String diskSerialNumber, String currency, String region, String locale, int page) {
        this.storeId = storeId;
        this.sellerId = sellerId;
        this.diskSerialNumber = diskSerialNumber;
        this.currency = currency;
        this.region = region;
        this.locale = locale;
        this.page = page;
    }
}
