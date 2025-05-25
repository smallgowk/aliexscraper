package com.phanduy.aliexscrap.utils;

import com.phanduy.aliexscrap.model.SettingInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExcelReader {
    public static SettingInfo readExcelFile(String filePath) throws IOException {
        SettingInfo settingInfo = new SettingInfo();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Đọc sheet DATA
            Sheet dataSheet = workbook.getSheet("DATA");
            if (dataSheet != null) {
                int[] keyValueRowIndexes = findKeyValueRowIndexesData(dataSheet);
                if (keyValueRowIndexes != null) {
                    readSheetData(dataSheet, settingInfo, keyValueRowIndexes[0], keyValueRowIndexes[1], "DATA");
                }
            }

            // Đọc sheet Settings
            Sheet settingsSheet = workbook.getSheet("Settings");
            if (settingsSheet != null) {
                int[] keyValueRowIndexes = findKeyValueRowIndexesSetting(settingsSheet);
                if (keyValueRowIndexes != null) {
                    readSheetData(settingsSheet, settingInfo, keyValueRowIndexes[0], keyValueRowIndexes[1], "Settings");
                }
            }
        }
        return settingInfo;
    }

    private static int[] findKeyValueRowIndexesData(Sheet sheet) {
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            for (Cell cell : row) {
                if (cell.getCellType() == CellType.STRING) {
                    String cellValue = cell.getStringCellValue().trim().toLowerCase();
                    if (cellValue.equals("query") || cellValue.equals("product_id") || cellValue.equals("store_id")) {
                        return new int[]{i, i + 1};  // keyRow, valueRow
                    }
                }
            }
        }
        return null; // Không tìm thấy
    }

    private static int[] findKeyValueRowIndexesSetting(Sheet sheet) {
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            for (Cell cell : row) {
                if (cell.getCellType() == CellType.STRING) {
                    String cellValue = cell.getStringCellValue().trim().toLowerCase();
                    if (cellValue.equals("price_limit") || cellValue.equals("price_rate")) {
                        return new int[]{i, i + 1};  // keyRow, valueRow
                    }
                }
            }
        }
        return null; // Không tìm thấy
    }


    private static void readSheetData(Sheet sheet, SettingInfo settingInfo, int keyRowIdx, int valueRowIdx, String sheetName) {
        Row keyRow = sheet.getRow(keyRowIdx);
        Row valueRow = sheet.getRow(valueRowIdx);
        if (keyRow == null || valueRow == null) return;
        Map<String, String> map = sheetName.equals("DATA") ? settingInfo.getData() : settingInfo.getSettings();

        int maxCell = keyRow.getLastCellNum();
        for (int i = 0; i < maxCell; i++) {
            Cell keyCell = keyRow.getCell(i);
            Cell valueCell = valueRow.getCell(i);

            if (keyCell == null || valueCell == null) continue;

            if (keyCell.getCellType() == CellType.STRING) {
                String key = keyCell.getStringCellValue().trim();
                String value = getCellValue(valueCell);
                switch (key) {
                    case SettingInfo.TEMP_TIPS:
                        settingInfo.getTemplates().put(key, value);
                        map.put(SettingInfo.TIP_LENGTH, String.valueOf(value.length()));
                        break;
                    case SettingInfo.TEMP_REASONS:
                        settingInfo.getTemplates().put(key, value);
                        map.put(SettingInfo.REASON_LENGTH, String.valueOf(value.length()));
                        break;
                    case SettingInfo.TEMP_DESCRIPTION:
                        settingInfo.getTemplates().put(key, value);
                        map.put(SettingInfo.DES_LENGTH, String.valueOf(value.length()));
                        break;
                    case SettingInfo.TEMP_BULLETS:
                        String[] parts = value.split("\n");
                        ArrayList<String> listBullets = new ArrayList<>();
                        for (String s : parts) {
                            if (!s.trim().isEmpty()) {
                                listBullets.add(s.trim());
                            }
                        }
                        settingInfo.setListBulletPoints(listBullets);
                        break;
                    default:
                        map.put(key, value);
                }
            }
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> {
                double value = cell.getNumericCellValue();
                if (value == Math.floor(value)) {
                    yield String.valueOf((long) value);
                } else {
                    yield String.valueOf(value);
                }
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }

    public static boolean isCustomTemplate(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
            if (sheet != null) {
                Row row = sheet.getRow(0); // Dòng đầu tiên (row 0)
                if (row != null) {
                    Cell cell = row.getCell(0); // Cột A (cell 0)
                    if (cell != null && cell.getCellType() == CellType.STRING) {
                        String value = cell.getStringCellValue();
                        if (value != null && value.contains("TemplateType")) {
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