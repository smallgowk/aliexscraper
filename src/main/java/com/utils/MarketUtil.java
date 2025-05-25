/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.net.URI;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class MarketUtil {
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

    public static String getVariationImageUrl(String url) {
        URI uri = URI.create(url);

        String domain = uri.getHost();
        String path = uri.getPath();
        String scheme = uri.getScheme();

        StringBuilder sb = new StringBuilder(scheme);
        sb.append("://").append(domain).append("/");

        String[] pathParts = path.split(Pattern.quote("/"));

        sb.append(pathParts[1]).append("/");

        String[] pathDotParts = null;
        if (pathParts.length == 3) {
            pathDotParts = pathParts[2].split(Pattern.quote("."));

            for (String s : pathDotParts) {
                if (s.startsWith("jpeg")) {
                    sb.append("jpeg");
                    return sb.toString();
                }

                if (s.startsWith("jpg")) {
                    sb.append("jpg");
                    return sb.toString();
                }

                sb.append(s).append(".");
            }

        } else {
            sb.append(pathParts[2]);
            pathDotParts = pathParts[pathParts.length - 1].split(Pattern.quote("."));

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

//                sb.append(pathDotParts[i]).append(".");

//            if(pathDotParts[i].contains("jpeg")) {
//                isJpegType = true;
//                break;
//            }
            }
            sb.append(isJpegType ? ".jpeg" : ".jpg");
        }

        return sb.toString();
    }

//    public static String getPrefrix(String storeName) {
//        return StringUtils.getStringBrief(storeName);
//    }
}
