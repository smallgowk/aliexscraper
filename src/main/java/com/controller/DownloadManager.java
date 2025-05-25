/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.models.amazon.NewProduct;
import com.models.amazon.ProductAmz;
import com.interfaces.DownloadListener;
import com.models.response.TransformCrawlResponse;
import com.utils.StringUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
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
    
    public int totalDownloadCount = 0;
    public int totalDownloadComplete = 0;
    
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
        totalDownloadComplete++;
    }
    
    public void updateDownloadKey(String key) {
        setKey.add(key);
        totalDownloadCount++;
    }
    
    public int getTotalDownload() {
        return totalDownloadCount;
    }
    
    public int getTotalComplete() {
        return totalDownloadComplete;
    }
    
    public void clearData() {
        mapUrl.clear();
        setKey.clear();
        setKeyDone.clear();
        totalDownloadCount = 0;
        totalDownloadComplete = 0;
    }
    
    public void downloadImage(String field, String key, String target) {
        if (StringUtils.isEmpty(key) || !mapUrl.containsKey(key) || StringUtils.isEmpty(target)) {
            return;
        }
        if (!setKey.contains(key)) {
            execute(new DownloadMachine(key, get(key), target, downloadListener));
            updateDownloadKey(key);
        } else {
            totalDownloadCount++;
            totalDownloadComplete++;
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
        InputStream in = null;
        HttpURLConnection connection = null;
        ReadableByteChannel rbc = null;
        FileChannel fileChannel = null;

        try {
            BufferedImage image = downloadImage(imageUrl);
            if (image == null) {
                System.out.println("Failed to download the image file.");
                throw new IOException("Failed to download the image file.");
            }
            
            ImageWriter writer = null;
            Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName("jpg");
            if (iterator.hasNext()) {
                writer = iterator.next();
            }
            if (writer != null) {
                // Cấu hình tham số ghi ảnh JPEG
                ImageWriteParam params = writer.getDefaultWriteParam();
                params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                params.setCompressionQuality(1.0f); // Chất lượng nén: từ 0.0 (tệ nhất) đến 1.0 (tốt nhất)

                // Ghi ảnh dưới dạng JPEG
                File outputFile = new File(targetFilePath);
                FileImageOutputStream outputStream = new FileImageOutputStream(outputFile);
                writer.setOutput(outputStream);
                writer.write(null, new IIOImage(image, null, null), params);
                writer.dispose();
                outputStream.close();
            } else {
                System.out.println("Không tìm thấy ImageWriter cho định dạng JPEG.");
            }
            
            if (key != null) {
                DownloadManager.getInstance().updateCompleteKey(key);
                if (downloadListener != null) {
                    downloadListener.onComplete(key);
                }
            }
        } catch (MalformedURLException ex) {
            System.out.println("MalformedURLException: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                }
            }
            
            try {
                if (fileChannel != null) {
                    fileChannel.close();
                }
                if (rbc != null) {
                    rbc.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    
    private static BufferedImage downloadImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Thêm tiêu đề User-Agent để giả lập trình duyệt
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Kiểm tra mã phản hồi HTTP
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to download image, HTTP response code: " + responseCode);
        }

        // Đọc tệp ảnh từ InputStream
        try (InputStream inputStream = connection.getInputStream()) {
            return ImageIO.read(inputStream);
        }
    }
}
