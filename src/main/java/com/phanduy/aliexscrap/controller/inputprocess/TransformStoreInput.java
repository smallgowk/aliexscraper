/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.controller.inputprocess;

import com.phanduy.aliexscrap.config.Configs;
import com.phanduy.aliexscrap.model.aliex.store.inputdata.BaseStoreOrderInfo;
import com.models.amazon.BTGManager;
import com.models.amazon.BTGNode;
import com.phanduy.aliexscrap.model.aliex.store.AliexStoreInfo;
import com.phanduy.aliexscrap.utils.ExcelUtils;
import com.phanduy.aliexscrap.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author duyuno
 */
public class TransformStoreInput {

    private static TransformStoreInput aliexStoreTransformSvs;

    public static TransformStoreInput getInstance() {
        if (aliexStoreTransformSvs == null) {
            aliexStoreTransformSvs = new TransformStoreInput();
        }

        return aliexStoreTransformSvs;
    }

    public AliexStoreInfo transformRawData(BaseStoreOrderInfo snakeStoreOrderInfo) {
        if (snakeStoreOrderInfo == null) {
            return null;
        }

        AliexStoreInfo aliexStoreInfo = new AliexStoreInfo();
        aliexStoreInfo.setStoreSign(snakeStoreOrderInfo.getStoreSign());
        aliexStoreInfo.setAccNo(StringUtils.replaceSpace(snakeStoreOrderInfo.getAcc_no(), "_"));
        aliexStoreInfo.setProductType(snakeStoreOrderInfo.getProduct_type());
        aliexStoreInfo.setLink(snakeStoreOrderInfo.getLink());
        aliexStoreInfo.setBrandName(snakeStoreOrderInfo.getBrand_name());
        aliexStoreInfo.setTotalPage(snakeStoreOrderInfo.pageTotal);
        aliexStoreInfo.setRegion(snakeStoreOrderInfo.region);

        aliexStoreInfo.setMain_key(snakeStoreOrderInfo.getMain_key());
        aliexStoreInfo.setTip(snakeStoreOrderInfo.getTip());
        aliexStoreInfo.setDescription(snakeStoreOrderInfo.getDescription());
        aliexStoreInfo.setReasons(snakeStoreOrderInfo.getReasons());
        aliexStoreInfo.setStoreId(snakeStoreOrderInfo.storeId);
        aliexStoreInfo.setProductId(snakeStoreOrderInfo.productId);
        aliexStoreInfo.setQuery(snakeStoreOrderInfo.query);

//        aliexStoreInfo.setIsOnlyUS(Configs.isOnlyUS);
        aliexStoreInfo.setPriceLimit(Configs.priceLimit);
        aliexStoreInfo.setPriceRate(Configs.priceRate);
        aliexStoreInfo.setDataSaveType(Configs.dataSaveType);
//        aliexStoreInfo.setDataLv(Configs.dataLevel);

        // BTG
//        String[] btgParts = snakeStoreOrderInfo.getCategory().split(Pattern.quote("/"));
//        BTGNode bTGNode = BTGManager.getInstance().getBTGNode(btgParts[btgParts.length - 1].trim());
        BTGNode bTGNode = BTGManager.getInstance().getBTGNode(snakeStoreOrderInfo.getCategory());
//        aliexStoreInfo.setbTGNode(bTGNode);

        // Init Valid values
        if (bTGNode != null) {
            HashMap<String, ArrayList<String>> hashMap = ExcelUtils.initValidValues(snakeStoreOrderInfo.getProduct_type(), bTGNode.getTemplateFile());
            aliexStoreInfo.initValidValues(hashMap);
        } else {
            aliexStoreInfo.initValidValues(null);
        }

        if (bTGNode != null && bTGNode.getKeyword() != null) {
            aliexStoreInfo.setItemType(bTGNode.getKeyword());
            aliexStoreInfo.setTargetAudience(bTGNode.getAudienceKeyword());
            aliexStoreInfo.setDepartmentName(bTGNode.getDepartMentName());
            aliexStoreInfo.setAudienceKeyword(bTGNode.getAudienceKeyword());
        } else {
            aliexStoreInfo.setItemType("multitools");
            aliexStoreInfo.setProductType("Tools");
            aliexStoreInfo.setVariationThemeBoth("SizeName-ColorName");
            aliexStoreInfo.setVariationThemeColor("Color_Name");
            aliexStoreInfo.setVariationThemeSize("Size_Name");
            aliexStoreInfo.setColorMap("White");
            aliexStoreInfo.setSizeMap("Large");
            aliexStoreInfo.setTargetAudience("Professional Audience Unisex Children Teens Men People Adults Children Seniors Consumer Audience Unisex Adult");
            aliexStoreInfo.setAudienceKeyword("Professional Audience Unisex Children Teens Men People Adults Children Seniors Consumer Audience Unisex Adult");
        }

        // Bullet Points
        ArrayList<String> listBullet = new ArrayList<>();
        String[] parts = snakeStoreOrderInfo.getBullet_points().split("\n");
        if (parts != null && parts.length != 0) {
            for (String s : parts) {
                if (!s.trim().isEmpty()) {
                    listBullet.add(s);
                }
            }
        }
        aliexStoreInfo.setListBulletPoints(listBullet);

//        int count = 0;
//        if (StringUtils.isEmpty(aliexStoreInfo.getMain_key())) {
//            String mainKey = "";
//
//            if (!StringUtils.isEmpty(aliexStoreInfo.getTargetAudience())) {
//                String[] audiencePart = aliexStoreInfo.getTargetAudience().split(" ");
//                for (String s : audiencePart) {
//                    if (s.trim().length() == 0) {
//                        continue;
//                    }
//                    mainKey += " " + s;
//                    count++;
//                    if (count == 3) {
//                        break;
//                    }
//                }
//            }
//            if(count < 3) {
//                String[] itemTypePart = aliexStoreInfo.getItemType().replaceAll("-", " ").split(" ");
//                for (String s : itemTypePart) {
//                    if (s.trim().length() == 0) {
//                        continue;
//                    }
//                    mainKey += " " + s;
//                    count++;
//                    if (count == 3) {
//                        break;
//                    }
//                }
//            }
//            mainKey += " " + aliexStoreInfo.getItemType().replaceAll("-", " ");
//            aliexStoreInfo.setMain_key(mainKey.trim());
//        }
//        System.out.println("Main key: " + aliexStoreInfo.getMain_key());
        return aliexStoreInfo;
    }

}
