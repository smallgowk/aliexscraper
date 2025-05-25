/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import com.google.gson.Gson;
import com.config.Configs;
import com.models.aliex.AliexOriginalInfo;
import com.models.aliex.store.BaseStoreInfo;
import com.models.aliex.store.AliexPageInfo;
import com.models.aliex.store.AliexStoreCommon;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Admin
 */
public class Utils {

    public static HashMap<String, AliexOriginalInfo> hashMapOriginalInfo = new HashMap<>();
//    public static HashMap<String, ArrayList<String>> hashMapMerchantKeys = new HashMap<>();
    public static HashMap<String, ArrayList<String>> hashMapRelatedKeys = new HashMap<>();

    public static String formatPrice(float price) {
        return String.format("%.2f", price);
    }

    public static String getCEOPrice(float price) {
//        int n = (int) price;
//
//        float ceoPrice = n * 1f + 0.99f;

//        return formatPrice(ceoPrice);
        return formatPrice(price);
    }

    public static String removeSpace(String input) {
        return input.replaceAll(Pattern.quote(" "), "");
    }

    public static String removeSpecialChar(String input) {
        return input.replaceAll("[^a-zA-Z0-9 ]", " ");
    }

//    public static void saveProductAliexToCache(String folderPath, AliexOriginalInfo aliexBasicInfo) throws FileNotFoundException, IOException {
//        File file = new File(Configs.CACHE_PATH + Configs.PRODUCT_CACHE_DIR + Configs.pathChar + folderPath);
//
//        if (!file.exists()) {
//            file.mkdir();
//        }
//
//        Gson gson = new Gson();
//
//        writeToFile(gson.toJson(aliexBasicInfo), file.getAbsolutePath() + Configs.pathChar + aliexBasicInfo.getId() + ".txt");
//
////        FileUtils.writeStringToFile(new File(file.getAbsolutePath() + Configs.pathChar + aliexBasicInfo.getId() + ".txt"), gson.toJson(aliexBasicInfo));
//        hashMapOriginalInfo.put(aliexBasicInfo.getId(), aliexBasicInfo);
//
//        System.out.println("Save " + aliexBasicInfo.getId() + " to cache");
//    }

    public static void saveStoreInfoToCache(AliexStoreCommon aliexStoreCache) throws FileNotFoundException, IOException {

        File file = new File(Configs.CACHE_PATH + Configs.STORE_INFO_CACHE_DIR);
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getAbsolutePath() + Configs.pathChar + aliexStoreCache.getStoreSign());
        if (!file.exists()) {
            file.mkdir();
        }

        String filePath = file.getAbsolutePath() + Configs.pathChar + aliexStoreCache.getStoreSign() + ".txt";

        Gson gson = new Gson();

        String data = gson.toJson(aliexStoreCache);

        String encrytData = EncryptUtil.encrypt(data);

        writeToFile(encrytData, filePath);

//        FileUtils.writeStringToFile(new File(filePath), gson.toJson(aliexStoreCache));
    }

    public static void saveStorePageCache(AliexStoreCommon aliexStoreCommon, AliexPageInfo aliexPageInfo) throws IOException {
        File file = new File(Configs.CACHE_PATH + Configs.STORE_INFO_CACHE_DIR);
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getAbsolutePath() + Configs.pathChar + aliexStoreCommon.getStoreSign());
        if (!file.exists()) {
            file.mkdir();
        }

        String filePath = file.getAbsolutePath() + Configs.pathChar + aliexStoreCommon.getStoreSign() + "_page" + aliexPageInfo.getPageIndex() + ".txt";

        Gson gson = new Gson();
        String data = gson.toJson(aliexPageInfo);
        String encrytData = EncryptUtil.encrypt(data);

//        FileUtils.writeStringToFile(new File(filePath), gson.toJson(aliexPageInfo));
        writeToFile(encrytData, filePath);
    }

    public static void saveStorePageCache(AliexStoreCommon aliexStoreCommon, String data, int pageIndex) throws IOException {
        File file = new File(Configs.CACHE_PATH + Configs.STORE_INFO_CACHE_DIR);
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getAbsolutePath() + Configs.pathChar + aliexStoreCommon.getStoreSign());
        if (!file.exists()) {
            file.mkdir();
        }

        String filePath = file.getAbsolutePath() + Configs.pathChar + aliexStoreCommon.getStoreSign() + "_page" + pageIndex + ".txt";

//        FileUtils.writeStringToFile(new File(filePath), gson.toJson(aliexPageInfo));
        writeToFile(data, filePath);
    }

//    public static void saveAliexProduct(AliexProductFull aliexProductFull) {
//        String filePath = Configs.CACHE_PATH + Configs.PRODUCT_CACHE_DIR + aliexProductFull.getId() + ".txt";
//
//        Gson gson = new Gson();
//        String data = gson.toJson(aliexProductFull);
//        String encrytData = EncryptUtil.encrypt(data);
//
//        try {
//            //        FileUtils.writeStringToFile(new File(filePath), gson.toJson(aliexPageInfo));
//            writeToFile(encrytData, filePath);
//        } catch (IOException ex) {
//            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public static void saveStoreCommonCache(String cacheFile, String data) throws IOException {
        File file = new File(Configs.CACHE_PATH + Configs.STORE_INFO_CACHE_DIR);
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getAbsolutePath() + Configs.pathChar + cacheFile);
        if (!file.exists()) {
            file.mkdir();
        }

        String filePath = file.getAbsolutePath() + Configs.pathChar + cacheFile + ".txt";

        writeToFile(data, filePath);
    }

    public static AliexStoreCommon getStoreCache(BaseStoreInfo storePageInfo) {
//        String filePath = Configs.CACHE_PATH + Configs.STORE_INFO_CACHE_DIR + Configs.pathChar + storePageInfo.getLastFileFolder() + Configs.pathChar + storePageInfo.getLastFileFolder() + ".txt";
//
//        File file = new File(filePath);
//        if (!file.exists()) {
//            return null;
//        }
//
//        String object;
//        try {
//            object = FileUtils.readFileToString(file);
//            object = EncryptUtil.decrypt(object);
//            Gson gson = new Gson();
//            return gson.fromJson(object, AliexStoreCommon.class);
//        } catch (IOException ex) {
//            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            System.out.println("" + object);
        return null;
    }

    public static AliexStoreCommon getStoreCache(String cacheFileName) {

        File file = new File(Configs.CACHE_PATH + Configs.STORE_INFO_CACHE_DIR + Configs.pathChar + cacheFileName + Configs.pathChar + cacheFileName + ".txt");
        if (!file.exists()) {
            return null;
        }

        String object;
        try {
            object = FileUtils.readFileToString(file);
            object = EncryptUtil.decrypt(object);
            Gson gson = new Gson();
            return gson.fromJson(object, AliexStoreCommon.class);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
//            System.out.println("" + object);
        return null;
    }

    public static AliexPageInfo getStorePageCache(String cacheFileName, int pageIndex) {

        if (cacheFileName == null) {
            return null;
        }

        File file = new File(Configs.CACHE_PATH + Configs.STORE_INFO_CACHE_DIR + Configs.pathChar + cacheFileName + Configs.pathChar + cacheFileName + "_page" + pageIndex + ".txt");
        if (!file.exists()) {
            return null;
        }

        String data = null;

        try {
            data = FileUtils.readFileToString(file);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        String cleanData = EncryptUtil.decrypt(data);
        Gson gson = new Gson();
        return gson.fromJson(cleanData, AliexPageInfo.class);

//        String object;
//        try {
//            object = FileUtils.readFileToString(file);
//            object = EncryptUtil.decrypt(object);
//            Gson gson = new Gson();
//            return gson.fromJson(object, AliexPageInfo.class);
//        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | DecoderException ex) {
//            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
    }

//    public static AliexOriginalInfo getAliexBasicInfoCache(String folderPath, String aliexId) {
//
//        if (hashMapOriginalInfo.containsKey(aliexId)) {
//            return hashMapOriginalInfo.get(aliexId);
//        }
//
//        try {
//            File file = new File(Configs.CACHE_PATH + Configs.PRODUCT_CACHE_DIR + Configs.pathChar + folderPath + Configs.pathChar + aliexId + ".txt");
//            if (!file.exists()) {
//                return null;
//            }
//
//            String object = FileUtils.readFileToString(file);
////            System.out.println("" + object);
//            Gson gson = new Gson();
//            AliexOriginalInfo aliexOriginalInfo = gson.fromJson(object, AliexOriginalInfo.class);
//            hashMapOriginalInfo.put(aliexId, aliexOriginalInfo);
//
//            return aliexOriginalInfo;
//        } catch (IOException ex) {
//            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }
//
//    public static void saveMerchantInfo(String keyword, ArrayList<String> listKeyword) {
//
//        String id = keyword.trim().toLowerCase().replaceAll(Pattern.quote(" "), "_");
//
//        hashMapMerchantKeys.put(id, listKeyword);
//
//        StringBuilder sb = new StringBuilder();
//
//        for (String key : listKeyword) {
//            if (sb.length() == 0) {
//                sb.append(key);
//            } else {
//                sb.append(",").append(key);
//            }
//        }
//
//        try {
//            File file = new File(Configs.CACHE_PATH + Configs.MERCHANT_CACHE_DIR);
//            if (!file.exists()) {
//                file.mkdir();
//            }
//            String cleanData = sb.toString();
//            String encryptData = EncryptUtil.encrypt(cleanData);
//            writeToFile(encryptData, Configs.CACHE_PATH + Configs.MERCHANT_CACHE_DIR + Configs.pathChar + id + ".txt");
////            FileUtils.writeStringToFile(new File(Configs.CACHE_PATH + Configs.MERCHANT_CACHE_DIR + Configs.pathChar + id + ".txt"), sb.toString());
//        } catch (IOException ex) {
//            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public static void saveRelatedSearch(String productId, ArrayList<String> listKeyword) {

        hashMapRelatedKeys.put(productId, listKeyword);

        StringBuilder sb = new StringBuilder();

        if (listKeyword == null) {
            sb.append("");
        } else {
            for (String key : listKeyword) {
                if (sb.length() == 0) {
                    sb.append(key);
                } else {
                    sb.append(",").append(key);
                }
            }
        }

        try {
            File file = new File(Configs.CACHE_PATH + Configs.RELATED_CACHE_DIR);
            if (!file.exists()) {
                file.mkdir();
            }
            String cleanData = sb.toString();
            String encryptData = EncryptUtil.encrypt(cleanData);
            writeToFile(encryptData, Configs.CACHE_PATH + Configs.RELATED_CACHE_DIR + Configs.pathChar + productId + ".txt");
//            FileUtils.writeStringToFile(new File(Configs.CACHE_PATH + Configs.MERCHANT_CACHE_DIR + Configs.pathChar + id + ".txt"), sb.toString());
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public static ArrayList<String> getMerchantInfo(String keyword) {
//        String id = keyword.trim().toLowerCase().replaceAll(Pattern.quote(" "), "_");
//
//        if (hashMapMerchantKeys.containsKey(id)) {
//            return hashMapMerchantKeys.get(id);
//        }
//
//        File file = new File(Configs.CACHE_PATH + Configs.MERCHANT_CACHE_DIR + Configs.pathChar + id + ".txt");
//        if (!file.exists()) {
//            return null;
//        }
//
//        String object;
//        try {
//            object = FileUtils.readFileToString(file);
//            if (StringUtils.isEmpty(object)) {
//                return null;
//            }
//
//            String decryptData = EncryptUtil.decrypt(object);
//
//            if (decryptData.toLowerCase().equals(keyword.toLowerCase())) {
//                return null;
//            }
//
//            String[] pars = decryptData.split(Pattern.quote(","));
//            ArrayList<String> list = new ArrayList<String>(Arrays.asList(pars));
//            hashMapMerchantKeys.put(id, list);
//            return list;
//        } catch (IOException ex) {
//            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }

    public static ArrayList<String> getRelatedInfo(String productId) {

        if (hashMapRelatedKeys.containsKey(productId)) {
            return hashMapRelatedKeys.get(productId);
        }

        File file = new File(Configs.CACHE_PATH + Configs.RELATED_CACHE_DIR + Configs.pathChar + productId + ".txt");
        if (!file.exists()) {
            return null;
        }

        String object;
        try {
            object = FileUtils.readFileToString(file);
            if (StringUtils.isEmpty(object)) {
                return null;
            }

            String decryptData = EncryptUtil.decrypt(object);

            if (StringUtils.isEmpty(decryptData)) {
                hashMapRelatedKeys.put(productId, null);
                return null;
            }

            String[] pars = decryptData.split(Pattern.quote(","));
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(pars));
            hashMapRelatedKeys.put(productId, list);
            return list;
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void writeToFile(String data, String filePath) throws IOException {

        FileUtils.writeStringToFile(new File(filePath), data, "UTF-8");

    }

}
