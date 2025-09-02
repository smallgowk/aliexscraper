package com.phanduy.aliexscrap.model.request;

import com.phanduy.aliexscrap.utils.ComputerIdentifier;

import java.util.ArrayList;

public class UpdateCrawlSignatureReq {
    public String id;
    public String signature;
    public String completedPages;
    public String status;
    public String error;
    public String notes;
    public String diskNumber;

    public ArrayList<StoreInfoDataReq> listStores;

    public UpdateCrawlSignatureReq() {
        this.diskNumber = ComputerIdentifier.getDiskSerialNumber();
    }

    public UpdateCrawlSignatureReq(String id, String signature, String completedPages, String status, String error, String notes) {
        this.id = id;
        this.signature = signature;
        this.diskNumber = ComputerIdentifier.getDiskSerialNumber();
        this.completedPages = completedPages;
        this.status = status;
        this.error = error;
        this.notes = notes;
    }

    public UpdateCrawlSignatureReq(String id, String signature) {
        this.id = id;
        this.signature = signature;
        this.diskNumber = ComputerIdentifier.getDiskSerialNumber();
    }
}
