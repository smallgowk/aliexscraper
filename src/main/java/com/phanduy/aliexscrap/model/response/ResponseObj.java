/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.model.response;

/**
 *
 * @author PhanDuy
 */
public class ResponseObj {
    private int resultCode;
    private int subData;
    private String message;
    private String data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    public boolean isSuccess() {
        return resultCode == 1;
    }

    public int getSubData() {
        return subData;
    }

    public void setSubData(int subData) {
        this.subData = subData;
    }
    
    
}
