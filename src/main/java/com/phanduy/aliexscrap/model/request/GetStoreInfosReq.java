package com.phanduy.aliexscrap.model.request;

public class GetStoreInfosReq extends BaseReqV3{
    public String storeId;
    public String keywordLink;
    public boolean useCache;

    public GetStoreInfosReq(String storeId, String keywordLink, String diskSerialNumber) {
        this.storeId = storeId;
        this.keywordLink = keywordLink;
        this.diskSerialNumber = diskSerialNumber;
    }
}
