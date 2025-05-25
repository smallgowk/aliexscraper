package com.phanduy.aliexscrap.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingInfo {

    public static final String STORE_ID = "storeId";
    public static final String KEYWORD_LINK = "keywordLink";
    public static final String KEYWORD = "keyword";
    public static final String PRODUCT_ID = "product_id";
    public static final String REGION = "region";

    public static final String PRICE_LIMIT = "price_limit";
    public static final String PRICE_RATE = "price_rate";
    public static final String FEATURES = "features";
    public static final String SHIPPING_METHODS = "shipping_methods";
    public static final String MAX_ROW = "max_row";
    public static final String VPS_IP = "vps_ip";
    public static final String TEMPLATE = "template";
    public static final String BRAND_NAME = "brand_name";
    public static final String MAIN_KEY = "main_key";
    public static final String PRODUCT_TYPE = "product_type";
    public static final String ITEM_TYPE = "item_type";
    public static final String AUDIENCE_KEYWORD = "audience_keyword";
    public static final String DEPARTMENT = "department";

    public static final String ONLY_SHIP_US = "ship_us";
    public static final String IMAGE_FROM_DES = "des_images";
    public static final String BUILD_DES_BY_AI = "ai";
    public static final String NO_SHIP_FOUND = "no_ship_found";
    public static final String IMAGE_DOWNLOAD = "image_download";
    public static final String SHIP_EPACKET = "ePacket";
    public static final String SHIP_ALIEXPRESS = "AliStandard";
    public static final String SHIP_ALIEXDIRECT = "AliDirect";
    public static final String LOCALE = "locale";
    public static final String TIP_LENGTH = "tip_length";
    public static final String REASON_LENGTH = "reason_length";
    public static final String DES_LENGTH = "des_length";

    public static final String TEMP_DESCRIPTION = "description";
    public static final String TEMP_TIPS = "tips";
    public static final String TEMP_REASONS = "reasons";
    public static final String TEMP_BULLETS = "bullet_points";

    public static final String TITLE_KEY = "{tittle}";
    public static final String BRANDNAME_KEY = "{brandname}";
    public static final String MAIN_KEYWORD_KEY = "{mainkeyword}";
    //    public static final String GROUP_TYPE_KEY = "{groupType}";
    public static final String KEY_PRODUCT_KEY = "{keyProduct}";
    public static final String SPECIFIC_KEY = "{Productspecific}";
    public static final String DESCRIPTION_KEY = "{Productdescription}";
    public static final String TIPS_KEY = "{Tips}";
    public static final String BULLET_KEY = "{bullet}";
    public static final String REASON_KEY = "{reason}";

    public static final String SEARCH_TERM_1 = "{searchterm1}";
    public static final String SEARCH_TERM_2 = "{searchterm2}";
    public static final String SEARCH_TERM_3 = "{searchterm3}";
    public static final String SEARCH_TERM_4 = "{searchterm4}";
    public static final String SEARCH_TERM_5 = "{searchterm5}";

    private Map<String, String> data;
    private Map<String, String> settings;
    private Map<String, String> templates;
    private ArrayList<String> listBulletPoints;

    public SettingInfo() {
        this.data = new HashMap<>();
        this.settings = new HashMap<>();
        this.templates = new HashMap<>();
    }

    public Map<String, String> getData() {
        return data;
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    public Map<String, String> getTemplates() {
        return templates;
    }

    public String getStoreId() {
        return data.getOrDefault(STORE_ID, "");
    }

    public String getKeywordLink() {
        return data.getOrDefault(KEYWORD_LINK, "");
    }

    public String getKeyword() {
        return data.getOrDefault(KEYWORD, "");
    }

    public String getProductId() {
        return data.getOrDefault(PRODUCT_ID, "");
    }

    public String getRegion() {
        String region = data.getOrDefault(REGION, "US");
        return region.isEmpty() ? "US" : region;
    }

    public String getLocale() {
        String locale = data.getOrDefault(REGION, "en_US");
        return locale.isEmpty() ? "en_US" : locale;
    }

    public void setTipLength(int length) {
        data.put(TIP_LENGTH, "" + length);
    }

    public void setReasonLength(int length) {
        data.put(REASON_LENGTH, "" + length);
    }

    public void setDesLength(int length) {
        data.put(DES_LENGTH, "" + length);
    }

    public String getTempDescription() {
        return templates.getOrDefault(TEMP_DESCRIPTION, "");
    }

    public String getTempReasons() {
        return templates.getOrDefault(TEMP_REASONS, "");
    }

    public String getTempTips() {
        return templates.getOrDefault(TEMP_TIPS, "");
    }

    public String getMainKey() {
        return data.getOrDefault(MAIN_KEYWORD_KEY, "");
    }

    public String getBrandName() {
        return data.getOrDefault(BRAND_NAME, "");
    }

    public void setListBulletPoints(ArrayList<String> listBulletPoints) {
        this.listBulletPoints = listBulletPoints;
    }

    public ArrayList<String> getListBulletPoints() {
        return listBulletPoints;
    }

    public boolean isNewTemplate() {
        return settings.containsKey(TEMPLATE) && !settings.get(TEMPLATE).isEmpty();
    }

    public boolean isUsingAI() {
        return settings.containsKey(FEATURES) && settings.get(FEATURES).contains(BUILD_DES_BY_AI);
    }
}
