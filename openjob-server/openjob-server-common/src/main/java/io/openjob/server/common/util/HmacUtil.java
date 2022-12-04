package io.openjob.server.common.util;

import org.apache.tomcat.util.security.MD5Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author inhere
 */
public class HmacUtil {
    /**
     * HmacSHA1 method, encrypted length is 32
     */
    public static final String HMAC_SHA1 = "HmacSHA1";

    /**
     * HmacSHA1 method, encrypted length is 64
     */
    public static final String HMAC_SHA256 = "HmacSHA256";

    /**
     * HmacSHA1 method, encrypted length is 128
     */
    public static final String HMAC_SHA512 = "HmacSHA512";

    /**
     * generate hashed passwd
     *
     * @param passwd user input passwd
     * @param key    passwd salt
     * @return hashed passwd
     */
    public static String hashPasswd(String passwd, String key) {
        return encrypt(passwd, key, HMAC_SHA256);
    }

    /**
     * verify passwd
     *
     * @param hashed hashed passwd from db
     * @param passwd user input passwd
     * @param key    passwd salt
     * @return ok
     */
    public static Boolean verifyPasswd(String hashed, String passwd, String key) {
        return Objects.equals(hashPasswd(passwd, key), hashed);
    }

    /**
     * 实现Hmac系列的加密算法HmacSHA1、HmacMD5等
     * - from <a href="https://skaygo.blog.csdn.net/article/details/121705023">csdn blog</a>
     *
     * @param input     需要加密的输入参数
     * @param key       密钥
     * @param algorithm 选择加密算法
     * @return 加密后的值
     **/
    public static String encrypt(String input, String key, String algorithm) {
        String cipher;

        try {
            byte[] data = key.getBytes(StandardCharsets.UTF_8);
            // 根据给定的字节数组构造一个密钥，第二个参数指定一个密钥的算法名称，生成HmacSHA1专属密钥
            SecretKey secretKey = new SecretKeySpec(data, algorithm);

            // 生成一个指定Mac算法的Mac对象
            Mac mac = Mac.getInstance(algorithm);
            // 用给定密钥初始化Mac对象
            mac.init(secretKey);

            byte[] text = input.getBytes(StandardCharsets.UTF_8);
            byte[] encryptByte = mac.doFinal(text);
            cipher = bytesToHexStr(encryptByte);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("generate a encrypt value fail", e);
        }

        return cipher;
    }

    /**
     * md5 input string
     *
     * @param input string
     * @return string
     */
    public static String md5(String input) {
        return MD5Encoder.encode(input.getBytes());
    }

    /**
     * byte数组转16进制字符串
     *
     * @param bytes byte数组
     * @return hex字符串
     */
    public static String bytesToHexStr(byte[] bytes) {
        StringBuilder hexStr = new StringBuilder();

        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexStr.append(hex);
        }

        return hexStr.toString();
    }

}
