/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex.store;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author duyuno
 */
public class BaseStoreInfo {

//    public BaseStoreOrderInfo baseStoreOrderInfo;
//    public BTGNode bTGNode;
    public ArrayList<String> listBulletPoints;
    public ArrayList<String> listKeyWords;
    public String variationThemeBoth;
    public String variationThemeColor;
    public String variationThemeSize;

    public String productType;

    public String brandName;
    public String link;
    public String colorMap;
    public String sizeMap;
    public ArrayList<String> fullAudienceTarget;
    public String itemType;
    public String targetAudience;
    public String departmentName;
    public String audienceKeyword;

    public float priceRate;
    public float priceLimit;
    public int dataSaveType;
    public int dataLv;
    public int totalPage;
    public String region;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAudienceKeyword() {
        return audienceKeyword;
    }

    public void setAudienceKeyword(String audienceKeyword) {
        this.audienceKeyword = audienceKeyword;
    }

//    public BaseStoreOrderInfo getBaseStoreOrderInfo() {
//        return baseStoreOrderInfo;
//    }
//
//    public void setBaseStoreOrderInfo(BaseStoreOrderInfo baseStoreOrderInfo) {
//        this.baseStoreOrderInfo = baseStoreOrderInfo;
//    }
    public float getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(float priceRate) {
        this.priceRate = priceRate;
    }

    public float getPriceLimit() {
        return priceLimit;
    }

    public void setPriceLimit(float priceLimit) {
        this.priceLimit = priceLimit;
    }

    public int getDataSaveType() {
        return dataSaveType;
    }

    public void setDataSaveType(int dataSaveType) {
        this.dataSaveType = dataSaveType;
    }

    public int getDataLv() {
        return dataLv;
    }

    public void setDataLv(int dataLv) {
        this.dataLv = dataLv;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setRegion(String region) {
        this.region = region;
    }

//    public BTGNode getbTGNode() {
//        return bTGNode;
//    }
//
//    public void setbTGNode(BTGNode bTGNode) {
//        this.bTGNode = bTGNode;
//    }
//
//    public String getTempleFile() {
//        if (bTGNode == null) {
//            return "Home.xlsx";
//        }
//        return bTGNode.getTemplateFile();
//    }
    public ArrayList<String> getListBulletPoints() {
        return listBulletPoints;
    }

    public void setListBulletPoints(ArrayList<String> listBulletPoints) {
        this.listBulletPoints = listBulletPoints;
    }

    public ArrayList<String> getListKeyWords() {
        return listKeyWords;
    }

    public void setListKeyWords(ArrayList<String> listKeyWords) {
        this.listKeyWords = listKeyWords;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getVariationThemeBoth() {
        return variationThemeBoth;
    }

    public void setVariationThemeBoth(String variationThemeBoth) {
        this.variationThemeBoth = variationThemeBoth;
    }

    public String getVariationThemeColor() {
        return variationThemeColor;
    }

    public void setVariationThemeColor(String variationThemeColor) {
        this.variationThemeColor = variationThemeColor;
    }

    public String getVariationThemeSize() {
        return variationThemeSize;
    }

    public void setVariationThemeSize(String variationThemeSize) {
        this.variationThemeSize = variationThemeSize;
    }

    public String getColorMap() {
        return colorMap;
    }

    public void setColorMap(String colorMap) {
        this.colorMap = colorMap;
    }

    public String getSizeMap() {
        return sizeMap;
    }

    public void setSizeMap(String sizeMap) {
        this.sizeMap = sizeMap;
    }

    public ArrayList<String> getFullAudienceTarget() {
        return fullAudienceTarget;
    }

    public void setFullAudienceTarget(ArrayList<String> fullAudienceTarget) {
        this.fullAudienceTarget = fullAudienceTarget;
    }

    public void initValidValues(HashMap<String, ArrayList<String>> hashMap) {
        if (hashMap != null) {
            fullAudienceTarget = hashMap.get("target_audience_keywords");
            if (fullAudienceTarget == null) {
                fullAudienceTarget = hashMap.get("target_audience_keywords1");
            }

            ArrayList<String> listValues = hashMap.get("variation_theme");
            String bothSub = null;
            String colorSub = null;
            String sizeSub = null;

            if (listValues != null && !listValues.isEmpty()) {
                for (String s : listValues) {
                    String check = s.toLowerCase();
                    if (check.contains("sizename") && check.contains("colorname")) {
                        variationThemeBoth = "SizeName-ColorName";
                        continue;
                    }

                    if (check.contains("size") && check.contains("color")) {
                        bothSub = s;
                        continue;
                    }

                    if (check.equals("colorname")) {
                        variationThemeColor = "Color_Name";
                        continue;
                    }

                    if (check.contains("color")) {
                        colorSub = s;
                        continue;
                    }

                    if (check.equals("sizename")) {
                        variationThemeSize = "Size_Name";
                        continue;
                    }

                    if (check.contains("size")) {
                        sizeSub = s;
                    }
                }
            } else {
                variationThemeBoth = "SizeName-ColorName";
                variationThemeColor = "Color_Name";
                variationThemeSize = "Size_Name";
            }

            if (variationThemeBoth == null) {
                variationThemeBoth = bothSub;
            }
            if (variationThemeColor == null) {
                variationThemeColor = colorSub;
            }
            if (variationThemeSize == null) {
                variationThemeSize = sizeSub;
            }

            System.out.println("VARIATION_THEME_BOTH: " + variationThemeBoth);
            System.out.println("VARIATION_THEME_COLOR: " + variationThemeColor);
            System.out.println("VARIATION_THEME_SIZE: " + variationThemeSize);

            ArrayList<String> listColorMap = hashMap.get("color_map");
            if (listColorMap != null) {
                for (String colors : listColorMap) {
                    if (colors.toLowerCase().equals("white")) {
                        colorMap = colors;
                        break;
                    }
                }

                if (colorMap == null) {
                    colorMap = listColorMap.get(0) != null ? listColorMap.get(0) : "white";
                }
            }

            ArrayList<String> listSizeMap = hashMap.get("size_map");
            if (listSizeMap != null) {
                for (String sizes : listSizeMap) {
                    if (sizes.toLowerCase().equals("l") || sizes.toLowerCase().equals("large")) {
                        sizeMap = sizes;
                        break;
                    }
                }

                if (sizeMap == null) {
                    sizeMap = listSizeMap.get(0);
                }
            }

        } else {
            variationThemeBoth = "SizeName-ColorName";
            variationThemeColor = "Color_Name";
            variationThemeSize = "Size_Name";
            colorMap = "white";
            sizeMap = "large";
        }

    }

}
