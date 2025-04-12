package com.phanduy.aliexscrap.model;

public class StoreInfo {
    private String sellerId;
    private String widgetId;
    private String moduleName;
    public String storeName;

    // Getters and Setters
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    // toString method
    @Override
    public String toString() {
        return "StoreInfo{" +
                "sellerId='" + sellerId + '\'' +
                ", widgetId='" + widgetId + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", storeName='" + storeName + '\'' +
                '}';
    }

}
