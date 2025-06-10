/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.model.request;

import com.phanduy.aliexscrap.model.aliex.store.AliexStoreInfo;
import com.phanduy.aliexscrap.utils.ComputerIdentifier;
import com.phanduy.aliexscrap.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author PhanDuy
 */
public class ToolAndStoreInfo {
    public String diskSerialNumber;
    public boolean isOnlyUs;
    public int filterAliexpress;
    public int filterEpacket;
    public int filterAliDirect;
    public int fetchingAliexKeywords;
    public int fetchingImageFromDes;
    
    public String main_key;
    public String storeSign;
    public String accNo;
    public int bullet1Length;
    public int bullet2Length;
    public int bullet3Length;
    public int bullet4Length;
    public int bullet5Length;
    public int tipLength;
    public int reasonLength;
    public int desLength;
    
    public String variationThemeBoth;
    public String variationThemeColor;
    public String variationThemeSize;

    public String productType;
    public String brandName;
    public String colorMap;
    public String sizeMap;
    public ArrayList<String> fullAudienceTarget;
    public String itemType;
    public String targetAudience;
    public String audienceKeyword;
    public String departmentName;

    public float priceRate;
    public float priceLimit;
    public int dataSaveType;
    public int dataLv;
    public int totalPage;
    
    public HashMap<String, String> configs;
    
    public ToolAndStoreInfo() {
    }
    
    
    
    public void updateToolConfig() {
        this.diskSerialNumber = ComputerIdentifier.getDiskSerialNumber().replaceAll(" ", "-");
//        this.isOnlyUs = Configs.isOnlyUS;
//        this.filterAliexpress = Configs.filterAliexpress;
//        this.filterEpacket = Configs.filterEpacket;
//        this.filterAliDirect = Configs.filterAliDirect;
//        this.fetchingAliexKeywords = Configs.fetchingAliexKeywords;
//        this.fetchingImageFromDes = Configs.fetchingImageFromDes;
    }
    
    public void updateStoreInfo(AliexStoreInfo aliexStoreInfo) {
        this.main_key = aliexStoreInfo.main_key;
        this.storeSign = aliexStoreInfo.storeSign;
        this.accNo = aliexStoreInfo.accNo;
        this.variationThemeBoth = aliexStoreInfo.variationThemeBoth;
        this.variationThemeColor = aliexStoreInfo.variationThemeColor;
        this.variationThemeSize = aliexStoreInfo.variationThemeSize;
        this.productType = aliexStoreInfo.productType;
        this.brandName = aliexStoreInfo.brandName;
        this.colorMap = aliexStoreInfo.colorMap;
        this.sizeMap = aliexStoreInfo.sizeMap;
        if (aliexStoreInfo.fullAudienceTarget != null) {
            this.fullAudienceTarget = new ArrayList<>();
            this.fullAudienceTarget.addAll(aliexStoreInfo.fullAudienceTarget);
        }
        this.itemType = aliexStoreInfo.itemType;
        this.targetAudience = aliexStoreInfo.targetAudience;
        this.audienceKeyword = aliexStoreInfo.audienceKeyword;
        this.departmentName = aliexStoreInfo.departmentName;
        this.priceRate = aliexStoreInfo.priceRate;
        this.priceLimit = aliexStoreInfo.priceLimit;
        this.dataSaveType = aliexStoreInfo.dataSaveType;
        this.totalPage = aliexStoreInfo.totalPage;
        
        if (aliexStoreInfo.listBulletPoints != null) {
            int size = aliexStoreInfo.listBulletPoints.size();
            if (size > 0) {
                bullet1Length = aliexStoreInfo.listBulletPoints.get(0).length();
            }
            if (size > 1) {
                bullet2Length = aliexStoreInfo.listBulletPoints.get(1).length();
            }
            if (size > 2) {
                bullet3Length = aliexStoreInfo.listBulletPoints.get(2).length();
            }
            if (size > 3) {
                bullet4Length = aliexStoreInfo.listBulletPoints.get(3).length();
            }
            if (size > 4) {
                bullet5Length = aliexStoreInfo.listBulletPoints.get(4).length();
            }
        }
        
        if (!StringUtils.isEmpty(aliexStoreInfo.tip)) {
            tipLength = aliexStoreInfo.tip.length();
        }
        if (!StringUtils.isEmpty(aliexStoreInfo.reasons)) {
            reasonLength = aliexStoreInfo.reasons.length();
        }
        if (!StringUtils.isEmpty(aliexStoreInfo.description)) {
            desLength = aliexStoreInfo.description.length();
        }
        
    }
}
