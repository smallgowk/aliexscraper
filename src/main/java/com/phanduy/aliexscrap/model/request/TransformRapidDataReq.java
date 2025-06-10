/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.model.request;

import com.phanduy.aliexscrap.model.aliex.store.AliexStoreInfo;
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
    public String module;

    public ToolAndStoreInfo toolAndStoreInfo;

    public TransformRapidDataReq(
            String id, 
            String currency, 
            String region, 
            String locale, 
            String module,
            AliexStoreInfo aliexStoreInfo,
            HashMap<String, String> toolParams
    ) {
        this.id = id;
        this.currency = currency;
        this.region = region;
        this.locale = locale;
        this.module = module;

        toolAndStoreInfo = new ToolAndStoreInfo();
        toolAndStoreInfo.configs = toolParams;
        toolAndStoreInfo.updateToolConfig();
        toolAndStoreInfo.updateStoreInfo(aliexStoreInfo);
    }
}
