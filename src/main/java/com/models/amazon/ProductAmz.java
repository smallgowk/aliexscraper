/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.amazon;

import com.config.Configs;
import com.utils.MarketUtil;
import com.models.aliex.store.AliexStoreInfo;
import com.controller.DownloadManager;
import com.models.request.ImagePathModel;
import com.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author duyuno
 */
public class ProductAmz {

//    public static final int TYPE_PARENT = 1;
//    public static final int TYPE_CHILD = 2;
//    public static final int TYPE_NORMAL = 0;
    public String aliexId;

    public String feed_product_type;
    public String item_sku;
    public String brand_name;
    public String external_product_id;
    public String external_product_id_type;
    public String item_name;
    public String manufacturer;
    public String part_number;
    public String item_type;
    public String standard_price;
    public String quantity;
    public String main_image_url;
    public String swatch_image_url;
    public String other_image_url1;
    public String other_image_url2;
    public String other_image_url3;
    public String other_image_url4;
    public String other_image_url5;
    public String other_image_url6;
    public String other_image_url7;
    public String other_image_url8;
    public String child_image_url;
    
    public String main_image_key;
    public String swatch_image_key;
    public String other_image_key;
    public String other1_image_key;
    public String other2_image_key;
    public String other3_image_key;
    public String other4_image_key;
    public String other5_image_key;
    public String other6_image_key;
    public String other7_image_key;
    public String other8_image_key;
    
    public String main_image_vps_name;
    public String swatch_image_vps_name;
    public String other1_image_vps_name;
    public String other2_image_vps_name;
    public String other3_image_vps_name;
    public String other4_image_vps_name;
    public String other5_image_vps_name;
    public String other6_image_vps_name;
    public String other7_image_vps_name;
    public String other8_image_vps_name;
    
    public String parent_child;
    public String parent_sku;
    public String relationship_type;
    public String variation_theme;
    public String update_delete;
    public String model;
    public String product_description;
    public String catalog_number;
    public String bullet_point1;
    public String bullet_point2;
    public String bullet_point3;
    public String bullet_point4;
    public String bullet_point5;
    public String specific_uses_keywords;
    public String target_audience_keywords;
    public String target_audience_keywords1;
    public String target_audience_keywords2;
    public String thesaurus_attribute_keywords;
    public String thesaurus_subject_keywords;
    public String generic_keywords;
    public String main_keywords;
    public String platinum_keywords1;
    public String platinum_keywords2;
    public String platinum_keywords3;
    public String platinum_keywords4;
    public String platinum_keywords5;
    public String wattage;
    public String is_portable;
    public String recommended_uses_for_product;
    public String target_audience_base;
    public String size_name;
    public String color_name;
    public String color_map;
    public String style_name;
    public String material_type;
    public String pattern_name;
    public String capacity_unit_of_measure;
    public String item_length;
    public String item_width;
    public String item_height;
    public String item_dimensions_unit_of_measure;
    public String item_shape;
    public String capacity;
    public String item_thickness_derived;
    public String item_thickness_unit_of_measure;
    public String product_grade;
    public String form_factor;
    public String maximum_pressure;
    public String measurement_system;
    public String item_diameter_derived;
    public String item_diameter_unit_of_measure;
    public String website_shipping_weight;
    public String website_shipping_weight_unit_of_measure;
    public String unit_count;
    public String unit_count_type;
    public String item_display_diameter;
    public String item_display_diameter_unit_of_measure;
    public String item_display_weight;
    public String item_display_weight_unit_of_measure;
    public String item_display_height;
    public String item_display_height_unit_of_measure;
    public String item_display_length;
    public String item_display_length_unit_of_measure;
    public String item_display_width;
    public String item_display_width_unit_of_measure;
    public String display_dimensions_unit_of_measure;
    public String size_map;
    public String package_height;
    public String fulfillment_center_id;
    public String package_width;
    public String package_length;
    public String package_weight;
    public String package_weight_unit_of_measure;
    public String package_dimensions_unit_of_measure;
    public String energy_efficiency_image_url;
    public String warranty_type;
    public String mfg_warranty_description_type;
    public String cpsia_cautionary_statement;
    public String cpsia_cautionary_description;
    public String item_weight;
    public String fabric_type;
    public String import_designation;
    public String country_of_origin;
    public String item_weight_unit_of_measure;
    public String legal_compliance_certification_metadata;
    public String legal_compliance_certification_expiration_date;
    public String specific_uses_for_product;
    public String battery_type1;
    public String battery_type2;
    public String battery_type3;
    public String number_of_batteries1;
    public String number_of_batteries2;
    public String number_of_batteries3;
    public String are_batteries_included;
    public String batteries_required;
    public String battery_cell_composition;
    public String lithium_battery_energy_content;
    public String lithium_battery_packaging;
    public String lithium_battery_weight;
    public String number_of_lithium_ion_cells;
    public String number_of_lithium_metal_cells;
    public String battery_weight;
    public String battery_weight_unit_of_measure;
    public String lithium_battery_energy_content_unit_of_measure;
    public String lithium_battery_weight_unit_of_measure;
    public String supplier_declared_dg_hz_regulation1;
    public String supplier_declared_dg_hz_regulation2;
    public String supplier_declared_dg_hz_regulation3;
    public String supplier_declared_dg_hz_regulation4;
    public String supplier_declared_dg_hz_regulation5;
    public String hazmat_united_nations_regulatory_id;
    public String safety_data_sheet_url;
    public String item_volume;
    public String item_volume_unit_of_measure;
    public String lighting_facts_image_url;
    public String flash_point;
    public String legal_compliance_certification_date_of_issue;
    public String external_testing_certification;
    public String ghs_classification_class1;
    public String ghs_classification_class2;
    public String ghs_classification_class3;
    public String california_proposition_65_compliance_type;
    public String california_proposition_65_chemical_names1;
    public String california_proposition_65_chemical_names2;
    public String california_proposition_65_chemical_names3;
    public String california_proposition_65_chemical_names4;
    public String california_proposition_65_chemical_names5;
    public String merchant_shipping_group_name;
    public String max_order_quantity;
    public String item_package_quantity;
    public String currency;
    public String list_price;
    public String map_price;
    public String product_site_launch_date;
    public String merchant_release_date;
    public String condition_type;
    public String fulfillment_latency;
    public String restock_date;
    public String max_aggregate_ship_quantity;
    public String product_tax_code;
    public String condition_note;
    public String sale_price;
    public String sale_from_date;
    public String sale_end_date;
    public String offering_can_be_gift_messaged;
    public String offering_can_be_giftwrapped;
    public String is_discontinued_by_manufacturer;
    public String delivery_schedule_group_id;
    public String offering_end_date;
    public String offering_start_date;
    public String horsepower;
    public String power_source_type;
    public String voltage;
    public String efficiency;
    public String included_components1;
    public String included_components2;
    public String included_components3;
    public String included_components4;
    public String energy_consumption;
    public String water_consumption;
    public String compatible_counter_depth;
    public String installation_type;
    public String compatible_devices;
    public String controller_type;
    public String noise_level;
    public String number_of_pieces;
    public String number_of_handles;
    public String specification_met;
    public String controls_type;
    public String brightness;
    public String minimum_efficiency_reporting_value;
    public String dryer_power_source;
    public String lighting_method;
    public String shelf_type;
    public String pore_size;
    public String item_torque;
    public String mfg_minimum;
    public String number_of_items;
    public String groupType;
    public String department_name;
    public String shipping_method;
    
    public boolean hasChild = false;

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
        DataStore.putProductData(item_sku, "department_name", department_name);
    }

    public void setType(int type, AliexStoreInfo aliexStoreInfo) {

        switch (type) {
            case ProductTypes.TYPE_NORMAL:
                parent_child = "";
                variation_theme = "";
                DataStore.putProductData(item_sku, "parent_child", "");
                DataStore.putProductData(item_sku, "variation_theme", "");
                break;
            case ProductTypes.TYPE_CHILD_COLOR:
                parent_child = "Child";
                variation_theme = "Color_Name";
                DataStore.putProductData(item_sku, "parent_child", "Child");
                DataStore.putProductData(item_sku, "variation_theme", "Color_Name");
                break;
            case ProductTypes.TYPE_CHILD_SIZE:
                parent_child = "Child";
                variation_theme = "Size_Name";
                DataStore.putProductData(item_sku, "parent_child", "Child");
                DataStore.putProductData(item_sku, "variation_theme", "Size_Name");
                break;
            case ProductTypes.TYPE_PARENT_BOTH:
                parent_child = "Parent";
                variation_theme = aliexStoreInfo.variationThemeBoth;
                quantity = "";
                standard_price = "";
                color_name = "";
                size_name = "";
                DataStore.putProductData(item_sku, "parent_child", "Parent");
                DataStore.putProductData(item_sku, "variation_theme", aliexStoreInfo.variationThemeBoth);
                DataStore.putProductData(item_sku, "quantity", "");
                DataStore.putProductData(item_sku, "standard_price", "");
                DataStore.putProductData(item_sku, "color_name", "");
                DataStore.putProductData(item_sku, "size_name", "");

                break;
            case ProductTypes.TYPE_PARENT_COLOR:
                parent_child = "Parent";
                variation_theme = aliexStoreInfo.variationThemeColor;
                quantity = "";
                standard_price = "";
                color_name = "";
                size_name = "";
                DataStore.putProductData(item_sku, "parent_child", "Parent");
                DataStore.putProductData(item_sku, "variation_theme", aliexStoreInfo.variationThemeColor);
                DataStore.putProductData(item_sku, "quantity", "");
                DataStore.putProductData(item_sku, "standard_price", "");
                DataStore.putProductData(item_sku, "color_name", "");
                DataStore.putProductData(item_sku, "size_name", "");
                break;
            case ProductTypes.TYPE_PARENT_SIZE:
                parent_child = "Parent";
                variation_theme = aliexStoreInfo.variationThemeSize;
                quantity = "";
                standard_price = "";
                color_name = "";
                size_name = "";
                DataStore.putProductData(item_sku, "parent_child", "Parent");
                DataStore.putProductData(item_sku, "variation_theme", aliexStoreInfo.variationThemeSize);
                DataStore.putProductData(item_sku, "quantity", "");
                DataStore.putProductData(item_sku, "standard_price", "");
                DataStore.putProductData(item_sku, "color_name", "");
                DataStore.putProductData(item_sku, "size_name", "");
                break;
        }
    }
    
    public boolean isParent() {
        return parent_child == null || parent_child.equals("Parent") || parent_child.isEmpty();
    }

    public String getMain_keywords() {
        return main_keywords;
    }

    public void setMain_keywords(String main_keywords) {
        this.main_keywords = main_keywords;
        DataStore.putProductData(item_sku, "main_keywords", main_keywords);
    }

    public String getTarget_audience_keywords() {
        return target_audience_keywords;
    }

    public void setTarget_audience_keywords(String target_audience_keywords) {
        this.target_audience_keywords = target_audience_keywords;
        DataStore.putProductData(item_sku, "target_audience_keywords", target_audience_keywords);
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
    
    public String getMfg_minimum() {
        return mfg_minimum;
    }

    public void setMfg_minimum(String mfg_minimum) {
        this.mfg_minimum = mfg_minimum;
        DataStore.putProductData(item_sku, "mfg_minimum", mfg_minimum);
    }

    public String getNumber_of_items() {
        return number_of_items;
    }

    public void setNumber_of_items(String number_of_items) {
        this.number_of_items = number_of_items;
        DataStore.putProductData(item_sku, "number_of_items", number_of_items);
    }

    public String getFeed_product_type() {
        return feed_product_type;
    }

    public void setFeed_product_type(String feed_product_type) {
        this.feed_product_type = feed_product_type;
        DataStore.putProductData(item_sku, "feed_product_type", feed_product_type);
    }

    public String getItem_sku() {
        return item_sku;
    }

    public void setItem_sku(String item_sku) {
        this.item_sku = item_sku;
        DataStore.putProductData(item_sku, "item_sku", item_sku);
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
        DataStore.putProductData(item_sku, "brand_name", brand_name);
    }

    public String getExternal_product_id() {
        return external_product_id;
    }

    public void setExternal_product_id(String external_product_id) {
        this.external_product_id = external_product_id;
        DataStore.putProductData(item_sku, "external_product_id", external_product_id);
    }

    public String getExternal_product_id_type() {
        return external_product_id_type;
    }

    public void setExternal_product_id_type(String external_product_id_type) {
        this.external_product_id_type = external_product_id_type;
        DataStore.putProductData(item_sku, "external_product_id_type", external_product_id_type);
    }

    public void setOther_image_url4(String other_image_url4) {
        this.other_image_url4 = other_image_url4;
        DataStore.putProductData(item_sku, "other_image_url4", other_image_url4);
    }

    public void setOther_image_url5(String other_image_url5) {
        this.other_image_url5 = other_image_url5;
        DataStore.putProductData(item_sku, "other_image_url5", other_image_url5);
    }

    public void setOther_image_url6(String other_image_url6) {
        this.other_image_url6 = other_image_url6;
        DataStore.putProductData(item_sku, "other_image_url6", other_image_url6);
    }

    public void setOther_image_url7(String other_image_url7) {
        this.other_image_url7 = other_image_url7;
        DataStore.putProductData(item_sku, "other_image_url7", other_image_url7);
    }

    public void setOther_image_url8(String other_image_url8) {
        this.other_image_url8 = other_image_url8;
        DataStore.putProductData(item_sku, "other_image_url8", other_image_url8);
    }
    
    public void setImageUrls(ArrayList<String> imageUrls) {
        
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
        DataStore.putProductData(item_sku, "item_name", item_name);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        DataStore.putProductData(item_sku, "manufacturer", manufacturer);
    }

    public String getPart_number() {
        return part_number;
    }

    public void setPart_number(String part_number) {
        this.part_number = part_number;
        DataStore.putProductData(item_sku, "part_number", part_number);
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
        DataStore.putProductData(item_sku, "item_type", item_type);
    }

    public String getStandard_price() {
        return standard_price;
    }

    public void setStandard_price(String standard_price) {
        this.standard_price = standard_price;
        DataStore.putProductData(item_sku, "standard_price", standard_price);
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
        DataStore.putProductData(item_sku, "quantity", quantity);
    }

    public String getMain_image_url() {
        return main_image_url;
    }

    public void setMain_image_url(String main_image_url) {
        this.main_image_url = main_image_url;
        DataStore.putProductData(item_sku, "main_image_url", main_image_url);
    }

    public String getSwatch_image_url() {
        return swatch_image_url;
    }

    public void setSwatch_image_url(String swatch_image_url) {
        this.swatch_image_url = swatch_image_url;
        DataStore.putProductData(item_sku, "swatch_image_url", swatch_image_url);
    }

    public String getOther_image_url1() {
        return other_image_url1;
    }

    public void setOther_image_url1(String other_image_url1) {
        this.other_image_url1 = other_image_url1;
        DataStore.putProductData(item_sku, "other_image_url1", other_image_url1);
    }

    public String getOther_image_url2() {
        return other_image_url2;
    }

    public void setOther_image_url2(String other_image_url2) {
        this.other_image_url2 = other_image_url2;
        DataStore.putProductData(item_sku, "other_image_url2", other_image_url2);
    }

    public String getOther_image_url3() {
        return other_image_url3;
    }

    public void setOther_image_url3(String other_image_url3) {
        this.other_image_url3 = other_image_url3;
        DataStore.putProductData(item_sku, "other_image_url3", other_image_url3);
    }

    public String getParent_child() {
        return parent_child;
    }

    public void setParent_child(String parent_child) {
        this.parent_child = parent_child;
        DataStore.putProductData(item_sku, "parent_child", parent_child);
    }

    public String getParent_sku() {
        return parent_sku;
    }

    public void setParent_sku(String parent_sku) {
        this.parent_sku = parent_sku;
        DataStore.putProductData(item_sku, "parent_sku", parent_sku);
    }

    public String getRelationship_type() {
        return relationship_type;
    }

    public void setRelationship_type(String relationship_type) {
        this.relationship_type = relationship_type;
        DataStore.putProductData(item_sku, "relationship_type", relationship_type);
    }

    public String getVariation_theme() {
        return variation_theme;
    }

    public void setVariation_theme(String variation_theme) {
        this.variation_theme = variation_theme;
        DataStore.putProductData(item_sku, "variation_theme", variation_theme);
    }

    public String getUpdate_delete() {
        return update_delete;
    }

    public void setUpdate_delete(String update_delete) {
        this.update_delete = update_delete;
        DataStore.putProductData(item_sku, "update_delete", update_delete);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
        DataStore.putProductData(item_sku, "model", model);
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
        DataStore.putProductData(item_sku, "product_description", product_description);
    }

    public String getCatalog_number() {
        return catalog_number;
    }

    public void setCatalog_number(String catalog_number) {
        this.catalog_number = catalog_number;
        DataStore.putProductData(item_sku, "catalog_number", catalog_number);
    }

    public String getBullet_point1() {
        return bullet_point1;
    }

    public void setBullet_point1(String bullet_point1) {
        this.bullet_point1 = bullet_point1;
        DataStore.putProductData(item_sku, "bullet_point1", bullet_point1);
    }

    public String getBullet_point2() {
        return bullet_point2;
    }

    public void setBullet_point2(String bullet_point2) {
        this.bullet_point2 = bullet_point2;
        DataStore.putProductData(item_sku, "bullet_point2", bullet_point2);
    }

    public String getBullet_point3() {
        return bullet_point3;
    }

    public void setBullet_point3(String bullet_point3) {
        this.bullet_point3 = bullet_point3;
        DataStore.putProductData(item_sku, "bullet_point3", bullet_point3);
    }

    public String getBullet_point4() {
        return bullet_point4;
    }

    public void setBullet_point4(String bullet_point4) {
        this.bullet_point4 = bullet_point4;
        DataStore.putProductData(item_sku, "bullet_point4", bullet_point4);
    }

    public String getBullet_point5() {
        return bullet_point5;
    }

    public void setBullet_point5(String bullet_point5) {
        this.bullet_point5 = bullet_point5;
        DataStore.putProductData(item_sku, "bullet_point5", bullet_point5);
    }

    public String getSpecific_uses_keywords() {
        return specific_uses_keywords;
    }

    public void setSpecific_uses_keywords(String specific_uses_keywords) {
        this.specific_uses_keywords = specific_uses_keywords;
        DataStore.putProductData(item_sku, "specific_uses_keywords", specific_uses_keywords);
    }

    public String getTarget_audience_keywords1() {
        return target_audience_keywords1;
    }

    public void setTarget_audience_keywords1(String target_audience_keywords1) {
        this.target_audience_keywords1 = target_audience_keywords1;
        DataStore.putProductData(item_sku, "target_audience_keywords1", target_audience_keywords1);
    }

    public String getTarget_audience_keywords2() {
        return target_audience_keywords2;
    }

    public void setTarget_audience_keywords2(String target_audience_keywords2) {
        this.target_audience_keywords2 = target_audience_keywords2;
        DataStore.putProductData(item_sku, "target_audience_keywords2", target_audience_keywords2);
    }

    public String getThesaurus_attribute_keywords() {
        return thesaurus_attribute_keywords;
    }

    public void setThesaurus_attribute_keywords(String thesaurus_attribute_keywords) {
        this.thesaurus_attribute_keywords = thesaurus_attribute_keywords;
        DataStore.putProductData(item_sku, "thesaurus_attribute_keywords", thesaurus_attribute_keywords);
    }

    public String getThesaurus_subject_keywords() {
        return thesaurus_subject_keywords;
    }

    public void setThesaurus_subject_keywords(String thesaurus_subject_keywords) {
        this.thesaurus_subject_keywords = thesaurus_subject_keywords;
        DataStore.putProductData(item_sku, "thesaurus_subject_keywords", thesaurus_subject_keywords);
    }

    public String getGeneric_keywords() {
        return generic_keywords;
    }

    public void setGeneric_keywords(String generic_keywords) {
        this.generic_keywords = generic_keywords;
        DataStore.putProductData(item_sku, "generic_keywords", generic_keywords);
    }

    public String getPlatinum_keywords1() {
        return platinum_keywords1;
    }

    public void setPlatinum_keywords1(String platinum_keywords1) {
        this.platinum_keywords1 = platinum_keywords1;
        DataStore.putProductData(item_sku, "platinum_keywords1", platinum_keywords1);
    }

    public String getPlatinum_keywords2() {
        return platinum_keywords2;
    }

    public void setPlatinum_keywords2(String platinum_keywords2) {
        this.platinum_keywords2 = platinum_keywords2;
        DataStore.putProductData(item_sku, "platinum_keywords2", platinum_keywords2);
    }

    public String getPlatinum_keywords3() {
        return platinum_keywords3;
    }

    public void setPlatinum_keywords3(String platinum_keywords3) {
        this.platinum_keywords3 = platinum_keywords3;
        DataStore.putProductData(item_sku, "platinum_keywords3", platinum_keywords3);
    }

    public String getPlatinum_keywords4() {
        return platinum_keywords4;
    }

    public void setPlatinum_keywords4(String platinum_keywords4) {
        this.platinum_keywords4 = platinum_keywords4;
        DataStore.putProductData(item_sku, "platinum_keywords4", platinum_keywords4);
    }

    public String getPlatinum_keywords5() {
        return platinum_keywords5;
    }

    public void setPlatinum_keywords5(String platinum_keywords5) {
        this.platinum_keywords5 = platinum_keywords5;
        DataStore.putProductData(item_sku, "platinum_keywords5", platinum_keywords5);
    }

    public String getWattage() {
        return wattage;
    }

    public void setWattage(String wattage) {
        this.wattage = wattage;
        DataStore.putProductData(item_sku, "wattage", wattage);
    }

    public String getIs_portable() {
        return is_portable;
    }

    public void setIs_portable(String is_portable) {
        this.is_portable = is_portable;
        DataStore.putProductData(item_sku, "is_portable", is_portable);
    }

    public String getRecommended_uses_for_product() {
        return recommended_uses_for_product;
    }

    public void setRecommended_uses_for_product(String recommended_uses_for_product) {
        this.recommended_uses_for_product = recommended_uses_for_product;
        DataStore.putProductData(item_sku, "recommended_uses_for_product", recommended_uses_for_product);
    }

    public String getTarget_audience_base() {
        return target_audience_base;
    }

    public void setTarget_audience_base(String target_audience_base) {
        this.target_audience_base = target_audience_base;
        DataStore.putProductData(item_sku, "target_audience_base", target_audience_base);
    }

    public String getSize_name() {
        return size_name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
        DataStore.putProductData(item_sku, "size_name", size_name);
    }

    public String getColor_name() {
        return color_name;
    }

    public void setColor_name(String color_name) {
        this.color_name = color_name;
        DataStore.putProductData(item_sku, "color_name", color_name);
    }

    public String getColor_map() {
        return color_map;
    }

    public void setColor_map(String color_map) {
        this.color_map = color_map;
        DataStore.putProductData(item_sku, "color_map", color_map);
    }

    public String getStyle_name() {
        return style_name;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
        DataStore.putProductData(item_sku, "style_name", style_name);
    }

    public String getMaterial_type() {
        return material_type;
    }

    public void setMaterial_type(String material_type) {
        this.material_type = material_type;
        DataStore.putProductData(item_sku, "material_type", material_type);
    }

    public String getPattern_name() {
        return pattern_name;
    }

    public void setPattern_name(String pattern_name) {
        this.pattern_name = pattern_name;
        DataStore.putProductData(item_sku, "pattern_name", pattern_name);
    }

    public String getCapacity_unit_of_measure() {
        return capacity_unit_of_measure;
    }

    public void setCapacity_unit_of_measure(String capacity_unit_of_measure) {
        this.capacity_unit_of_measure = capacity_unit_of_measure;
        DataStore.putProductData(item_sku, "capacity_unit_of_measure", capacity_unit_of_measure);
    }

    public String getItem_length() {
        return item_length;
    }

    public void setItem_length(String item_length) {
        this.item_length = item_length;
        DataStore.putProductData(item_sku, "item_length", item_length);
    }

    public String getItem_width() {
        return item_width;
    }

    public void setItem_width(String item_width) {
        this.item_width = item_width;
        DataStore.putProductData(item_sku, "item_width", item_width);
    }

    public String getItem_height() {
        return item_height;
    }

    public void setItem_height(String item_height) {
        this.item_height = item_height;
        DataStore.putProductData(item_sku, "item_height", item_height);
    }

    public String getItem_dimensions_unit_of_measure() {
        return item_dimensions_unit_of_measure;
    }

    public void setItem_dimensions_unit_of_measure(String item_dimensions_unit_of_measure) {
        this.item_dimensions_unit_of_measure = item_dimensions_unit_of_measure;
        DataStore.putProductData(item_sku, "item_dimensions_unit_of_measure", item_dimensions_unit_of_measure);
    }

    public String getItem_shape() {
        return item_shape;
    }

    public void setItem_shape(String item_shape) {
        this.item_shape = item_shape;
        DataStore.putProductData(item_sku, "item_shape", item_shape);
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
        DataStore.putProductData(item_sku, "capacity", capacity);
    }

    public String getItem_thickness_derived() {
        return item_thickness_derived;
    }

    public void setItem_thickness_derived(String item_thickness_derived) {
        this.item_thickness_derived = item_thickness_derived;
        DataStore.putProductData(item_sku, "item_thickness_derived", item_thickness_derived);
    }

    public String getItem_thickness_unit_of_measure() {
        return item_thickness_unit_of_measure;
    }

    public void setItem_thickness_unit_of_measure(String item_thickness_unit_of_measure) {
        this.item_thickness_unit_of_measure = item_thickness_unit_of_measure;
        DataStore.putProductData(item_sku, "item_thickness_unit_of_measure", item_thickness_unit_of_measure);
    }

    public String getProduct_grade() {
        return product_grade;
    }

    public void setProduct_grade(String product_grade) {
        this.product_grade = product_grade;
        DataStore.putProductData(item_sku, "product_grade", product_grade);
    }

    public String getForm_factor() {
        return form_factor;
    }

    public void setForm_factor(String form_factor) {
        this.form_factor = form_factor;
        DataStore.putProductData(item_sku, "form_factor", form_factor);
    }

    public String getMaximum_pressure() {
        return maximum_pressure;
    }

    public void setMaximum_pressure(String maximum_pressure) {
        this.maximum_pressure = maximum_pressure;
        DataStore.putProductData(item_sku, "maximum_pressure", maximum_pressure);
    }

    public String getMeasurement_system() {
        return measurement_system;
    }

    public void setMeasurement_system(String measurement_system) {
        this.measurement_system = measurement_system;
        DataStore.putProductData(item_sku, "measurement_system", measurement_system);
    }

    public String getItem_diameter_derived() {
        return item_diameter_derived;
    }

    public void setItem_diameter_derived(String item_diameter_derived) {
        this.item_diameter_derived = item_diameter_derived;
        DataStore.putProductData(item_sku, "item_diameter_derived", item_diameter_derived);
    }

    public String getItem_diameter_unit_of_measure() {
        return item_diameter_unit_of_measure;
    }

    public void setItem_diameter_unit_of_measure(String item_diameter_unit_of_measure) {
        this.item_diameter_unit_of_measure = item_diameter_unit_of_measure;
        DataStore.putProductData(item_sku, "item_diameter_unit_of_measure", item_diameter_unit_of_measure);
    }

    public String getWebsite_shipping_weight() {
        return website_shipping_weight;
    }

    public void setWebsite_shipping_weight(String website_shipping_weight) {
        this.website_shipping_weight = website_shipping_weight;
        DataStore.putProductData(item_sku, "website_shipping_weight", website_shipping_weight);
    }

    public String getWebsite_shipping_weight_unit_of_measure() {
        return website_shipping_weight_unit_of_measure;
    }

    public void setWebsite_shipping_weight_unit_of_measure(String website_shipping_weight_unit_of_measure) {
        this.website_shipping_weight_unit_of_measure = website_shipping_weight_unit_of_measure;
        DataStore.putProductData(item_sku, "website_shipping_weight_unit_of_measure", website_shipping_weight_unit_of_measure);
    }

    public String getUnit_count() {
        return unit_count;
    }

    public void setUnit_count(String unit_count) {
        this.unit_count = unit_count;
        DataStore.putProductData(item_sku, "unit_count", unit_count);
    }

    public String getUnit_count_type() {
        return unit_count_type;
    }

    public void setUnit_count_type(String unit_count_type) {
        this.unit_count_type = unit_count_type;
        DataStore.putProductData(item_sku, "unit_count_type", unit_count_type);
    }

    public String getItem_display_diameter() {
        return item_display_diameter;
    }

    public void setItem_display_diameter(String item_display_diameter) {
        this.item_display_diameter = item_display_diameter;
        DataStore.putProductData(item_sku, "item_display_diameter", item_display_diameter);
    }

    public String getItem_display_diameter_unit_of_measure() {
        return item_display_diameter_unit_of_measure;
    }

    public void setItem_display_diameter_unit_of_measure(String item_display_diameter_unit_of_measure) {
        this.item_display_diameter_unit_of_measure = item_display_diameter_unit_of_measure;
        DataStore.putProductData(item_sku, "item_display_diameter_unit_of_measure", item_display_diameter_unit_of_measure);
    }

    public String getItem_display_weight() {
        return item_display_weight;
    }

    public void setItem_display_weight(String item_display_weight) {
        this.item_display_weight = item_display_weight;
        DataStore.putProductData(item_sku, "item_display_weight", item_display_weight);
    }

    public String getItem_display_weight_unit_of_measure() {
        return item_display_weight_unit_of_measure;
    }

    public void setItem_display_weight_unit_of_measure(String item_display_weight_unit_of_measure) {
        this.item_display_weight_unit_of_measure = item_display_weight_unit_of_measure;
        DataStore.putProductData(item_sku, "item_display_weight_unit_of_measure", item_display_weight_unit_of_measure);
    }

    public String getItem_display_height() {
        return item_display_height;
    }

    public void setItem_display_height(String item_display_height) {
        this.item_display_height = item_display_height;
        DataStore.putProductData(item_sku, "item_display_height", item_display_height);
    }

    public String getItem_display_height_unit_of_measure() {
        return item_display_height_unit_of_measure;
    }

    public void setItem_display_height_unit_of_measure(String item_display_height_unit_of_measure) {
        this.item_display_height_unit_of_measure = item_display_height_unit_of_measure;
        DataStore.putProductData(item_sku, "item_display_height_unit_of_measure", item_display_height_unit_of_measure);
    }

    public String getItem_display_length() {
        return item_display_length;
    }

    public void setItem_display_length(String item_display_length) {
        this.item_display_length = item_display_length;
        DataStore.putProductData(item_sku, "item_display_length", item_display_length);
    }

    public String getItem_display_length_unit_of_measure() {
        return item_display_length_unit_of_measure;
    }

    public void setItem_display_length_unit_of_measure(String item_display_length_unit_of_measure) {
        this.item_display_length_unit_of_measure = item_display_length_unit_of_measure;
        DataStore.putProductData(item_sku, "item_display_length_unit_of_measure", item_display_length_unit_of_measure);
    }

    public String getItem_display_width() {
        return item_display_width;
    }

    public void setItem_display_width(String item_display_width) {
        this.item_display_width = item_display_width;
        DataStore.putProductData(item_sku, "item_display_width", item_display_width);
    }

    public String getItem_display_width_unit_of_measure() {
        return item_display_width_unit_of_measure;
    }

    public void setItem_display_width_unit_of_measure(String item_display_width_unit_of_measure) {
        this.item_display_width_unit_of_measure = item_display_width_unit_of_measure;
        DataStore.putProductData(item_sku, "item_display_width_unit_of_measure", item_display_width_unit_of_measure);
    }

    public String getDisplay_dimensions_unit_of_measure() {
        return display_dimensions_unit_of_measure;
    }

    public void setDisplay_dimensions_unit_of_measure(String display_dimensions_unit_of_measure) {
        this.display_dimensions_unit_of_measure = display_dimensions_unit_of_measure;
        DataStore.putProductData(item_sku, "display_dimensions_unit_of_measure", display_dimensions_unit_of_measure);
    }

    public String getSize_map() {
        return size_map;
    }

    public void setSize_map(String size_map) {
        this.size_map = size_map;
        DataStore.putProductData(item_sku, "size_map", size_map);
    }

    public String getPackage_height() {
        return package_height;
    }

    public void setPackage_height(String package_height) {
        this.package_height = package_height;
        DataStore.putProductData(item_sku, "package_height", package_height);
    }

    public String getFulfillment_center_id() {
        return fulfillment_center_id;
    }

    public void setFulfillment_center_id(String fulfillment_center_id) {
        this.fulfillment_center_id = fulfillment_center_id;
        DataStore.putProductData(item_sku, "fulfillment_center_id", fulfillment_center_id);
    }

    public String getPackage_width() {
        return package_width;
    }

    public void setPackage_width(String package_width) {
        this.package_width = package_width;
        DataStore.putProductData(item_sku, "package_width", package_width);
    }

    public String getPackage_length() {
        return package_length;
    }

    public void setPackage_length(String package_length) {
        this.package_length = package_length;
        DataStore.putProductData(item_sku, "package_length", package_length);
    }

    public String getPackage_weight() {
        return package_weight;
    }

    public void setPackage_weight(String package_weight) {
        this.package_weight = package_weight;
        DataStore.putProductData(item_sku, "package_weight", package_weight);
    }

    public String getPackage_weight_unit_of_measure() {
        return package_weight_unit_of_measure;
    }

    public void setPackage_weight_unit_of_measure(String package_weight_unit_of_measure) {
        this.package_weight_unit_of_measure = package_weight_unit_of_measure;
        DataStore.putProductData(item_sku, "package_weight_unit_of_measure", package_weight_unit_of_measure);
    }

    public String getPackage_dimensions_unit_of_measure() {
        return package_dimensions_unit_of_measure;
    }

    public void setPackage_dimensions_unit_of_measure(String package_dimensions_unit_of_measure) {
        this.package_dimensions_unit_of_measure = package_dimensions_unit_of_measure;
        DataStore.putProductData(item_sku, "package_dimensions_unit_of_measure", package_dimensions_unit_of_measure);
    }

    public String getEnergy_efficiency_image_url() {
        return energy_efficiency_image_url;
    }

    public void setEnergy_efficiency_image_url(String energy_efficiency_image_url) {
        this.energy_efficiency_image_url = energy_efficiency_image_url;
        DataStore.putProductData(item_sku, "energy_efficiency_image_url", energy_efficiency_image_url);
    }

    public String getWarranty_type() {
        return warranty_type;
    }

    public void setWarranty_type(String warranty_type) {
        this.warranty_type = warranty_type;
        DataStore.putProductData(item_sku, "warranty_type", warranty_type);
    }

    public String getMfg_warranty_description_type() {
        return mfg_warranty_description_type;
    }

    public void setMfg_warranty_description_type(String mfg_warranty_description_type) {
        this.mfg_warranty_description_type = mfg_warranty_description_type;
        DataStore.putProductData(item_sku, "mfg_warranty_description_type", mfg_warranty_description_type);
    }

    public String getCpsia_cautionary_statement() {
        return cpsia_cautionary_statement;
    }

    public void setCpsia_cautionary_statement(String cpsia_cautionary_statement) {
        this.cpsia_cautionary_statement = cpsia_cautionary_statement;
        DataStore.putProductData(item_sku, "cpsia_cautionary_statement", cpsia_cautionary_statement);
    }

    public String getCpsia_cautionary_description() {
        return cpsia_cautionary_description;
    }

    public void setCpsia_cautionary_description(String cpsia_cautionary_description) {
        this.cpsia_cautionary_description = cpsia_cautionary_description;
        DataStore.putProductData(item_sku, "cpsia_cautionary_description", cpsia_cautionary_description);
    }

    public String getItem_weight() {
        return item_weight;
    }

    public void setItem_weight(String item_weight) {
        this.item_weight = item_weight;
        DataStore.putProductData(item_sku, "item_weight", item_weight);
    }

    public String getFabric_type() {
        return fabric_type;
    }

    public void setFabric_type(String fabric_type) {
        this.fabric_type = fabric_type;
        DataStore.putProductData(item_sku, "fabric_type", fabric_type);
    }

    public String getImport_designation() {
        return import_designation;
    }

    public void setImport_designation(String import_designation) {
        this.import_designation = import_designation;
        DataStore.putProductData(item_sku, "import_designation", import_designation);
    }

    public String getCountry_of_origin() {
        return country_of_origin;
    }

    public void setCountry_of_origin(String country_of_origin) {
        this.country_of_origin = country_of_origin;
        DataStore.putProductData(item_sku, "country_of_origin", country_of_origin);
    }

    public String getItem_weight_unit_of_measure() {
        return item_weight_unit_of_measure;
    }

    public void setItem_weight_unit_of_measure(String item_weight_unit_of_measure) {
        this.item_weight_unit_of_measure = item_weight_unit_of_measure;
        DataStore.putProductData(item_sku, "item_weight_unit_of_measure", item_weight_unit_of_measure);
    }

    public String getLegal_compliance_certification_metadata() {
        return legal_compliance_certification_metadata;
    }

    public void setLegal_compliance_certification_metadata(String legal_compliance_certification_metadata) {
        this.legal_compliance_certification_metadata = legal_compliance_certification_metadata;
        DataStore.putProductData(item_sku, "legal_compliance_certification_metadata", legal_compliance_certification_metadata);
    }

    public String getLegal_compliance_certification_expiration_date() {
        return legal_compliance_certification_expiration_date;
    }

    public void setLegal_compliance_certification_expiration_date(String legal_compliance_certification_expiration_date) {
        this.legal_compliance_certification_expiration_date = legal_compliance_certification_expiration_date;
        DataStore.putProductData(item_sku, "legal_compliance_certification_expiration_date", legal_compliance_certification_expiration_date);
    }

    public String getSpecific_uses_for_product() {
        return specific_uses_for_product;
    }

    public void setSpecific_uses_for_product(String specific_uses_for_product) {
        this.specific_uses_for_product = specific_uses_for_product;
        DataStore.putProductData(item_sku, "specific_uses_for_product", specific_uses_for_product);
    }

    public String getBattery_type1() {
        return battery_type1;
    }

    public void setBattery_type1(String battery_type1) {
        this.battery_type1 = battery_type1;
        DataStore.putProductData(item_sku, "battery_type1", battery_type1);
    }

    public String getBattery_type2() {
        return battery_type2;
    }

    public void setBattery_type2(String battery_type2) {
        this.battery_type2 = battery_type2;
        DataStore.putProductData(item_sku, "battery_type2", battery_type2);
    }

    public String getBattery_type3() {
        return battery_type3;
    }

    public void setBattery_type3(String battery_type3) {
        this.battery_type3 = battery_type3;
    }

    public String getNumber_of_batteries1() {
        return number_of_batteries1;
    }

    public void setNumber_of_batteries1(String number_of_batteries1) {
        this.number_of_batteries1 = number_of_batteries1;
    }

    public String getNumber_of_batteries2() {
        return number_of_batteries2;
    }

    public void setNumber_of_batteries2(String number_of_batteries2) {
        this.number_of_batteries2 = number_of_batteries2;
    }

    public String getNumber_of_batteries3() {
        return number_of_batteries3;
    }

    public void setNumber_of_batteries3(String number_of_batteries3) {
        this.number_of_batteries3 = number_of_batteries3;
    }

    public String getAre_batteries_included() {
        return are_batteries_included;
    }

    public void setAre_batteries_included(String are_batteries_included) {
        this.are_batteries_included = are_batteries_included;
    }

    public String getBatteries_required() {
        return batteries_required;
    }

    public void setBatteries_required(String batteries_required) {
        this.batteries_required = batteries_required;
    }

    public String getBattery_cell_composition() {
        return battery_cell_composition;
    }

    public void setBattery_cell_composition(String battery_cell_composition) {
        this.battery_cell_composition = battery_cell_composition;
    }

    public String getLithium_battery_energy_content() {
        return lithium_battery_energy_content;
    }

    public void setLithium_battery_energy_content(String lithium_battery_energy_content) {
        this.lithium_battery_energy_content = lithium_battery_energy_content;
    }

    public String getLithium_battery_packaging() {
        return lithium_battery_packaging;
    }

    public void setLithium_battery_packaging(String lithium_battery_packaging) {
        this.lithium_battery_packaging = lithium_battery_packaging;
    }

    public String getLithium_battery_weight() {
        return lithium_battery_weight;
    }

    public void setLithium_battery_weight(String lithium_battery_weight) {
        this.lithium_battery_weight = lithium_battery_weight;
    }

    public String getNumber_of_lithium_ion_cells() {
        return number_of_lithium_ion_cells;
    }

    public void setNumber_of_lithium_ion_cells(String number_of_lithium_ion_cells) {
        this.number_of_lithium_ion_cells = number_of_lithium_ion_cells;
    }

    public String getNumber_of_lithium_metal_cells() {
        return number_of_lithium_metal_cells;
    }

    public void setNumber_of_lithium_metal_cells(String number_of_lithium_metal_cells) {
        this.number_of_lithium_metal_cells = number_of_lithium_metal_cells;
    }

    public String getBattery_weight() {
        return battery_weight;
    }

    public void setBattery_weight(String battery_weight) {
        this.battery_weight = battery_weight;
    }

    public String getBattery_weight_unit_of_measure() {
        return battery_weight_unit_of_measure;
    }

    public void setBattery_weight_unit_of_measure(String battery_weight_unit_of_measure) {
        this.battery_weight_unit_of_measure = battery_weight_unit_of_measure;
    }

    public String getLithium_battery_energy_content_unit_of_measure() {
        return lithium_battery_energy_content_unit_of_measure;
    }

    public void setLithium_battery_energy_content_unit_of_measure(String lithium_battery_energy_content_unit_of_measure) {
        this.lithium_battery_energy_content_unit_of_measure = lithium_battery_energy_content_unit_of_measure;
    }

    public String getLithium_battery_weight_unit_of_measure() {
        return lithium_battery_weight_unit_of_measure;
    }

    public void setLithium_battery_weight_unit_of_measure(String lithium_battery_weight_unit_of_measure) {
        this.lithium_battery_weight_unit_of_measure = lithium_battery_weight_unit_of_measure;
    }

    public String getSupplier_declared_dg_hz_regulation1() {
        return supplier_declared_dg_hz_regulation1;
    }

    public void setSupplier_declared_dg_hz_regulation1(String supplier_declared_dg_hz_regulation1) {
        this.supplier_declared_dg_hz_regulation1 = supplier_declared_dg_hz_regulation1;
    }

    public String getSupplier_declared_dg_hz_regulation2() {
        return supplier_declared_dg_hz_regulation2;
    }

    public void setSupplier_declared_dg_hz_regulation2(String supplier_declared_dg_hz_regulation2) {
        this.supplier_declared_dg_hz_regulation2 = supplier_declared_dg_hz_regulation2;
    }

    public String getSupplier_declared_dg_hz_regulation3() {
        return supplier_declared_dg_hz_regulation3;
    }

    public void setSupplier_declared_dg_hz_regulation3(String supplier_declared_dg_hz_regulation3) {
        this.supplier_declared_dg_hz_regulation3 = supplier_declared_dg_hz_regulation3;
    }

    public String getSupplier_declared_dg_hz_regulation4() {
        return supplier_declared_dg_hz_regulation4;
    }

    public void setSupplier_declared_dg_hz_regulation4(String supplier_declared_dg_hz_regulation4) {
        this.supplier_declared_dg_hz_regulation4 = supplier_declared_dg_hz_regulation4;
    }

    public String getSupplier_declared_dg_hz_regulation5() {
        return supplier_declared_dg_hz_regulation5;
    }

    public void setSupplier_declared_dg_hz_regulation5(String supplier_declared_dg_hz_regulation5) {
        this.supplier_declared_dg_hz_regulation5 = supplier_declared_dg_hz_regulation5;
    }

    public String getHazmat_united_nations_regulatory_id() {
        return hazmat_united_nations_regulatory_id;
    }

    public void setHazmat_united_nations_regulatory_id(String hazmat_united_nations_regulatory_id) {
        this.hazmat_united_nations_regulatory_id = hazmat_united_nations_regulatory_id;
    }

    public String getSafety_data_sheet_url() {
        return safety_data_sheet_url;
    }

    public void setSafety_data_sheet_url(String safety_data_sheet_url) {
        this.safety_data_sheet_url = safety_data_sheet_url;
    }

    public String getItem_volume() {
        return item_volume;
    }

    public void setItem_volume(String item_volume) {
        this.item_volume = item_volume;
    }

    public String getItem_volume_unit_of_measure() {
        return item_volume_unit_of_measure;
    }

    public void setItem_volume_unit_of_measure(String item_volume_unit_of_measure) {
        this.item_volume_unit_of_measure = item_volume_unit_of_measure;
    }

    public String getLighting_facts_image_url() {
        return lighting_facts_image_url;
    }

    public void setLighting_facts_image_url(String lighting_facts_image_url) {
        this.lighting_facts_image_url = lighting_facts_image_url;
    }

    public String getFlash_point() {
        return flash_point;
    }

    public void setFlash_point(String flash_point) {
        this.flash_point = flash_point;
    }

    public String getLegal_compliance_certification_date_of_issue() {
        return legal_compliance_certification_date_of_issue;
    }

    public void setLegal_compliance_certification_date_of_issue(String legal_compliance_certification_date_of_issue) {
        this.legal_compliance_certification_date_of_issue = legal_compliance_certification_date_of_issue;
    }

    public String getExternal_testing_certification() {
        return external_testing_certification;
    }

    public void setExternal_testing_certification(String external_testing_certification) {
        this.external_testing_certification = external_testing_certification;
    }

    public String getGhs_classification_class1() {
        return ghs_classification_class1;
    }

    public void setGhs_classification_class1(String ghs_classification_class1) {
        this.ghs_classification_class1 = ghs_classification_class1;
    }

    public String getGhs_classification_class2() {
        return ghs_classification_class2;
    }

    public void setGhs_classification_class2(String ghs_classification_class2) {
        this.ghs_classification_class2 = ghs_classification_class2;
    }

    public String getGhs_classification_class3() {
        return ghs_classification_class3;
    }

    public void setGhs_classification_class3(String ghs_classification_class3) {
        this.ghs_classification_class3 = ghs_classification_class3;
    }

    public String getCalifornia_proposition_65_compliance_type() {
        return california_proposition_65_compliance_type;
    }

    public void setCalifornia_proposition_65_compliance_type(String california_proposition_65_compliance_type) {
        this.california_proposition_65_compliance_type = california_proposition_65_compliance_type;
    }

    public String getCalifornia_proposition_65_chemical_names1() {
        return california_proposition_65_chemical_names1;
    }

    public void setCalifornia_proposition_65_chemical_names1(String california_proposition_65_chemical_names1) {
        this.california_proposition_65_chemical_names1 = california_proposition_65_chemical_names1;
    }

    public String getCalifornia_proposition_65_chemical_names2() {
        return california_proposition_65_chemical_names2;
    }

    public void setCalifornia_proposition_65_chemical_names2(String california_proposition_65_chemical_names2) {
        this.california_proposition_65_chemical_names2 = california_proposition_65_chemical_names2;
    }

    public String getCalifornia_proposition_65_chemical_names3() {
        return california_proposition_65_chemical_names3;
    }

    public void setCalifornia_proposition_65_chemical_names3(String california_proposition_65_chemical_names3) {
        this.california_proposition_65_chemical_names3 = california_proposition_65_chemical_names3;
    }

    public String getCalifornia_proposition_65_chemical_names4() {
        return california_proposition_65_chemical_names4;
    }

    public void setCalifornia_proposition_65_chemical_names4(String california_proposition_65_chemical_names4) {
        this.california_proposition_65_chemical_names4 = california_proposition_65_chemical_names4;
    }

    public String getCalifornia_proposition_65_chemical_names5() {
        return california_proposition_65_chemical_names5;
    }

    public void setCalifornia_proposition_65_chemical_names5(String california_proposition_65_chemical_names5) {
        this.california_proposition_65_chemical_names5 = california_proposition_65_chemical_names5;
    }

    public String getMerchant_shipping_group_name() {
        return merchant_shipping_group_name;
    }

    public void setMerchant_shipping_group_name(String merchant_shipping_group_name) {
        this.merchant_shipping_group_name = merchant_shipping_group_name;
    }

    public String getMax_order_quantity() {
        return max_order_quantity;
    }

    public void setMax_order_quantity(String max_order_quantity) {
        this.max_order_quantity = max_order_quantity;
    }

    public String getItem_package_quantity() {
        return item_package_quantity;
    }

    public void setItem_package_quantity(String item_package_quantity) {
        this.item_package_quantity = item_package_quantity;
        DataStore.putProductData(item_sku, "item_package_quantity", item_package_quantity);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getList_price() {
        return list_price;
    }

    public void setList_price(String list_price) {
        this.list_price = list_price;
    }

    public String getMap_price() {
        return map_price;
    }

    public void setMap_price(String map_price) {
        this.map_price = map_price;
    }

    public String getProduct_site_launch_date() {
        return product_site_launch_date;
    }

    public void setProduct_site_launch_date(String product_site_launch_date) {
        this.product_site_launch_date = product_site_launch_date;
    }

    public String getMerchant_release_date() {
        return merchant_release_date;
    }

    public void setMerchant_release_date(String merchant_release_date) {
        this.merchant_release_date = merchant_release_date;
    }

    public String getCondition_type() {
        return condition_type;
    }

    public void setCondition_type(String condition_type) {
        this.condition_type = condition_type;
    }

    public String getFulfillment_latency() {
        return fulfillment_latency;
    }

    public void setFulfillment_latency(String fulfillment_latency) {
        this.fulfillment_latency = fulfillment_latency;
        DataStore.putProductData(item_sku, "fulfillment_latency", fulfillment_latency);
    }

    public String getRestock_date() {
        return restock_date;
    }

    public void setRestock_date(String restock_date) {
        this.restock_date = restock_date;
    }

    public String getMax_aggregate_ship_quantity() {
        return max_aggregate_ship_quantity;
    }

    public void setMax_aggregate_ship_quantity(String max_aggregate_ship_quantity) {
        this.max_aggregate_ship_quantity = max_aggregate_ship_quantity;
    }

    public String getProduct_tax_code() {
        return product_tax_code;
    }

    public void setProduct_tax_code(String product_tax_code) {
        this.product_tax_code = product_tax_code;
    }

    public String getCondition_note() {
        return condition_note;
    }

    public void setCondition_note(String condition_note) {
        this.condition_note = condition_note;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getSale_from_date() {
        return sale_from_date;
    }

    public void setSale_from_date(String sale_from_date) {
        this.sale_from_date = sale_from_date;
    }

    public String getSale_end_date() {
        return sale_end_date;
    }

    public void setSale_end_date(String sale_end_date) {
        this.sale_end_date = sale_end_date;
    }

    public String getOffering_can_be_gift_messaged() {
        return offering_can_be_gift_messaged;
    }

    public void setOffering_can_be_gift_messaged(String offering_can_be_gift_messaged) {
        this.offering_can_be_gift_messaged = offering_can_be_gift_messaged;
    }

    public String getOffering_can_be_giftwrapped() {
        return offering_can_be_giftwrapped;
    }

    public void setOffering_can_be_giftwrapped(String offering_can_be_giftwrapped) {
        this.offering_can_be_giftwrapped = offering_can_be_giftwrapped;
    }

    public String getIs_discontinued_by_manufacturer() {
        return is_discontinued_by_manufacturer;
    }

    public void setIs_discontinued_by_manufacturer(String is_discontinued_by_manufacturer) {
        this.is_discontinued_by_manufacturer = is_discontinued_by_manufacturer;
    }

    public String getDelivery_schedule_group_id() {
        return delivery_schedule_group_id;
    }

    public void setDelivery_schedule_group_id(String delivery_schedule_group_id) {
        this.delivery_schedule_group_id = delivery_schedule_group_id;
    }

    public String getOffering_end_date() {
        return offering_end_date;
    }

    public void setOffering_end_date(String offering_end_date) {
        this.offering_end_date = offering_end_date;
    }

    public String getOffering_start_date() {
        return offering_start_date;
    }

    public void setOffering_start_date(String offering_start_date) {
        this.offering_start_date = offering_start_date;
    }

    public String getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(String horsepower) {
        this.horsepower = horsepower;
    }

    public String getPower_source_type() {
        return power_source_type;
    }

    public void setPower_source_type(String power_source_type) {
        this.power_source_type = power_source_type;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(String efficiency) {
        this.efficiency = efficiency;
    }

    public String getIncluded_components1() {
        return included_components1;
    }

    public void setIncluded_components1(String included_components1) {
        this.included_components1 = included_components1;
    }

    public String getIncluded_components2() {
        return included_components2;
    }

    public void setIncluded_components2(String included_components2) {
        this.included_components2 = included_components2;
    }

    public String getIncluded_components3() {
        return included_components3;
    }

    public void setIncluded_components3(String included_components3) {
        this.included_components3 = included_components3;
    }

    public String getIncluded_components4() {
        return included_components4;
    }

    public void setIncluded_components4(String included_components4) {
        this.included_components4 = included_components4;
    }

    public String getEnergy_consumption() {
        return energy_consumption;
    }

    public void setEnergy_consumption(String energy_consumption) {
        this.energy_consumption = energy_consumption;
    }

    public String getWater_consumption() {
        return water_consumption;
    }

    public void setWater_consumption(String water_consumption) {
        this.water_consumption = water_consumption;
    }

    public String getCompatible_counter_depth() {
        return compatible_counter_depth;
    }

    public void setCompatible_counter_depth(String compatible_counter_depth) {
        this.compatible_counter_depth = compatible_counter_depth;
    }

    public String getInstallation_type() {
        return installation_type;
    }

    public void setInstallation_type(String installation_type) {
        this.installation_type = installation_type;
    }

    public String getCompatible_devices() {
        return compatible_devices;
    }

    public void setCompatible_devices(String compatible_devices) {
        this.compatible_devices = compatible_devices;
    }

    public String getController_type() {
        return controller_type;
    }

    public void setController_type(String controller_type) {
        this.controller_type = controller_type;
    }

    public String getNoise_level() {
        return noise_level;
    }

    public void setNoise_level(String noise_level) {
        this.noise_level = noise_level;
    }

    public String getNumber_of_pieces() {
        return number_of_pieces;
    }

    public void setNumber_of_pieces(String number_of_pieces) {
        this.number_of_pieces = number_of_pieces;
    }

    public String getNumber_of_handles() {
        return number_of_handles;
    }

    public void setNumber_of_handles(String number_of_handles) {
        this.number_of_handles = number_of_handles;
    }

    public String getSpecification_met() {
        return specification_met;
    }

    public void setSpecification_met(String specification_met) {
        this.specification_met = specification_met;
    }

    public String getControls_type() {
        return controls_type;
    }

    public void setControls_type(String controls_type) {
        this.controls_type = controls_type;
    }

    public String getBrightness() {
        return brightness;
    }

    public void setBrightness(String brightness) {
        this.brightness = brightness;
    }

    public String getMinimum_efficiency_reporting_value() {
        return minimum_efficiency_reporting_value;
    }

    public void setMinimum_efficiency_reporting_value(String minimum_efficiency_reporting_value) {
        this.minimum_efficiency_reporting_value = minimum_efficiency_reporting_value;
    }

    public String getDryer_power_source() {
        return dryer_power_source;
    }

    public void setDryer_power_source(String dryer_power_source) {
        this.dryer_power_source = dryer_power_source;
    }

    public String getLighting_method() {
        return lighting_method;
    }

    public void setLighting_method(String lighting_method) {
        this.lighting_method = lighting_method;
    }

    public String getShelf_type() {
        return shelf_type;
    }

    public void setShelf_type(String shelf_type) {
        this.shelf_type = shelf_type;
    }

    public String getPore_size() {
        return pore_size;
    }

    public void setPore_size(String pore_size) {
        this.pore_size = pore_size;
    }

    public String getItem_torque() {
        return item_torque;
    }

    public void setItem_torque(String item_torque) {
        this.item_torque = item_torque;
    }

    public String getAliexId() {
        return aliexId;
    }

    public void setAliexId(String aliexId) {
        this.aliexId = aliexId;
    }

    public String getOther_image_url4() {
        return other_image_url4;
    }

    public String getOther_image_url5() {
        return other_image_url5;
    }

    public String getOther_image_url6() {
        return other_image_url6;
    }

    public String getOther_image_url7() {
        return other_image_url7;
    }

    public String getOther_image_url8() {
        return other_image_url8;
    }

    public String getValueForCell(String column) {

        Class aClass = ProductAmz.class;

        try {
            Field field = aClass.getField(column);
            return (String) field.get(this);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
//            Logger.getLogger(ProductAmz.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    public void copyProduct(ProductAmz productAmz) {
        Class aClass = ProductAmz.class;

        try {
            Field[] fields = aClass.getFields();
            if (fields != null) {
                for (Field field : fields) {
                    String fieldName = field.getName();
                    Object value = field.get(productAmz);
                    if (value != null) {
                        if (shouldCopyFieldNames(fieldName)) {
                            DataStore.putProductData(item_sku, fieldName, value.toString());
                            field.set(this, value);
                        }
                    }
                }
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
//            Logger.getLogger(ProductAmz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void copyAllProduct(ProductAmz productAmz) {
        Class aClass = ProductAmz.class;

        try {
            Field[] fields = aClass.getFields();
            if (fields != null) {
                for (Field field : fields) {
                    String fieldName = field.getName();
                    Object value = field.get(productAmz);
                    if (!fieldName.equals("item_sku") && value != null) {
                        DataStore.putProductData(item_sku, fieldName, value.toString());
                        field.set(this, value);
                    }
                }
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
//            Logger.getLogger(ProductAmz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean shouldCopyFieldNames(String fieldName) {
        return !fieldName.equals("item_sku") &&
                !fieldName.equals("parent_child") &&
                !fieldName.equals("parent_sku") &&
                !fieldName.equals("color_map") &&
                !fieldName.equals("color_name") &&
                !fieldName.equals("size_map") &&
                !fieldName.equals("size_name") &&
                !fieldName.equals("variation_theme") &&
                !fieldName.equals("standard_price") &&
                !fieldName.equals("main_image_url") &&
                !fieldName.equals("other_image_url1") &&
                !fieldName.equals("other_image_url2") &&
                !fieldName.equals("other_image_url3") &&
                !fieldName.equals("other_image_url4") &&
                !fieldName.equals("other_image_url5") &&
                !fieldName.equals("other_image_url6") &&
                !fieldName.equals("other_image_url7") &&
                !fieldName.equals("other_image_url8") &&
                !fieldName.equals("quantity") &&
                !fieldName.equals("item_name") &&
                !fieldName.equals("relationship_type");
    }
    
    public int getProductHash() {
        int result = parent_sku != null ? parent_sku.hashCode() : 0;
        result += parent_sku != null ? parent_sku.hashCode() : 0;
        result += color_name != null ? color_name.hashCode() : 0;
        result += color_map != null ? color_map.hashCode() : 0;
        result += size_name != null ? size_name.hashCode() : 0;
        result += size_map != null ? size_map.hashCode() : 0;
        result += variation_theme != null ? variation_theme.hashCode() : 0;
        result += standard_price != null ? standard_price.hashCode() : 0;
        return result;
    }

    public String getItemTempHtml(ArrayList<String> list, HashMap<String, Boolean> hashMap) {
        if (list != null && !list.isEmpty()) {

            StringBuilder sb = new StringBuilder();
            sb.append("<p><strong>Product Specifics:</strong></p>\n").append("<ul>");

            for (String s : list) {
                addContentWithTag(sb, "li", s, "\n");
            }
            sb.append("</ul>");
            return sb.toString();
        } else {
            return null;
        }
    }

    public void addContentWithTag(StringBuilder sb, String tag, String... content) {

        if (content == null || content.length == 0) {
            return;
        }

        if (sb == null) {
            sb = new StringBuilder();
        }
        sb.append("<").append(tag).append(">");
        for (String s : content) {
            sb.append(s);
        }
        sb.append("</").append(tag).append(">");
    }

    public String getContentWithTag(String tag, String... content) {

        if (content == null || content.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<").append(tag).append(">");
        for (String s : content) {
            sb.append(s);
        }
        sb.append("</").append(tag).append(">");
        return sb.toString();
    }

    public void setImageUrl(ArrayList<String> productImages) {

        main_image_url = MarketUtil.processImgUrl(productImages.get(0));
        DataStore.putProductData(item_sku, "main_image_url", main_image_url);

        if (productImages.size() <= 1) {
            return;
        }

        Class aClass = ProductAmz.class;

        for (int i = 1, size = productImages.size(); i < size; i++) {
            String url = productImages.get(i);
            if (url == null || url.isEmpty()) {
                continue;
            }

            url = MarketUtil.processImgUrl(url);
            DataStore.putProductData(item_sku, "other_image_url" + i, url);

            try {
                Field field = aClass.getField("other_image_url" + i);
                field.set(this, url);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
//                Logger.getLogger(ProductAmz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void setValueForAFiled(String fieldName, String value) {
        Class aClass = ProductAmz.class;

        try {
            Field field = aClass.getField(fieldName);
            field.set(this, value);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException ex) {
//            Logger.getLogger(ProductAmz.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
//    public String swatch_image_url;
//    public String other_image_url1;
//    public String other_image_url2;
//    public String other_image_url3;
//    public String other_image_url4;
//    public String other_image_url5;
//    public String other_image_url6;
//    public String other_image_url7;
//    public String other_image_url8;
    
    public void updateImageUrl(String vpsFolderPath) {
        updateImageUrl(vpsFolderPath, "main_image_url");
        if (Configs.downloadAllImage == 1) {
            updateImageUrl(vpsFolderPath, "other_image_url1");
            updateImageUrl(vpsFolderPath, "other_image_url2");
            updateImageUrl(vpsFolderPath, "other_image_url3");
            updateImageUrl(vpsFolderPath, "other_image_url4");
            updateImageUrl(vpsFolderPath, "other_image_url5");
            updateImageUrl(vpsFolderPath, "other_image_url6");
            updateImageUrl(vpsFolderPath, "other_image_url7");
            updateImageUrl(vpsFolderPath, "other_image_url8");
        }
    }
    
    public void updateImageUrl(String vpsFolderPath, String field) {
        switch (field) {
            case "main_image_url":
                main_image_key = !StringUtils.isEmpty(main_image_url) ? "" + Math.abs(main_image_url.hashCode()) : null;
                if (main_image_key == null) return;

                main_image_vps_name = DownloadManager.getInstance().getFileName(main_image_key);
                if (StringUtils.isEmpty(main_image_vps_name)) {
                    DownloadManager.getInstance().put(main_image_key, main_image_url);
                    main_image_vps_name = item_sku + "_" + main_image_key + ".jpg";
                    DownloadManager.getInstance().putMapFileName(main_image_key, main_image_vps_name);
                }
                main_image_url = vpsFolderPath + main_image_vps_name;
                DataStore.putProductData(item_sku, field, main_image_url);
                break;
            case "other_image_url1":
                other1_image_key = !StringUtils.isEmpty(other_image_url1) ? "" + Math.abs(other_image_url1.hashCode()) : null;
                if (other1_image_key == null) return;

                other1_image_vps_name = DownloadManager.getInstance().getFileName(other1_image_key);
                if (StringUtils.isEmpty(other1_image_vps_name)) {
                    DownloadManager.getInstance().put(other1_image_key, other_image_url1);
                    other1_image_vps_name = item_sku + "_" + other1_image_key + ".jpg";
                    DownloadManager.getInstance().putMapFileName(other1_image_key, other1_image_vps_name);
                }
                other_image_url1 = vpsFolderPath + other1_image_vps_name;
                DataStore.putProductData(item_sku, field, other_image_url1);
                break;
            case "other_image_url2":
                other2_image_key = !StringUtils.isEmpty(other_image_url2) ? "" + Math.abs(other_image_url2.hashCode()) : null;
                if (other2_image_key == null) return;

                other2_image_vps_name = DownloadManager.getInstance().getFileName(other2_image_key);
                if (StringUtils.isEmpty(other2_image_vps_name)) {
                    DownloadManager.getInstance().put(other2_image_key, other_image_url2);
                    other2_image_vps_name = item_sku + "_" + other2_image_key + ".jpg";
                    DownloadManager.getInstance().putMapFileName(other2_image_key, other2_image_vps_name);
                }
                other_image_url2 = vpsFolderPath + other2_image_vps_name;
                DataStore.putProductData(item_sku, field, other_image_url2);
                break;
            case "other_image_url3":
                other3_image_key = !StringUtils.isEmpty(other_image_url3) ? "" + Math.abs(other_image_url3.hashCode()) : null;
                if (other3_image_key == null) return;

                other3_image_vps_name = DownloadManager.getInstance().getFileName(other3_image_key);
                if (StringUtils.isEmpty(other3_image_vps_name)) {
                    DownloadManager.getInstance().put(other3_image_key, other_image_url3);
                    other3_image_vps_name = item_sku + "_" + other3_image_key + ".jpg";
                    DownloadManager.getInstance().putMapFileName(other3_image_key, other3_image_vps_name);
                }
                other_image_url3 = vpsFolderPath + other3_image_vps_name;
                DataStore.putProductData(item_sku, field, other_image_url3);
                break;
            case "other_image_url4":
                other4_image_key = !StringUtils.isEmpty(other_image_url4) ? "" + Math.abs(other_image_url4.hashCode()) : null;
                if (other4_image_key == null) return;

                other4_image_vps_name = DownloadManager.getInstance().getFileName(other4_image_key);
                if (StringUtils.isEmpty(other4_image_vps_name)) {
                    DownloadManager.getInstance().put(other4_image_key, other_image_url4);
                    other4_image_vps_name = item_sku + "_" + other4_image_key + ".jpg";
                    DownloadManager.getInstance().putMapFileName(other4_image_key, other4_image_vps_name);
                }
                other_image_url4 = vpsFolderPath + other4_image_vps_name;
                DataStore.putProductData(item_sku, field, other_image_url4);
                break;
            case "other_image_url5":
                other5_image_key = !StringUtils.isEmpty(other_image_url5) ? "" + Math.abs(other_image_url5.hashCode()) : null;
                if (other5_image_key == null) return;

                other5_image_vps_name = DownloadManager.getInstance().getFileName(other5_image_key);
                if (StringUtils.isEmpty(other5_image_vps_name)) {
                    DownloadManager.getInstance().put(other5_image_key, other_image_url5);
                    other5_image_vps_name = item_sku + "_" + other5_image_key + ".jpg";
                    DownloadManager.getInstance().putMapFileName(other5_image_key, other5_image_vps_name);
                }
                other_image_url5 = vpsFolderPath + other5_image_vps_name;
                DataStore.putProductData(item_sku, field, other_image_url5);
                break;
            case "other_image_url6":
                other6_image_key = !StringUtils.isEmpty(other_image_url6) ? "" + Math.abs(other_image_url6.hashCode()) : null;
                if (other6_image_key == null) return;

                other6_image_vps_name = DownloadManager.getInstance().getFileName(other6_image_key);
                if (StringUtils.isEmpty(other6_image_vps_name)) {
                    DownloadManager.getInstance().put(other6_image_key, other_image_url6);
                    other6_image_vps_name = item_sku + "_" + other6_image_key + ".jpg";
                    DownloadManager.getInstance().putMapFileName(other6_image_key, other6_image_vps_name);
                }
                other_image_url6 = vpsFolderPath + other6_image_vps_name;
                DataStore.putProductData(item_sku, field, other_image_url6);
                break;
            case "other_image_url7":
                other7_image_key = !StringUtils.isEmpty(other_image_url7) ? "" + Math.abs(other_image_url7.hashCode()) : null;
                if (other7_image_key == null) return;

                other7_image_vps_name = DownloadManager.getInstance().getFileName(other7_image_key);
                if (StringUtils.isEmpty(other7_image_vps_name)) {
                    DownloadManager.getInstance().put(other7_image_key, other_image_url7);
                    other7_image_vps_name = item_sku + "_" + other7_image_key + ".jpg";
                    DownloadManager.getInstance().putMapFileName(other7_image_key, other7_image_vps_name);
                }
                other_image_url7 = vpsFolderPath + other7_image_vps_name;
                DataStore.putProductData(item_sku, field, other_image_url7);
                break;
            case "other_image_url8":
                other8_image_key = !StringUtils.isEmpty(other_image_url8) ? "" + Math.abs(other_image_url8.hashCode()) : null;
                if (other8_image_key == null) return;

                other8_image_vps_name = DownloadManager.getInstance().getFileName(other8_image_key);
                if (StringUtils.isEmpty(other8_image_vps_name)) {
                    DownloadManager.getInstance().put(other8_image_key, other_image_url8);
                    other8_image_vps_name = item_sku + "_" + other8_image_key + ".jpg";
                    DownloadManager.getInstance().putMapFileName(other8_image_key, other8_image_vps_name);
                }
                other_image_url8 = vpsFolderPath + other8_image_vps_name;
                DataStore.putProductData(item_sku, field, other_image_url8);
                break;
        }
    }

    public void setShipping_method(String shipping_method) {
        this.shipping_method = shipping_method;
        DataStore.putProductData(item_sku, "shipping_method", shipping_method);
    }
    
    public void updateImageUrl(ArrayList<ImagePathModel> productImages) {
        if (productImages == null || productImages.isEmpty()) {
            return;
        }

        main_image_url = MarketUtil.processImgUrl(productImages.get(0).url);
        DataStore.putProductData(item_sku, "main_image_url", main_image_url);

        if (productImages.size() <= 1) {
            return;
        }

        Class aClass = ProductAmz.class;

        for (int i = 1, size = productImages.size(); i < size; i++) {
            String url = productImages.get(i).url;
            if (url == null || url.isEmpty()) {
                continue;
            }

            url = MarketUtil.processImgUrl(url);
            DataStore.putProductData(item_sku, "other_image_url" + i, url);

            try {
                Field field = aClass.getField("other_image_url" + i);
                field.set(this, url);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
//                Logger.getLogger(ProductAmz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public void updateRapidImageUrl(ArrayList<String> productImages) {
        if (productImages == null || productImages.isEmpty()) {
            return;
        }

        main_image_url = MarketUtil.processImgUrl(productImages.get(0));
        DataStore.putProductData(item_sku, "main_image_url", main_image_url);

        if (productImages.size() <= 1) {
            return;
        }

        Class aClass = ProductAmz.class;

        for (int i = 1, size = productImages.size(); i < size; i++) {
            String url = productImages.get(i);
            if (url == null || url.isEmpty()) {
                continue;
            }

            url = MarketUtil.processImgUrl(url);
            DataStore.putProductData(item_sku, "other_image_url" + i, url);

            try {
                Field field = aClass.getField("other_image_url" + i);
                field.set(this, url);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
//                Logger.getLogger(ProductAmz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public void updateImageUrlForChild(ArrayList<ImagePathModel> productImages) {
        if (productImages.size() <= 1) {
            return;
        }

        Class aClass = ProductAmz.class;

        for (int i = 1, size = productImages.size(); i < size; i++) {
            String url = productImages.get(i).url;
            if (url == null || url.isEmpty()) {
                continue;
            }

            url = MarketUtil.processImgUrl(url);
            DataStore.putProductData(item_sku, "other_image_url" + i, url);

            try {
                Field field = aClass.getField("other_image_url" + i);
                field.set(this, url);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
//                Logger.getLogger(ProductAmz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public void updateRapidImageUrlForChild(ArrayList<String> productImages) {
        if (productImages.size() <= 1) {
            return;
        }

        Class aClass = ProductAmz.class;

        for (int i = 1, size = productImages.size(); i < size; i++) {
            String url = productImages.get(i);
            if (url == null || url.isEmpty()) {
                continue;
            }

            url = MarketUtil.processImgUrl(url);
            DataStore.putProductData(item_sku, "other_image_url" + i, url);

            try {
                Field field = aClass.getField("other_image_url" + i);
                field.set(this, url);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
//                Logger.getLogger(ProductAmz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public void setBulletPoints(ArrayList<String> listBulletPoints) {
        if (listBulletPoints != null && !listBulletPoints.isEmpty()) {
            setBullet_point1(listBulletPoints.get(0));
            if (listBulletPoints.size() > 1) {
                setBullet_point2(listBulletPoints.get(1));
                if (listBulletPoints.size() > 2) {
                    setBullet_point3(listBulletPoints.get(2));
                    if (listBulletPoints.size() > 3) {
                        setBullet_point4(listBulletPoints.get(3));
                        if (listBulletPoints.size() > 4) {
                            setBullet_point5(listBulletPoints.get(4));
                        }
                    }
                }
            }
        }
    }
}
