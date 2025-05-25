package com.phanduy.aliexscrap.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.phanduy.aliexscrap.model.ProfileAccount;

import java.io.FileReader;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ScrapUtil {

//    public String crawlSellerId(AliexStoreInfo aliexStoreInfo) {
////        goToPage("https://www.aliexpress.com/store/" + aliexStoreInfo.storeSign);
////        Document document = PhantomJsManager.getInstance().renderPage("https://www.aliexpress.com/store/" + aliexStoreInfo.storeSign);
//        Document document = AliexCrawlSvs.getInstance().processWithoutCookie("https://www.aliexpress.com/store/" + aliexStoreInfo.storeSign);
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(AliexCrawlSvs.class.getName()).log(Level.SEVERE, null, ex);
//        }
////        String pageSource = AliexCrawlSvs.getInstance().getPageSource();
//        String pageSource = document.html();
//        int index = pageSource.indexOf("sellerId");
//        String r1 = pageSource.substring(index, index + "sellerId".length() + 1 + 15);
//        return r1.replaceAll("[^0-9]", "");
//    }

    public static String readProfileName(String dir, String profileFolderName) {
        String profilePath = dir + profileFolderName + "/Preferences";
        try (FileReader reader = new FileReader(Paths.get(profilePath).toFile())) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray jsonArray = json.getAsJsonArray("account_info");
            if (jsonArray.isEmpty()) {
                return null;
            }
            JsonObject accountJson = jsonArray.get(0).getAsJsonObject();
            String fullName = accountJson.get("full_name").getAsString();
            String email = accountJson.get("email").getAsString();
            return fullName + " - " + email;
        } catch (Exception e) {
            System.err.println("Lỗi đọc file hoặc không tìm thấy tên profile: " + e.getMessage());
        }
        return profileFolderName;
    }

    public static String processImgUrl(String url) {

        URI uri = URI.create(url);

        String domain = uri.getHost();
        String path = uri.getPath();
        String scheme = uri.getScheme();

        String[] pathDotParts = path.split(Pattern.quote("."));

        if (pathDotParts == null || pathDotParts.length <= 2) {
            return url;
        }

        StringBuilder sb = new StringBuilder(scheme);
        sb.append("://").append(domain).append(pathDotParts[0]).append(".");

        boolean isJpegType = false;

        for (int i = 1, length = pathDotParts.length; i < length; i++) {

            if (pathDotParts[i].startsWith("jpeg")) {
                isJpegType = true;
                break;
            }

            if (pathDotParts[i].startsWith("jpg")) {
                isJpegType = false;
                break;
            }

            sb.append(pathDotParts[i]).append(".");

//            if(pathDotParts[i].contains("jpeg")) {
//                isJpegType = true;
//                break;
//            }
        }
        sb.append(isJpegType ? "jpeg" : "jpg");

        return sb.toString();
    }

    public static String formatPrice(float price) {
        return String.format("%.2f", price);
    }

    public static String getCEOPrice(float price) {
        return formatPrice(price);
    }
}
