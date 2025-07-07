package com.phanduy.aliexscrap.controller.thread;

import com.phanduy.aliexscrap.controller.transform.ProcessStoreInfoSvs;
import com.phanduy.aliexscrap.model.aliex.store.AliexPageInfo;

public class ExportFileOldFlowThread extends Thread {

    private AliexPageInfo aliexPageInfo;
    private ProcessStoreInfoSvs processStoreInfoSvs;

    public ExportFileOldFlowThread(AliexPageInfo aliexPageInfo, ProcessStoreInfoSvs processStoreInfoSvs) {
        this.aliexPageInfo = aliexPageInfo;
        this.processStoreInfoSvs = processStoreInfoSvs;
    }

    @Override
    public void run() {
        try {
            processStoreInfoSvs.processPageInfo(aliexPageInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
