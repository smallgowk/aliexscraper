/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

/**
 *
 * @author Duypn4
 */
public class FuncUtil {

    private static final Logger LOG = Logger.getLogger(FuncUtil.class.getSimpleName());

    private static byte[] key = {'P', 'H', 'A', 'N', 'N', 'A', 'N', 'G', 'D', 'U', 'Y', 'B', 'K', 'A', 'H', 'N'};

    public static String encryptSHA256(String str, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest((str + salt).getBytes("UTF-8"));
        return Hex.encodeHexString(hash);
    }

    // 15/11/2104 -> Migrate du lieu tu Vtracking sang nen doi encrypt tu SHA256 + Hex -> SHA1 + Base64
    public static String encryptSHA1(String input, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest((salt + input).getBytes("UTF-8"));
        return Base64.encodeBase64String(result);
    }

    public static String getCurrentFunctionName(Object object) {
        return object.getClass().getEnclosingMethod().getName();
    }

    public static String getCurrentClassName(Object object) {
        return object.getClass().getName();
    }

    public static String decrypt(String strToDecrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, DecoderException {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        final String decryptedString = new String(cipher.doFinal(Hex.decodeHex(strToDecrypt.toCharArray())));
        return decryptedString;
    }

    // Ham ma hoa AES
    public static String encrypt(String strToEncrypt)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        final String encryptedString = bytesToHex(cipher.doFinal(strToEncrypt
                .getBytes()));
        return encryptedString;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String createSalt() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }
    
    public static String createSalt(int numberChar) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < numberChar; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }
    
    public static String createSaltNumber(int numberChar) {
        char[] chars = "0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < numberChar; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }

    public static String formatPhoneNumber(String devicePhone) {
        if (devicePhone.startsWith("0")) {
            devicePhone = devicePhone.substring(1);
        } else if (devicePhone.startsWith("84")) {
            devicePhone = devicePhone.substring(2);
        }
        return devicePhone;
    }

    public static Boolean isNonASCII(String password) {
        for (char val : password.toCharArray()) {
            if (val > 128) {
                return true;
            }
        }
        return false;
    }

    public static Boolean checkCharASCII(String text) {
        for (char val : text.toCharArray()) {
            if (((65 < val) && (val < 91)) || ((96 < val) && (val < 123) || (val > 126) || val == 32)) {
                return true;
            }
        }
        return false;
    }

    private static Pattern pattern_user;
    private static Matcher matcher_user;

    public static String genRandomPassword() {

        // A - Z: 65 -> 90
        // a-z: 97 - > 122
        // 0 - 9: 48 - 57
        // ki tu dac biet: 33-47
        // ki tu dac biet: 33,64,35,36,37,94,38,42 (!@#$%^&*)
        // @: 64
        String result = "";

        ArrayList<Integer> cards = new ArrayList<>();

        Integer[] pass = new Integer[8];
        for (int i = 0; i < 8; i++) {
            if (i < 3) { // Random 3 ki tu so
                pass[i] = new Random().nextInt(10) + 48;
            } else if (i < 6) { // Random 3 ki tu chu cai thuong
                pass[i] = new Random().nextInt(26) + 97;
            } else if (i == 6) { // Random chu hoa
                pass[i] = new Random().nextInt(26) + 65;
            } else { // Random ki tu dac biet
                int[] arrSc = new int[]{33, 64, 35, 36, 37, 94, 38, 42};
                int arrScLength = arrSc.length;
                int sc = new Random().nextInt(arrScLength);
                pass[i] = arrSc[sc];
            }
        }

        cards.addAll(Arrays.asList(pass));
        Collections.shuffle(cards);

        for (Integer i : cards) {
            result = result + (char) (i.intValue());
        }

        return result;
    }



    public static boolean validateCustompass(String input) {
        return input.length() >= 8 && input.length() <= 20;
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateViettelPhoneNumber(String phone) {
        String[] reg10 = {"0961", "0962", "09630", "09631", "09632", "09633", "09634", "09635", "09636", "09637", "09638", "09639", "0964", "0965", "0966", "0967", "0968", "0969", "0971", "0972", "0973", "0974", "0975", "0976", "0977", "0978", "0979", "0981", "0982", "0983", "0984", "0985", "0986", "0987", "0988", "0989"};
        String[] reg11 = {"01626", "01627", "016284", "016285", "016286", "016287", "016288", "016289", "016280", "016281", "016282", "016283", "01629", "01632", "01633", "01634", "01635", "01636", "01637", "01638", "01639", "0164", "01659", "01663", "01664", "01665", "01666", "01667", "01668", "01652", "01653", "01654", "01655", "01656", "01657", "01658", "01662", "01669", "01672", "01673", "01674", "01675", "01676", "01677", "01678", "01679", "0168", "01692", "01693", "01694", "01695", "01696", "01697", "01698", "01699"};

        if (phone.length() == 10) {
            for (String reg101 : reg10) {
                if (phone.indexOf(reg101) == 0) {
                    return true;
                }
            }
            return false;
        } else {
            for (String reg111 : reg11) {
                if (phone.indexOf(reg111) == 0) {
                    return true;
                }
            }
            return false;
        }

    }

    public static boolean validateProvince(String province) {
        String[] provinces = {"AGG", "BDH", "BTN", "BTE", "BGG", "BNH", "BKN", "BDG", "BPC", "BLU", "CBG", "CTO", "CMU", "DNI", "DTP", "DBN", "DLK", "DCN", "DNG", "GLI", "HNI", "HBH", "HGG", "HPG", "HTH", "HDG", "HYN", "HUG", "KHA", "KTM", "KGG", "LCI", "LSN", "LDG", "LAN", "LCU", "NBH", "NAN", "NTN", "NDH", "HNM", "PYN", "PTO", "QNH", "QBH", "QTI", "QNI", "QNM", "SLA", "STG", "HCM", "TQG", "TBH", "THA", "TTH", "TNH", "TGG", "TVH", "TNN", "VTU", "VLG", "VPC", "YBI"};
        for (String provinces1 : provinces) {
            if (province.equals(provinces1)) {
                return true;
            }
        }
        return false;

    }

    public static String formatNumberDateTime(int time) {
        if (time < 10) {
            return "0" + String.valueOf(time);
        }
        return String.valueOf(time);
    }

    public static String getLocalhost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public static Date createSqlDate() {
        Calendar cal = Calendar.getInstance();
        Date utilDate = new Date();
        cal.setTime(utilDate);
        java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());
        return sqlDate;
    }

    public static Timestamp createSqlTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp createSqlDate(Date date) {
        Timestamp sq = new Timestamp(date.getTime());
        return sq;
    }

    public static Date getExpiredDate(Date activeTime, int promotionMonth) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(activeTime);
        cal.add(Calendar.DAY_OF_MONTH, 1 - cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.MONTH, promotionMonth);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static Date getNextChargingDate(Date activeTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(activeTime);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getReturnChargingDate(Date activeTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(activeTime);
        cal.set(Calendar.DAY_OF_MONTH, -3);
        return cal.getTime();
    }

    public static Date parseDateFromString(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.parse(date);
    }

    public static String formatPhoneNumberWith84(String devicePhone) {
        if (devicePhone.startsWith("0")) {
            devicePhone = devicePhone.substring(1);
            devicePhone = "84" + devicePhone;
        } else if (devicePhone.startsWith("+84")) {
            devicePhone = devicePhone.substring(1);
        } else if (devicePhone.startsWith("84")) {
            return devicePhone;
        } else {
            devicePhone = "84" + devicePhone;
        }
        return devicePhone;
    }

    public static String formatTime(Date time) {
        if (time == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(time);
    }

    public static String convertDateReqTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        return format.format(date);
    }

    public static String parseMonthPartition(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String monthString = sdf.format(date);
        String monthPartition = "partition (DATA" + monthString + ")";
        return monthPartition;
    }

    public static int getSumEAN13(CharSequence s) {
        int length = s.length();
        if (length == 0) {
            return -1;
        }

        int sum = 0;
        for (int i = length - 2; i >= 0; i -= 2) {
            int digit = (int) s.charAt(i) - (int) '0';
            if (digit < 0 || digit > 9) {
                return -1;
            }
            sum += digit;
        }
        sum *= 3;
        for (int i = length - 1; i >= 0; i -= 2) {
            int digit = (int) s.charAt(i) - (int) '0';
            if (digit < 0 || digit > 9) {
                return -1;
            }
            sum += digit;
        }

        return sum;
    }

    public static String formatAsEAN13(String s) {
        String s1 = null;

        if (s.length() >= 10) {
            s1 = s.substring(s.length() - 9, s.length());
        } else if (s.length() < 9) {
            s1 = s;
            for (int i = 0, size = 9 - s.length(); i < size; i++) {
                s1 += "0";
            }
        } else {
            s1 = s;
        }
        s1 = "311" + s1;
        String temp = s1 + "0";

        int sum = getSumEAN13(temp);
        int mod10 = sum % 10;
        return s1 + (10 - mod10);
    }
    

    public static int compareTwoDate(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
//        
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        if (calendar1.get(Calendar.DAY_OF_YEAR) - calendar2.get(Calendar.DAY_OF_YEAR) >= 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public static Date convertStringToDate(String input, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(input);
    }

    public static String convertDateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date convertBirthDayToDate(String birthDay) throws ParseException {
        if (birthDay == null) {
            return null;
        }
        return convertStringToDate(birthDay, "dd/MM/yyyy");
    }

    public static String convertBirthDayToString(Date birthDay) {
        if (birthDay == null) {
            return null;
        }
        return convertDateToString(birthDay, "dd/MM/yyyy");
    }

}
