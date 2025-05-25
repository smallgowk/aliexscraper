/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.inputprocess;

import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author PhanDuy
 */
public class InputDataConfig {
    public ArrayList<BaseStoreOrderInfo> listStores;
    public HashMap<String, String> params;
    public ArrayList<String> productIds;

    public InputDataConfig() {
    }

    public void setListStores(ArrayList<BaseStoreOrderInfo> listStores) {
        this.listStores = listStores;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public void setProductIds(ArrayList<String> productIds) {
        this.productIds = productIds;
    }
}
