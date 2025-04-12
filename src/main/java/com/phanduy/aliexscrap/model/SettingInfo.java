package com.phanduy.aliexscrap.model;

import java.util.HashMap;
import java.util.Map;

public class SettingInfo {

    public static final String STORE_ID = "storeId";
    public static final String KEYWORD_LINK = "keywordLink";

    private Map<String, String> data;
    private Map<String, String> settings;

    public SettingInfo() {
        this.data = new HashMap<>();
        this.settings = new HashMap<>();
    }

    public Map<String, String> getData() {
        return data;
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    public String getStoreId() {
        return data.get(STORE_ID);
    }

    public String getKeywordLink() {
        return data.get(KEYWORD_LINK);
    }
}
