/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.inputprocess;

import com.config.Configs;
import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import static com.utils.ExcelUtils.DATA_SHEET;
import com.utils.StringUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author duyuno
 */
public abstract class ReadStoreOrderInfoSvs {
    
    public static final String DATA_SHEET = "DATA";
    public static final String SETTING_SHEET = "SETTINGS";
    public static final String IDS_SHEET = "IDS";

    public abstract BaseStoreOrderInfo readStoreFromRow(Row fieldNameRow, Row fieldRow, int cellMax, DataFormatter formatter);

    private ArrayList<BaseStoreOrderInfo> readStoreOrderLinks(Sheet sheet) {

        ArrayList<BaseStoreOrderInfo> listResult = null;

        Row fieldNameRow = sheet.getRow(1);
        if (fieldNameRow == null) {
            return null;
        }

        int cellMax = fieldNameRow.getLastCellNum();
        System.out.println("CellMax: " + cellMax);

        int i = 2;
        Row fieldRow = sheet.getRow(i);

        DataFormatter formatter = new DataFormatter();

        while (fieldRow != null) {

            BaseStoreOrderInfo baseStoreOrderInfo = readStoreFromRow(fieldNameRow, fieldRow, cellMax, formatter);
            

            if (baseStoreOrderInfo != null) {
                baseStoreOrderInfo.initStoreSign();
                if (listResult == null) {
                    listResult = new ArrayList<>();
                }
                listResult.add(baseStoreOrderInfo);
            } else {
                break;
            }

            i++;
            fieldRow = sheet.getRow(i);
        }

        return listResult;
    }
    
    private HashMap<String, String> readSettings(Sheet sheet) {

        HashMap<String, String> params = new HashMap<>();

        Row fieldNameRow = sheet.getRow(0);
        if (fieldNameRow == null) {
            return null;
        }

        int cellMax = fieldNameRow.getLastCellNum();

        Row fieldRow = sheet.getRow(1);
        DataFormatter formatter = new DataFormatter();
        for (int j = 0; j < cellMax; j++) {
            String fieldName = formatter.formatCellValue(fieldNameRow.getCell(j)).trim();
            String value = formatter.formatCellValue(fieldRow.getCell(j)).trim();
            if (fieldName.isEmpty() || value.isEmpty()) continue;
            String[] valueParts = value.split(Pattern.quote(","));
            StringBuilder sb = new StringBuilder();
            for (String part: valueParts) {
                String val = part.trim();
                if (val.isEmpty()) continue;
                if (sb.length() == 0) {
                    sb.append(val);
                } else {
                    sb.append(",").append(val);
                }
            }
            if (sb.length() != 0) {
                params.put(fieldName, sb.toString());
            }
        }
        return params;
    }
    
    private ArrayList<String> readIds(Sheet sheet) {
        ArrayList<String> ids = new ArrayList<>();
        int row = 0;
        Row fieldRow = sheet.getRow(row);
        if (fieldRow == null) return null;
        int cellMax = fieldRow.getLastCellNum();
        DataFormatter formatter = new DataFormatter();
        
        while (fieldRow != null) {      
            for (int j = 0; j < cellMax; j++) {
                String value = formatter.formatCellValue(fieldRow.getCell(j)).trim();
                ids.add(value);
            }
            row ++;
            fieldRow = sheet.getRow(row);
        }
        return ids;
    }

    public InputDataConfig readStoreOrderLinks(String filePath) {
        InputDataConfig inputDataConfig = new InputDataConfig();

        FileInputStream fis = null;
        Workbook workbook = null;
        try {
            fis = new FileInputStream(filePath);
            workbook = WorkbookFactory.create(fis);
            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();
                if (sh.getSheetName().toUpperCase().equals(DATA_SHEET)) {
                    sheet = sh;
                    inputDataConfig.setListStores(readStoreOrderLinks(sh));
                } else if (sh.getSheetName().toUpperCase().equals(SETTING_SHEET)) {
                    sheet = sh;
                    inputDataConfig.setParams(readSettings(sh));
                } else if (sh.getSheetName().toUpperCase().equals(IDS_SHEET)) {
                    sheet = sh;
                    inputDataConfig.setProductIds(readIds(sh));
                }
            }
            if (sheet == null) {
                return null;
            }   // Create a Sheet
            return inputDataConfig;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadStoreOrderInfoSvs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(ReadStoreOrderInfoSvs.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
                workbook.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadStoreOrderInfoSvs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }
}
