package com.phanduy.aliexscrap.model.request;

import com.phanduy.aliexscrap.model.SettingInfo;

public class TransformRapidDataReqV3 extends BaseReqV3 {
    public String productId;
    public String storeName;
    public String keyword;
    public SettingInfo settingInfo;

    public TransformRapidDataReqV3(String productId, String storeName, String keyword, SettingInfo settingInfo, String diskSerialNumber) {
        this.productId = productId;
        this.storeName = storeName;
        this.keyword = keyword;
        this.settingInfo = settingInfo;
        this.diskSerialNumber = diskSerialNumber;
    }
}
