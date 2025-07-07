package com.phanduy.aliexscrap.controller.thread;

import com.phanduy.aliexscrap.controller.transform.ProcessStoreInfoSvs;
import com.phanduy.aliexscrap.model.aliex.store.AliexPageInfo;
import com.phanduy.aliexscrap.model.aliex.store.AliexStoreInfo;
import com.phanduy.aliexscrap.model.response.TransformCrawlResponse;

import java.util.ArrayList;

public class ExportFileNewFlowThread extends Thread {

    private AliexStoreInfo aliexStoreInfo;
    private AliexPageInfo aliexPageInfo;
    private int page;
    private ArrayList<TransformCrawlResponse> listResults;
    private String cagetory;
    private ProcessStoreInfoSvs processStoreInfoSvs;

    public ExportFileNewFlowThread(ProcessStoreInfoSvs processStoreInfoSvs, AliexPageInfo aliexPageInfo, ArrayList<TransformCrawlResponse> listResults, int page, String cagetory, AliexStoreInfo aliexStoreInfo) {
        this.processStoreInfoSvs = processStoreInfoSvs;
        this.aliexPageInfo = aliexPageInfo;
        this.listResults = listResults;
        this.page = page;
        this.cagetory = cagetory;
        this.aliexStoreInfo = aliexStoreInfo;
    }

    @Override
    public void run() {
        try {
            processStoreInfoSvs.processPageInfoNew(aliexStoreInfo, aliexPageInfo, page, listResults, cagetory);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
