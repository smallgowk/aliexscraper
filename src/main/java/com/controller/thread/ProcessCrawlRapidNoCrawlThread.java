/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.thread;

import com.config.Configs;
import com.interfaces.CrawlProcessListener;
import com.models.aliex.store.AliexPageInfo;
import com.models.aliex.store.AliexStoreInfo;
import com.controller.inputprocess.TransformStoreInput;
import com.controller.CacheSvs;
import com.controller.transform.ProcessPageDataSvs;
import com.controller.transform.ProcessStoreInfoSvs;
import com.google.gson.Gson;
import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import com.models.request.CheckConfigsReq;
import com.models.request.GetPageDataRapidDataReq;
import com.models.request.SearchRapidReq;
import com.models.request.TransformRapidDataReq;
import com.models.response.*;
import com.phanduy.aliexscrap.api.ApiCall;
import com.phanduy.aliexscrap.api.ApiClient;
import com.phanduy.aliexscrap.api.ApiService;
import com.phanduy.aliexscrap.model.request.GetStoreInfoRapidDataReq;
import com.phanduy.aliexscrap.model.response.GetStoreInfoRapidData;
import com.utils.ComputerIdentifier;
import com.utils.DialogUtil;
import com.utils.ExcelUtils;
import com.utils.StringUtils;
import com.view.DataUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

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

//    StringBuffer sb;
    public ProcessCrawlRapidNoCrawlThread(
            BaseStoreOrderInfo baseStoreOrderInfo,
            HashMap<String, String> toolParams,
            CrawlProcessListener crawlProcessListener
    ) {
        try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
            fw.write("Entered ProcessCrawlRapidNoCrawlThread constructor\n");
            fw.write("baseStoreOrderInfo: " + (baseStoreOrderInfo == null ? "null" : baseStoreOrderInfo.getClass().getName()) + "\n");
            fw.write("toolParams: " + (toolParams == null ? "null" : toolParams.toString()) + "\n");
            fw.write("crawlProcessListener: " + (crawlProcessListener == null ? "null" : crawlProcessListener.getClass().getName()) + "\n");
        } catch (Exception e) {}
        try {
            this.baseStoreOrderInfo = baseStoreOrderInfo;
            this.toolParams = toolParams;
            this.crawlProcessListener = crawlProcessListener;
            processStoreInfoSvs = new ProcessStoreInfoSvs();

            apiService = ApiClient.getClient().create(ApiService.class);
            apiServiceNoLog = ApiClient.getClientNoLog().create(ApiService.class);
        } catch (Exception ex) {
            try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
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

    public void doStop() {
        isStop = true;
        try {
            interrupt();
        } catch (Exception ex) {
            System.out.println("Do stop: " + ex.getMessage());
        } finally {
            crawlProcessListener.onStop("");
        }
    }

    public int getPercentProcess(int size, int j) {
        int percent = (int) ((((j + 1) * 1f) / size) * 100);
        if (percent == 100) {
            percent = 99;
        }
        return percent;
    }

    @Override
    public void run() {
        try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
            fw.write("[Thread] run() bắt đầu\n");
        } catch (Exception e) {}
        try {
            successCount = 0;
            totalCount = 0;
            isHasShip = false;
            System.out.println("=================");
            CheckConfigsReq checkConfigsReq = new CheckConfigsReq();
            checkConfigsReq.configs = toolParams;
            ConfigInfo configInfo = ApiCall.getInstance().getConfig(checkConfigsReq);

            if (configInfo == null) {
                isStop = true;
                try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
                    fw.write("[Thread] configInfo null, thoát\n");
                } catch (Exception e) {}
                DialogUtil.showInfoMessage(null, "Lỗi hệ thống! Vui lòng kiểm tra kết nối mạng hoặc báo người quản trị!");
                crawlProcessListener.onExit();
                return;
            }

            Configs.updateConfig(configInfo);

            AliexStoreInfo aliexStoreInfo = TransformStoreInput.getInstance().transformRawData(baseStoreOrderInfo);
            String computerSerial = ComputerIdentifier.getDiskSerialNumber().replaceAll(" ", "-");

            if (StringUtils.isEmpty(aliexStoreInfo.query)) {
                try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
                    fw.write("[Thread] Gọi processStore\n");
                } catch (Exception e) {}
                processStore(aliexStoreInfo, computerSerial);
            } else {
                try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
                    fw.write("[Thread] Gọi processQuery\n");
                } catch (Exception e) {}
                processQuery(aliexStoreInfo, computerSerial);
            }
            try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
                fw.write("[Thread] run() completed\n");
            } catch (Exception e) {}
        } catch (Exception ex) {
            try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
                fw.write("[Thread] Exception: " + ex.toString() + "\n");
                for (StackTraceElement ste : ex.getStackTrace()) {
                    fw.write("    at " + ste.toString() + "\n");
                }
            } catch (Exception e) {}
            ex.printStackTrace();
        }
    }

    public void processQuery(AliexStoreInfo aliexStoreInfo, String computerSerial) {
        try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
            fw.write("[Thread] processQuery bắt đầu\n");
        } catch (Exception e) {}
        try {
            Gson gson = new Gson();
            crawlProcessListener.onStartProcess(aliexStoreInfo.getStoreSign(), aliexStoreInfo.info);
            long start = System.currentTimeMillis();
            crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Getting aliex store info...");
            System.out.println("Getting aliex store info...");
            processStoreInfoSvs.processStoreInfo(aliexStoreInfo);
            crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Getting product info...");
            SearchRapidReq searchRapidReq = new SearchRapidReq(
                    aliexStoreInfo.query,
                    computerSerial,
                    "USD",
                    aliexStoreInfo.region,
                    Configs.regionMap.get(aliexStoreInfo.region),
                    1
            );
            GetPageRapidData getPageRapidData = ApiCall.getInstance().searchPageData(searchRapidReq);
            int page = 1;
            while (getPageRapidData != null && getPageRapidData.items != null && !getPageRapidData.items.isEmpty()) {
                System.out.println("successCount: " + successCount + "/" + totalCount);
                getPageRapidData = processSearchData(aliexStoreInfo.query, computerSerial, getPageRapidData, aliexStoreInfo, page++);
            }
            crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Done (" + successCount + "/" + totalCount + ")");
            crawlProcessListener.onFinishPage(aliexStoreInfo.getStoreSign());
            try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
                fw.write("[Thread] processQuery completed\n");
            } catch (Exception e) {}
        } catch (Exception ex) {
            try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
                fw.write("[Thread] processQuery Exception: " + ex.toString() + "\n");
                for (StackTraceElement ste : ex.getStackTrace()) {
                    fw.write("    at " + ste.toString() + "\n");
                }
            } catch (Exception e) {}
            ex.printStackTrace();
        }
    }

    public void processStore(AliexStoreInfo aliexStoreInfo, String computerSerial) {
        try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
            fw.write("[Thread] processStore bắt đầu\n");
        } catch (Exception e) {}
        try {
            Gson gson = new Gson();
            String sellerId = null;
            GetStoreInfoRapidDataReq getStoreInfoRapidDataReq = new GetStoreInfoRapidDataReq(
                    aliexStoreInfo.productId,
                    computerSerial,
                    "USD",
                    aliexStoreInfo.region,
                    Configs.regionMap.get(aliexStoreInfo.region)
            );
            GetStoreInfoRapidData data = ApiCall.getInstance().getStoreInfoRapidData(getStoreInfoRapidDataReq);
            if (data == null) {
                DialogUtil.showInfoMessage(null, "Không xác định được thông tin seller!");
                crawlProcessListener.onFinishPage(aliexStoreInfo.getStoreSign());
                crawlProcessListener.onExit();
                try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
                    fw.write("[Thread] processStore: data null, thoát\n");
                } catch (Exception e) {}
                return;
            } else {
                sellerId = data.sellerId;
                aliexStoreInfo.setStoreId(data.storeId);
                aliexStoreInfo.setInfo("Store: " + data.storeId + "             Seller: " + sellerId);
                baseStoreOrderInfo.setStoreId(data.storeId);
                DataUtils.updateStoreByProductId(baseStoreOrderInfo);
                System.out.println("SellerId by api: " + sellerId);
            }
            crawlProcessListener.onStartProcess(aliexStoreInfo.getStoreSign(), aliexStoreInfo.info);
            long start = System.currentTimeMillis();
            crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Getting aliex store info...");
            System.out.println("Getting aliex store info...");
            processStoreInfoSvs.processStoreInfo(aliexStoreInfo);
            crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Getting product info...");
            GetPageDataRapidDataReq getPageDataRapidDataReq = new GetPageDataRapidDataReq(
                    aliexStoreInfo.getStoreSign(),
                    sellerId,
                    computerSerial,
                    "USD",
                    aliexStoreInfo.region,
                    Configs.regionMap.get(aliexStoreInfo.region),
                    1
            );
            GetPageRapidData getPageRapidData = ApiCall.getInstance().getPageData(getPageDataRapidDataReq);
            System.out.println("Page request: " + gson.toJson(getPageDataRapidDataReq));
            System.out.println("Page res: " + gson.toJson(getPageRapidData));
            int page = 1;
            aliexStoreInfo.totalPage = getPageRapidData.totalPages;
            while (page <= aliexStoreInfo.totalPage) {
                System.out.println("successCount: " + successCount + "/" + totalCount);
                if (StringUtils.isEmpty(Configs.template)) {
                    getPageRapidData = processOldFlow(sellerId, computerSerial, getPageRapidData, aliexStoreInfo, page++);
                } else {
                    getPageRapidData = processNewFormatFlow(sellerId, computerSerial, getPageRapidData, aliexStoreInfo, page++);
                }
            }
            crawlProcessListener.onPushState(aliexStoreInfo.getStoreSign(), "Done (" + successCount + "/" + totalCount + ")");
            crawlProcessListener.onFinishPage(aliexStoreInfo.getStoreSign());
            try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
                fw.write("[Thread] processStore completed\n");
            } catch (Exception e) {}
        } catch (Exception ex) {
            try (java.io.FileWriter fw = new java.io.FileWriter("debug.log", true)) {
                fw.write("[Thread] processStore Exception: " + ex.toString() + "\n");
                for (StackTraceElement ste : ex.getStackTrace()) {
                    fw.write("    at " + ste.toString() + "\n");
                }
            } catch (Exception e) {}
            ex.printStackTrace();
        }
    }

    public GetPageRapidData processSearchData(String query, String computerSerial, GetPageRapidData getPageRapidData, AliexStoreInfo aliexStoreInfo, int page) {
        if (isStop) {
            return null;
        }

        int size = getPageRapidData.items.size();
        totalCount += size;
        int crawlCount = 0;
        ArrayList<TransformCrawlResponse> listResults = new ArrayList<>();
        
        String keyCache = aliexStoreInfo.getKeyCache(toolParams);

        for (int j = 0; j < size; j++) {
            if (isStop) {
                return null;
            }
            String productId = getPageRapidData.items.get(j);
            crawlCount++;
            TransformCrawlResponse res = CacheSvs.getInstance().getProductResFromCache(productId, keyCache);
            if (res == null) {
                TransformCrawlResponse data = null;
                try {
                    data = getProductDetail(productId, aliexStoreInfo);
                    if (data != null) {
                        res = data;
                        CacheSvs.getInstance().saveProductInfo(data, keyCache);
                    }
                } catch (Exception e) {
                    CacheSvs.getInstance().saveProductInfo(new TransformCrawlResponse(productId), keyCache);
                    if (Configs.isStopByNoShipping && page == 1 && j == 9 && !isHasShip) {
                        DialogUtil.showInfoMessage(null, "Store có nhiều sản phẩm không có ship. Tool sẽ dừng crawl store này để tiết kiệm request!");
                        crawlProcessListener.onExit();
                        return null;
                    }
                    processStoreInfoSvs.processErrorProducts(productId, aliexStoreInfo.getStoreSign(), page, e.getMessage());
                    crawlProcessListener.onPushState(
                            aliexStoreInfo.getStoreSign(),
                            processStoreInfoSvs.getStatusPageOnly(aliexStoreInfo.getStoreSign(), page, j, size)
                    );
                    System.out.println(productId + ": tranform fail " + page);
                }
            } 
            
            if (res != null && res.hasData()) {
                isHasShip = true;
                res.updateImageDownloads();
                if (StringUtils.isEmpty(Configs.template)) {
                    processStoreInfoSvs.processRapidProduct(
                            productId,
                            res,
                            aliexStoreInfo,
                            page,
                            aliexStoreInfo.getStoreSign()
                    );
                } else {
                    res.updateImageDownloads();
                    listResults.add(res);
                }
                successCount++;
            }
            
            if (!isStop) {
                crawlProcessListener.onPushState(
                        aliexStoreInfo.getStoreSign(),
                        processStoreInfoSvs.getStatusPageOnly(aliexStoreInfo.getStoreSign(), page, j, size)
                );
            }
        }

        AliexPageInfo aliexPageInfo = new AliexPageInfo();
        aliexPageInfo.setPageIndex(page);
        aliexPageInfo.setTotalProduct(size);
        aliexPageInfo.setStoreSign(aliexStoreInfo.getStoreSign());
        if (StringUtils.isEmpty(Configs.template)) {
            processStoreInfoSvs.processPageInfo(aliexPageInfo);
        } else {
            try {
                String fileName = processStoreInfoSvs.genExcelFileNameWithPage(aliexStoreInfo, page);
                ExcelUtils.saveListProductsToExcelNew(listResults, fileName, Configs.excelSampleFilePath, aliexStoreInfo, false, baseStoreOrderInfo.getCategory());
            } catch (EncryptedDocumentException | InvalidFormatException | IOException ex) {
                java.util.logging.Logger.getLogger(ProcessPageDataSvs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        crawlProcessListener.onPushState(
                aliexStoreInfo.getStoreSign(),
                processStoreInfoSvs.getStatusPageOnlyWithFixedPercent(aliexStoreInfo.getStoreSign(), page, 100, size)
        );
        successCount += processStoreInfoSvs.getSuccessCount(aliexStoreInfo.getStoreSign(), page);
        processStoreInfoSvs.clearMapData();

        SearchRapidReq searchRapidReq = new SearchRapidReq(
                aliexStoreInfo.query,
                computerSerial,
                "USD",
                aliexStoreInfo.region,
                Configs.regionMap.get(aliexStoreInfo.region),
                page + 1
        );

        return ApiCall.getInstance().searchPageData(searchRapidReq);
    }

    public TransformCrawlResponse getProductDetail(String productId, AliexStoreInfo aliexStoreInfo) throws Exception {
        TransformRapidDataReq transformRapidDataReq = new TransformRapidDataReq(
                productId,
                "USD",
                aliexStoreInfo.region,
                Configs.regionMap.get(aliexStoreInfo.region),
                aliexStoreInfo,
                toolParams
        );
        
        return StringUtils.isEmpty(Configs.template) ?
                ApiCall.getInstance().getOldTemplateProduct(transformRapidDataReq) :
                ApiCall.getInstance().getNewTemplateProduct(transformRapidDataReq);
    }

    public GetPageRapidData processNewFormatFlow(String sellerId, String computerSerial, GetPageRapidData getPageRapidData, AliexStoreInfo aliexStoreInfo, int page) {
        if (isStop) {
            return null;
        }

        int size = getPageRapidData.items.size();
        totalCount += size;
        int crawlCount = 0;
        ArrayList<TransformCrawlResponse> listResults = new ArrayList<>();
        
        String keyCache = aliexStoreInfo.getKeyCache(toolParams);

        for (int j = 0; j < size; j++) {
            if (isStop) {
                return null;
            }
            String productId = getPageRapidData.items.get(j);
            TransformCrawlResponse res = CacheSvs.getInstance().getProductResFromCache(productId, keyCache);
            if (res == null) {
                crawlCount++;
                TransformRapidDataReq transformRapidDataReq = new TransformRapidDataReq(
                        productId,
                        "USD",
                        aliexStoreInfo.region,
                        Configs.regionMap.get(aliexStoreInfo.region),
                        aliexStoreInfo,
                        toolParams
                );
                try {
                    TransformCrawlResponse data = ApiCall.getInstance().getNewTemplateProduct(transformRapidDataReq);
                    isHasShip = true;
                    CacheSvs.getInstance().saveProductInfo(data, keyCache);
                    data.updateImageDownloads();
                    listResults.add(data);
                    successCount++;
                } catch (Exception e) {
                    CacheSvs.getInstance().saveProductInfo(new TransformCrawlResponse(productId), keyCache);
                    if (Configs.isStopByNoShipping && page == 1 && j == 9 && !isHasShip) {
                        DialogUtil.showInfoMessage(null, "Store có nhiều sản phẩm không có ship. Tool sẽ dừng crawl store này để tiết kiệm request!");
                        crawlProcessListener.onExit();
                        return null;
                    }
                    processStoreInfoSvs.processErrorProducts(productId, aliexStoreInfo.getStoreSign(), page, e.getMessage());
                }
            } else {
                if (res.hasData()) {
                    successCount++;
                    isHasShip = true;
                    res.updateImageDownloads();
                    listResults.add(res);
                }
            }
            if (!isStop) {
                crawlProcessListener.onPushState(
                        aliexStoreInfo.getStoreSign(),
                        processStoreInfoSvs.getStatus(aliexStoreInfo.getStoreSign(), page, aliexStoreInfo.totalPage, j, size)
                );
            }
        }

        AliexPageInfo aliexPageInfo = new AliexPageInfo();
        aliexPageInfo.setPageIndex(page);
        aliexPageInfo.setTotalProduct(size);
        aliexPageInfo.setStoreSign(aliexStoreInfo.getStoreSign());

        try {
            String fileName = processStoreInfoSvs.genExcelFileNameWithPage(aliexStoreInfo, page);
            ExcelUtils.saveListProductsToExcelNew(listResults, fileName, Configs.excelSampleFilePath, aliexStoreInfo, false, baseStoreOrderInfo.getCategory());
        } catch (EncryptedDocumentException | InvalidFormatException | IOException ex) {
            java.util.logging.Logger.getLogger(ProcessPageDataSvs.class.getName()).log(Level.SEVERE, null, ex);
        }

        crawlProcessListener.onPushState(
                aliexStoreInfo.getStoreSign(),
                processStoreInfoSvs.getStatusWithFixedPercent(aliexStoreInfo.getStoreSign(), page, aliexStoreInfo.totalPage, 100, size)
        );
        successCount += processStoreInfoSvs.getSuccessCount(aliexStoreInfo.getStoreSign(), page);
        processStoreInfoSvs.clearMapData();
        if (page <= aliexStoreInfo.totalPage) {
            GetPageDataRapidDataReq getPageDataRapidDataReq = new GetPageDataRapidDataReq(
                    aliexStoreInfo.getStoreSign(),
                    sellerId,
                    computerSerial,
                    "USD",
                    aliexStoreInfo.region,
                    Configs.regionMap.get(aliexStoreInfo.region),
                    page + 1
            );
            return ApiCall.getInstance().getPageData(getPageDataRapidDataReq);
        } else {
            return null;
        }
    }

    public GetPageRapidData processOldFlow(String sellerId, String computerSerial, GetPageRapidData getPageRapidData, AliexStoreInfo aliexStoreInfo, int page) {
        if (isStop) {
            return null;
        }

        if (getPageRapidData.items == null) {
            return null;
        }

        int size = getPageRapidData.items.size();
        totalCount += size;
        int crawlCount = 0;

        Gson gson = new Gson();
        
        String keyCache = aliexStoreInfo.getKeyCache(toolParams);

        for (int j = 0; j < size; j++) {
            if (isStop) {
                return null;
            }
            String productId = getPageRapidData.items.get(j);
            TransformCrawlResponse res = CacheSvs.getInstance().getProductResFromCache(productId, keyCache);
            if (res == null) {
                crawlCount++;
                TransformRapidDataReq transformRapidDataReq = new TransformRapidDataReq(
                        productId,
                        "USD",
                        aliexStoreInfo.region,
                        Configs.regionMap.get(aliexStoreInfo.region),
                        aliexStoreInfo,
                        toolParams
                );

                try {
                    TransformCrawlResponse data = ApiCall.getInstance().getOldTemplateProduct(transformRapidDataReq);
                    isHasShip = true;
                    CacheSvs.getInstance().saveProductInfo(data, keyCache);
                    processStoreInfoSvs.processRapidProduct(
                            productId,
                            data,
                            aliexStoreInfo,
                            page,
                            aliexStoreInfo.getStoreSign()
                    );
                } catch (Exception e) {
                    CacheSvs.getInstance().saveProductInfo(new TransformCrawlResponse(productId), keyCache);
                    if (Configs.isStopByNoShipping && page == 1 && j == 9 && !isHasShip) {
                        DialogUtil.showInfoMessage(null, "Store có nhiều sản phẩm không có ship. Tool sẽ dừng crawl store này để tiết kiệm request!");
                        crawlProcessListener.onExit();
                        return null;
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
                }
            }

            if (!isStop) {
                crawlProcessListener.onPushState(
                        aliexStoreInfo.getStoreSign(),
                        processStoreInfoSvs.getStatus(aliexStoreInfo.getStoreSign(), page, aliexStoreInfo.totalPage, j, size)
                );
            }
        }

        AliexPageInfo aliexPageInfo = new AliexPageInfo();
        aliexPageInfo.setPageIndex(page);
        aliexPageInfo.setTotalProduct(size);
        aliexPageInfo.setStoreSign(aliexStoreInfo.getStoreSign());

        processStoreInfoSvs.processPageInfo(aliexPageInfo);

        crawlProcessListener.onPushState(
                aliexStoreInfo.getStoreSign(),
                processStoreInfoSvs.getStatusWithFixedPercent(aliexStoreInfo.getStoreSign(), page, aliexStoreInfo.totalPage, 100, size)
        );
        successCount += processStoreInfoSvs.getSuccessCount(aliexStoreInfo.getStoreSign(), page);
        processStoreInfoSvs.clearMapData();
        if (page <= aliexStoreInfo.totalPage) {
            GetPageDataRapidDataReq getPageDataRapidDataReq = new GetPageDataRapidDataReq(
                    aliexStoreInfo.getStoreSign(),
                    sellerId,
                    computerSerial,
                    "USD",
                    aliexStoreInfo.region,
                    Configs.regionMap.get(aliexStoreInfo.region),
                    page + 1
            );
            return ApiCall.getInstance().getPageData(getPageDataRapidDataReq);
        } else {
            return null;
        }
    }
}
