/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author Admin
 */
public class EncryptUtil {

    private static byte[] key = {'P', 'H', 'A', 'N', 'N', 'A', 'N', 'G', 'D', 'U', 'Y', 'B', 'K', 'A', 'H', 'N'};

    public static String encryptHmacSHA1(String cleanData, String keyStr) {
        String signature = null;
        try {
            SecretKey key = new SecretKeySpec(keyStr.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(key);
            byte[] doFinal = mac.doFinal(cleanData.getBytes());
            signature = Base64.getEncoder().encodeToString(doFinal);
        } catch (NoSuchAlgorithmException ignored) {
        } catch (InvalidKeyException ignored) {
        }
        return signature;
    }

    public static String encryptHmacSHA256(String cleanData, String keyStr) {
        String signature = null;
        try {
            SecretKey key = new SecretKeySpec(keyStr.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key);
            byte[] doFinal = mac.doFinal(cleanData.getBytes());
            signature = Base64.getEncoder().encodeToString(doFinal);
        } catch (NoSuchAlgorithmException ignored) {
        } catch (InvalidKeyException ignored) {
        }
        return signature;
    }
    
//    public static String decrypt(String strToDecrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, DecoderException {
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
//        final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
//        cipher.init(Cipher.DECRYPT_MODE, secretKey);
//        final String decryptedString = new String(cipher.doFinal(Hex.decodeHex(strToDecrypt.toCharArray())));
//        return decryptedString;
//    }
//
//    // Ham ma hoa AES
//    public static String encrypt(String strToEncrypt)
//            throws NoSuchAlgorithmException, NoSuchPaddingException,
//            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//        final String encryptedString = bytesToHex(cipher.doFinal(strToEncrypt
//                .getBytes()));
//        return encryptedString;
//    }

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
    
//    public static String charsToHex(char[] chars) {
//        char[] hexChars = new char[chars.length * 2];
//        for (int j = 0; j < chars.length; j++) {
//            
//            if((int)chars[j] > 1000) {
//                
//            }
//            
//            int v = chars[j] & 0xFF;
//            hexChars[j * 2] = hexArray[v >>> 4];
//            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
//        }
//        return new String(hexChars);
//    }

    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            return bytesToHex(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            return new String(cipher.doFinal(Hex.decodeHex(strToDecrypt.toCharArray())));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

}
