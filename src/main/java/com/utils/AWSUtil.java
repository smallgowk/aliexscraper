package com.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static com.config.Configs.CONFIG_FOLDER_PATH;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class AWSUtil {

    public static final String AWSAccessKeyId = "AKIAIERTU7T5QAR4Y5DQ";
    public static final String AWSSecretKey = "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";

    public static final String BRAND_NAME_FILE = "GTIN_brands.txt";
    public static final String AVOID_KEYWORD_FILE = "/avoid_key_words.txt";
    private static Set<String> setGtinBrands = new HashSet<>();
    private static Set<String> setAvoidKeywords = new HashSet<>();
    public static void init() {

        if (setGtinBrands.isEmpty())
        readData(setGtinBrands, CONFIG_FOLDER_PATH + BRAND_NAME_FILE, false, false, null);
        if (setAvoidKeywords.isEmpty())
        readData(setAvoidKeywords, CONFIG_FOLDER_PATH + AVOID_KEYWORD_FILE, true, false, null);
        setAvoidKeywords.add("fits");
    }

    public static String readData(Set<String> hashSet, String fileName, boolean isToLowerCase, boolean isSendData, String dataType) {
        BufferedReader br = null;
        StringBuilder sb = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            InputStreamReader clientSecretReader = new InputStreamReader(fileInputStream);
            br = new BufferedReader(clientSecretReader);
            String st;
            while ((st = br.readLine()) != null) {
                if (st != null && !st.isEmpty()) {
                    String word = isToLowerCase ? st.trim().toLowerCase() : st.trim();
                    hashSet.add(word);

                    if (isSendData) {

                        if (sb == null) {
                            sb = new StringBuilder();
                        }

                        if (sb.length() == 0) {
                            sb.append(word);
                        } else {
                            sb.append("\n").append(word);
                        }
                    }
                }
            }

        } catch (IOException ex) {

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(AWSUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        return sb != null ? sb.toString() : "";
    }

    public static String readData(String fileName) {
        BufferedReader br = null;
        StringBuilder sb = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            InputStreamReader clientSecretReader = new InputStreamReader(fileInputStream);
            br = new BufferedReader(clientSecretReader);

            String st;
            while ((st = br.readLine()) != null) {
                if (st != null && !st.isEmpty()) {
                    String word = st.trim().toLowerCase();

                    if (sb == null) {
                        sb = new StringBuilder();
                    }

                    if (sb.length() == 0) {
                        sb.append(word);
                    } else {
                        sb.append(" ").append(word);
                    }
                }
            }
        } catch (IOException ex) {

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(AWSUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        return sb != null ? sb.toString() : "";
    }

    private static void readData(ArrayList<String> listKeys, String fileName, boolean isSendData, String dataType, boolean isToLowerCase) {
        BufferedReader br = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            InputStreamReader clientSecretReader = new InputStreamReader(fileInputStream);
            br = new BufferedReader(clientSecretReader);

            String st;

            StringBuilder sb = null;

            while ((st = br.readLine()) != null) {
                if (st != null && !st.isEmpty()) {
                    String word = st.trim();
                    
                    if (isToLowerCase) {
                        word = word.toLowerCase();
                    }

                    listKeys.add(word);

                    if (isSendData) {

                        if (sb == null) {
                            sb = new StringBuilder();
                        }

                        if (sb.length() == 0) {
                            sb.append(word);
                        } else {
                            sb.append("\n").append(word);
                        }
                    }

                }
            }

        } catch (IOException ex) {

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(AWSUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static boolean checkContainBrand(String brandName) {
        return setGtinBrands.contains(brandName);
    }

    public static String createSignatureCleanData(String method, String domain, String context, String params) {
        return method + "\n"
                + domain + "\n"
                + context + "\n"
                + params;
    }

    public static String createSignature(String method, String domain, String context, String params) {
        String str = AWSUtil.createSignatureCleanData("GET", "webservices.amazon.com", "/onca/xml", "AWSAccessKeyId=AKIAIERTU7T5QAR4Y5DQ&AssociateTag=mytag-20&ItemId=0679722769&Operation=ItemLookup&ResponseGroup=Images%2CItemAttributes%2COffers%2CReviews&Service=AWSECommerceService&Timestamp=2019-01-24T12%3A00%3A00Z&Version=2019-01-24");
        return EncryptUtil.encryptHmacSHA256(str, AWSSecretKey);
    }

    public static boolean isAvoidKeyword(String input) {
        return setAvoidKeywords.contains(input);
    }
    
//    public static boolean containTrademarkKeyword(String input) {
//        return checkContainKeyword(listTradeMarks, input) != null;
//    }
//    
//    public static boolean containMyBrandKeyword(String input) {
//        return checkContainKeyword(listMyBrands, input) != null;
//    }
//
//    public static String containBannedKeyword(String input) {
//        return checkContainKeyword(listBannedKeyword, input);
//    }
    
//    public static boolean containSpecialKeyword(String input) {
//        
//        if(input.equals("korean")) {
//            System.out.println("OK");
//        }
//        
//        return containBannedKeyword(input) != null || containTrademarkKeyword(input) || containMyBrandKeyword(input) || isAvoidKeyword(input);
//    }

    public static String checkContainKeyword(ArrayList<String> listChecks, String input) {
        if (listChecks == null || input == null) {
            return null;
        }
        String inputLower = input.toLowerCase();
        for (String s : listChecks) {
            if (checkContainsKeywordsNoRegex(inputLower, s, 0) >= 0) {
                return s;
            }
        }

        return null;
    }

//    public static String processTrademarkAndBrandname(String input) {
//        input = removeTrademark(input);
//        input = addForBrand(input);
//        input = removeHiddenKeys(input);
//        return input;
//    }
    
//    public static String removeTrademark(String input) {
//        return removeKeywords(input, listTradeMarks);
//    }
//    
//    public static String removeBrandname(String input) {
//        return removeKeywords(input, listMyBrands);
//    }
//
//    public static String removeBanndedKeys(String input) {
//        return removeKeywords(input, listBannedKeyword);
//    }
//    
//    public static String removeHiddenKeys(String input) {
//        return removeKeywords(input, listHiddenKeys);
//    }
    
    public static String removeKeywords(String input, ArrayList<String> listKeys) {
        if (listKeys == null || listKeys.isEmpty()) {
            return input;
        }
        
        String inputLower = input.toLowerCase();
        for (String keyword : listKeys) {

            ArrayList<Integer> listKeyIndex = getListKeywordIndex(inputLower, keyword);

            if (listKeyIndex == null) {
                continue;
            }

            for (int size = listKeyIndex.size(), i = size - 1; i >= 0; i--) {
                input = removeWithKeyIndex(input, listKeyIndex.get(i), keyword.length());
                inputLower = removeWithKeyIndex(inputLower, listKeyIndex.get(i), keyword.length());
            }
        }
        
        return input;
    }
    
//    public static String addForBrand(String input) {
//        if (listMyBrands == null) {
//            return input;
//        }
//        
//        String inputLower = input.toLowerCase();
//        for (String keyword : listMyBrands) {
//
//            ArrayList<Integer> listKeyIndex = getListKeywordIndex(inputLower, keyword);
//
//            if (listKeyIndex == null) {
//                continue;
//            }
//
//            for (int size = listKeyIndex.size(), i = size - 1; i >= 0; i--) {
//                input = addForWithKeyIndex(input, listKeyIndex.get(i));
//                inputLower = addForWithKeyIndex(inputLower, listKeyIndex.get(i));
//            }
//        }
//        
//        return input;
//    }

    public static boolean isContainsKeywords(String input, String keyword) {

        String regex1 = "(.*) " + keyword + " (.*)";
        String regex2 = "(.*) " + keyword;
        String regex3 = keyword + " (.*)";

        return input.matches(regex1) || input.matches(regex2) || input.matches(regex3);
    }

    public static ArrayList<Integer> getListKeywordIndex(String input, String keyword) {
        ArrayList<Integer> results = null;

        int inputLength = input.length();
        int keyLength = keyword.length();
        int index = input.indexOf(keyword, 0);

        if (index < 0) {
            return null;
        }

        while (index >= 0) {
            if (isKeywordIndexForBrand(input, inputLength, keyLength, index)) {
                if (results == null) {
                    results = new ArrayList<>();
                }
                results.add(index);
            } 
            index = input.indexOf(keyword, index + 1);
        }

        return results;
    }

    public static String removeWithKeyIndex(String input, int index, int keylength) {

        int inputLength = input.length();

        if (keylength == inputLength) {
            return "";
        }

        StringBuilder string = new StringBuilder(input);

        //Tu khoa o cuoi
        if (index + keylength == input.length()) {
            string.delete(index - 1, index + keylength + 1);
        } else {
            string.delete(index, index + keylength + 1);
        }
//        string.delete(index, index + keylength + 1);

        return string.toString().trim();
    }
    
    public static String addForWithKeyIndex(String input, int index) {
        StringBuilder string = new StringBuilder(input);
        string.insert(index, "fits for ");
        return string.toString();
    }

    public static int checkContainsKeywordsNoRegex(String input, String keyword, int fromIndex) {

        int inputLength = input.length();
        int keyLength = keyword.length();
        int index = input.indexOf(keyword, fromIndex);

        if (index < 0) {
            return index;
        }

        while (index >= 0) {
            if (isKeywordIndexForBanned(input, inputLength, keyLength, index)) {
                return index;
            } else {
                index = input.indexOf(keyword, index + 1);
            }

        }

        return -1;
    }

    private static boolean isKeywordIndexForBanned(String input, int inputLength, int keyLength, int index) {
        if (index == 0) {
            if (keyLength == inputLength) {
                return true;
            }

            if (isSpaceChar(input.charAt(keyLength))) {
                return true;
            }

            return false;
        }

        //Tu khoa o cuoi
        if (index + keyLength == inputLength) {
            if (keyLength == inputLength) {
                return true;
            }

            if (isSpaceChar(input.charAt(index - 1))) {
                return true;
            }
            return false;
        }
        //Tu khoa o giua
        if (isSpaceChar(input.charAt(index - 1)) && isSpaceChar(input.charAt(index + keyLength))) {
            return true;
        }

        return false;
    }

    private static boolean isKeywordIndexForBrand(String input, int inputLength, int keyLength, int index) {
        if (index == 0) {
            if (keyLength == inputLength) {
                return true;
            }

            if (!isAlphabetChar(input.charAt(keyLength))) {
                return true;
            }

            return false;
        }

        //Tu khoa o cuoi
        if (index + keyLength == inputLength) {
            if (keyLength == inputLength) {
                return true;
            }

            if (!isAlphabetChar(input.charAt(index - 1))) {
                return true;
            }
            return false;
        }
        //Tu khoa o giua
        if (!isAlphabetChar(input.charAt(index - 1)) && !isAlphabetChar(input.charAt(index + keyLength))) {
            return true;
        }

        return false;
    }

    public static boolean isAlphabetChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean isSpaceChar(char c) {
        return c == ' ';
    }

//    public static boolean containBannedKeywordForce(String input) {
//        
//        if(listBannedKeyword == null || input == null) {
//            return false;
//        }
//        String inputLower = input.toLowerCase();
//        for(String s : listBannedKeyword) {
//            int index = inputLower.indexOf(s.toLowerCase().trim());
//            if(index >= 0) {
//                if(index == 0 || inputLower.charAt(index - 1) == 32)
//                System.out.println("| " + input + " | contains banned key: " + s);
//                return true;
//            }
//        }
//        
//        return false;
//    }
    public static String removeKeyword(String input) {

        if (input == null || input.isEmpty()) {
            return null;
        }

        String[] parts = input.split(" ");

        StringBuilder sb = new StringBuilder();

        for (int i = 0, length = parts.length; i < length; i++) {
            if (!isAvoidKeyword(parts[i])) {
                sb.append(parts[i]);

                if (i != length - 1) {
                    sb.append(" ");
                }
            }
        }

        return sb.toString();
    }

}
