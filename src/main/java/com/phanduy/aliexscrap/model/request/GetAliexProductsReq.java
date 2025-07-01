package com.phanduy.aliexscrap.model.request;

public class GetAliexProductsReq {
    public String diskSerialNumber;
    public String signature;
    public String pageNumber;

    public GetAliexProductsReq(String diskSerialNumber, String signature, String pageNumber) {
        this.diskSerialNumber = diskSerialNumber;
        this.signature = signature;
        this.pageNumber = pageNumber;
    }
}
