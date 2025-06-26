package com.phanduy.aliexscrap.model.amazon;

import com.phanduy.aliexscrap.utils.StringUtils;

import java.util.regex.Pattern;

public class NewProduct {
    public String property_value_1;
    public String property_value_2;
    public String property_value_1_image;
    public String price;
    public String warehouse_quantity;
    public String seller_sku;
    public String promotionPrice;
    public String shippingPrice;
    public String shippingMethod;

    public String getImageName() {
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isEmpty(property_value_1)) {
            sb.append(property_value_1.replaceAll(Pattern.quote(" "), "_"));
        }
        if (!StringUtils.isEmpty(property_value_2)) {
            if (sb.length() == 0) {
                sb.append(property_value_1.replaceAll(Pattern.quote(" "), "_"));
            } else {
                sb.append("_").append(property_value_1.replaceAll(Pattern.quote(" "), "_"));
            }
        }
        return sb.toString();
    }
}
