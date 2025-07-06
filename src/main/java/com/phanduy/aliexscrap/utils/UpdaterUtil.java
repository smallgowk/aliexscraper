package com.phanduy.aliexscrap.utils;

import java.io.*;
import java.net.URL;

public class UpdaterUtil {
    /**
     * Tải file từ url về local
     */
    public static boolean downloadFile(String fileURL, String savePath) {
        InputStream in = null;
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL(fileURL);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(30000);
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.err.println("HTTP error: " + responseCode);
                return false;
            }
            in = new BufferedInputStream(conn.getInputStream());
            fileOutputStream = new FileOutputStream(savePath);
            byte dataBuffer[] = new byte[8192];
            int bytesRead;
            long totalBytes = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 8192)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                totalBytes += bytesRead;
            }
            fileOutputStream.flush();
            System.out.println("Downloaded file size: " + totalBytes + " bytes");
            if (totalBytes == 0) {
                System.err.println("Downloaded file is empty!");
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {}
            try {
                if (fileOutputStream != null) fileOutputStream.close();
            } catch (IOException e) {}
        }
    }

    /**
     * Tạo file batch update
     * @param appExe tên file exe hiện tại
     * @param newExe tên file exe mới tải về
     * @param batchPath đường dẫn file batch
     */
    public static void createUpdateBatch(String appExe, String newExe, String batchPath) throws IOException {
        String content =
                "@echo off\n" +
                "echo Updating...\n" +
                "timeout /t 2 >nul\n" +
                "del \"" + appExe + "\"\n" +
                "move /y \"" + newExe + "\" \"" + appExe + "\"\n" +
                "start \"\" \"" + appExe + "\"\n" +
                "exit\n";
        try (FileWriter fw = new FileWriter(batchPath)) {
            fw.write(content);
        }
    }

    /**
     * Gọi file batch update và thoát app
     */
    public static void runBatchAndExit(String batchPath) {
        try {
            Runtime.getRuntime().exec("cmd /c start " + batchPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
} 