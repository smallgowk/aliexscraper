/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.request;

import com.models.aliex.store.AliexStoreInfo;
import java.util.HashMap;

/**
 *
 * @author PhanDuy
 */
public class TransformRapidDataReq {
    public String id;
    public String currency;
    public String region;
    public String locale;
    
    public ToolAndStoreInfo toolAndStoreInfo;

    public TransformRapidDataReq(
            String id, 
            String currency, 
            String region, 
            String locale, 
            AliexStoreInfo aliexStoreInfo,
            HashMap<String, String> toolParams
    ) {
        this.id = id;
        this.currency = currency;
        this.region = region;
        this.locale = locale;
        
        toolAndStoreInfo = new ToolAndStoreInfo();
        toolAndStoreInfo.configs = toolParams;
        toolAndStoreInfo.updateToolConfig();
        toolAndStoreInfo.updateStoreInfo(aliexStoreInfo);
    }
}
