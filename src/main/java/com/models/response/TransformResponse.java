package com.models.response;


public class TransformResponse {

    private TransformCrawlResponse data;
    
    public int status;
    
    public String message;
    
    public String error;

    public TransformResponse() {
    }

    public TransformResponse(TransformCrawlResponse data) {
        this.data = data;
    }

    public TransformCrawlResponse getData() {
        return data;
    }

    public void setData(TransformCrawlResponse data) {
        this.data = data;
    }

    public TransformResponse(TransformCrawlResponse data, int status, String message, String error) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.error = error;
    }
    
    public TransformResponse(int status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    
}
