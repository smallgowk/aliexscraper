package com.phanduy.aliexscrap.model.request;

import com.phanduy.aliexscrap.utils.StringUtils;

public class GetItemsByCategoryReq extends BaseReqV3 {
    public String storeId;
    public String parentCatId;
    public String subCatId;
    public int parentCatIndex;
    public int subCatIndex;
    public String widgetId;
    public String moduleName;

    public GetItemsByCategoryReq(
            String diskSerialNumber,
            String storeId,
            String parentCatId,
            String subCatId,
            String widgetId,
            String moduleName,
            int parentCatIndex,
            int subCatIndex
    ) {
        this.diskSerialNumber = diskSerialNumber;
        this.storeId = storeId;
        this.parentCatId = parentCatId;
        this.subCatId = subCatId;
        this.widgetId = widgetId;
        this.moduleName = moduleName;
        this.parentCatIndex = parentCatIndex;
        this.subCatIndex = subCatIndex;
    }

    public String getCatFolder() {
        return StringUtils.isEmpty(subCatId) ? parentCatId : parentCatId + "_" + subCatId;
    }
}
