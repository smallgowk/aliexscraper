/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.model.aliex.store;

import com.phanduy.aliexscrap.config.Configs;
import com.phanduy.aliexscrap.model.amazon.BTGNode;
import com.phanduy.aliexscrap.model.amazon.BrowseTreeManager;
import com.phanduy.aliexscrap.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author duyuno
 */
public class AliexStoreCommon {

    public String storeId;
    public String categoryId;
    public String searchKey;
    public String brandName;
    public String productType;
    public String accNo;
    public BTGNode btgNode;
    public String categoryPath;
    public String mainKey;
    public String marketName;
    public ArrayList<String> listKeyWords;
    public String tips;
    public ArrayList<String> listBulletPoints;
    public String reasons;
    public String descriptionForm;
    public String url;
    public boolean isCheckBannedWords;
    public boolean isOnlyUS;
    public float priceRate;
    public float priceLimit;
    public int totalPage;
    public int sendEmailMode;
    public String btgTempleFile;
    public String variationThemeBoth;
    public String variationThemeColor;
    public String variationThemeSize;
    public String colorMap;
    public String sizeMap;
    public String storeSign;
    public ArrayList<String> fullAudienceTarget;
    
    public String getStoreId() {
        return storeId;
    }
    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public String getBtgTempleFile() {
        return btgTempleFile;
    }

    public void setBtgTempleFile(String btgTempleFile) {
        this.btgTempleFile = btgTempleFile;
    }

    public ArrayList<String> getFullAudienceTarget() {
        return fullAudienceTarget;
    }

    public void setFullAudienceTarget(ArrayList<String> fullAudienceTarget) {
        this.fullAudienceTarget = fullAudienceTarget;
    }
    
    

    public String getMainKey() {
        return mainKey;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
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

    public String getStoreSign() {
        return storeSign;
    }

    public void setStoreSign(String storeSign) {
        this.storeSign = storeSign;
    }
    
    public void initBTG() {
        String[] btgParts = categoryPath.split(Pattern.quote("/"));
        btgNode = BrowseTreeManager.getBTGNode(btgParts[btgParts.length - 1].trim());
    }
    
    
    public String getTempleFile() {
        if (btgNode == null) {
            return "Home.xlsx";
        }
        return btgNode.getTemplateFile();
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public ArrayList<String> getListKeyWords() {
        return listKeyWords;
    }

    public int getSendEmailMode() {
        return sendEmailMode;
    }

    public void setSendEmailMode(int sendEmailMode) {
        this.sendEmailMode = sendEmailMode;
    }

    public void setListKeyWords(ArrayList<String> listKeyWords) {
        this.listKeyWords = listKeyWords;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

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

    public BTGNode getBtgNode() {
        return btgNode;
    }

    public void setBtgNode(BTGNode btgNode) {
        this.btgNode = btgNode;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public ArrayList<String> getListBulletPoints() {
        return listBulletPoints;
    }

    public void setListBulletPoints(ArrayList<String> listBulletPoints) {
        this.listBulletPoints = listBulletPoints;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getDescriptionForm() {
        return descriptionForm;
    }

    public void setDescriptionForm(String descriptionForm) {
        this.descriptionForm = descriptionForm;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

//    public ArrayList<AliexPageInfo> getListPages() {
//        return listPages;
//    }
//
//    public void setListPages(ArrayList<AliexPageInfo> listPages) {
//        this.listPages = listPages;
//    }
//    
//    public void addAliexPageInfo(AliexPageInfo aliexPageInfo) {
//        if(listPages == null) {
//            listPages = new ArrayList<>();
//        }
//        
//        listPages.add(aliexPageInfo);
//    }
    public String getCacheFile() {
        StringBuilder sb = new StringBuilder();
        sb.append(storeId);

        if (!StringUtils.isEmpty(categoryId)) {
            sb.append("_").append(categoryId);
        }

        if (!StringUtils.isEmpty(searchKey)) {
            sb.append("_").append(searchKey);
        }

        return sb.toString();
    }

    public String prefix;

    public String getPrefix() {

        if (prefix != null) {
            return prefix;
        }

        String brandBrief = StringUtils.getStringBrief(brandName);
        if (brandBrief.length() > 2) {
            brandBrief = brandBrief.substring(0, 2);
        }
        prefix = brandBrief.toUpperCase();
        return prefix;
    }

    public boolean isIsCheckBannedWords() {
        return isCheckBannedWords;
    }

    public void setIsCheckBannedWords(boolean isCheckBannedWords) {
        this.isCheckBannedWords = isCheckBannedWords;
    }

    public boolean isIsOnlyUS() {
        return isOnlyUS;
    }

    public void setIsOnlyUS(boolean isOnlyUS) {
        this.isOnlyUS = isOnlyUS;
    }

    public String genExcelFileNameWithPage(int pageIndex) {
        if (pageIndex == 0) {
            pageIndex = 1;
        }

        File file = new File(Configs.PRODUCT_DATA_PATH + accNo);

        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getPath() + Configs.pathChar + "Aliex");
        if (!file.exists()) {
            file.mkdir();
        }

        String folder = getCacheFile();

        file = new File(file.getPath() + Configs.pathChar + folder);

        if (!file.exists()) {
            file.mkdir();
        }

        return file.getPath() + Configs.pathChar + accNo + "_" + folder + "_page" + pageIndex + ".xlsx";

    }
    
    

}
