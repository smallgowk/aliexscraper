/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.controller.transform;

import com.phanduy.aliexscrap.config.Configs;
import com.phanduy.aliexscrap.controller.DownloadManager;
import com.phanduy.aliexscrap.model.aliex.AliexProductFull;
import com.phanduy.aliexscrap.model.amazon.ProductAmz;
import com.phanduy.aliexscrap.model.aliex.store.AliexPageInfo;
import com.phanduy.aliexscrap.model.aliex.store.AliexStoreInfo;
import com.phanduy.aliexscrap.model.amazon.AmzContentFormat;
import com.phanduy.aliexscrap.model.amazon.DataStore;

import com.phanduy.aliexscrap.model.request.ImagePathModel;
import com.phanduy.aliexscrap.model.response.TransformCrawlResponse;
import com.phanduy.aliexscrap.model.response.TransformResponse;
import com.phanduy.aliexscrap.utils.CheckingDataUtil;
import com.phanduy.aliexscrap.utils.ComputerIdentifier;
import com.phanduy.aliexscrap.utils.ExcelUtils;
import com.phanduy.aliexscrap.utils.StringUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.regex.Pattern;
import org.apache.commons.math3.util.Pair;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author PhanDuy
 */
public class ProcessStoreInfoSvs {

    static HashMap<String, AliexStoreInfo> mapStoreInfo = new HashMap<>();
    static HashMap<String, ArrayList<ProductAmz>> mapProducts = new HashMap<>();
    static HashMap<String, ArrayList<Pair<String, String>>> mapErrorsProducts = new HashMap<>();
    static HashMap<String, ArrayList<String>> mapBrandName = new HashMap<>();
    
    public static void clearMapData() {
//        mapStoreInfo.clear();
        mapProducts.clear();
        mapErrorsProducts.clear();
        mapBrandName.clear();
    }

    public void processStoreInfo(AliexStoreInfo aliexStoreInfo) {
        mapStoreInfo.put(genKey(ComputerIdentifier.diskSerial, aliexStoreInfo.getStoreSign()), aliexStoreInfo);

        for (int i = 0; i < aliexStoreInfo.getTotalPage(); i++) {
            String keyPage = genKey(ComputerIdentifier.diskSerial, aliexStoreInfo.getStoreSign(), (i + 1));

            if (mapProducts.containsKey(keyPage) && mapProducts.get(keyPage) != null) {
                mapProducts.get(keyPage).clear();
            }
        }
    }

    private boolean isFeatureTextInDes(String text) {
        boolean sign1 = text.contains("feature") || text.contains("function") || text.contains("advantage");
        if (!sign1) return false;
        String[] parts = text.split(Pattern.quote(" "));
        return parts.length <= 2;
    }
    
    public void changeBulletPoint(AliexProductFull aliexProductFull, String key, String value) {
        if (aliexProductFull.bullet_point1 != null) {
            aliexProductFull.bullet_point1 = aliexProductFull.bullet_point1.replaceAll(Pattern.quote(key), value);
            DataStore.putProductData(aliexProductFull.item_sku, "bullet_point1", aliexProductFull.bullet_point1);
        }

        if (aliexProductFull.bullet_point2 != null) {
            aliexProductFull.bullet_point2 = aliexProductFull.bullet_point2.replaceAll(Pattern.quote(key), value);
            DataStore.putProductData(aliexProductFull.item_sku, "bullet_point2", aliexProductFull.bullet_point2);
        }

        if (aliexProductFull.bullet_point3 != null) {
            aliexProductFull.bullet_point3 = aliexProductFull.bullet_point3.replaceAll(Pattern.quote(key), value);
            DataStore.putProductData(aliexProductFull.item_sku, "bullet_point3", aliexProductFull.bullet_point3);
        }

        if (aliexProductFull.bullet_point4 != null) {
            aliexProductFull.bullet_point4 = aliexProductFull.bullet_point4.replaceAll(Pattern.quote(key), value);
            DataStore.putProductData(aliexProductFull.item_sku, "bullet_point4", aliexProductFull.bullet_point4);
        }

        if (aliexProductFull.bullet_point5 != null) {
            aliexProductFull.bullet_point5 = aliexProductFull.bullet_point5.replaceAll(Pattern.quote(key), value);
            DataStore.putProductData(aliexProductFull.item_sku, "bullet_point5", aliexProductFull.bullet_point5);
        }
    }
    
    public void changeBulletPoint(AliexProductFull aliexProductFull, int number, String words) {
        String pattern = null;
        switch (number) {
            case 1:
                pattern = AmzContentFormat.SEARCH_TERM_1;
                if (aliexProductFull.bullet_point1 != null) {
                    aliexProductFull.bullet_point1 = aliexProductFull.bullet_point1.replaceAll(Pattern.quote(pattern), words);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point1", aliexProductFull.bullet_point1);
                }

                break;
            case 2:
                pattern = AmzContentFormat.SEARCH_TERM_2;
                if (aliexProductFull.bullet_point2 != null) {
                    aliexProductFull.bullet_point2 = aliexProductFull.bullet_point2.replaceAll(Pattern.quote(pattern), words);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point2", aliexProductFull.bullet_point2);
                }
                break;
            case 3:
                pattern = AmzContentFormat.SEARCH_TERM_3;
                if (aliexProductFull.bullet_point3 != null) {
                    aliexProductFull.bullet_point3 = aliexProductFull.bullet_point3.replaceAll(Pattern.quote(pattern), words);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point3", aliexProductFull.bullet_point3);
                }
                break;
            case 4:
                pattern = AmzContentFormat.SEARCH_TERM_4;
                if (aliexProductFull.bullet_point4 != null) {
                    aliexProductFull.bullet_point4 = aliexProductFull.bullet_point4.replaceAll(Pattern.quote(pattern), words);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point4", aliexProductFull.bullet_point4);
                }
                break;
            case 5:
                pattern = AmzContentFormat.SEARCH_TERM_5;
                if (aliexProductFull.bullet_point5 != null) {
                    aliexProductFull.bullet_point5 = aliexProductFull.bullet_point5.replaceAll(Pattern.quote(pattern), words);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point5", aliexProductFull.bullet_point5);
                }
                break;
        }
    }
    
    public void changeBulletPoint(AliexProductFull aliexProductFull, int number, ArrayList<String> keywords) {

        String pattern = null;
        switch (number) {
            case 1:
                pattern = AmzContentFormat.SEARCH_TERM_1;
                if (aliexProductFull.bullet_point1 != null) {
                    String bulletKeys = genListKeyForBullet(aliexProductFull.bullet_point1.length(), keywords);
                    aliexProductFull.bullet_point1 = aliexProductFull.bullet_point1.replaceAll(Pattern.quote(pattern), bulletKeys);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point1", aliexProductFull.bullet_point1);
                }

                break;
            case 2:
                pattern = AmzContentFormat.SEARCH_TERM_2;
                if (aliexProductFull.bullet_point2 != null) {
                    String bulletKeys = genListKeyForBullet(aliexProductFull.bullet_point2.length(), keywords);
                    aliexProductFull.bullet_point2 = aliexProductFull.bullet_point2.replaceAll(Pattern.quote(pattern), bulletKeys);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point2", aliexProductFull.bullet_point2);
                }
                break;
            case 3:
                pattern = AmzContentFormat.SEARCH_TERM_3;
                if (aliexProductFull.bullet_point3 != null) {
                    String bulletKeys = genListKeyForBullet(aliexProductFull.bullet_point3.length(), keywords);
                    aliexProductFull.bullet_point3 = aliexProductFull.bullet_point3.replaceAll(Pattern.quote(pattern), bulletKeys);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point3", aliexProductFull.bullet_point3);
                }
                break;
            case 4:
                pattern = AmzContentFormat.SEARCH_TERM_4;
                if (aliexProductFull.bullet_point4 != null) {
                    String bulletKeys = genListKeyForBullet(aliexProductFull.bullet_point4.length(), keywords);
                    aliexProductFull.bullet_point4 = aliexProductFull.bullet_point4.replaceAll(Pattern.quote(pattern), bulletKeys);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point4", aliexProductFull.bullet_point4);
                }
                break;
            case 5:
                pattern = AmzContentFormat.SEARCH_TERM_5;
                if (aliexProductFull.bullet_point5 != null) {
                    String bulletKeys = genListKeyForBullet(aliexProductFull.bullet_point5.length(), keywords);
                    aliexProductFull.bullet_point5 = aliexProductFull.bullet_point5.replaceAll(Pattern.quote(pattern), bulletKeys);
                    DataStore.putProductData(aliexProductFull.item_sku, "bullet_point5", aliexProductFull.bullet_point5);
                }
                break;
        }
    }
    
    public String genListKeyForBullet(int currentLength, ArrayList<String> listKeys) {

        if (listKeys == null || listKeys.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        HashMap<String, String> hashMap = new HashMap<>();

        for (String key : listKeys) {

            if (hashMap.containsKey(key.trim().toLowerCase())) {
                continue;
            }

            hashMap.put(key.trim().toLowerCase(), "");

            if (currentLength + sb.length() + key.length() > 400) {
                break;
            }

            if (sb.length() == 0) {
                sb.append(key);
            } else {
                sb.append(", ").append(key);
            }

        }

        String result = sb.toString();
        return CheckingDataUtil.processTrademarkAndBrandname(result);
    }
    
    public void removeBrandNameInfo(AliexProductFull aliexProductFull) {
        String brandName = aliexProductFull.getBranName();
        aliexProductFull.title = StringUtils.removeWord(aliexProductFull.title, brandName);
        aliexProductFull.generic_keywords = StringUtils.removeWord(aliexProductFull.generic_keywords, brandName);
        aliexProductFull.description = StringUtils.removeWord(aliexProductFull.description, brandName);
        aliexProductFull.bullet_point1 = StringUtils.removeWord(aliexProductFull.bullet_point1, brandName);
        aliexProductFull.bullet_point2 = StringUtils.removeWord(aliexProductFull.bullet_point2, brandName);
        aliexProductFull.bullet_point3 = StringUtils.removeWord(aliexProductFull.bullet_point3, brandName);
        aliexProductFull.bullet_point4 = StringUtils.removeWord(aliexProductFull.bullet_point4, brandName);
        aliexProductFull.bullet_point5 = StringUtils.removeWord(aliexProductFull.bullet_point5, brandName);
        aliexProductFull.material_type = StringUtils.removeWord(aliexProductFull.material_type, brandName);
        
        DataStore.putProductData(aliexProductFull.item_sku, "item_name", aliexProductFull.title);
        DataStore.putProductData(aliexProductFull.item_sku, "generic_keywords", aliexProductFull.generic_keywords);
        DataStore.putProductData(aliexProductFull.item_sku, "product_description", aliexProductFull.description);
        DataStore.putProductData(aliexProductFull.item_sku, "bullet_point1", aliexProductFull.bullet_point1);
        DataStore.putProductData(aliexProductFull.item_sku, "bullet_point2", aliexProductFull.bullet_point2);
        DataStore.putProductData(aliexProductFull.item_sku, "bullet_point3", aliexProductFull.bullet_point3);
        DataStore.putProductData(aliexProductFull.item_sku, "bullet_point4", aliexProductFull.bullet_point4);
        DataStore.putProductData(aliexProductFull.item_sku, "bullet_point5", aliexProductFull.bullet_point5);
        DataStore.putProductData(aliexProductFull.item_sku, "material_type", aliexProductFull.material_type);
    }
    
    public void removeBrandNameInfo(String brandName, ProductAmz productAmz) {
        productAmz.item_name = StringUtils.removeWord(productAmz.item_name, brandName);
        productAmz.generic_keywords = StringUtils.removeWord(productAmz.generic_keywords, brandName);
        productAmz.product_description = StringUtils.removeWord(productAmz.product_description, brandName);
        productAmz.bullet_point1 = StringUtils.removeWord(productAmz.bullet_point1, brandName);
        productAmz.bullet_point2 = StringUtils.removeWord(productAmz.bullet_point2, brandName);
        productAmz.bullet_point3 = StringUtils.removeWord(productAmz.bullet_point3, brandName);
        productAmz.bullet_point4 = StringUtils.removeWord(productAmz.bullet_point4, brandName);
        productAmz.bullet_point5 = StringUtils.removeWord(productAmz.bullet_point5, brandName);
        productAmz.material_type = StringUtils.removeWord(productAmz.material_type, brandName);
        
        DataStore.putProductData(productAmz.item_sku, "item_name", productAmz.item_name);
        DataStore.putProductData(productAmz.item_sku, "generic_keywords", productAmz.generic_keywords);
        DataStore.putProductData(productAmz.item_sku, "product_description", productAmz.product_description);
        DataStore.putProductData(productAmz.item_sku, "bullet_point1", productAmz.bullet_point1);
        DataStore.putProductData(productAmz.item_sku, "bullet_point2", productAmz.bullet_point2);
        DataStore.putProductData(productAmz.item_sku, "bullet_point3", productAmz.bullet_point3);
        DataStore.putProductData(productAmz.item_sku, "bullet_point4", productAmz.bullet_point4);
        DataStore.putProductData(productAmz.item_sku, "bullet_point5", productAmz.bullet_point5);
        DataStore.putProductData(productAmz.item_sku, "material_type", productAmz.material_type);
    }
    
    public void removeBrandInfo(ArrayList<String> listBrandName, ProductAmz productAmz) {
        if (listBrandName == null || listBrandName.isEmpty()) {
            return;
        }

        for (String brandName : listBrandName) {
            if (!StringUtils.isEmpty(brandName)) {
                removeBrandNameInfo(brandName.trim(), productAmz);
            }
        }
    }

//    public void processProduct(AliexProductFull aliexProductFull, AliexStoreInfo store) {
//        String keyStore = genKey(ComputerIdentifier.diskSerial, store.getStoreSign());
//        if (aliexProductFull.getBranName() != null) {
//            ArrayList<String> listBranchName = null;
//            if (mapBrandName.containsKey(keyStore)) {
//                listBranchName = mapBrandName.get(keyStore);
//            } else {
//                listBranchName = new ArrayList<>();
//                mapBrandName.put(keyStore, listBranchName);
//            }
//            if (!listBranchName.contains(aliexProductFull.getBranName())) {
//                listBranchName.add(aliexProductFull.getBranName());
//            }
//        }
//        
//        if (setBannedProduct.containsKey(aliexProductFull.getId())) {
//            System.out.println("Remove " + aliexProductFull.getId() + " because of banned words: " + setBannedProduct.get(aliexProductFull.getId()));
//            return;
//        }
//
//        if (aliexProductFull.getShippingPrice() < 0) {
//            System.out.println("Remove " + aliexProductFull.getId() + " because of no shipping method");
//            return;
//        }
//        AliexStoreInfo aliexStoreInfo = mapStoreInfo.get(keyStore);
//        float origiPrice = aliexProductFull.getProductPrice(aliexProductFull.getFirstPrice(), aliexStoreInfo.getPriceRate());
//        if (!aliexProductFull.isHasVarition()) {
//            if (origiPrice > aliexStoreInfo.getPriceLimit()) {
//                System.out.println("Remove " + aliexProductFull.getId() + " because of over the price limit");
//                return;
//            }
//        }
//
//        if (aliexStoreInfo.isIsOnlyUS()) {
//            if (!aliexProductFull.isHasShipFromUS()) {
//                System.out.println("Remove " + aliexProductFull.getId() + " because of no ship from US variation");
//                return;
//            }
//        } 
//        
//        if (StringUtils.isEmpty(aliexProductFull.title)) {
//            System.out.println("Remove " + aliexProductFull.getId() + " because of no title");
//            return;
//        }
//        
//        if (StringUtils.isEmpty(aliexProductFull.description)) {
//            System.out.println("Remove " + aliexProductFull.getId() + " because of no description");
//            return;
//        }
//
//        ArrayList<ProductAmz> listProductAmz = ProcessTransformAliexToAmz.transform(aliexProductFull, aliexStoreInfo, origiPrice);
////        TransformAliexToAmzReq req = new TransformAliexToAmzReq();
////        req.aliexProductFull = aliexProductFull;
////        req.aliexStoreInfo = aliexStoreInfo;
////        req.fetchingImageFromDes = Configs.fetchingImageFromDes;
////        TransformAliexToAmzResponse res = DropApiCall.doTransformAliexToAmz(req, null);
////        ArrayList<ProductAmz> listProductAmz = res.data;
//
//        if (listProductAmz != null) {
//            String key = genKey(ComputerIdentifier.diskSerial, aliexProductFull.getStoreSign(), aliexProductFull.getPageIndex());
//            if (!mapProducts.containsKey(key)) {
//                mapProducts.put(key, listProductAmz);
//            } else {
//                ArrayList<ProductAmz> list = mapProducts.get(key);
//                list.addAll(listProductAmz);
//            }
//        } else {
//            System.out.println("Can not get any product from id: " + aliexProductFull.getId());
//        }
//    }
    
    public void processProduct(String id, TransformCrawlResponse res, AliexStoreInfo aliexStoreInfo, int pageIndex, String storeSign, ArrayList<ImagePathModel> imagePathModels) {
        ArrayList<ProductAmz> listProductAmz = ProcessTransformAliexToAmz.transform(
                res,
                aliexStoreInfo,
                imagePathModels
        );
        String key = genKey(ComputerIdentifier.diskSerial, storeSign, pageIndex);
        if (listProductAmz != null) {
            if (!mapProducts.containsKey(key)) {
                mapProducts.put(key, listProductAmz);
            } else {
                ArrayList<ProductAmz> list = mapProducts.get(key);
                list.addAll(listProductAmz);
            }
        } else {
            processErrorProduct(storeSign, pageIndex, id, "Can not get any product");
            System.out.println("Can not get any product from id: " + id + " page " + pageIndex);
        }
    }
    
    public void processRapidProduct(String id, TransformCrawlResponse res, AliexStoreInfo aliexStoreInfo, int pageIndex, String storeSign) {
        ArrayList<ProductAmz> listProductAmz = ProcessTransformAliexToAmz.transformRapid(
                res,
                aliexStoreInfo
        );
        String key = genKey(ComputerIdentifier.diskSerial, storeSign, pageIndex);
        if (listProductAmz != null) {
            if (!mapProducts.containsKey(key)) {
                mapProducts.put(key, listProductAmz);
            } else {
                ArrayList<ProductAmz> list = mapProducts.get(key);
                list.addAll(listProductAmz);
            }
        } else {
            processErrorProduct(storeSign, pageIndex, id, "Can not get any product");
            System.out.println("Can not get any product from id: " + id + " page " + pageIndex);
        }
    }
    
    public int getSuccessCount(String storeSign, int pageIndex) {
        String key = genKey(ComputerIdentifier.diskSerial, storeSign, pageIndex);
        if (mapProducts.containsKey(key)) {
            int count = 0;
            for (ProductAmz productAmz : mapProducts.get(key)) {
                if (productAmz.isParent()) {
                    count ++;
                }
            }
            return count;
        } else {
            return 0;
        }
    }
    
//    public String getStatus(String storeSign, int pageIndex, int pageTotal, int index, int size) {
//        return "Page (" + pageIndex + "/" + pageTotal + ") " + 
//                getPercentProcess(size, index) + "%    " + 
//                "Success (" + getSuccessCount(storeSign, pageIndex) + "/" + size + ")";
//        return "Page (" + pageIndex + "/" + pageTotal + ") " +
//                getPercentProcess(size, index) + "%";
//    }
    
    public String getStatusPageOnly(String storeSign, int pageIndex, int index, int size) {
//        return "Page (" + pageIndex + "/" + pageTotal + ") " + 
//                getPercentProcess(size, index) + "%    " + 
//                "Success (" + getSuccessCount(storeSign, pageIndex) + "/" + size + ")";
        return "Page (" + pageIndex + ") " + 
                getPercentProcess(size, index) + "%";
    }
    
    public String getStatusWithFixedPercent(String storeSign, int pageIndex, int pageTotal, int percent, int size) {
//        return "Page (" + pageIndex + "/" + pageTotal + ") " + 
//                percent + "%    " + 
//                "Success (" + getSuccessCount(storeSign, pageIndex) + "/" + size + ")";
        return "Page (" + pageIndex + "/" + pageTotal + ") " + 
                percent + "%";
    }
    
    public String getStatusPageOnlyWithFixedPercent(String storeSign, int pageIndex, int percent, int size) {
//        return "Page (" + pageIndex + "/" + pageTotal + ") " + 
//                percent + "%    " + 
//                "Success (" + getSuccessCount(storeSign, pageIndex) + "/" + size + ")";
        return "Page (" + pageIndex + ") " + 
                percent + "%";
    }
    
    public String getStatusSumarize(String storeSign, int pageIndex, int total) {
        return "Done (" + getSuccessCount(storeSign, pageIndex) + "/" + total + ")";
    }
    
    public int getPercentProcess(int size, int j) {
        int percent = (int) ((((j + 1) * 1f) / size) * 100);
        if (percent == 100) {
            percent = 99;
        }
        return percent;
    }
    
    public void processErrorProducts(TransformResponse res, String storeSign, int pageIndex) {
        String key = genKey(ComputerIdentifier.diskSerial, storeSign, pageIndex);
        System.out.println("processErrorProducts: " + res.getData().productId);
        if (!mapErrorsProducts.containsKey(key)) {
                ArrayList<Pair<String, String>> list = new ArrayList<>();
                list.add(new Pair<>(res.getData().productId, res.error));
                mapErrorsProducts.put(key, list);
            } else {
                ArrayList<Pair<String, String>> list = mapErrorsProducts.get(key);
                list.add(new Pair<>(res.getData().productId, res.error));
            }
    }

    public void processErrorProducts(String productId, String storeSign, int pageIndex, String error) {
        String key = genKey(ComputerIdentifier.diskSerial, storeSign, pageIndex);
        System.out.println("processErrorProducts: " + productId);
        if (!mapErrorsProducts.containsKey(key)) {
            ArrayList<Pair<String, String>> list = new ArrayList<>();
            list.add(new Pair<>(productId, error));
            mapErrorsProducts.put(key, list);
        } else {
            ArrayList<Pair<String, String>> list = mapErrorsProducts.get(key);
            list.add(new Pair<>(productId, error));
        }
    }
    
    public void processErrorProduct(String storeSign, int pageIndex, String productId, String error) {
        String key = genKey(ComputerIdentifier.diskSerial, storeSign, pageIndex);
        if (!mapErrorsProducts.containsKey(key)) {
            ArrayList<Pair<String, String>> list = new ArrayList<>();
            list.add(new Pair<>(productId, error));
            mapErrorsProducts.put(key, list);
        } else {
            ArrayList<Pair<String, String>> list = mapErrorsProducts.get(key);
            list.add(new Pair<>(productId, error));
        }
    }

    public void processPageInfo(AliexPageInfo aliexPageInfo) {
        String keyProduct = genKey(ComputerIdentifier.diskSerial, aliexPageInfo.getStoreSign(), aliexPageInfo.getPageIndex());
        ArrayList<ProductAmz> list = mapProducts.get(keyProduct);

        String keyStore1 = genKey(ComputerIdentifier.diskSerial, aliexPageInfo.getStoreSign());
        AliexStoreInfo aliexStoreInfo2 = mapStoreInfo.get(keyStore1);
        boolean isNeedRemoveBrandInfo = mapBrandName.get(keyStore1) != null && !mapBrandName.get(keyStore1).isEmpty();

        if (list != null) {
            ArrayList<ProductAmz> listTemp = new ArrayList<>();
            int productCount = 0;
            boolean isChildProcessing = false;
            int size = list.size();
            System.out.println("Page " + aliexPageInfo.getPageIndex() + ": Total Row => " + size);
            
            int partCount = 0;
            int rowCount = 0;
            int parentCount = 0;
            for (ProductAmz productAmz : list) {
                if (isNeedRemoveBrandInfo) {
                    removeBrandInfo(mapBrandName.get(keyStore1), productAmz);
                }
                productCount ++;
                rowCount ++;
                isChildProcessing = !productAmz.isParent();
                if (!isChildProcessing) {
                    parentCount ++;
                }
                if (productCount == size || (rowCount >= Configs.maxRow && !isChildProcessing)) {
                    String fileName = null;
                    System.out.println("processPageInfo " + productCount + "/" + size + " " + partCount);
                    if (productCount == size && partCount == 0) {
                            listTemp.add(productAmz);
                        fileName = genExcelFileNameWithPage(aliexStoreInfo2, aliexPageInfo.getPageIndex());
                        ProcessPageDataSvs.processPageData(listTemp, aliexStoreInfo2, fileName, false);
                    } else {
                        fileName = genExcelFileNameWithPage(aliexStoreInfo2, aliexPageInfo.getPageIndex(), partCount + 1);
                        ProcessPageDataSvs.processPageData(listTemp, aliexStoreInfo2, fileName, false);
                        listTemp.clear();
                            listTemp.add(productAmz);
                        partCount ++;
                        rowCount = 1;
                    }
                } else {
                        listTemp.add(productAmz);
                    }
                }
            
            System.out.println("Page " + aliexPageInfo.getPageIndex() + ": Total parent => " + parentCount);
        }
        
        ArrayList<Pair<String, String>> listErrors = mapErrorsProducts.get(keyProduct);
        if (listErrors != null && !listErrors.isEmpty()) {
            ProcessPageDataSvs.processPageErrorData(listErrors, aliexStoreInfo2, aliexPageInfo.getPageIndex());
        }
    }

    public void processPageInfoNew(AliexStoreInfo aliexStoreInfo, AliexPageInfo aliexPageInfo, int page, ArrayList<TransformCrawlResponse> listResults, String cagetory) {
        String localImageFolder = aliexStoreInfo.getLocalImageFolder();
        if (localImageFolder != null && Configs.downloadAllImage == 1) {
            for (TransformCrawlResponse response : listResults) {
                String productImageFolder = aliexStoreInfo.getLocalImageFolderForProductId(localImageFolder, response.productId);
                DownloadManager.getInstance().downloadImageNewFormat(response, productImageFolder);
            }
        }

        String fileName = genExcelFileNameWithPage(aliexStoreInfo, page);
        try {
            ExcelUtils.saveListProductsToExcelNew(listResults, fileName, Configs.excelSampleFilePath, aliexStoreInfo, false, cagetory);
        } catch (EncryptedDocumentException | InvalidFormatException | IOException ex) {
            java.util.logging.Logger.getLogger(ProcessPageDataSvs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String genKey(String diskSerialNumber, String storeSign) {
        return diskSerialNumber + storeSign;
    }

    public static String genKey(String diskSerialNumber, String storeSign, int page) {
        return diskSerialNumber + storeSign + "page" + page;
    }

    public String genExcelFileNameWithPage(AliexStoreInfo aliexStoreInfo, int pageIndex, int indexPart) {
        if (pageIndex == 0) {
            pageIndex = 1;
        }

        File file = new File(Configs.PRODUCT_DATA_PATH + aliexStoreInfo.accNo);

        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getPath() + Configs.pathChar + "Aliex");
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getPath() + Configs.pathChar + aliexStoreInfo.storeSign);

        if (!file.exists()) {
            file.mkdir();
        }

        return file.getPath() + Configs.pathChar + aliexStoreInfo.accNo + "_" + aliexStoreInfo.storeSign + "_page" + pageIndex + "_" + indexPart + ".xlsx";

    }
    
    public String genExcelFileNameWithPage(AliexStoreInfo aliexStoreInfo, int pageIndex) {
        if (pageIndex == 0) {
            pageIndex = 1;
        }

        File file = new File(Configs.PRODUCT_DATA_PATH + aliexStoreInfo.accNo);

        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getPath() + Configs.pathChar + "Aliex");
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getPath() + Configs.pathChar + aliexStoreInfo.storeSign);

        if (!file.exists()) {
            file.mkdir();
        }

        return file.getPath() + Configs.pathChar + aliexStoreInfo.accNo + "_" + aliexStoreInfo.storeSign + "_page" + pageIndex + ".xlsx";

    }
    
    public String genExcelFileNameForStore(AliexStoreInfo aliexStoreInfo) {
        File file = new File(Configs.PRODUCT_DATA_PATH + aliexStoreInfo.accNo);

        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getPath() + Configs.pathChar + "Aliex");
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getPath() + Configs.pathChar + aliexStoreInfo.storeSign);

        if (!file.exists()) {
            file.mkdir();
        }

        return file.getPath() + Configs.pathChar + aliexStoreInfo.accNo + "_" + aliexStoreInfo.storeSign + ".xlsx";

    }
}
