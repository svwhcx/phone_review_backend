package com.svwh.phonereview.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;


public class PasswordUtils {

    private static final char hexDigits[] ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    private static final Pattern HAS_UPPER_CASE = Pattern.compile("^(?=.*?[A-Z]).{8,15}$");
    private static final Pattern HAS_NUMBER = Pattern.compile("^(?=.*?[0-9]).{8,15}$");
    private static final Pattern HAS_SPECIAL_CHAR = Pattern.compile("^(?=.*?[a-z]).{8,15}$");
    private static final Pattern HAS_LOWER_CASE = Pattern.compile("^(?=.*?[#?!@$%^&*-.]).{8,15}$");


    public static boolean isStrong(String password){
        int count = 0;
        if (HAS_UPPER_CASE.matcher(password).matches()){
            count++;
        }
        if (HAS_LOWER_CASE.matcher(password).matches()){
            count++;
        }
        if (HAS_NUMBER.matcher(password).matches()){
            count++;
        }
        if (HAS_SPECIAL_CHAR.matcher(password).matches()){
            count++;
        }
        return count >= 3;
    }


    public static String encryption(String password){
        // 进行混淆
        password = password + "salt";
        byte[] bytes = password.getBytes();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(bytes);
            // 获取密文
            byte[] digest = md5.digest();
            // 转换16进制
            int length = digest.length;
            char c[] = new char[length * 2];
            int k = 0;
            for (byte b : digest) {
                c[k++] = hexDigits[b >>> 4 & 0xf];
                c[k++] = hexDigits[b & 0xf];
            }
            return new String(c);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
