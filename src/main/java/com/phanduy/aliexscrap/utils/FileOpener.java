package com.phanduy.aliexscrap.utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FileOpener {
    public static void openFileOrFolder(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.err.println("File not found: " + path);
                return;
            }
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFileNameWithoutExtension(String path) {
        if (path.isEmpty()) return "";
        File file = new File(path);
        String fileName = file.getName(); // lấy "template_crawling.xlsx"
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(0, dotIndex); // cắt bỏ ".xlsx"
        } else {
            return fileName; // không có đuôi mở rộng
        }
    }
}
