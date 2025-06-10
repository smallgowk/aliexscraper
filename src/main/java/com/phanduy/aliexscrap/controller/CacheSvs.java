/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.controller;

import com.google.gson.Gson;
import com.phanduy.aliexscrap.config.Configs;
import com.phanduy.aliexscrap.model.response.TransformCrawlResponse;
import com.phanduy.aliexscrap.utils.EncryptUtil;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author PhanDuy
 */
public class CacheSvs {

    private static CacheSvs fetchAliexProductInfoSvs;

    public static CacheSvs getInstance() {
        if (fetchAliexProductInfoSvs == null) {
            fetchAliexProductInfoSvs = new CacheSvs();
        }
        return fetchAliexProductInfoSvs;
    }
    
//    public AliexProductFull getProductFromCache(String id, String storeSign) {
//        String filePath = Configs.CACHE_PATH + Configs.PRODUCT_CACHE_DIR + Configs.pathChar + storeSign + Configs.pathChar + id + ".txt";
//        String cacheData = null;
//
//        File cache = new File(filePath);
//
//        try {
//            if (cache.exists()) {
//                cacheData = FileUtils.readFileToString(cache);
//            }
//
//        } catch (IOException ex) {
//            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        if (cacheData != null) {
//            String cleanData = EncryptUtil.decrypt(cacheData);
//            Gson gson = new Gson();
//            return gson.fromJson(cleanData, AliexProductFull.class);
//        }
//        
//        return null;
//    }
    
    public TransformCrawlResponse getProductResFromCache(String id, String storeSign) {
        String filePath = Configs.CACHE_PATH + Configs.PRODUCT_CACHE_DIR_V2 + Configs.pathChar + storeSign + Configs.pathChar + id + ".txt";
        String cacheData = null;

        File cache = new File(filePath);

        try {
            if (cache.exists()) {
                cacheData = FileUtils.readFileToString(cache);
            }

        } catch (IOException ex) {
            Logger.getLogger(CacheSvs.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (cacheData != null) {
            String cleanData = EncryptUtil.decrypt(cacheData);
            Gson gson = new Gson();
            return gson.fromJson(cleanData, TransformCrawlResponse.class);
        }
        
        return null;
    }

    public void saveProductInfo(TransformCrawlResponse res, String storeSign) {
//        if (res == null
//                || ((res.baseAmzProduct == null || res.baseAmzProduct.aliexId == null)
//                && (res.listProducts == null || res.listProducts.isEmpty()))) {
//            return;
//        }
        File file = new File(Configs.CACHE_PATH + Configs.PRODUCT_CACHE_DIR_V2 + Configs.pathChar + storeSign);
        if (!file.exists()) {
            file.mkdir();
        }

        try {
            Gson gson = new Gson();
            String data = gson.toJson(res, TransformCrawlResponse.class);
            String encrytData = EncryptUtil.encrypt(data);
            FileUtils.writeStringToFile(new File(file.getAbsolutePath() + Configs.pathChar + (res.productId != null ? res.productId : res.id) + ".txt"), encrytData);
        } catch (Exception ex) {
            Logger.getLogger(CacheSvs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
