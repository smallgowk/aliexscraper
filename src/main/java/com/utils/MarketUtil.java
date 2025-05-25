/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.net.URI;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

/**
 *
 * @author Admin
 */
public class MarketUtil {

    public static String removeImgSrc(String html, HashMap<String, Boolean> hashMapTradeMark) {

        Whitelist myCustomWhitelist = new Whitelist();
//        myCustomWhitelist.addTags("b", "br", "p", "ul", "div", "li", "strong", "table", "border", "tbody", "tr", "td");
        myCustomWhitelist.addTags("b", "br", "p", "ul", "div", "li", "strong", "border", "tbody", "span");

        String clean = Jsoup.clean(html, myCustomWhitelist);

        clean = clean.replaceAll("\\<\\/div\\>", "\\<\\/p\\>");
        clean = clean.replaceAll("\\<div\\>", "\\<p\\>");
        clean = clean.replaceAll("\\<\\/span", "\\<\\/p");
        clean = clean.replaceAll("\\<span\\>", "\\<p\\>");

        Document doc = Jsoup.parse(clean);
//        Elements tables = doc.select("table");
//        if (tables != null && !tables.isEmpty()) {
//            for (Element element : tables) {
//                element.attr("border", "1");
//            }
//
//            if (doc.body().html().length() <= 2000) {
//                return doc.body().html();
//            } else {
//                for (Element element : tables) {
//                    element.remove();
//                }
//            }
//        }

        Elements allP = doc.select("p");
        StringBuilder sb = new StringBuilder();

        if (allP != null) {
            String prevText = null;
            for (Element element : allP) {

                String text = element.text();

                if (text != null) {
                    text = text.trim();
                }

                if (text == null || text.isEmpty()) {
                    continue;
                }

//                if (text.length() == 1 && (int) (text.charAt(0)) == 160 && (text.equals(prevText) || prevText == null)) {
                if (text.length() == 1 && (int) (text.charAt(0)) == 160) {
                    continue;
                }

                prevText = text;

                text = StringUtils.removeTradeMark(text, hashMapTradeMark);

                if (text == null) {
                    return null;
                }

                element.text(text);

                String appendStr = element.outerHtml() + "\n";
//                appendStr = StringUtils.removeTradeMark(appendStr, hashMapTradeMark);
//                String appendStr = element.outerHtml();
//                if (text.length() != prevText.length()) {
//                    System.out.println("Changed: " + prevText + " \nto: " + text);
//                    System.out.println("Outer html: " + appendStr);
//                }

                if (sb.length() + appendStr.length() > 2000) {
                    break;
                } else {
                    sb.append(appendStr);
                }

            }

            return sb.toString();
        } else {
            return null;
        }
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
