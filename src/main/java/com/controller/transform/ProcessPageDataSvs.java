/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.transform;

import com.config.Configs;
import com.models.amazon.ProductAmz;
import com.models.aliex.store.AliexStoreInfo;
import com.utils.ExcelUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.util.Pair;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author PhanDuy
 */
public class ProcessPageDataSvs {

    public static void processPageData(ArrayList<ProductAmz> listProducts, AliexStoreInfo aliexStoreInfo, int pageIndex) {
        String fileName = aliexStoreInfo.genExcelFileNameWithPage(pageIndex, false);
        processPageData(listProducts, aliexStoreInfo, fileName, false);
    }
    
//    public static void processPageDataNew(ArrayList<ProductAmz> listProducts, AliexStoreInfo aliexStoreInfo, int pageIndex, String category) {
//        String fileName = aliexStoreInfo.genExcelFileNameWithPage(pageIndex, false);
//        processPageDataNew(listProducts, aliexStoreInfo, fileName, false, category);
//    }
    
    public static void processPageData(ArrayList<ProductAmz> listProducts, AliexStoreInfo aliexStoreInfo) {
        String fileName = aliexStoreInfo.genExcelFileNameForStore(false);
        processPageData(listProducts, aliexStoreInfo, fileName, true);
    }
    
    public static void processPageData(ArrayList<ProductAmz> listProducts, AliexStoreInfo aliexStoreInfo, String fileName, boolean isSaveAll) {
        try {
            ExcelUtils.saveListProductsToExcel(listProducts, fileName, Configs.excelSampleFilePath, aliexStoreInfo, isSaveAll);
        } catch (EncryptedDocumentException | InvalidFormatException | IOException ex) {
            Logger.getLogger(ProcessPageDataSvs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public static void processPageDataNew(ArrayList<ProductAmz> listProducts, AliexStoreInfo aliexStoreInfo, String fileName, boolean isSaveAll, String category) {
//        try {
//            ExcelUtils.saveListProductsToExcelNew(listProducts, fileName, Configs.excelSampleFilePath, aliexStoreInfo, isSaveAll, category);
//        } catch (EncryptedDocumentException | InvalidFormatException | IOException ex) {
//            Logger.getLogger(ProcessPageDataSvs.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    public static void processPageErrorData(ArrayList<Pair<String, String>> listErrorProducts, AliexStoreInfo aliexStoreInfo, int pageIndex) {
        String fileName = aliexStoreInfo.genExcelFileNameWithPage(pageIndex, true);
        processPageErrorData(listErrorProducts, fileName);
    }
    
    public static void processPageErrorData(ArrayList<Pair<String, String>> listErrorProducts, AliexStoreInfo aliexStoreInfo) {
        String fileName = aliexStoreInfo.genExcelFileNameForStore(true);
        processPageErrorData(listErrorProducts, fileName);
    }
    
    public static void processPageErrorData(ArrayList<Pair<String, String>> listProducts, String fileName) {
        try {
            ExcelUtils.saveErrorProducts(fileName, listProducts);
        } catch (EncryptedDocumentException | IOException ex) {
            Logger.getLogger(ProcessPageDataSvs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
