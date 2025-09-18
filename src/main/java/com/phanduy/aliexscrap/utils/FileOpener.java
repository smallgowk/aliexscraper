package com.phanduy.aliexscrap.utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FileOpener {
    public static void openFileOrFolder(String path) {
        try {
            if (path == null || path.trim().isEmpty()) {
                AlertUtil.showError("", "Đường dẫn không hợp lệ!");
                return;
            }
            File file = new File(path);
            if (!file.exists()) {
                AlertUtil.showError("", "File hoặc thư mục không tồn tại!");
                return;
            }
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            AlertUtil.showError("Lỗi", "Không thể mở: " + e.getMessage());
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
