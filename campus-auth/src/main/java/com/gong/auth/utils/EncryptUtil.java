package com.gong.auth.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class EncryptUtil {

    // 确保密钥长度为16、24或32字节
    private static String secretKey = "GongChengYun1234";

    public static String encrypt(String plainText) throws Exception {
        Key key = generateKey(secretKey);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 解密方法
    public static String decrypt(String encryptedText) throws Exception {
        Key key = generateKey(secretKey);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    // 生成密钥
    private static Key generateKey(String secretKey) throws Exception {
        return new SecretKeySpec(secretKey.getBytes(), "AES");
    }

    public static void main(String[] args) {
        try {
//            String originalString = "true";
//            // 加密
//            String encryptedString = encrypt(originalString);
//            System.out.println("Original: " + originalString);
//            System.out.println("Encrypted: " + encryptedString);
//
//            // 解密
//            String decryptedString = decrypt(encryptedString);
//            System.out.println("Decrypted: " + decryptedString);
            String decrypt = decrypt("MqGoyHir5b5NSEa2O86MCg==");
//
            System.out.println("decrypt = " + decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
