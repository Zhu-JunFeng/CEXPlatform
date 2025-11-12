package com.cexpay.user.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 密码工具类
 */
public class PasswordUtil {
    
    /**
     * 加密密码
     */
    public static String encrypt(String password) {
        try {
            // 生成随机盐
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            
            // 对密码进行SHA256加密
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            
            // 合并salt和hash
            byte[] saltAndHash = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, saltAndHash, 0, salt.length);
            System.arraycopy(hashedPassword, 0, saltAndHash, salt.length, hashedPassword.length);
            
            // Base64编码
            return Base64.getEncoder().encodeToString(saltAndHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("加密算法不支持", e);
        }
    }
    
    /**
     * 验证密码
     */
    public static boolean verify(String password, String encryptedPassword) {
        try {
            // Base64解码
            byte[] saltAndHash = Base64.getDecoder().decode(encryptedPassword);
            
            // 分离salt和hash
            byte[] salt = new byte[16];
            byte[] hash = new byte[saltAndHash.length - 16];
            System.arraycopy(saltAndHash, 0, salt, 0, 16);
            System.arraycopy(saltAndHash, 16, hash, 0, hash.length);
            
            // 对输入的密码进行同样的加密
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            
            // 比对hash
            return MessageDigest.isEqual(hashedPassword, hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("加密算法不支持", e);
        }
    }
}
