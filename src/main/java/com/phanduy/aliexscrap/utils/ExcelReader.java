package com.phanduy.aliexscrap.utils;

import com.phanduy.aliexscrap.model.SettingInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class ExcelReader {
    public static SettingInfo readExcelFile(String filePath) throws IOException {
        SettingInfo settingInfo = new SettingInfo();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Đọc sheet DATA
            Sheet dataSheet = workbook.getSheet("DATA");
            if (dataSheet != null) {
                readSheetData(dataSheet, settingInfo.getData(), 0, 1);
            }

            // Đọc sheet Settings
            Sheet settingsSheet = workbook.getSheet("Settings");
            if (settingsSheet != null) {
                readSheetData(settingsSheet, settingInfo.getSettings(), 0, 1);
            }
        }
        return settingInfo;
    }

    private static void readSheetData(Sheet sheet, Map<String, String> map, int keyRowIdx, int valueRowIdx) {
        Row keyRow = sheet.getRow(keyRowIdx);
        Row valueRow = sheet.getRow(valueRowIdx);
        if (keyRow == null || valueRow == null) return;

        int maxCell = keyRow.getLastCellNum();
        for (int i = 0; i < maxCell; i++) {
            Cell keyCell = keyRow.getCell(i);
            Cell valueCell = valueRow.getCell(i);

            if (keyCell == null || valueCell == null) continue;

            if (keyCell.getCellType() == CellType.STRING) {
                String key = keyCell.getStringCellValue().trim();
                String value = getCellValue(valueCell);
                map.put(key, value);
            }
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}