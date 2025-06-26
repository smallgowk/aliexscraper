/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.controller.transform;

import com.phanduy.aliexscrap.config.Configs;
import com.phanduy.aliexscrap.model.aliex.AliexProductFull;
import com.phanduy.aliexscrap.model.aliex.PriceFull;
import com.phanduy.aliexscrap.model.amazon.ProductAmz;
import com.phanduy.aliexscrap.model.amazon.ProductTypes;
import com.phanduy.aliexscrap.model.aliex.store.AliexStoreInfo;
import com.phanduy.aliexscrap.model.amazon.AmzContentFormat;
import com.phanduy.aliexscrap.model.request.ImagePathModel;
import com.phanduy.aliexscrap.model.response.BulletPointSearchTerm;
import com.phanduy.aliexscrap.model.response.TransformCrawlResponse;
import com.phanduy.aliexscrap.utils.StringUtils;
import com.phanduy.aliexscrap.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 * @author PhanDuy
 */
public class ProcessTransformAliexToAmz {

    public static HashMap<String, String> setBannedProduct = new HashMap<>();
    
    public static ArrayList<ProductAmz> transform(TransformCrawlResponse res, AliexStoreInfo aliexStoreInfo, ArrayList<ImagePathModel> imagePathModels) {
        ArrayList<ProductAmz> results = new ArrayList<>();
        ProductAmz parent = new ProductAmz();
        parent.setItem_sku(res.baseAmzProduct.item_sku);
        parent.copyAllProduct(res.baseAmzProduct);
        updateDescription(parent, res, aliexStoreInfo);
        updateBulletPoints(parent, res, aliexStoreInfo);
        parent.updateImageUrl(imagePathModels);
        results.add(parent);
        if (res.childs != null && !res.childs.isEmpty()) {
            res.childs.forEach((child) -> {
                child.copyProduct(parent);
                if (StringUtils.isEmpty(child.main_image_url)) {
                    child.main_image_url = parent.main_image_url;
                }
                child.updateImageUrlForChild(imagePathModels);
            });
            results.addAll(res.childs);
        } else {
            if (StringUtils.isEmpty(res.baseAmzProduct.standard_price)) {
                parent.setStandard_price("" + Utils.getCEOPrice(res.origiPrice));
            } else {
                parent.setStandard_price(res.baseAmzProduct.standard_price);
            }
        }
        return results;
    }
    
    public static ArrayList<ProductAmz> transformRapid(TransformCrawlResponse res, AliexStoreInfo aliexStoreInfo) {
        ArrayList<ProductAmz> results = new ArrayList<>();
        ProductAmz parent = new ProductAmz();
        parent.setItem_sku(res.baseAmzProduct.item_sku);
        parent.copyAllProduct(res.baseAmzProduct);
        parent.updateRapidImageUrl(res.imagePaths);
        if (StringUtils.isEmpty(Configs.template)) {
            if (!Configs.ai) {
                updateDescription(parent, res, aliexStoreInfo);
                updateBulletPoints(parent, res, aliexStoreInfo);
            } else {
                parent.setProduct_description(res.htmlDescription);
                parent.setBulletPoints(res.bulletPoints);
            }
        }
        results.add(parent);
        if (res.childs != null && !res.childs.isEmpty()) {
            res.childs.forEach((child) -> {
                child.copyProduct(parent);
                if (StringUtils.isEmpty(Configs.template)) {
                    if (StringUtils.isEmpty(child.main_image_url)) {
                        child.main_image_url = parent.main_image_url;
                    }
                    child.updateRapidImageUrlForChild(res.imagePaths);
                } else {
                    child.updateRapidImageUrl(res.imagePaths);
                }
            });
            results.addAll(res.childs);
            parent.hasChild = true;
        } else {
            if (StringUtils.isEmpty(res.baseAmzProduct.standard_price)) {
                parent.setStandard_price("" + Utils.getCEOPrice(res.origiPrice));
            } else {
                parent.setStandard_price(res.baseAmzProduct.standard_price);
            }
            parent.child_image_url = parent.main_image_url;
            parent.hasChild = false;
        }
        return results;
    }
    
    public static void updateDescription(ProductAmz productAmz, TransformCrawlResponse res, AliexStoreInfo storePageInfo) {
        String descriptionForm = storePageInfo.getDescription();
        String tips = storePageInfo.getTip();
        String reasons = storePageInfo.getReasons();
        
        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.TITLE_KEY, res.baseAmzProduct.item_name, false);
        descriptionForm = replaceAllPattern(descriptionForm, AmzContentFormat.MAIN_KEYWORD_KEY, storePageInfo.getMain_key(), true);
        descriptionForm = replaceAllPattern(descriptionForm, AmzContentFormat.BRANDNAME_KEY, storePageInfo.getBrandName(), false);
        
        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.SPECIFIC_KEY, !StringUtils.isEmpty(res.htmlSpecifics) ? res.htmlSpecifics : "", false);
        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.TIPS_KEY, !StringUtils.isEmpty(tips) && res.showTip ? tips : "", false);
        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.REASON_KEY, !StringUtils.isEmpty(reasons) && res.showReason ? reasons : "", false);
        
        descriptionForm = replacePattern(descriptionForm, AmzContentFormat.DESCRIPTION_KEY, !StringUtils.isEmpty(res.htmlDescription) ? res.htmlDescription : "", false);
        productAmz.setProduct_description(descriptionForm);
    }
    
    public static void updateBulletPoints(ProductAmz productAmz, TransformCrawlResponse res, AliexStoreInfo storePageInfo) {
        BulletPointSearchTerm bulletPointSearchTerm = res.bulletPointSearchTerm != null ? res.bulletPointSearchTerm : new BulletPointSearchTerm("", "", "", "", "");
        if (storePageInfo.listBulletPoints != null && !storePageInfo.listBulletPoints.isEmpty()) {
            if (storePageInfo.listBulletPoints.size() > 0) {
                String bullet1 = storePageInfo.listBulletPoints.get(0).replace(
                                AmzContentFormat.SEARCH_TERM_1,
                                bulletPointSearchTerm.searchTerm1
                        );
                bullet1 = bullet1.replace(AmzContentFormat.TITLE_KEY, productAmz.item_name);
                productAmz.setBullet_point1(bullet1);
                if (storePageInfo.listBulletPoints.size() > 1) {
                    String bullet2 = storePageInfo.listBulletPoints.get(1).replace(
                                    AmzContentFormat.SEARCH_TERM_2,
                                    bulletPointSearchTerm.searchTerm2
                            );
                    bullet2 = bullet2.replace(AmzContentFormat.TITLE_KEY, productAmz.item_name);
                    productAmz.setBullet_point2(bullet2);
                    if (storePageInfo.listBulletPoints.size() > 2) {
                        String bullet3 = storePageInfo.listBulletPoints.get(2).replace(
                                        AmzContentFormat.SEARCH_TERM_3,
                                        bulletPointSearchTerm.searchTerm3
                                );
                        bullet3 = bullet3.replace(AmzContentFormat.TITLE_KEY, productAmz.item_name);
                        productAmz.setBullet_point3(bullet3);
                        if (storePageInfo.listBulletPoints.size() > 3) {
                            String bullet4 = storePageInfo.listBulletPoints.get(3).replace(
                                            AmzContentFormat.SEARCH_TERM_4,
                                            bulletPointSearchTerm.searchTerm4
                                    );
                            bullet4 = bullet4.replace(AmzContentFormat.TITLE_KEY, productAmz.item_name);
                            productAmz.setBullet_point4(bullet4);
                            if (storePageInfo.listBulletPoints.size() > 4) {
                                String bullet5 = storePageInfo.listBulletPoints.get(4).replace(
                                                AmzContentFormat.SEARCH_TERM_5,
                                                bulletPointSearchTerm.searchTerm5
                                        );
                                bullet5 = bullet5.replace(AmzContentFormat.TITLE_KEY, productAmz.item_name);
                                productAmz.setBullet_point5(bullet5);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static String replacePattern(String form, String key, String content, boolean isNeedUpperPrefix) {
        if (content == null || content.isEmpty()) {
            return form.replace(key, "");
        }

        return form.replace(key, isNeedUpperPrefix ? StringUtils.getPrefixCapitalWord(content) : content);
    }
    
    public static String replaceAllPattern(String form, String key, String content, boolean isNeedUpperPrefix) {
        if (content == null || content.isEmpty()) {
            return form.replaceAll(Pattern.quote(key), "");
        }

        return form.replaceAll(Pattern.quote(key), isNeedUpperPrefix ? StringUtils.getPrefixCapitalWord(content) : content);
    }
    
    public static boolean isHasVarition(ArrayList<PriceFull> prices) {
        if (prices == null || prices.isEmpty()) {
            return false;
        }

        if (prices.size() == 1) {
            PriceFull priceFull = prices.get(0);
            return priceFull.isHasVariation();
        }

        for (PriceFull priceFull : prices) {
            if (priceFull.isHasVariation()) {
                return true;
            }
        }

        return false;
    }

    public static ProductAmz createBasicProductAmz(AliexProductFull aliexProductFull, AliexStoreInfo aliexStoreInfo, float origiPrice) {
        ProductAmz productAmz = new ProductAmz();
//        productAmz.setItem_sku(aliexStoreInfo.getPrefix().toUpperCase() + FuncUtil.createSaltNumber(5) + "_" + aliexProductFull.getId());
        productAmz.setItem_sku(aliexProductFull.item_sku);
        productAmz.setAliexId(aliexProductFull.getId());
        productAmz.setExternal_product_id_type("UPC");
        productAmz.setFeed_product_type(aliexStoreInfo.getProductType());
        productAmz.setQuantity("100");
        productAmz.setFulfillment_latency("5");
        productAmz.setMfg_minimum("10");
        productAmz.setUnit_count("1");
        productAmz.setUnit_count_type("PC");
        productAmz.setItem_package_quantity("1");
        productAmz.setNumber_of_items("1");
        productAmz.setMaterial_type("other");
        productAmz.setBrand_name(aliexStoreInfo.getBrandName());
        productAmz.setImageUrl(aliexProductFull.getProductImages());
        productAmz.setShipping_method(aliexProductFull.shipingMethod);
        
        if (!StringUtils.isEmpty(aliexStoreInfo.getMain_key())) {
            productAmz.setMain_keywords(aliexStoreInfo.getMain_key());
        }

        productAmz.setBullet_point1(aliexProductFull.bullet_point1);
        productAmz.setBullet_point2(aliexProductFull.bullet_point2);
        productAmz.setBullet_point3(aliexProductFull.bullet_point3);
        productAmz.setBullet_point4(aliexProductFull.bullet_point4);
        productAmz.setBullet_point5(aliexProductFull.bullet_point5);
        productAmz.setPart_number(productAmz.getItem_sku().substring(0, productAmz.getItem_sku().length() - 2));

        productAmz.setItem_name(aliexProductFull.getTitle());
        productAmz.setGeneric_keywords(aliexProductFull.generic_keywords);
        productAmz.setProduct_description(aliexProductFull.description);
        productAmz.setItem_name(aliexProductFull.getTitle());

        productAmz.setItem_type(aliexStoreInfo.getItemType());
        productAmz.setTarget_audience_keywords(aliexStoreInfo.getAudienceKeyword());
        productAmz.setTarget_audience_keywords1(aliexStoreInfo.getAudienceKeyword());
        productAmz.setDepartment_name(aliexStoreInfo.getDepartmentName());

        productAmz.setUnit_count_type("PC");
        productAmz.setMaterial_type("other");
        productAmz.setManufacturer(productAmz.getBrand_name());
        productAmz.setType(ProductTypes.TYPE_NORMAL, aliexStoreInfo);
        

        return productAmz;
    }
}
