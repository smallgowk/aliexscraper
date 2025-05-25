package com.phanduy.aliexscrap.utils;

import com.phanduy.aliexscrap.model.NewProductAmz;
import com.phanduy.aliexscrap.model.SettingInfo;
import com.phanduy.aliexscrap.model.response.NewTransformCrawlResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExcelWriter {

    public static void writeDataToTemplate(File templateFile, File outputFile, ArrayList<NewTransformCrawlResponse> dataList, SettingInfo settingInfo) throws Exception {
        try (FileInputStream fis = new FileInputStream(templateFile);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row fieldRow = sheet.getRow(2); // dòng thứ 3, index = 2

            // Mapping column index -> field name
            Map<Integer, String> colToFieldMap = new HashMap<>();
            for (Cell cell : fieldRow) {
                colToFieldMap.put(cell.getColumnIndex(), cell.getStringCellValue());
            }

            int rowIndex = 3; // bắt đầu ghi từ dòng thứ 4
            for (NewTransformCrawlResponse item : dataList) {
                try {
                    ArrayList<NewProductAmz> listProducs = item.genListProducts(settingInfo);
                    if (listProducs != null) {
                        for (NewProductAmz productAmz : listProducs) {
                            Row row = sheet.createRow(rowIndex++);
                            updateRow(row, productAmz, colToFieldMap);
                        }

                    }
                } catch (Exception ex) {
                    System.out.println("" + item.productId + " lỗi: " + ex.getMessage());
                }


            }

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                workbook.write(fos);
            }
        }
    }

    private static void updateRow(Row row, NewProductAmz productAmz, Map<Integer, String> colToFieldMap) {
        for (Map.Entry<Integer, String> entry : colToFieldMap.entrySet()) {
            int colIndex = entry.getKey();
            String fieldName = entry.getValue();

            String value = productAmz.getValueForField(fieldName);

            Cell cell = row.createCell(colIndex);
            cell.setCellValue(value);
        }
    }
}
