package io.openjob.server.common.util;

import org.springframework.util.DigestUtils;

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
     * @param input     input
     * @param key       key
     * @param algorithm algorithm
     * @return encrypt value
     **/
    public static String encrypt(String input, String key, String algorithm) {
        String cipher;

        try {
            byte[] data = key.getBytes(StandardCharsets.UTF_8);
            SecretKey secretKey = new SecretKeySpec(data, algorithm);

            Mac mac = Mac.getInstance(algorithm);
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
        return DigestUtils.md5DigestAsHex(input.getBytes());
    }

    /**
     * bytes to hex string
     *
     * @param bytes byte array
     * @return hex string
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
