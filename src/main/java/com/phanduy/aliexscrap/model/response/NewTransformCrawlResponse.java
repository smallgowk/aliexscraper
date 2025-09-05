package com.phanduy.aliexscrap.model.response;

import com.phanduy.aliexscrap.model.NewProductAmz;
import com.phanduy.aliexscrap.model.SettingInfo;
import com.phanduy.aliexscrap.utils.ScrapUtil;
import com.phanduy.aliexscrap.utils.StringUtils;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static com.phanduy.aliexscrap.model.SettingInfo.*;

public class NewTransformCrawlResponse {
    public String productId;
    public NewProductAmz baseAmzProduct;
    public float origiPrice;
    public float shippingPrice;
    public ArrayList<NewProductAmz> childs;
    public ArrayList<String> imagePaths;
    public String htmlDescription;
    public ArrayList<String> bulletPoints;
    public String promptDes;
    public String promptBullet;
    public String htmlSpecifics;
    public boolean showReason;
    public boolean showTip;
    public NewBulletPointSearchTerm bulletPointSearchTerm;

    public int requestCount;
    public int remainRequest;

    public ArrayList<NewProductAmz> genListProducts(SettingInfo settingInfo) {
        ArrayList<NewProductAmz> results = new ArrayList<>();
        NewProductAmz parent = new NewProductAmz();
        parent.setItem_sku(baseAmzProduct.item_sku);
        parent.copyAllProduct(baseAmzProduct);
        parent.updateRapidImageUrl(imagePaths);
        if (!settingInfo.isNewTemplate()) {
            if (!settingInfo.isUsingAI()) {
                updateDescription(parent, settingInfo);
                updateBulletPoints(parent, settingInfo);
            } else {
                parent.setProduct_description(htmlDescription);
                parent.setBulletPoints(bulletPoints);
            }
        }
        results.add(parent);
        if (childs != null && !childs.isEmpty()) {
            childs.forEach((child) -> {
                child.copyProduct(parent);
                if (!settingInfo.isNewTemplate()) {
                    if (StringUtils.isEmpty(child.main_image_url)) {
                        child.main_image_url = parent.main_image_url;
                    }
                    child.updateRapidImageUrlForChild(imagePaths);
                } else {
                    child.updateRapidImageUrl(imagePaths);
                }
            });
            results.addAll(childs);
            parent.hasChild = true;
        } else {
            if (StringUtils.isEmpty(baseAmzProduct.standard_price)) {
                parent.setStandard_price("" + ScrapUtil.getCEOPrice(origiPrice));
            } else {
                parent.setStandard_price(baseAmzProduct.standard_price);
            }
            parent.child_image_url = parent.main_image_url;
            parent.hasChild = false;
        }
        return results;
    }

    public void updateDescription(NewProductAmz productAmz, SettingInfo settingInfo) {
        String descriptionForm = settingInfo.getTempDescription();
        String tips = settingInfo.getTempTips();
        String reasons = settingInfo.getTempReasons();

        descriptionForm = replacePattern(descriptionForm, TITLE_KEY, baseAmzProduct.item_name, false);
        descriptionForm = replaceAllPattern(descriptionForm, MAIN_KEYWORD_KEY, settingInfo.getMainKey(), true);
        descriptionForm = replaceAllPattern(descriptionForm, BRANDNAME_KEY, settingInfo.getBrandName(), false);

        descriptionForm = replacePattern(descriptionForm, SPECIFIC_KEY, !StringUtils.isEmpty(htmlSpecifics) ? htmlSpecifics : "", false);
        descriptionForm = replacePattern(descriptionForm, TIPS_KEY, !StringUtils.isEmpty(tips) && showTip ? tips : "", false);
        descriptionForm = replacePattern(descriptionForm, REASON_KEY, !StringUtils.isEmpty(reasons) && showReason ? reasons : "", false);

        descriptionForm = replacePattern(descriptionForm, DESCRIPTION_KEY, !StringUtils.isEmpty(htmlDescription) ? htmlDescription : "", false);
        productAmz.setProduct_description(descriptionForm);
    }

    public void updateBulletPoints(NewProductAmz productAmz, SettingInfo settingInfo) {
        NewBulletPointSearchTerm tempTerm = bulletPointSearchTerm != null ? bulletPointSearchTerm : new NewBulletPointSearchTerm("", "", "", "", "");
        if (settingInfo.getListBulletPoints() != null && !settingInfo.getListBulletPoints().isEmpty()) {
            String bullet1 = settingInfo.getListBulletPoints().get(0).replace(
                    SEARCH_TERM_1,
                    bulletPointSearchTerm.searchTerm1
            );
            bullet1 = bullet1.replace(TITLE_KEY, productAmz.item_name);
            productAmz.setBullet_point1(bullet1);
            if (settingInfo.getListBulletPoints().size() > 1) {
                String bullet2 = settingInfo.getListBulletPoints().get(1).replace(
                        SEARCH_TERM_2,
                        bulletPointSearchTerm.searchTerm2
                );
                bullet2 = bullet2.replace(TITLE_KEY, productAmz.item_name);
                productAmz.setBullet_point2(bullet2);
                if (settingInfo.getListBulletPoints().size() > 2) {
                    String bullet3 = settingInfo.getListBulletPoints().get(2).replace(
                            SEARCH_TERM_3,
                            bulletPointSearchTerm.searchTerm3
                    );
                    bullet3 = bullet3.replace(TITLE_KEY, productAmz.item_name);
                    productAmz.setBullet_point3(bullet3);
                    if (settingInfo.getListBulletPoints().size() > 3) {
                        String bullet4 = settingInfo.getListBulletPoints().get(3).replace(
                                SEARCH_TERM_4,
                                bulletPointSearchTerm.searchTerm4
                        );
                        bullet4 = bullet4.replace(TITLE_KEY, productAmz.item_name);
                        productAmz.setBullet_point4(bullet4);
                        if (settingInfo.getListBulletPoints().size() > 4) {
                            String bullet5 = settingInfo.getListBulletPoints().get(4).replace(
                                    SEARCH_TERM_5,
                                    bulletPointSearchTerm.searchTerm5
                            );
                            bullet5 = bullet5.replace(TITLE_KEY, productAmz.item_name);
                            productAmz.setBullet_point5(bullet5);
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
}
