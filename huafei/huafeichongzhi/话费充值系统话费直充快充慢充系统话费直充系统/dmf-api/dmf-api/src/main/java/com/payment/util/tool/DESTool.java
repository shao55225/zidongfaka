package com.payment.util.tool;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * @Author: Chand
 * @Date: 13-12-04
 * @Time: 下午2:30
 * @Description: DES加密器
 */
public class DESTool {
    private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};

    public static byte[] encrypt(byte[] encryptString, String encryptKey) throws Exception {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(encryptKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            return cipher.doFinal(encryptString);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] decryptString, String decryptKey) throws Exception {
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(decryptKey.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        return cipher.doFinal(decryptString);
    }

    public static void main(String[] args) throws Exception {
        String d = UUID.randomUUID().toString();
        String key = "!1231231sdaf3ra中文gt23*(&(*^";
        byte[] aes = DESTool.encrypt(key.getBytes(), d);
        System.out.println(new String(aes));
        System.out.println(new String(DESTool.decrypt(aes, d)));
    }
}
