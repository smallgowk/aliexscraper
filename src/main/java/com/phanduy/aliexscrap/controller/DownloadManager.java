/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.controller;

import com.models.amazon.NewProduct;
import com.models.amazon.ProductAmz;
import com.phanduy.aliexscrap.interfaces.DownloadListener;
import com.phanduy.aliexscrap.model.response.TransformCrawlResponse;
import com.phanduy.aliexscrap.utils.StringUtils;
import org.apache.commons.io.FileUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

/**
 *
 * @author PhanDuy
 */
public class DownloadManager {
    ExecutorService executor = Executors.newFixedThreadPool(10);
    private static DownloadManager serviceManager;
    HashMap<String, String> mapUrl = new HashMap<>();
    HashMap<String, String> mapKeyFileName = new HashMap<>();
    HashSet<String> setKey = new HashSet<>();
    HashSet<String> setKeyDone = new HashSet<>();
    
//    public int totalDownloadCount = 0;
//    public int totalDownloadComplete = 0;
    
    public DownloadListener downloadListener;
    
    public static DownloadManager getInstance() {
        if (serviceManager == null) {
            serviceManager = new DownloadManager();
        }
        return serviceManager;
    }
    
    public void setListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }
    
    public void put(String key, String url) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(url)) return;
        mapUrl.put(key, url);
    }
   
    public String get(String key) {
        return mapUrl.get(key);
    }
    
    public void putMapFileName(String key, String fileName) {
        mapKeyFileName.put(key, fileName);
    }
    
    public String getFileName(String key) {
        return mapKeyFileName.get(key);
    }
    
    public void execute(Thread thread) {
        executor.execute(thread);
    }
    
    public void updateCompleteKey(String key) {
        setKeyDone.add(key);
    }
    
    public void updateDownloadKey(String key) {
        setKey.add(key);
    }
    
    public int getTotalDownload() {
        return setKey.size();
    }
    
    public int getTotalComplete() {
        return setKeyDone.size();
    }
    
    public void clearData() {
        mapUrl.clear();
        setKey.clear();
        setKeyDone.clear();
//        totalDownloadCount = 0;
//        totalDownloadComplete = 0;
    }
    
    public void downloadImage(String field, String key, String target) {
        if (StringUtils.isEmpty(key) || !mapUrl.containsKey(key) || StringUtils.isEmpty(target)) {
            return;
        }
        if (!setKey.contains(key)) {
            execute(new DownloadMachine(key, get(key), target, downloadListener));
            updateDownloadKey(key);
        } else {
//            totalDownloadCount++;
//            totalDownloadComplete++;
            if (downloadListener != null) {
                downloadListener.onComplete(key);
            }
        }
        
    }
    
    public void testDownload(String imageUrl, String target) {
        execute(new DownloadMachine(imageUrl, target));
    }
    
    public void downloadImageAndUpdate(ProductAmz productAmz, String targetFolder) {
        downloadImage("main_image_key", productAmz.main_image_key, targetFolder + productAmz.main_image_vps_name);
//        downloadImage("swatch_image_key", productAmz.swatch_image_key, targetFolder + productAmz.swatch_image_vps_name);
        downloadImage("other1_image_key", productAmz.other1_image_key, targetFolder + productAmz.other1_image_vps_name);
        downloadImage("other2_image_key", productAmz.other2_image_key, targetFolder + productAmz.other2_image_vps_name);
        downloadImage("other3_image_key", productAmz.other3_image_key, targetFolder + productAmz.other3_image_vps_name);
        downloadImage("other4_image_key", productAmz.other4_image_key, targetFolder + productAmz.other4_image_vps_name);
        downloadImage("other5_image_key", productAmz.other5_image_key, targetFolder + productAmz.other5_image_vps_name);
        downloadImage("other6_image_key", productAmz.other6_image_key, targetFolder + productAmz.other6_image_vps_name);
        downloadImage("other7_image_key", productAmz.other7_image_key, targetFolder + productAmz.other7_image_vps_name);
        downloadImage("other8_image_key", productAmz.other8_image_key, targetFolder + productAmz.other8_image_vps_name);
    }
    
    public void downloadImageNewFormat(TransformCrawlResponse response, String targetFolder) {
        downloadImageNewFormatFromField("Main", response.main_image, response.productId, targetFolder);
        downloadImageNewFormatFromField("1", response.image_2, response.productId, targetFolder);
        downloadImageNewFormatFromField("2", response.image_3, response.productId, targetFolder);
        downloadImageNewFormatFromField("3", response.image_4, response.productId, targetFolder);
        downloadImageNewFormatFromField("4", response.image_5, response.productId, targetFolder);
        downloadImageNewFormatFromField("5", response.image_6, response.productId, targetFolder);
        downloadImageNewFormatFromField("6", response.image_7, response.productId, targetFolder);
        downloadImageNewFormatFromField("7", response.image_8, response.productId, targetFolder);
        downloadImageNewFormatFromField("8", response.image_9, response.productId, targetFolder);
        if (response.listProducts != null) {
            for (NewProduct newProduct : response.listProducts) {
                if (!StringUtils.isEmpty(newProduct.getImageName())) {
                    downloadImageNewFormatFromField(newProduct.getImageName(), newProduct.property_value_1_image, response.productId, targetFolder);
                }
            }
        }
    }
    
    public void downloadImageNewFormatFromField(String field, String url, String productId, String targetFolder) {
        if (url == null || url.isEmpty()) return;
        downloadImage(field, getKeyForImage(productId, field, url), targetFolder + field + ".jpg");
    }
    
    public String getKeyForImage(String productId, String field, String url) {
        return productId + field + Math.abs(url.hashCode());
    }
    
    public void shutDown() {
        executor.shutdown();
    }
}


class DownloadMachine extends Thread{
    
    private final String imageUrl;
    private final String targetFilePath;
    private final String key;
    private final DownloadListener downloadListener;
    
    public DownloadMachine(String key, String imageUrl, String targetFilePath, DownloadListener downloadListener) {
        this.key = key;
        this.imageUrl = imageUrl;
        this.targetFilePath = targetFilePath;
        this.downloadListener = downloadListener;
    }
    
    public DownloadMachine(String imageUrl, String targetFilePath) {
        this.imageUrl = imageUrl;
        this.targetFilePath = targetFilePath;
        key = null;
        downloadListener = null;
    }

    @Override
    public void run() {
        try {
            BufferedImage image = downloadImage(imageUrl);
            if (image == null) {
                // Nếu không download được, thử save WebP file
                String webPPath = targetFilePath.replace(".jpg", ".webp");
                if (downloadWebPFile(imageUrl, webPPath)) {
                    WebPJarSolution.convertWebPToJPEG(webPPath, targetFilePath, 1.0f);
                    FileUtils.delete(new File(webPPath));
                    // Notify completion với WebP file
                    if (key != null) {
                        DownloadManager.getInstance().updateCompleteKey(key);
                        if (downloadListener != null) {
                            downloadListener.onComplete(key);
                        }
                    }
                    return;
                }
                throw new IOException("Failed to download the image file: " + imageUrl);
            }

            // Lưu ảnh thành JPEG như cũ
            saveImageAsJPEG(image, targetFilePath, 1.0f);

            if (key != null) {
                DownloadManager.getInstance().updateCompleteKey(key);
                if (downloadListener != null) {
                    downloadListener.onComplete(key);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error downloading image: " + ex.getMessage() + " " + imageUrl);
            ex.printStackTrace();
        }
    }

    private BufferedImage downloadImage(String imageUrl) {
        // Thử request JPEG trước
        BufferedImage jpegImage = requestSpecificFormat(imageUrl, "image/jpeg,image/png,image/gif,*/*;q=0.8");
        if (jpegImage != null) {
            return jpegImage;
        }

        // Nếu không được, thử WebP (cần webp-imageio library)
        return requestSpecificFormat(imageUrl, "image/webp,image/*,*/*;q=0.8");
    }

    private BufferedImage requestSpecificFormat(String imageUrl, String acceptHeader) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();

            // Headers
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            connection.setRequestProperty("Accept", acceptHeader);
            connection.setRequestProperty("Accept-Encoding", "identity");
            connection.setRequestProperty("Referer", "https://www.aliexpress.com/");

            // Timeout
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(30000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                return ImageIO.read(inputStream);
            }

        } catch (Exception e) {
            System.out.println("Request failed for " + acceptHeader + ": " + e.getMessage());
        } finally {
            if (inputStream != null) try { inputStream.close(); } catch (IOException e) {}
            if (connection != null) connection.disconnect();
        }

        return null;
    }

    private boolean downloadWebPFile(String imageUrl, String filePath) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            connection.setRequestProperty("Accept", "image/webp,image/*");
            connection.setRequestProperty("Referer", "https://www.aliexpress.com/");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                outputStream = new FileOutputStream(filePath);

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                return true;
            }

        } catch (Exception e) {
            System.out.println("WebP download failed: " + e.getMessage());
        } finally {
            if (inputStream != null) try { inputStream.close(); } catch (IOException e) {}
            if (outputStream != null) try { outputStream.close(); } catch (IOException e) {}
            if (connection != null) connection.disconnect();
        }

        return false;
    }

    private void saveImageAsJPEG(BufferedImage image, String filePath, float quality) throws IOException {
        File outputFile = new File(filePath);

        // Tạo thư mục cha nếu chưa tồn tại
        File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IOException("No JPEG writer found");
        }

        ImageWriter writer = writers.next();
        FileImageOutputStream outputStream = null;

        try {
            // Cấu hình tham số nén JPEG
            ImageWriteParam params = writer.getDefaultWriteParam();
            if (params.canWriteCompressed()) {
                params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                params.setCompressionQuality(quality); // 0.0 - 1.0
            }

            outputStream = new FileImageOutputStream(outputFile);
            writer.setOutput(outputStream);
            writer.write(null, new IIOImage(image, null, null), params);

        } finally {
            if (writer != null) {
                writer.dispose();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
