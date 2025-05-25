/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaces;

import com.models.response.ResponseObj;


/**
 *
 * @author PhanDuy
 */
public interface CrawlProcessListener {
    public void onPushState(String storeSign, String state);
    public void onPushErrorRequest(String storeSign, ResponseObj responseObj);
    public void onStartProcess(String storeSign, String info);
    public void onStop(String storeSign);
    public void onStopToLogin(String currentUrl, String storeSign);
    public void onFinishPage(String storeSign);
    public void onExit();
}
