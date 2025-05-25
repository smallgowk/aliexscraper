/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex;

import com.utils.AWSUtil;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class AliexProductDetail {

    public String id;
    public String categoryId;
    public String sellerId;
    public String detailUrl;
    public String title;
    public String[] productImages;
    public ArrayList<Promotion> promotions;
    public ArrayList<ProductAttribute> attributes;
    public ArrayList<SkuProperty> skuProperties;
    public ArrayList<Price> prices;
    public int statusId;
    public int countPerLot;
    public int wishListCount;
    public String unit;
    public String multiUnit;
    public Reviews reviews;
    public Trade trade;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getProductImages() {
        return productImages;
    }

    public void setProductImages(String[] productImages) {
        this.productImages = productImages;
    }

    public ArrayList<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(ArrayList<Promotion> promotions) {
        this.promotions = promotions;
    }

    public ArrayList<ProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<ProductAttribute> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<Price> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<Price> prices) {
        this.prices = prices;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getCountPerLot() {
        return countPerLot;
    }

    public void setCountPerLot(int countPerLot) {
        this.countPerLot = countPerLot;
    }

    public int getWishListCount() {
        return wishListCount;
    }

    public void setWishListCount(int wishListCount) {
        this.wishListCount = wishListCount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMultiUnit() {
        return multiUnit;
    }

    public void setMultiUnit(String multiUnit) {
        this.multiUnit = multiUnit;
    }

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public String getAttributeValue(String attribute) {
        if (attributes == null || attributes.isEmpty()) {
            return null;
        }

        for (ProductAttribute productAttribute : attributes) {
            if (productAttribute.getName().equals(attribute)) {
                return productAttribute.getValue();
            }
        }

        return null;
    }

    public String getValueForCell(String column) {

        switch (column) {
            case "feed_product_type":
                return null;
            case "item_sku":
                return null;
            case "brand_name":
                String brandName = getAttributeValue(ProductAttribute.BRAND_NAME);
                if (brandName == null || AWSUtil.checkContainBrand(brandName)) {
                    return "SnakeVN";
                }
                return brandName;
            case "external_product_id":
                return null;
            case "external_product_id_type":
                return null;
            case "item_name":
                return title;
            case "manufacturer":
                return null;
            case "part_number":
                return null;
            case "item_type":
                return null;
            case "standard_price":
                return null;
            case "quantity":
                return null;
            case "main_image_url":
                return productImages != null && productImages.length > 0 ? productImages[0] : "";
            case "swatch_image_url":
                return "";
            case "other_image_url1":
                return productImages != null && productImages.length > 1 ? productImages[1] : "";
            case "other_image_url2":
                return productImages != null && productImages.length > 2 ? productImages[2] : "";
            case "other_image_url3":
                return productImages != null && productImages.length > 3 ? productImages[3] : "";
            case "parent_child":
                return null;
            case "parent_sku":
                return null;
            case "relationship_type":
                return null;
            case "variation_theme":
                return null;
            case "update_delete":
                return null;
            case "model":
                return null;
            case "product_description":
                return null;
            case "catalog_number":
                return null;
            case "bullet_point1":
                return null;
            case "bullet_point2":
                return null;
            case "bullet_point3":
                return null;
            case "bullet_point4":
                return null;
            case "bullet_point5":
                return null;
            case "specific_uses_keywords":
                return null;
            case "target_audience_keywords1":
                return null;
            case "target_audience_keywords2":
                return null;
            case "thesaurus_attribute_keywords":
                return null;
            case "thesaurus_subject_keywords":
                return null;
            case "generic_keywords":
                return null;
            case "platinum_keywords1":
                return null;
            case "platinum_keywords2":
                return null;
            case "platinum_keywords3":
                return null;
            case "platinum_keywords4":
                return null;
            case "platinum_keywords5":
                return null;
            case "wattage":
                return null;
            case "is_portable":
                return null;
            case "recommended_uses_for_product":
                return null;
            case "target_audience_base":
                return null;
            case "size_name":
                return null;
            case "color_name":
                return null;
            case "color_map":
                return null;
            case "style_name":
                return null;
            case "material_type":
                return null;
            case "pattern_name":
                return null;
            case "capacity_unit_of_measure":
                return null;
            case "item_length":
                return null;
            case "item_width":
                return null;
            case "item_height":
                return null;
            case "item_dimensions_unit_of_measure":
                return null;
            case "item_shape":
                return null;
            case "capacity":
                return null;
            case "item_thickness_derived":
                return null;
            case "item_thickness_unit_of_measure":
                return null;
            case "product_grade":
                return null;
            case "form_factor":
                return null;
            case "maximum_pressure":
                return null;
            case "measurement_system":
                return null;
            case "item_diameter_derived":
                return null;
            case "item_diameter_unit_of_measure":
                return null;
            case "website_shipping_weight":
                return null;
            case "website_shipping_weight_unit_of_measure":
                return null;
            case "unit_count":
                return null;
            case "item_display_diameter":
                return null;
            case "item_display_diameter_unit_of_measure":
                return null;
            case "item_display_weight":
                return null;
            case "item_display_weight_unit_of_measure":
                return null;
            case "item_display_height":
                return null;
            case "item_display_height_unit_of_measure":
                return null;
            case "item_display_length":
                return null;
            case "item_display_length_unit_of_measure":
                return null;
            case "item_display_width":
                return null;
            case "item_display_width_unit_of_measure":
                return null;
            case "display_dimensions_unit_of_measure":
                return null;
            case "size_map":
                return null;
            case "package_height":
                return null;
            case "fulfillment_center_id":
                return null;
            case "package_width":
                return null;
            case "package_length":
                return null;
            case "package_weight":
                return null;
            case "package_weight_unit_of_measure":
                return null;
            case "package_dimensions_unit_of_measure":
                return null;
            case "energy_efficiency_image_url":
                return null;
            case "warranty_type":
                return null;
            case "mfg_warranty_description_type":
                return null;
            case "cpsia_cautionary_statement":
                return null;
            case "cpsia_cautionary_description":
                return null;
            case "item_weight":
                return null;
            case "fabric_type":
                return null;
            case "import_designation":
                return null;
            case "country_of_origin":
                return null;
            case "item_weight_unit_of_measure":
                return null;
            case "legal_compliance_certification_metadata":
                return null;
            case "legal_compliance_certification_expiration_date":
                return null;
            case "specific_uses_for_product":
                return null;
            case "battery_type1":
                return null;
            case "battery_type2":
                return null;
            case "battery_type3":
                return null;
            case "number_of_batteries1":
                return null;
            case "number_of_batteries2":
                return null;
            case "number_of_batteries3":
                return null;
            case "are_batteries_included":
                return null;
            case "batteries_required":
                return null;
            case "battery_cell_composition":
                return null;
            case "lithium_battery_energy_content":
                return null;
            case "lithium_battery_packaging":
                return null;
            case "lithium_battery_weight":
                return null;
            case "number_of_lithium_ion_cells":
                return null;
            case "number_of_lithium_metal_cells":
                return null;
            case "battery_weight":
                return null;
            case "battery_weight_unit_of_measure":
                return null;
            case "lithium_battery_energy_content_unit_of_measure":
                return null;
            case "lithium_battery_weight_unit_of_measure":
                return null;
            case "supplier_declared_dg_hz_regulation1":
                return null;
            case "supplier_declared_dg_hz_regulation2":
                return null;
            case "supplier_declared_dg_hz_regulation3":
                return null;
            case "supplier_declared_dg_hz_regulation4":
                return null;
            case "supplier_declared_dg_hz_regulation5":
                return null;
            case "hazmat_united_nations_regulatory_id":
                return null;
            case "safety_data_sheet_url":
                return null;
            case "item_volume":
                return null;
            case "item_volume_unit_of_measure":
                return null;
            case "lighting_facts_image_url":
                return null;
            case "flash_point":
                return null;
            case "legal_compliance_certification_date_of_issue":
                return null;
            case "external_testing_certification":
                return null;
            case "ghs_classification_class1":
                return null;
            case "ghs_classification_class2":
                return null;
            case "ghs_classification_class3":
                return null;
            case "california_proposition_65_compliance_type":
                return null;
            case "california_proposition_65_chemical_names1":
                return null;
            case "california_proposition_65_chemical_names2":
                return null;
            case "california_proposition_65_chemical_names3":
                return null;
            case "california_proposition_65_chemical_names4":
                return null;
            case "california_proposition_65_chemical_names5":
                return null;
            case "merchant_shipping_group_name":
                return null;
            case "max_order_quantity":
                return null;
            case "item_package_quantity":
                return null;
            case "currency":
                return null;
            case "list_price":
                return null;
            case "map_price":
                return null;
            case "product_site_launch_date":
                return null;
            case "merchant_release_date":
                return null;
            case "condition_type":
                return null;
            case "fulfillment_latency":
                return null;
            case "restock_date":
                return null;
            case "max_aggregate_ship_quantity":
                return null;
            case "product_tax_code":
                return null;
            case "condition_note":
                return null;
            case "sale_price":
                return null;
            case "sale_from_date":
                return null;
            case "sale_end_date":
                return null;
            case "offering_can_be_gift_messaged":
                return null;
            case "offering_can_be_giftwrapped":
                return null;
            case "is_discontinued_by_manufacturer":
                return null;
            case "delivery_schedule_group_id":
                return null;
            case "offering_end_date":
                return null;
            case "offering_start_date":
                return null;
            case "horsepower":
                return null;
            case "power_source_type":
                return null;
            case "voltage":
                return null;
            case "efficiency":
                return null;
            case "included_components1":
                return null;
            case "included_components2":
                return null;
            case "included_components3":
                return null;
            case "included_components4":
                return null;
            case "energy_consumption":
                return null;
            case "water_consumption":
                return null;
            case "compatible_counter_depth":
                return null;
            case "installation_type":
                return null;
            case "compatible_devices":
                return null;
            case "controller_type":
                return null;
            case "noise_level":
                return null;
            case "number_of_pieces":
                return null;
            case "number_of_handles":
                return null;
            case "specification_met":
                return null;
            case "controls_type":
                return null;
            case "brightness":
                return null;
            case "minimum_efficiency_reporting_value":
                return null;
            case "dryer_power_source":
                return null;
            case "lighting_method":
                return null;
            case "shelf_type":
                return null;
            case "pore_size":
                return null;
            case "item_torque":
                return null;

        }

        return "";
    }

    public float getProductPrice() {
        if (prices == null || prices.isEmpty()) {
            return 0;
        }

        String maxStr = prices.get(0).getMaxAmount().getValue();

        return Float.parseFloat(maxStr);
    }

    public float getPromotionRate() {
        if (promotions == null || promotions.isEmpty()) {
            return 0;
        }

        String promotionDiscount = promotions.get(0).discount;

        return Float.parseFloat(promotionDiscount) / 100;

    }

    public String getSkuName(int id) {
        if (skuProperties == null || skuProperties.isEmpty()) {
            return null;
        }

        for (SkuProperty skuProperty : skuProperties) {
            if (skuProperty.getPropertyId() == id) {
                return skuProperty.getPropertyName();
            }
        }

        return null;
    }

    public String getVariationName(int id, int valueId) {
        if (skuProperties == null || skuProperties.isEmpty()) {
            return null;
        }

        for (SkuProperty skuProperty : skuProperties) {
            if (skuProperty.getPropertyId() == id) {

                for (SkuValue skuValue : skuProperty.getValues()) {
                    if (skuValue.getPropertyValueId() == valueId) {
                        return skuValue.getPropertyValueDisplayName();
                    }
                }

            }
        }

        return null;
    }

    SkuProperty colorSkuProperty = null;

    public boolean isColor(int propertyId) {
        if (skuProperties == null || skuProperties.isEmpty()) {
            return false;
        }

        if (colorSkuProperty != null) {
            return colorSkuProperty.getPropertyId() == propertyId;
        }

        if (skuProperties.size() == 1) {
            SkuProperty skuProperty = skuProperties.get(0);
            String name = skuProperty.getPropertyName().toLowerCase();
            return name.contains("color") || name.contains("plug");
        }

        for (SkuProperty skuProperty : skuProperties) {
            String name = skuProperty.getPropertyName().toLowerCase();
            if (name != null && name.contains("color")) {
                colorSkuProperty = skuProperty;
                break;
            }
        }

        if (colorSkuProperty == null) {
            for (SkuProperty skuProperty : skuProperties) {
                String name = skuProperty.getPropertyName().toLowerCase();
                if (name != null && name.contains("plug")) {
                    colorSkuProperty = skuProperty;
                    break;
                }
            }
        }

        if (colorSkuProperty == null) {
            return false;
        }

        return colorSkuProperty.getPropertyId() == propertyId;
    }

    public boolean isShipFrom(int propertyId) {
        if (skuProperties == null || skuProperties.isEmpty()) {
            return false;
        }

        for (SkuProperty skuProperty : skuProperties) {

            if (skuProperty.propertyId == propertyId) {
                String name = skuProperty.getPropertyName().toLowerCase();
                if(name.contains("ship")) {
                    return true;
                }
                break;
            }

        }

        return false;
    }

    public ArrayList<SkuProperty> getSkuProperties() {
        return skuProperties;
    }
    
    public boolean isHasInfo() {
        if(getTitle() == null) return false;
        
        if(getProductImages() == null || getProductImages().length == 0) return false;
        
        return true;
    }

}
