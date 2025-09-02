package com.phanduy.aliexscrap.model.response;

public class UpdateCrawlSignatureResponse {
    public boolean success;
    public String message;
    public String data;

    public UpdateCrawlSignatureResponse() {
    }

    public UpdateCrawlSignatureResponse(boolean success, String message, String data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
