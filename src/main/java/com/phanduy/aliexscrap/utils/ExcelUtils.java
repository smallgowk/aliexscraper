/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.utils;

import com.phanduy.aliexscrap.config.Configs;
import com.phanduy.aliexscrap.model.aliex.store.AliexStoreInfo;
import com.phanduy.aliexscrap.model.aliex.store.inputdata.BaseStoreOrderInfo;
import com.phanduy.aliexscrap.model.amazon.*;
import com.phanduy.aliexscrap.model.response.TransformCrawlResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.math3.util.Pair;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */
public class ExcelUtils {

    public static final String DATA_SHEET = "DATA";
    public static final String TEMPLATE_SHEET = "TEMPLATE";

    public static void saveListProductsToExcel(AliexStoreInfo aliexStorePageInfo, String sampleFilePath) throws EncryptedDocumentException, InvalidFormatException, IOException {
//        saveListProductsToExcel(aliexStorePageInfo.getListProductAmzs(), aliexStorePageInfo.getExcelFilePath(), sampleFilePath);
    }

    public static void saveListProductsToExcel(ArrayList<ProductAmz> listProducts, String newFilePath, String sampleFilePath, AliexStoreInfo aliexStoreInfo, boolean isSaveAllData) throws EncryptedDocumentException, InvalidFormatException, IOException {
        if (listProducts == null || listProducts.isEmpty()) {
            return;
        }
        FileInputStream fis = new FileInputStream(sampleFilePath);
        try (Workbook workbook = WorkbookFactory.create(fis)) {

            /* CreationHelper helps us create instances of various things like DataFormat,
            Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            if (!sheetIterator.hasNext()) {
                return;
            }
            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();
                if (sh.getSheetName().toUpperCase().equals(TEMPLATE_SHEET)) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return;
            }   // Create a Sheet

            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

            int fieldRowIndex = 0;
            Row fieldRow = sheet.getRow(fieldRowIndex);
            boolean isFound = false;
            while (!isFound) {
                int lastCol = fieldRow.getLastCellNum();
                for (int i = 0; i <= lastCol; i++) {
                    Cell cell = fieldRow.getCell(i);
                    if (cell == null) {
                        continue;
                    }
                    String value = cell.getStringCellValue();
                    if (value == null) {
                        continue;
                    }
                    if (value.toLowerCase().equals("feed_product_type")) {
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) {
                    fieldRowIndex ++;
                    fieldRow = sheet.getRow(fieldRowIndex);
                    if (fieldRow == null) {
                        return;
                    }
                }
            }

            int cellMax = 0;

            if (fieldRow == null) {
                workbook.close();
                return;
            }

            cellMax = fieldRow.getLastCellNum();

            if (cellMax == 0) {
                workbook.close();
                return;
            }

            int rowNum = fieldRowIndex + 1;
            Row checkRow = sheet.getRow(rowNum );
            while (checkRow != null) {
                rowNum  ++;
                checkRow = sheet.getRow(rowNum );
            }

            for (ProductAmz productAmz : listProducts) {
                Row row = sheet.createRow(rowNum++);
                for (int j = 0; j < cellMax; j++) {
                    Cell cell = fieldRow.getCell(j);
                    String fieldName = cell.getStringCellValue().trim();
                    String value = DataStore.getValue(productAmz.getItem_sku(), fieldName);
                    if (!StringUtils.isEmpty(value)) {
                        row.createCell(j).setCellValue(value);
                    } else {
                        row.createCell(j).setCellValue(productAmz.getValueForCell(fieldName));
                    }
                }
            }

            fis.close();

            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(newFilePath);
                workbook.write(fileOut);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } catch (IOException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } finally {
                try {
                    if (fileOut != null) {
                        fileOut.close();
                    }
                    workbook.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
    
    public static void saveListProductsToExcelNew(ArrayList<TransformCrawlResponse> listResponses, String newFilePath, String sampleFilePath, AliexStoreInfo aliexStoreInfo, boolean isSaveAllData, String category) throws EncryptedDocumentException, InvalidFormatException, IOException {
        if (listResponses == null || listResponses.isEmpty()) {
            return;
        }

        System.out.println("NewFile: " + newFilePath);

        FileInputStream fis = new FileInputStream(sampleFilePath);
        System.out.println("Template: " + Configs.pathChar + sampleFilePath);
        try (Workbook workbook = WorkbookFactory.create(fis)) {
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            if (!sheetIterator.hasNext()) {
                return;
            }

            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();
                if (sh.getSheetName().toUpperCase().equals(TEMPLATE_SHEET)) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return;
            }   // Create a Sheet

            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
            int fieldRowIndex = 0;
            
            Row fieldRow = sheet.getRow(fieldRowIndex);
            boolean isFound = false;
            while (!isFound) {
                int lastCol = fieldRow.getLastCellNum();
                for (int i = 0; i <= lastCol; i++) {
                    Cell cell = fieldRow.getCell(i);
                    if (cell == null) {
                        continue;
                    }
                    String value = cell.getStringCellValue();
                    if (value == null) {
                        continue;
                    }
                    if (value.toLowerCase().equals("product name")) {
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) {
                    fieldRowIndex ++;
                    fieldRow = sheet.getRow(fieldRowIndex);
                    if (fieldRow == null) {
                        return;
                    }
                }
            }
            
            int cellMax = 0;

            if (fieldRow == null) {
                workbook.close();
                return;
            }

            cellMax = fieldRow.getLastCellNum();

            if (cellMax == 0) {
                workbook.close();
                return;
            }
            
            HashMap<Integer, String> hashMapField = new HashMap<>();
            for (int i = 0; i <= cellMax; i++) {
                Cell cell = fieldRow.getCell(i);
                if (cell == null) continue;
                String value = cell.getStringCellValue();
                if (value == null) continue;
                hashMapField.put(i, value.trim().toLowerCase());
            }
            
            int rowNum = fieldRowIndex + 1;
            Row checkRow = sheet.getRow(rowNum );
            while (checkRow != null) {
                rowNum  ++;
                checkRow = sheet.getRow(rowNum );
            }

            for (TransformCrawlResponse response : listResponses) {
                if (response.listProducts == null || response.listProducts.isEmpty()) continue;
                for (NewProduct newProduct : response.listProducts) {
                    createRow(sheet, response, newProduct, category, rowNum++, hashMapField, cellMax);
                }
            }

            fis.close();

            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(newFilePath);
                workbook.write(fileOut);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } catch (IOException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } finally {
                try {
                    if (fileOut != null) {
                        fileOut.close();
                    }

                    workbook.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
    
    private static void createRow(Sheet sheet, TransformCrawlResponse response, NewProduct newProduct, String category, int rowNum, HashMap<Integer, String> hashMapField, int cellMax) {
        if (newProduct == null) return;
        Row row = sheet.createRow(rowNum);
        
        for (int i = 0; i <= cellMax; i++) {
            if (!hashMapField.containsKey(i)) continue;
            String fieldName = hashMapField.get(i);
            if (fieldName == null) continue;
            switch (fieldName) {
                case "category":
                    row.createCell(i).setCellValue(category);
                    break;
                case "brand":
                    row.createCell(i).setCellValue(response.brand);
                    break;
                case "product name":
                    row.createCell(i).setCellValue(response.product_name);
                    break;
                case "product description":
                    if (response.htmlDescription != null) {
                        row.createCell(i).setCellValue(response.htmlDescription);
                    }
                    break;
                case "main product image":
                    row.createCell(i).setCellValue(response.main_image);
                    break;
                case "product image 2":
                    row.createCell(i).setCellValue(response.image_2);
                    break;
                case "product image 3":
                    row.createCell(i).setCellValue(response.image_3);
                    break;
                case "product image 4":
                    row.createCell(i).setCellValue(response.image_4);
                    break;
                case "product image 5":
                    row.createCell(i).setCellValue(response.image_5);
                    break;
                case "product image 6":
                    row.createCell(i).setCellValue(response.image_6);
                    break;
                case "product image 7":
                    row.createCell(i).setCellValue(response.image_7);
                    break;
                case "product image 8":
                    row.createCell(i).setCellValue(response.image_8);
                    break;
                case "product image 9":
                    row.createCell(i).setCellValue(response.image_9);
                    break;
                case "variation 1":
                    row.createCell(i).setCellValue(newProduct.property_value_1);
                    break;
                case "variation 2":
                    row.createCell(i).setCellValue(newProduct.property_value_2);
                    break;
                case "variant image":
                    row.createCell(i).setCellValue(newProduct.property_value_1_image);
                    break;
                case "package weight(lb)":
                    row.createCell(i).setCellValue("0.2");
                    break;
                case "package length(inch)":
                    row.createCell(i).setCellValue("10");
                    break;
                case "package width(inch)":
                    row.createCell(i).setCellValue("2");
                    break;
                case "package height(inch)":
                    row.createCell(i).setCellValue("10");
                    break;
                case "delivery options":
                    row.createCell(i).setCellValue("The delivery options for this product are the same as the delivery options for the shop. ");
                    break;
                case "retail price (local currency)":
                    row.createCell(i).setCellValue(newProduct.price);
                    break;
                case "quantity in u.s pickup warehouse":
                    row.createCell(i).setCellValue(newProduct.warehouse_quantity);
                    break;
                case "seller sku":
                    row.createCell(i).setCellValue(newProduct.seller_sku);
                    break;
                case "other dangerous goods or hazardous materials":
                    row.createCell(i).setCellValue("No");
                    break;
                case "contains batteries or cells?":
                    row.createCell(i).setCellValue("None");
                    break;
                case "product status":
                    row.createCell(i).setCellValue("Active");
                    break;
                case "promoptionprice":
                    row.createCell(i).setCellValue(newProduct.promotionPrice);
                    break;
                case "shippingprice":
                    row.createCell(i).setCellValue(newProduct.shippingPrice);
                    break;
                case "shippingmethod":
                    row.createCell(i).setCellValue(newProduct.shippingMethod);
                    break;
                case "dealtype":
                    row.createCell(i).setCellValue(response.dealType != null ? response.dealType : "");
                    break;
                case "shipfrom":
                    row.createCell(i).setCellValue(response.shipFrom != null ? response.shipFrom : "");
                    break;
            }
        }
    }

    public static void saveListProductsToExcel(ArrayList<ProductAmz> listProducts, String sampleFilePath, String sampleSheet, String newFilePath, String dataFolder) throws EncryptedDocumentException, InvalidFormatException, IOException {

//        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
//        File src = new File(SAMPLE_XLSX_FILE_PATH);
//        File dst = new File(storeId + ".xlsx");
//        FileUtils.copyFile(src, dst);
//        FileInputStream fis = new FileInputStream(storeId + ".xlsx");
        FileInputStream fis = new FileInputStream(sampleFilePath);

        /* CreationHelper helps us create instances of various things like DataFormat,
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        try (Workbook workbook = WorkbookFactory.create(fis)) {

            /* CreationHelper helps us create instances of various things like DataFormat,
            Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();
            //        Sheet btgSheet = workbook.createSheet("Employee");

            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            System.out.println("Retrieving Sheets using Iterator");
            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();
                System.out.println("=> " + sh.getSheetName());

                if (sh.getSheetName().equals(sampleSheet)) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return;
            }   // Create a Sheet

            // Create a Font for styling header cells
//        Font headerFont = workbook.createFont();
//        headerFont.setBold(true);
//        headerFont.setFontHeightInPoints((short) 14);
//        headerFont.setColor(IndexedColors.RED.getIndex());
// Create a CellStyle with the font
//        CellStyle headerCellStyle = workbook.createCellStyle();
//        headerCellStyle.setFont(headerFont);
// Create a Row
//        Row headerRow = btgSheet.createRow(0);
// Create cells
//        for (int i = 0; i < listProductColumns.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(listProductColumns[i]);
//            cell.setCellStyle(headerCellStyle);
//        }
// Create Cell Style for formatting Date
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
// Create Other rows and cells with employees data

            Row fieldRow = sheet.getRow(2);
            int cellMax = 0;

            if (fieldRow == null) {
                workbook.close();
                return;
            }

            cellMax = fieldRow.getLastCellNum();

            if (cellMax == 0) {
                workbook.close();
                return;
            }

            int rowNum = 3;
            while (sheet.getRow(rowNum) != null) {
                rowNum++;
            }

            for (ProductAmz productAmz : listProducts) {
                Row row = sheet.createRow(rowNum++);
                for (int j = 0; j < cellMax; j++) {
                    Cell cell = fieldRow.getCell(j);
                    row.createCell(j)
                            .setCellValue(productAmz.getValueForCell(cell.getStringCellValue()));

                }

//                
//
//                row.createCell(0)
//                        .setCellValue("Name " + i);
//
//                row.createCell(1)
//                        .setCellValue("Email " + i);
//
//                Cell dateOfBirthCell = row.createCell(2);
//                dateOfBirthCell.setCellValue("Birthday " + i);
//                dateOfBirthCell.setCellStyle(dateCellStyle);
//
//                row.createCell(3)
//                        .setCellValue("Salary " + i);
            }

            fis.close();

            FileOutputStream fileOut = null;
            try {
//                if (newFilePath.endsWith(".xlsx")) {
//                    newFilePath = newFilePath.replace(".xlsx", "_" + listProducts.size() + ".xlsx");
//                } else {
//                    newFilePath += "_" + listProducts.size() + ".xlsx";
//                }
//                fileOut = new FileOutputStream(newFilePath);
                fileOut = new FileOutputStream(newFilePath);
                workbook.write(fileOut);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } catch (IOException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } finally {
                try {
                    if (fileOut != null) {
                        fileOut.close();
                    }

                    workbook.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public static ArrayList<String> getBannedKeywords(String filePath) throws FileNotFoundException {
        ArrayList<String> listKeywords = null;
        File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }

        FileInputStream fis = new FileInputStream(file);
        try (Workbook workbook = WorkbookFactory.create(fis)) {

            /* CreationHelper helps us create instances of various things like DataFormat,
            Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
//            CreationHelper createHelper = workbook.getCreationHelper();
            //        Sheet btgSheet = workbook.createSheet("Employee");
            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();

                if (sh.getSheetName().equals("Data")) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return null;
            }   // Create a Sheet

            int i = 0;
            Row fieldRow = sheet.getRow(i);

            while (fieldRow != null) {
                DataFormatter formatter = new DataFormatter();
                String keyword = formatter.formatCellValue(fieldRow.getCell(0));

                if (StringUtils.isEmpty(keyword)) {
                    i++;
                    fieldRow = sheet.getRow(i);
                    continue;
                }

                if (listKeywords == null) {
                    listKeywords = new ArrayList<>();
                }

                listKeywords.add(keyword.trim());
                i++;
                fieldRow = sheet.getRow(i);
            }

            workbook.close();
        } catch (Exception ex) {

        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        return listKeywords;
    }

    public static void getBTGFromExcel(String filePath, String templateFile, HashMap<String, BTGNode> hashMapBTG, HashMap<String, String> hashMapBTGSub) throws FileNotFoundException, IOException, InvalidFormatException {

        if (hashMapBTG == null) {
            hashMapBTG = new HashMap<>();
        }

        HashMap<String, ArrayList<Refiment>> hashMapRefiment = new HashMap<>();
//        System.out.println("BTG file: " + "/BTG" + Configs.pathChar + filePath);
//        ArrayList<BTGNode> listBTGNode = null;
//        InputStream fis = ExcelUtils.class.getResourceAsStream("/BTG" + Configs.pathChar + filePath);
//        InputStream fis = ExcelUtils.class.getResourceAsStream("/" + filePath);
        FileInputStream fis = new FileInputStream(Configs.CONFIG_FOLDER_PATH + Configs.pathChar + "BTG" + Configs.pathChar + filePath);

        String parentBranch = null;

        SimpleDateFormat spd = new SimpleDateFormat("HH':'mm':'ss' - 'dd/MM/yyyy");
        String time = spd.format(new Date());


        /* CreationHelper helps us create instances of various things like DataFormat,
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        try (Workbook workbook = WorkbookFactory.create(fis)) {
            CreationHelper createHelper = workbook.getCreationHelper();

            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            if (!sheetIterator.hasNext()) {
                return;
            }

            Sheet btgSheet = sheetIterator.next();

            if (btgSheet == null) {
                return;
            }

            if (!sheetIterator.hasNext()) {
                return;
            }
            Sheet refimentSheet = sheetIterator.next();

            if (refimentSheet == null) {
                return;
            }

            DataFormatter formatter = new DataFormatter();

            int i = 1;
            Row fieldRowRefiment = refimentSheet.getRow(i);

//            List<? extends DataValidation> dataValidations = refimentSheet.getDataValidations();
//            for(DataValidation dataValidation : dataValidations) {
//                System.out.println("" + dataValidation.getValidationConstraint().getExplicitListValues());
//            }
            while (fieldRowRefiment != null) {

                String nodeId = formatter.formatCellValue(fieldRowRefiment.getCell(0));
                String name = formatter.formatCellValue(fieldRowRefiment.getCell(2));
                String atttribute = formatter.formatCellValue(fieldRowRefiment.getCell(3));

                if (hashMapRefiment.containsKey(nodeId)) {
                    ArrayList<Refiment> listRefiments = hashMapRefiment.get(nodeId);
                    if (listRefiments == null) {
                        listRefiments = new ArrayList<>();
                    }
                    listRefiments.add(new Refiment(name, atttribute, nodeId));
                } else {
                    ArrayList<Refiment> listRefiments = new ArrayList<>();
                    listRefiments.add(new Refiment(name, atttribute, nodeId));
                    hashMapRefiment.put(nodeId, listRefiments);
                }
                i++;
                fieldRowRefiment = refimentSheet.getRow(i);
            }

            i = 1;
            Row fieldRow = btgSheet.getRow(i);

//            String keywordFiels = "item_type_keyword";
//            String departMentField = "department_name";
            while (fieldRow != null) {

                String nodeId = formatter.formatCellValue(fieldRow.getCell(0));
                String nodePath = formatter.formatCellValue(fieldRow.getCell(1));
                String[] paths = nodePath.split(Pattern.quote("/"));
                String lastNode = paths[paths.length - 1];
                String nodeName = nodePath.trim();

                hashMapBTGSub.put(lastNode.toUpperCase(), nodeName.toUpperCase());

                if (i == 1) {
                    parentBranch = nodeName;
                }

                String keyInfo = formatter.formatCellValue(fieldRow.getCell(2));

                String keyword = null;
                String departMentName = null;
                String audienceKeywords = null;

                if (keyInfo != null && !keyInfo.isEmpty()) {
                    String[] infoParts = keyInfo.split("AND");

                    for (String s : infoParts) {
                        if (s.contains(BTGNode.ITEM_TYPE_KEY_FIELD_NAME)) {
                            keyword = s.trim();
                        } else if (s.contains(BTGNode.DEPART_FIELD_NAME)) {
                            departMentName = s.trim();
                        } else if (s.contains(BTGNode.AUDIENCE_KEY_FIELD_NAME)) {
                            audienceKeywords = s.trim();
                        }
                    }

//                    if (infoParts.length > 1) {
//                        keyword = infoParts[1].trim();
//                        departMentName = infoParts[0].trim();
//                    } else {
//                        keyword = infoParts[0].trim();
//                    }
                }

//                if (listBTGNode == null) {
//                    listBTGNode = new ArrayList<>();
//                }
//
//                listBTGNode.add(new BTGNode(nodePath, nodeName, nodeId, keyword, departMentName, parentBranch, filePath));
                BTGNode bTGNode = new BTGNode(nodePath, nodeName, nodeId, keyword, audienceKeywords, departMentName, parentBranch, filePath, templateFile);
                bTGNode.setListRefiment(hashMapRefiment.get(nodeId));
                hashMapBTG.put(nodeName.trim().toUpperCase(), bTGNode);
                i++;
                fieldRow = btgSheet.getRow(i);
            }

            fis.close();
            workbook.close();

        }

//        return listBTGNode;
    }

    public static int countTotalProductSku(String filePath) {
        if (!filePath.endsWith(".xlsx")) {
            return 0;
        }

        FileInputStream fis;
        try {
            fis = new FileInputStream(filePath);
//            System.out.println("" + filePath);
            return countTotalProductSku(fis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static int countTotalProductSku(InputStream fis) throws FileNotFoundException, IOException, InvalidFormatException {
        int total = 0;

        HashMap<String, String> skuHash = new HashMap<>();

        /* CreationHelper helps us create instances of various things like DataFormat,
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        Workbook workbook = null;
        try {
//            workbook = WorkbookFactory.create(fis);

            workbook = new XSSFWorkbook(fis);
        } catch (Exception ex) {
//            OlePackage pkg = OLEPackage.open(fis);
            workbook = new HSSFWorkbook(fis);
        }

//        workbook.
//        Workbook workbook = new XSSFWorkbook();
//        if (fis.toString().endsWith(".xls")  {
//            workbook = new HSSFWorkbook();
//        } else {
//            workbook = new XSSFWorkbook();
//        }
//        XSSFWorkbook wb = new XSSFWorkbook(pkg);
//        try (workbook = WorkbookFactory.create(fis)) {
        try {

            /* CreationHelper helps us create instances of various things like DataFormat,
            Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();
            //        Sheet btgSheet = workbook.createSheet("Employee");

            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();

                if (sh.getSheetName().equals("Template")) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return -1;
            }   // Create a Sheet

            Row fieldnameRow = sheet.getRow(2);
            int cellMax = fieldnameRow.getLastCellNum();

            int i = 3;
            Row fieldRow = sheet.getRow(i);

            while (fieldRow != null) {
                ProductAmz productAmz = new ProductAmz();

                DataFormatter formatter = new DataFormatter();
                for (int j = 0; j < cellMax; j++) {
                    String fieldName = formatter.formatCellValue(fieldnameRow.getCell(j));

                    if (fieldName.equals("parent_child")) {
                        String value = formatter.formatCellValue(fieldRow.getCell(j));
                        if (value == null || value.isEmpty() || value.toLowerCase().equals("parent")) {
                            total++;
                        }
                        break;
                    }

                }

                i++;
                fieldRow = sheet.getRow(i);
            }

            fis.close();
            workbook.close();
        } catch (Exception ex) {

        }

        return total;
    }

    private static String zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return null;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return fileToZip.getPath();
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();

        return fileToZip.getPath();
    }
    
    public static HashMap<String, ArrayList<String>> initValidValues(String productType, String sampleFile) {
        HashMap<String, ArrayList<String>> hashMapValidValues = new HashMap<>();
        System.out.println("/ValidateValue" + Configs.pathChar + sampleFile);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(Configs.CONFIG_FOLDER_PATH + Configs.pathChar + "ValidateValue" + Configs.pathChar + sampleFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(fis == null) {
            return null;
        }
//        InputStream fis = ExcelUtils.class.getResourceAsStream("/ValidateValue" + Configs.pathChar + sampleFile);
//        InputStream fis = ExcelUtils.class.getResourceAsStream("/ValidateValue" + Configs.pathChar + sampleFile);
        try (Workbook workbook = WorkbookFactory.create(fis)) {

            /* CreationHelper helps us create instances of various things like DataFormat,
            Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();
            //        Sheet btgSheet = workbook.createSheet("Employee");

            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            if (!sheetIterator.hasNext()) {
                return null;
            }

//            sheet = sheetIterator.next();
            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();
//                System.out.println("=> " + sh.getSheetName());

                if (sh.getSheetName().equals("Valid Values")) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return null;
            }   // Create a Sheet

            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

// Create Other rows and cells with employees data
            Row fieldRowFirst = sheet.getRow(0);
            Row fieldRow = sheet.getRow(1);
            int cellMax = 0;

            if (fieldRow == null) {
                workbook.close();
                return null;
            }

            cellMax = fieldRow.getLastCellNum();

            if (cellMax == 0) {
                workbook.close();
                return null;
            }

            DataFormatter formatter = new DataFormatter();

            int rowNum = 2;
            Row row = sheet.getRow(rowNum);

            while (row != null) {
//                System.out.println("Row " + rowNum);
                boolean hasRowValue = false;
                for (int i = 0; i < cellMax; i++) {
                    String key = formatter.formatCellValue(fieldRow.getCell(i));
                    String value = formatter.formatCellValue(row.getCell(i));

                    if (value == null || value.isEmpty()) {
                        continue;
                    }

                    hasRowValue = true;

                    String firstRowValue = formatter.formatCellValue(fieldRowFirst.getCell(i));
                    if (firstRowValue.contains("[")) {
                        if (!firstRowValue.contains(productType.toLowerCase())) {
                            continue;
                        }
                    }

                    if (hashMapValidValues.containsKey(key)) {
                        ArrayList<String> listValues = hashMapValidValues.get(key);
                        if (listValues == null) {
                            listValues = new ArrayList<>();
                        }

                        listValues.add(value.trim());
                    } else {
                        ArrayList<String> listValues = new ArrayList<>();
                        listValues.add(value.trim());
                        hashMapValidValues.put(key, listValues);

                    }
                }

                if (!hasRowValue) {
                    break;
                }

                rowNum++;
                row = sheet.getRow(rowNum);
            }

            fis.close();
            workbook.close();
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return hashMapValidValues;
        
    }

    public static void updateStoreStatus(String filePath, BaseStoreOrderInfo storePageInfo, boolean isDone, int total) throws FileNotFoundException, IOException, InvalidFormatException {

        if (storePageInfo == null) {
            return;
        }

        FileInputStream fis = new FileInputStream(filePath);

//        SimpleDateFormat spd = new SimpleDateFormat("HH':'mm':'ss' - 'dd/MM/yyyy");
//        String time = spd.format(new Date());
        /* CreationHelper helps us create instances of various things like DataFormat,
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        try (Workbook workbook = WorkbookFactory.create(fis)) {
            CreationHelper createHelper = workbook.getCreationHelper();

            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            if (!sheetIterator.hasNext()) {
                return;
            }
            Sheet sheet = null;

            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();
//                System.out.println("=> " + sh.getSheetName());

                if (sh.getSheetName().toUpperCase().equals(DATA_SHEET)) {
                    sheet = sh;
                    break;
                }
            }

            if (sheet == null) {
                return;
            }   // Create a Sheet

            Row fieldNameRow = sheet.getRow(1);
            if (fieldNameRow == null) {
                return;
            }

            int cellMax = fieldNameRow.getLastCellNum();

            int i = 2;
            Row fieldRow = sheet.getRow(i);

            DataFormatter formatter = new DataFormatter();

//            HashMap<String, Integer> hashMap = new HashMap<>();
            while (fieldRow != null) {

                String storeUrl = null;
                String accNo = null;
                String status = null;

                for (int j = 0; j < cellMax; j++) {
                    String fieldName = formatter.formatCellValue(fieldNameRow.getCell(j));
                    switch (fieldName) {
                        case "link":
                            storeUrl = formatter.formatCellValue(fieldRow.getCell(j));
                            break;
                        case "acc_no":
                            accNo = formatter.formatCellValue(fieldRow.getCell(j));
                            break;
                        case "status":
                            status = formatter.formatCellValue(fieldRow.getCell(j));
                            break;
                    }
                }

                if (storeUrl == null || storeUrl.trim().isEmpty() || !storeUrl.startsWith("http")) {
                    i++;
                    fieldRow = sheet.getRow(i);
                    continue;
                }

//                URI uri = URI.create(storeUrl);
//                String domain = uri.getHost();
//                String path = uri.getPath();
//                URI storeUri = URI.create(storePageInfo.getOrginalStoreLink());
                if (storeUrl.equals(storePageInfo.getLink())) {
                    for (int j = 0; j < cellMax; j++) {
                        String fieldName = formatter.formatCellValue(fieldNameRow.getCell(j));
                        if (fieldName.equals("status")) {
                            Cell cellValue = fieldRow.getCell(j);
                            if (cellValue == null) {
                                cellValue = fieldRow.createCell(j);
                                cellValue.setCellValue(isDone ? "Done" : "");
                            } else {
                                cellValue.setCellValue(isDone ? "Done" : "");
                            }
                        } else if (fieldName.contains("total")) {
                            Cell cellValueTotal = fieldRow.getCell(j);
                            if (cellValueTotal == null) {
                                cellValueTotal = fieldRow.createCell(j);
                                cellValueTotal.setCellValue(isDone ? "" + total : "");
                            } else {
                                cellValueTotal.setCellValue(isDone ? "" + total : "");
                            }
                        }
                    }

//                    Cell cellValue = fieldRow.getCell(7);
//                    if (cellValue == null) {
//                        cellValue = fieldRow.createCell(7);
//                        cellValue.setCellValue(isDone ? "Done" : "");
//                    } else {
//                        cellValue.setCellValue(isDone ? "Done" : "");
//                    }
//
//                    Cell cellValueTotal = fieldRow.getCell(8);
//                    if (cellValueTotal == null) {
//                        cellValueTotal = fieldRow.createCell(8);
//                        cellValueTotal.setCellValue(isDone ? "" + total : "");
//                    } else {
//                        cellValueTotal.setCellValue(isDone ? "" + total : "");
//                    }
                    break;
                }
                i++;
                fieldRow = sheet.getRow(i);
            }

            fis.close();

            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(filePath);
                workbook.write(fileOut);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } catch (IOException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } finally {
                try {
                    if (fileOut != null) {
                        fileOut.close();
                    }

                    workbook.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }

    public static void readCustomValue() throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(Configs.excelCustomValueFilePath);

        /* CreationHelper helps us create instances of various things like DataFormat,
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        try (Workbook workbook = WorkbookFactory.create(fis)) {

            /* CreationHelper helps us create instances of various things like DataFormat,
            Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();
            //        Sheet btgSheet = workbook.createSheet("Employee");

            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();

                if (sh.getSheetName().equals("data")) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return;
            }   // Create a Sheet

            int i = 1;
            Row fieldRow = sheet.getRow(i);

            while (fieldRow != null) {
                DataFormatter formatter = new DataFormatter();
                
                String fieldName = formatter.formatCellValue(fieldRow.getCell(0));
                String value = formatter.formatCellValue(fieldRow.getCell(1));
                
                if (!StringUtils.isEmpty(fieldName) && !StringUtils.isEmpty(value)) {
                    Configs.hashCustomValue.put(fieldName.trim(), value.trim());
                }
                i++;
                fieldRow = sheet.getRow(i);
            }

            fis.close();
            workbook.close();
        }
    }

    public static void saveErrorProducts(String filePath, ArrayList<Pair<String, String>> listErrorProducts) throws FileNotFoundException, IOException {

        if (listErrorProducts == null || listErrorProducts.isEmpty()) {
            return;
        }

        System.out.println("Save error file: " + filePath + " total: " + listErrorProducts.size());
//        FileInputStream fis = null;
        FileOutputStream fileOut = null;

        Workbook workbook = null;


        /* CreationHelper helps us create instances of various things like DataFormat,
         Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        try {
            if (filePath.endsWith("xlsx")) {
                workbook = new XSSFWorkbook();
            } else {
                workbook = new HSSFWorkbook();
            }

            /* CreationHelper helps us create instances of various things like DataFormat,
             Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Data");

            CellStyle style = workbook.createCellStyle();
            style.setFillBackgroundColor(IndexedColors.RED.getIndex());

            Row fieldnameRow = sheet.createRow(0);
            Cell cellProductIdTitle = fieldnameRow.createCell(0);
            cellProductIdTitle.setCellValue("ProductId");
            Cell cellReasonTitle = fieldnameRow.createCell(1);
            cellReasonTitle.setCellValue("Reason");

            int row = 1;
//            Row fieldRow = sheet.getRow(i);

//            int size = listData.size();
//            int count = 0;
            DataFormatter formatter = new DataFormatter();

            for (Pair<String, String> res : listErrorProducts) {
                Row fieldRow = sheet.createRow(row);
                Cell cellProductId = fieldRow.createCell(0);
                Cell cellReason = fieldRow.createCell(1);
                if (res.getFirst() != null) {
                    cellProductId.setCellValue(res.getFirst());
                }
                cellReason.setCellValue(res.getSecond());
                row++;
            }
//            fis.close();

            fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);

        } catch (IOException | EncryptedDocumentException ex) {
        } finally {
            try {

                if (fileOut != null) {
                    fileOut.close();
                }

                if (workbook != null) {
                    workbook.close();
                }

            } catch (IOException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static boolean isOldTemplate(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
            if (sheet != null) {
                Row row = sheet.getRow(0); // Dòng đầu tiên (row 0)
                if (row != null) {
                    Cell cell = row.getCell(0); // Cột A (cell 0)
                    if (cell != null && cell.getCellType() == CellType.STRING) {
                        String value = cell.getStringCellValue();
                        if (value != null && (value.contains("TemplateType") || value.contains("feed_product_type"))) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Có thể log lỗi nếu cần
        }
        return false;
    }
}
