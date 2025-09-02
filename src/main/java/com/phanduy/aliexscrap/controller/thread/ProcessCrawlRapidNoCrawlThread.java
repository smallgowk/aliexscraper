/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.controller.thread;

import com.phanduy.aliexscrap.config.Configs;
import com.phanduy.aliexscrap.interfaces.CrawlProcessListener;
import com.phanduy.aliexscrap.model.aliex.store.AliexPageInfo;
import com.phanduy.aliexscrap.model.aliex.store.AliexStoreInfo;
import com.phanduy.aliexscrap.controller.inputprocess.TransformStoreInput;
import com.phanduy.aliexscrap.controller.CacheSvs;
import com.phanduy.aliexscrap.controller.transform.ProcessStoreInfoSvs;
import com.google.gson.Gson;
import com.phanduy.aliexscrap.model.aliex.store.inputdata.BaseStoreOrderInfo;
import com.phanduy.aliexscrap.model.request.*;
import com.phanduy.aliexscrap.api.ApiCall;
import com.phanduy.aliexscrap.api.ApiClient;
import com.phanduy.aliexscrap.api.ApiService;
import com.phanduy.aliexscrap.model.response.*;
import com.phanduy.aliexscrap.utils.ComputerIdentifier;
import com.phanduy.aliexscrap.utils.DialogUtil;
import com.phanduy.aliexscrap.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.phanduy.aliexscrap.model.response.CheckInfoResponse.PRODUCT_LIMIT_IN_DAY;

/**
 *
 * @author duyuno
 */
public class ProcessCrawlRapidNoCrawlThread extends Thread {

    BaseStoreOrderInfo baseStoreOrderInfo;
    HashMap<String, String> toolParams;
    CrawlProcessListener crawlProcessListener;
    ProcessStoreInfoSvs processStoreInfoSvs;
    ApiService apiService;
    ApiService apiServiceNoLog;
    String signature;
    String linkSheetId;
    String pageNumber;
    ArrayList<String> listProducts;

//    StringBuffer sb;
    public ProcessCrawlRapidNoCrawlThread(
            BaseStoreOrderInfo baseStoreOrderInfo,
            HashMap<String, String> toolParams,
            String signature,
            String linkSheetId,
            String linkSheetName,
            String pageNumber,
            CrawlProcessListener crawlProcessListener,
            ArrayList<String> listProducts
    ) {
        this.signature = signature;
        this.linkSheetId = linkSheetId;
        this.pageNumber = pageNumber;
        this.listProducts = listProducts;
        try {
            this.baseStoreOrderInfo = baseStoreOrderInfo;
            this.toolParams = toolParams;
            this.crawlProcessListener = crawlProcessListener;
            processStoreInfoSvs = new ProcessStoreInfoSvs();

            apiService = ApiClient.getClient().create(ApiService.class);
            apiServiceNoLog = ApiClient.getClientNoLog().create(ApiService.class);
        } catch (Exception ex) {
            try (java.io.FileWriter fw = new java.io.FileWriter("error.log", true)) {
                fw.write("Exception in ProcessCrawlRapidNoCrawlThread constructor: " + ex.toString() + "\n");
                for (StackTraceElement ste : ex.getStackTrace()) {
                    fw.write("    at " + ste.toString() + "\n");
                }
            } catch (Exception e) {}
            throw ex;
        }
    }

    public boolean isStop = false;

    int successCount = 0;
    int totalCount = 0;
    boolean isHasShip = false;

    public int getPercentProcess(int size, int j) {
        int percent = (int) ((((j + 1) * 1f) / size) * 100);
        if (percent == 100) {
            percent = 99;
        }
        return percent;
    }

    @Override
    public void run() {
        String checkSignature = CrawlExecutor.checkState(signature);
        if (!StringUtils.isEmpty(checkSignature)) {
            crawlProcessListener.onPushState(signature, pageNumber, checkSignature);
            return;
        }
        try {
            successCount = 0;
            totalCount = 0;
            isHasShip = false;
            CheckConfigsReq checkConfigsReq = new CheckConfigsReq();
            checkConfigsReq.configs = toolParams;
            ConfigInfo configInfo = ApiCall.getInstance().getConfig(checkConfigsReq);

            if (configInfo == null) {
                isStop = true;
//                DialogUtil.showInfoMessage(null, "Lỗi hệ thống! Vui lòng kiểm tra kết nối mạng hoặc báo người quản trị!");
                crawlProcessListener.onPushState(signature, pageNumber, "Error");
                return;
            }

            Configs.updateConfig(configInfo);

            AliexStoreInfo aliexStoreInfo = TransformStoreInput.getInstance().transformRawData(baseStoreOrderInfo);
            aliexStoreInfo.setStoreSign(signature);
            String computerSerial = ComputerIdentifier.getDiskSerialNumber().replaceAll(" ", "-");

            UpdateCrawlSignatureReq updateCrawlSignatureReq = new UpdateCrawlSignatureReq(
                    linkSheetId,
                    signature
            );
            updateCrawlSignatureReq.notes = Configs.getConfigInfo(
                    toolParams,
                    computerSerial
            );
            updateCrawlSignatureReq.status = "Getting";

            try {
                ApiCall.getInstance().updateCrawlSignature(updateCrawlSignatureReq);
            } catch (Exception ex) {
                System.out.println("" + ex.getMessage());
            }

            processData(aliexStoreInfo, computerSerial, listProducts);
        } catch (Exception ex) {
            try (java.io.FileWriter fw = new java.io.FileWriter("error.log", true)) {
                fw.write("[Thread] Exception: " + ex.toString() + "\n");
                for (StackTraceElement ste : ex.getStackTrace()) {
                    fw.write("    at " + ste.toString() + "\n");
                }
            } catch (Exception e) {}
            ex.printStackTrace();
        }
    }

    public void processData(AliexStoreInfo aliexStoreInfo, String computerSerial, ArrayList<String> listProducts) {
        try {
            aliexStoreInfo.setStoreSign(signature);
//            crawlProcessListener.onStartProcess(aliexStoreInfo.getStoreSign(), aliexStoreInfo.info);
            processStoreInfoSvs.processStoreInfo(aliexStoreInfo);
            crawlProcessListener.onPushState(signature, pageNumber, "...");
//            GetAliexProductsReq getAliexProductsReq = new GetAliexProductsReq(
//                    computerSerial,
//                    signature,
//                    pageNumber
//            );
//            List<String> getPageGGProducts = ApiCall.getInstance().getAliexProducts(getAliexProductsReq);

            if (StringUtils.isEmpty(Configs.template)) {
                processOldFlow(listProducts, aliexStoreInfo);
            } else {
                processNewFormatFlow(listProducts, aliexStoreInfo);
            }
//            UpdateCrawlSignatureReq updateCrawlSignatureReq = new UpdateCrawlSignatureReq(
//                    linkSheetId,
//                    linkSheetName,
//                    signature
//            );
//            updateCrawlSignatureReq.completedPages = pageNumber;
//
//            try {
//                ApiCall.getInstance().updateCrawlSignature(updateCrawlSignatureReq);
//            } catch (Exception ex) {
//                System.out.println("" + ex.getMessage());
//            }

            crawlProcessListener.onPushState(signature, pageNumber, "Done");
//            crawlProcessListener.onFinishPage(aliexStoreInfo.getStoreSign());
        } catch (Exception ex) {
            try (java.io.FileWriter fw = new java.io.FileWriter("error.log", true)) {
                fw.write("[Thread] processStore Exception: " + ex.toString() + "\n");
                for (StackTraceElement ste : ex.getStackTrace()) {
                    fw.write("    at " + ste.toString() + "\n");
                }
            } catch (Exception e) {}
            ex.printStackTrace();
        }
    }

    public TransformCrawlResponse getProductDetail(String productId, AliexStoreInfo aliexStoreInfo) throws Exception {
        TransformRapidDataReq transformRapidDataReq = new TransformRapidDataReq(
                productId,
                "USD",
                aliexStoreInfo.region,
                Configs.regionMap.get(aliexStoreInfo.region),
                "newpltool",
                aliexStoreInfo,
                toolParams
        );
        
        return StringUtils.isEmpty(Configs.template) ?
                ApiCall.getInstance().getOldTemplateProduct(transformRapidDataReq) :
                ApiCall.getInstance().getNewTemplateProduct(transformRapidDataReq);
    }

    public void processNewFormatFlow(List<String> items, AliexStoreInfo aliexStoreInfo) throws Exception {
        if (isStop) {
            return;
        }

        int size = items.size();
        totalCount += size;
        int crawlCount = 0;
        int page = Integer.parseInt(pageNumber);
        ArrayList<TransformCrawlResponse> listResults = new ArrayList<>();
        
        String keyCache = aliexStoreInfo.getKeyCache(toolParams);

        HashMap<String, RapidStoreSeller> hashMapStore = new HashMap<>();

        for (int j = 0; j < size; j++) {
            if (isStop) {
                return;
            }
            String productId = items.get(j);
            TransformCrawlResponse res = CacheSvs.getInstance().getProductResFromCache(productId, keyCache);
            if (res == null) {
                crawlCount++;
                TransformRapidDataReq transformRapidDataReq = new TransformRapidDataReq(
                        productId,
                        "USD",
                        aliexStoreInfo.region,
                        Configs.regionMap.get(aliexStoreInfo.region),
                        "newpltool",
                        aliexStoreInfo,
                        toolParams
                );
                try {
                    TransformCrawlResponse data = ApiCall.getInstance().getNewTemplateProduct(transformRapidDataReq);
                    if (data.isSuccess()) {
                        isHasShip = true;
                        CacheSvs.getInstance().saveProductInfo(data, keyCache);
                        data.updateImageDownloads();
                        listResults.add(data);
                        if (data.storeInfo != null && !StringUtils.isEmpty(data.storeInfo.sellerId)) {
                            if (!hashMapStore.containsKey(data.storeInfo.sellerId)) {
                                hashMapStore.put(data.storeInfo.sellerId, data.storeInfo);
                            }
                        }
                        successCount++;
                    } else {
                        if (data.getResultCode() == PRODUCT_LIMIT_IN_DAY) {
                            isStop = true;
                            UpdateCrawlSignatureReq updateCrawlSignatureReq = new UpdateCrawlSignatureReq(
                                    linkSheetId,
                                    signature
                            );
                            updateCrawlSignatureReq.error = data.getMessage();

                            try {
                                ApiCall.getInstance().updateCrawlSignature(updateCrawlSignatureReq);
                            } catch (Exception ex) {
                                System.out.println("" + ex.getMessage());
                            }
                            crawlProcessListener.onStop(data.getMessage());
                            return;
                        } else {
                            CacheSvs.getInstance().saveProductInfo(new TransformCrawlResponse(productId), keyCache);
                            processStoreInfoSvs.processErrorProducts(productId, aliexStoreInfo.getStoreSign(), page, data.getMessage());
                        }
                    }
                } catch (Exception e) {
                    CacheSvs.getInstance().saveProductInfo(new TransformCrawlResponse(productId), keyCache);
                    if (Configs.isStopByNoShipping && page == 1 && j == 9 && !isHasShip) {
                        UpdateCrawlSignatureReq updateCrawlSignatureReq = new UpdateCrawlSignatureReq(
                                linkSheetId,
                                signature
                        );
                        updateCrawlSignatureReq.error = "Store có nhiều sản phẩm không có ship. Tool sẽ dừng crawl store này để tiết kiệm request!";
                        try {
                            ApiCall.getInstance().updateCrawlSignature(updateCrawlSignatureReq);
                        } catch (Exception ex) {
                            System.out.println("" + ex.getMessage());
                        }
                        CrawlExecutor.addSignatureToBanned(signature);
                        crawlProcessListener.onPushState(signature, pageNumber, "Post.");
                        return;
                    }
                    processStoreInfoSvs.processErrorProducts(productId, aliexStoreInfo.getStoreSign(), page, e.getMessage());
                }
            } else {
                if (res.hasData()) {
                    successCount++;
                    isHasShip = true;
                    res.updateImageDownloads();
                    listResults.add(res);

                    if (res.storeInfo != null && !StringUtils.isEmpty(res.storeInfo.sellerId)) {
                        if (!hashMapStore.containsKey(res.storeInfo.sellerId)) {
                            hashMapStore.put(res.storeInfo.sellerId, res.storeInfo);
                        }
                    }
                }
            }
            if (!isStop) {
                crawlProcessListener.onPushState(
                        signature,
                        pageNumber,
                        processStoreInfoSvs.getPercentProcess(size, j) + "%"
                );
            }
        }

        AliexPageInfo aliexPageInfo = new AliexPageInfo();
        aliexPageInfo.setPageIndex(page);
        aliexPageInfo.setTotalProduct(size);
        aliexPageInfo.setStoreSign(aliexStoreInfo.getStoreSign());

//        processStoreInfoSvs.processPageInfoNew(aliexStoreInfo, aliexPageInfo, page, listResults, baseStoreOrderInfo.getCategory());

        ExportFileExecutor.executeThread(new ExportFileNewFlowThread(
                processStoreInfoSvs,
                aliexPageInfo,
                listResults,
                page,
                baseStoreOrderInfo.getCategory(),
                aliexStoreInfo
        ));

        UpdateCrawlSignatureReq updateCrawlSignatureReq = new UpdateCrawlSignatureReq(
                linkSheetId,
                signature
        );
        updateCrawlSignatureReq.completedPages = pageNumber;

        if (!hashMapStore.isEmpty()) {
            updateCrawlSignatureReq.listStores = new ArrayList<>();
            String keyword = StringUtils.extractKeyword(signature);
            for (RapidStoreSeller store : hashMapStore.values()) {
                updateCrawlSignatureReq.listStores.add(
                        new StoreInfoDataReq(
                                "https:" + store.storeUrl,
                                store.storeTitle,
                                keyword,
                                "" + store.storeAge,
                                "" + store.storeRating.sellerScore,
                                "" + store.storeFollowers,
                                "" + store.storeRating.sellerTotalNum
                        )
                );
            }
        }

        try {
            ApiCall.getInstance().updateCrawlSignature(updateCrawlSignatureReq);
        } catch (Exception ex) {
            System.out.println("" + ex.getMessage());
        }

        crawlProcessListener.onPushState(
                signature,
                pageNumber,
                processStoreInfoSvs.getStatusWithFixedPercent(aliexStoreInfo.getStoreSign(), page, aliexStoreInfo.totalPage, 100, size)
        );
        successCount += processStoreInfoSvs.getSuccessCount(aliexStoreInfo.getStoreSign(), page);
//        processStoreInfoSvs.clearMapData();
    }

    public void processOldFlow(List<String> getPageGGProducts, AliexStoreInfo aliexStoreInfo) throws Exception {
        if (isStop) {
            return;
        }

        if (getPageGGProducts == null) {
            return;
        }

        int size = getPageGGProducts.size();
        totalCount += size;
        int crawlCount = 0;
        int page = Integer.parseInt(pageNumber);

        Gson gson = new Gson();

        String keyCache = aliexStoreInfo.getKeyCache(toolParams);

        HashMap<String, RapidStoreSeller> hashMapStore = new HashMap<>();

        for (int j = 0; j < size; j++) {
            if (isStop) {
                return;
            }
            String productId = getPageGGProducts.get(j);
            TransformCrawlResponse res = CacheSvs.getInstance().getProductResFromCache(productId, keyCache);
            if (res == null) {
                crawlCount++;
                TransformRapidDataReq transformRapidDataReq = new TransformRapidDataReq(
                        productId,
                        "USD",
                        aliexStoreInfo.region,
                        Configs.regionMap.get(aliexStoreInfo.region),
                        "newpltool",
                        aliexStoreInfo,
                        toolParams
                );

                try {
                    TransformCrawlResponse data = ApiCall.getInstance().getOldTemplateProduct(transformRapidDataReq);
                    if (data.isSuccess()) {
                        isHasShip = true;
                        CacheSvs.getInstance().saveProductInfo(data, keyCache);
                        processStoreInfoSvs.processRapidProduct(
                                productId,
                                data,
                                aliexStoreInfo,
                                page,
                                aliexStoreInfo.getStoreSign()
                        );
                        if (data.storeInfo != null && !StringUtils.isEmpty(data.storeInfo.sellerId)) {
                            if (!hashMapStore.containsKey(data.storeInfo.sellerId)) {
                                hashMapStore.put(data.storeInfo.sellerId, data.storeInfo);
                            }
                        }
                    } else {
                        if (data.getResultCode() == PRODUCT_LIMIT_IN_DAY) {
                            isStop = true;
                            crawlProcessListener.onStop(data.getMessage());
                            UpdateCrawlSignatureReq updateCrawlSignatureReq = new UpdateCrawlSignatureReq(
                                    linkSheetId,
                                    signature
                            );
                            updateCrawlSignatureReq.error = data.getMessage();
                            try {
                                ApiCall.getInstance().updateCrawlSignature(updateCrawlSignatureReq);
                            } catch (Exception ex) {
                                System.out.println("" + ex.getMessage());
                            }
                            return;
                        } else {
                            CacheSvs.getInstance().saveProductInfo(new TransformCrawlResponse(productId), keyCache);
                            processStoreInfoSvs.processErrorProducts(productId, aliexStoreInfo.getStoreSign(), page, data.getMessage());
                        }
                    }

                } catch (Exception e) {
                    CacheSvs.getInstance().saveProductInfo(new TransformCrawlResponse(productId), keyCache);
                    if (Configs.isStopByNoShipping && page == 1 && j == 9 && !isHasShip) {
                        UpdateCrawlSignatureReq updateCrawlSignatureReq = new UpdateCrawlSignatureReq(
                                linkSheetId,
                                signature
                        );
                        updateCrawlSignatureReq.error = "Store có nhiều sản phẩm không có ship. Tool sẽ dừng crawl store này để tiết kiệm request!";
                        try {
                            ApiCall.getInstance().updateCrawlSignature(updateCrawlSignatureReq);
                        } catch (Exception ex) {
                            System.out.println("" + ex.getMessage());
                        }
                        CrawlExecutor.addSignatureToBanned(signature);
                        crawlProcessListener.onPushState(
                                signature,
                                pageNumber,
                                "Post."
                        );
                        return;
                    }
                    processStoreInfoSvs.processErrorProducts(productId, aliexStoreInfo.getStoreSign(), page, e.getMessage());
                }
            } else {
                if (res.hasData()) {
                    processStoreInfoSvs.processRapidProduct(
                            productId,
                            res,
                            aliexStoreInfo,
                            page,
                            aliexStoreInfo.getStoreSign()
                    );

                    if (res.storeInfo != null && !StringUtils.isEmpty(res.storeInfo.sellerId)) {
                        if (!hashMapStore.containsKey(res.storeInfo.sellerId)) {
                            hashMapStore.put(res.storeInfo.sellerId, res.storeInfo);
                        }
                    }
                }
            }

            if (!isStop) {
                crawlProcessListener.onPushState(
                        signature,
                        pageNumber,
                        processStoreInfoSvs.getPercentProcess(size, j) + "%"
                );
            }
        }

        AliexPageInfo aliexPageInfo = new AliexPageInfo();
        aliexPageInfo.setPageIndex(page);
        aliexPageInfo.setTotalProduct(size);
        aliexPageInfo.setStoreSign(aliexStoreInfo.getStoreSign());

//        processStoreInfoSvs.processPageInfo(aliexPageInfo);
        ExportFileExecutor.executeThread(new ExportFileOldFlowThread(aliexPageInfo, processStoreInfoSvs));

        crawlProcessListener.onPushState(
                signature,
                pageNumber,
                "Done"
        );

        UpdateCrawlSignatureReq updateCrawlSignatureReq = new UpdateCrawlSignatureReq(
                linkSheetId,
                signature
        );
        updateCrawlSignatureReq.completedPages = pageNumber;

        if (!hashMapStore.isEmpty()) {
            updateCrawlSignatureReq.listStores = new ArrayList<>();
            String keyword = StringUtils.extractKeyword(signature);
            for (RapidStoreSeller store : hashMapStore.values()) {
                updateCrawlSignatureReq.listStores.add(
                        new StoreInfoDataReq(
                                "https:" + store.storeUrl,
                                store.storeTitle,
                                keyword,
                                "" + store.storeAge,
                                "" + store.storeRating.sellerScore,
                                "" + store.storeFollowers,
                                "" + store.storeRating.sellerTotalNum
                        )
                );
            }
        }

        try {
            ApiCall.getInstance().updateCrawlSignature(updateCrawlSignatureReq);
        } catch (Exception ex) {
            System.out.println("" + ex.getMessage());
        }

        successCount += processStoreInfoSvs.getSuccessCount(aliexStoreInfo.getStoreSign(), page);
//        processStoreInfoSvs.clearMapData();
    }
}
