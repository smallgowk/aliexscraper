package com.phanduy.aliexscrap.utils;

import java.util.HashMap;
import java.util.regex.Pattern;

public class StringUtils {
    public static String getFirstCapitalWord(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        input = input.trim();

        char c = input.charAt(0);

        if (c >= 97 && c <= 122) {
            c -= 32;
            return c + input.substring(1, input.length());
        }

        return input;
    }

    public static String getPrefixCapitalWord(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        input = input.trim();

        StringBuilder sb = new StringBuilder();
        char firstChar = input.charAt(0);
        if (firstChar >= 97 && firstChar <= 122) {
            firstChar -= 32;
            sb.append(firstChar);
        } else if ((firstChar >= 48 && firstChar <= 57) || (firstChar >= 65 && firstChar <= 90)) {
            sb.append(firstChar);
        }

        for (int i = 1, length = input.length(); i < length; i++) {
            char c = input.charAt(i);
            sb.append(c);
            if (c == 32) {
                char nextChar = input.charAt(i + 1);
                if (nextChar >= 97 && nextChar <= 122) {
                    nextChar -= 32;
                    sb.append(nextChar);
                    i++;
                }
            }
        }

        return sb.toString();
    }

    public static String getStringBrief(String input) {

        if(input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(input.charAt(0));
        for (int i = 1, length = input.length(); i < length; i++) {
            char c = input.charAt(i);
            if (c >= 65 && c <= 90) {
                sb.append(c);
            } else {
                if (c == 32) {
                    if (i < length - 1) {
                        char nextChar = input.charAt(i + 1);
                        if (nextChar >= 65 && nextChar <= 90) {
                            sb.append(nextChar);
                        }

                        i++;
                    }
                }
            }
        }

        if (sb.length() == 1) {
            if (input.length() <= 5) {
                return input;
            }
            for (int i = 0; i < 5; i++) {
                char c = input.charAt(i);
                if (c != 32) {
                    sb.append(c);
                }
            }
        }

        return sb.toString();
    }

    public static boolean isValidWord(String input) {

        if (input == null || input.length() <= 2) {
            return false;
        }

        Pattern pattern = Pattern.compile("[A-Za-z]+");
        return pattern.matcher(input).matches();
    }

    public static boolean isCharactorOnly(String input) {

        if (input == null || input.length() <= 2) {
            return false;
        }

        Pattern pattern = Pattern.compile("[A-Za-z ]+");
        return pattern.matcher(input).matches();
    }

    public static boolean isTextVisible(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        if (input.trim().equals("&nbsp;")) {
            return false;
        }

        for (int i = 0, length = input.length(); i < length; i++) {
            char c = input.charAt(i);
            if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122) || (c >= 48 && c <= 57)) {
                return true;
            }
        }

        return false;
    }

    public static String removeTradeMark(String input, HashMap<String, Boolean> hashMapTradeMark) {

        if (input == null || input.isEmpty()) {
            return "";
        }
//        if (input.contains("LED")) {
//            System.out.println("" + input);
//        }
        StringBuilder sb = new StringBuilder();
        String[] temple = input.split(" ");
        for (String part : temple) {
            String checkPart = part.trim().toUpperCase();

            if (hashMapTradeMark != null && !hashMapTradeMark.isEmpty()) {

                if (hashMapTradeMark.containsKey(checkPart)) {
                    if (hashMapTradeMark.get(checkPart)) {
                        return null;
                    } else {
                        continue;
                    }
                } else {
                    sb.append(part).append(" ");
                }

//                if (!hashMapTradeMark.containsKey(checkPart)) {
//                    sb.append(part).append(" ");
//                } else {
//                    if (hashMapTradeMark.get(checkPart)) {
//                        return null;
//                    }
//                }
            } else {
                sb.append(part).append(" ");
            }

        }

        return sb.toString().trim();

    }

    public static boolean containTradeMarkOrBannedWord(String input, HashMap<String, Boolean> hashMapTradeMark) {
        String[] temple = input.split(" ");
        for (String part : temple) {
            String checkPart = part.trim().toUpperCase();

            if ((hashMapTradeMark != null && hashMapTradeMark.containsKey(checkPart) && hashMapTradeMark.get(checkPart)) ) {
                return true;
            }
        }

        return false;
    }

    public static String getItemTypeKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }
        keyword = keyword.toLowerCase().trim().replaceAll(" ", "-");
        return keyword;
    }

    public static String replaceSpace(String input, String character) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        input = input.trim().replaceAll(" ", character);
        return input;
    }

    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isTextOnly(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        String regex = "[a-zA-Z-_ ]+";
        return Pattern.matches(regex, input);

//        if (input.trim().equals("&nbsp;")) {
//            return false;
//        }

//        for (int i = 0, length = input.length(); i < length; i++) {
//            char c = input.charAt(i);
//            if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
//                return true;
//            }
//        }
//
//        return false;
    }

    public static String removeWord(String input, String word) {
        if (isEmpty(word)) {
            return input;
        }

        if (isEmpty(input)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        String[] inputParts = input.split(Pattern.quote(" "));

        for (String part : inputParts) {
            if (part.toLowerCase().equals(word.toLowerCase())) {
                continue;
            }

            if (sb.length() == 0) {
                sb.append(part);
            } else {
                sb.append(" ").append(part);
            }
        }

        return sb.toString();
    }

    public static String formatPrice(float price) {
        return String.format("%.2f", price);
    }

    public static String getCEOPrice(float price) {
//        int n = (int) price;
//
//        float ceoPrice = n * 1f + 0.99f;
//
//        return formatPrice(ceoPrice);
        return formatPrice(price);
    }
}
