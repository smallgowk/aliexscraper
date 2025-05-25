/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.response;

/**
 *
 * @author PhanDuy
 */
public class CheckConfigResponse {
    public ConfigInfo data;
    public int status;
    
    public String message;
    
    public String error;
    
    public CheckConfigResponse() {
        super();
    }

    public CheckConfigResponse(int status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }
    
    public CheckConfigResponse(ConfigInfo data, int status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.data = data;
    }
}
