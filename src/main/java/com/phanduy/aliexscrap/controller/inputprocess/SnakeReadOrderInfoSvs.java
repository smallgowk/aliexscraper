/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.controller.inputprocess;

import com.phanduy.aliexscrap.config.Configs;
import com.phanduy.aliexscrap.model.aliex.store.inputdata.BaseStoreOrderInfo;
import com.phanduy.aliexscrap.utils.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author duyuno
 */
public class SnakeReadOrderInfoSvs extends ReadStoreOrderInfoSvs{
    
    private static SnakeReadOrderInfoSvs snakeReadOrderInfoSvs;
    
    public static SnakeReadOrderInfoSvs getInstance() {
        if(snakeReadOrderInfoSvs == null) {
            snakeReadOrderInfoSvs = new SnakeReadOrderInfoSvs();
        }
        return snakeReadOrderInfoSvs;
    }
    
    @Override
    public BaseStoreOrderInfo readStoreFromRow(Row fieldNameRow, Row fieldRow, int cellMax, DataFormatter formatter) {
        String link = null;
        String productType = null;
        String brandName = null;
        String bulletPoint = null;
        String category = null;
        String accNo = null;
        String status = null;
        String descriptionForm = null;
        String mainKeywords = null;
        String tips = null;
        String reasons = null;
        int pageTotal = 0;
        String region = "US";

        for (int j = 0; j < cellMax; j++) {
            String fieldName = formatter.formatCellValue(fieldNameRow.getCell(j));
            switch (fieldName.trim().toLowerCase()) {
                case "link":
                    link = formatter.formatCellValue(fieldRow.getCell(j));
                    break;
                case "product_type":
                    productType = formatter.formatCellValue(fieldRow.getCell(j));
                    break;
                case "brand_name":
                    brandName = formatter.formatCellValue(fieldRow.getCell(j));
                    break;
                case "main_key":
                    mainKeywords = formatter.formatCellValue(fieldRow.getCell(j));
                    break;
                case "tips":
                    tips = formatter.formatCellValue(fieldRow.getCell(j));
                    break;
                case "reasons":
                    reasons = formatter.formatCellValue(fieldRow.getCell(j));
                    break;
                case "description":
                    descriptionForm = formatter.formatCellValue(fieldRow.getCell(j));
                    break;
                case "bullet_points":
                    bulletPoint = formatter.formatCellValue(fieldRow.getCell(j));
                    break;
                case "category":
                    category = formatter.formatCellValue(fieldRow.getCell(j));
                    break;
                case "acc_no":
                    accNo = formatter.formatCellValue(fieldRow.getCell(j));
                    break;
                case "status":
                    status = formatter.formatCellValue(fieldRow.getCell(j));
                    break;
                case "region":
                    String value = formatter.formatCellValue(fieldRow.getCell(j));
                    if (!StringUtils.isEmpty(value) && Configs.regionMap.containsKey(value)) {
                        region = value;
                    }
                    break;
            }
        }

        BaseStoreOrderInfo baseStoreOrderInfo = new BaseStoreOrderInfo();

        if (link != null) {
            baseStoreOrderInfo.setLink(link.trim());
        }
        
        baseStoreOrderInfo.setProduct_type(productType != null ? productType.trim() : "");
        baseStoreOrderInfo.setBrand_name(brandName != null ? brandName.trim() : "");
        baseStoreOrderInfo.setBullet_points(bulletPoint != null ? bulletPoint.trim() : "");
        baseStoreOrderInfo.setAcc_no(accNo != null ? accNo.trim() : "");
        baseStoreOrderInfo.setStatus(status != null ? status.trim() : "");
        baseStoreOrderInfo.setCategory(category != null ? category.trim() : "");
        baseStoreOrderInfo.setMain_key(mainKeywords != null ? mainKeywords.trim() : "");
        baseStoreOrderInfo.setTip(tips);
        baseStoreOrderInfo.setReasons(reasons);
        baseStoreOrderInfo.setDescription(descriptionForm);
        baseStoreOrderInfo.setPageTotal(pageTotal);
        baseStoreOrderInfo.setRegion(region);
        return baseStoreOrderInfo;
    }
}
