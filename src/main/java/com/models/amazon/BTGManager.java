/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.amazon;

import com.config.Configs;
import com.utils.ExcelUtils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author duyuno
 */
public class BTGManager {
    
    private static BTGManager bTGManager;
    
    public static BTGManager getInstance() {
        if(bTGManager == null) {
            bTGManager = new BTGManager();
//            bTGManager.initBTG();
        }
        
        return bTGManager;
    }
    
    public HashMap<String, BTGNode> hashMapBTG = new HashMap<>();
    public HashMap<String, String> hashMapBTGSub = new HashMap<>();
    
    public void initBTG() {
        try {
            File file = new File(Configs.CONFIG_FOLDER_BTG_PATH);
            if(!file.exists()) {
                return;
            }
            String[] btgfiles = file.list();
            for(String s : btgfiles) {
                String fileName = s.substring(0, s.indexOf(".xls"));
                String[] fileParts = fileName.split("-");
                
                ExcelUtils.getBTGFromExcel(s, fileParts[0] + ".xlsx", hashMapBTG, hashMapBTGSub);
            }
        
        
        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(BTGManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public BTGNode getBTGNode(String nodeName) {
        if(nodeName == null || nodeName.isEmpty()) return null;
        
//        String node = nodeName.trim().toUpperCase().replaceAll(" ", "");
        String node = nodeName.trim().toUpperCase();
        
        if(hashMapBTG.containsKey(node)) {
            return hashMapBTG.get(node);
        } else {
            if(hashMapBTGSub.containsKey(node)) {
                String fullNode = hashMapBTGSub.get(node);
                if(hashMapBTG.containsKey(fullNode)) {
                    return hashMapBTG.get(fullNode);
                }
            }
            return null;
        }
    }
}
