package com.controller;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;

public class WebPJarSolution {

    static {
        // Register WebP ImageIO plugin khi class được load
        try {
            // This will register the WebP ImageIO plugin
            Class.forName("com.luciad.imageio.webp.WebPImageReaderSpi");
            System.out.println("✓ WebP ImageIO plugin registered successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("⚠️ WebP ImageIO plugin not found - WebP support disabled");
        }
    }

    public static BufferedImage loadWebPImage(String webpFilePath) {
        try {
            File webpFile = new File(webpFilePath);

            // ImageIO.read() sẽ tự động sử dụng WebP plugin nếu có
            BufferedImage image = ImageIO.read(webpFile);

            if (image != null) {
                System.out.println("✓ WebP loaded successfully: " + image.getWidth() + "x" + image.getHeight());
                return image;
            } else {
                System.out.println("❌ Could not decode WebP file");
            }

        } catch (Exception e) {
            System.out.println("Error loading WebP: " + e.getMessage());
        }

        return null;
    }

    public static boolean convertWebPToJPEG(String webpPath, String jpegPath, float quality) {
        BufferedImage image = loadWebPImage(webpPath);
        if (image != null) {
            return saveAsJPEG(image, jpegPath, quality);
        }
        return false;
    }

    private static boolean saveAsJPEG(BufferedImage image, String jpegPath, float quality) {
        try {
            File outputFile = new File(jpegPath);
            File parentDir = outputFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            if (!writers.hasNext()) {
                return false;
            }

            ImageWriter writer = writers.next();
            FileImageOutputStream outputStream = new FileImageOutputStream(outputFile);

            try {
                ImageWriteParam params = writer.getDefaultWriteParam();
                if (params.canWriteCompressed()) {
                    params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    params.setCompressionQuality(quality);
                }

                writer.setOutput(outputStream);
                writer.write(null, new IIOImage(image, null, null), params);

                System.out.println("✓ JPEG saved: " + jpegPath);
                return true;

            } finally {
                writer.dispose();
                outputStream.close();
            }

        } catch (Exception e) {
            System.out.println("JPEG save failed: " + e.getMessage());
            return false;
        }
    }
}

