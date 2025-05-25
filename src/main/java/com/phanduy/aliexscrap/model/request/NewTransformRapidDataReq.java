package com.phanduy.aliexscrap.model.request;

import java.util.Map;

public class NewTransformRapidDataReq {
    public String diskSerialNumber;
    public String id;
    public String storeId;
    public Map<String, String> settings;
    public Map<String, String> data;

    public NewTransformRapidDataReq(String diskSerialNumber, String id, String storeId, Map<String, String> settings, Map<String, String> data) {
        this.diskSerialNumber = diskSerialNumber;
        this.id = id;
        this.storeId = storeId;
        this.settings = settings;
        this.data = data;
    }
}
