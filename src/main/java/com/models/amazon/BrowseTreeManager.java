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
import org.apache.log4j.Priority;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author duyuno
 */
public class BrowseTreeManager {
    public static HashMap<String, BTGNode> hashMapBTG = new HashMap<>();
    public static HashMap<String, String> hashMapBTGSub = new HashMap<>();
    
    private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger("BrowseTreeManager");
    
    public static void initBTG() {
        try {
           File btgFolder = new File(Configs.CONFIG_FOLDER_PATH + Configs.pathChar + "BTG");
            LOGGER.log(Priority.ERROR, Configs.CONFIG_FOLDER_PATH + Configs.pathChar + "BTG");
            System.out.println(Configs.CONFIG_FOLDER_PATH + Configs.pathChar + "BTG");
           String[] btgfiles = btgFolder.list();
            for(String file : btgfiles) {
                String fileName = file.substring(0, file.indexOf(".xls"));
                String[] fileParts = fileName.split("-");
                
                ExcelUtils.getBTGFromExcel(file, fileParts[0] + ".xlsx", hashMapBTG, hashMapBTGSub);
            }
        
        
        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(BrowseTreeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static BTGNode getBTGNode(String nodeName) {
//        System.out.println("NodeName: " + nodeName);
        
        if(nodeName == null || nodeName.isEmpty()) return null;
        
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
