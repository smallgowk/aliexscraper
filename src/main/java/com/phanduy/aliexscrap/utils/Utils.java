/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.utils;

import com.google.gson.Gson;
import com.phanduy.aliexscrap.config.Configs;
import com.phanduy.aliexscrap.model.ProductPage;
import com.phanduy.aliexscrap.model.aliex.AliexOriginalInfo;
import com.phanduy.aliexscrap.model.aliex.store.BaseStoreInfo;
import com.phanduy.aliexscrap.model.aliex.store.AliexPageInfo;
import com.phanduy.aliexscrap.model.aliex.store.AliexStoreCommon;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
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

    public static void saveProducts(String signature, String pageNumber, ArrayList<String> productIds) {
        if (StringUtils.isEmpty(signature)) return;
        if (productIds == null || productIds.isEmpty()) return;

        try {
            // Tạo thư mục nếu chưa tồn tại
            File directory = new File(Configs.SIGNATURE_CACHE_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Tạo đường dẫn file
            String filePath = Configs.SIGNATURE_CACHE_PATH + Configs.pathChar + signature + "_page" + pageNumber + ".txt";

            // Chuyển ArrayList thành chuỗi, mỗi ID trên một dòng
            StringBuilder sb = new StringBuilder();
            for (String productId : productIds) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(productId);
            }

            // Mã hóa dữ liệu trước khi lưu
            String data = sb.toString();
//            String encryptedData = EncryptUtil.encrypt(data);

            // Ghi vào file
            writeToFile(data, filePath);

            System.out.println("Saved " + productIds.size() + " product IDs to " + filePath);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void removeSignatureCache(String signature, String pageNumber) {
        String filePath = Configs.SIGNATURE_CACHE_PATH + Configs.pathChar + signature + "_page" + pageNumber + ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public static ProductPage loadProducts(String fileName) {
        try {
            // Tạo đường dẫn file
            String filePath = Configs.SIGNATURE_CACHE_PATH + Configs.pathChar + fileName;
            File file = new File(filePath);

            // Kiểm tra file có tồn tại không
            if (!file.exists()) {
                return null;
            }

            // Đọc dữ liệu từ file
            String encryptedData = FileUtils.readFileToString(file, "UTF-8");
            
            // Giải mã dữ liệu
//            String decryptedData = EncryptUtil.decrypt(encryptedData);
            
            if (StringUtils.isEmpty(encryptedData)) {
                return null;
            }

            // Chuyển chuỗi thành ArrayList
            ProductPage productPage = new ProductPage();

            Pattern pattern = Pattern.compile("(.+?)_page(\\d+)\\.txt");
            Matcher matcher = pattern.matcher(fileName);

            if (matcher.matches()) {
                String name = matcher.group(1);
                int number = Integer.parseInt(matcher.group(2));
                productPage.setSignature(name);
                productPage.setPageNumber(number);
            } else {
                return null;
            }
            ArrayList<String> productIds = new ArrayList<>();
            String[] lines = encryptedData.split("\n");
            
            for (String line : lines) {
                if (!StringUtils.isEmpty(line.trim())) {
                    productIds.add(line.trim());
                }
            }

            System.out.println("Loaded " + productIds.size() + " product IDs from " + filePath);
            productPage.setIds(productIds);
            return productPage;

        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static ArrayList<ProductPage> loadCacheData() {
        File cacheDir = new File(Configs.SIGNATURE_CACHE_PATH);
        if (!cacheDir.exists()) return null;
        File[] files = cacheDir.listFiles();
        if (files == null || files.length == 0) return null;
        ArrayList<ProductPage> results = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                ProductPage productPage = loadProducts(fileName);
                if (productPage != null) {
                    results.add(productPage);
                }
            }
        }
        return results;
    }
}
