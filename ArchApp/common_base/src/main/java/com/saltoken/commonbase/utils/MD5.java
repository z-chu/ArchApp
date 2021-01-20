package com.saltoken.commonbase.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private MD5() {
    }

    public static String getMessageDigest(byte[] buffer) {
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte mdItem : md) {
                str[k++] = hexDigits[mdItem >>> 4 & 0xf];
                str[k++] = hexDigits[mdItem & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return new String(buffer);
        }
    }

    public static String getMessageDigest(String content) {
        return getMessageDigest(content.getBytes());
    }


    public static byte[] hash(byte[] bytes, byte[] salt, int hashIterations) throws NoSuchAlgorithmException {
        MessageDigest digest = getDigest();
        if (salt != null) {
            digest.reset();
            digest.update(salt);
        }
        byte[] hashed = digest.digest(bytes);
        int iterations = hashIterations - 1; //already hashed once above
        //iterate remaining number:
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            hashed = digest.digest(hashed);
        }
        return hashed;
    }


    private static MessageDigest getDigest() throws NoSuchAlgorithmException {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            String msg = "No native '" + "MD5" + "' MessageDigest instance available on the current JVM.";
            throw new NoSuchAlgorithmException(msg, e);
        }
    }
}
