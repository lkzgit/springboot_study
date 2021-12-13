package com.demo.token.util;


import com.demo.token.constant.EncryptConstant;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @ClassName AESUtil
 * @Description AES的工具类
 * @Author wujh
 * @Date 2021/5/26 0026 下午 1:04
 * @Version 1.0
 */
public class AESUtil {

    private static AESUtil instance = null;

    private static Logger log = LoggerFactory.getLogger(AESUtil.class);

    private static String aesGcm2128Algorithm = "AES/GCM/PKCS5Padding";

    /**
     * 通过构造方法进行初始化
     */
    private AESUtil() {
    }

    public static AESUtil getInstance() {
        if (null == instance) {
            instance = new AESUtil();
        }
        return instance;
    }

    //

    /**
     * 生成AES秘钥，然后Base64编码
     * @param key 公钥
     * @return SecretKey
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static SecretKey genSecretKey(byte[] key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator keyGen = KeyGenerator.getInstance(EncryptConstant.AES_KEY_ALGORITHM);
        SecureRandom random = SecureRandom.getInstance(EncryptConstant.SIGN_ALGORITHMS);
        random.setSeed(key);
        keyGen.init(EncryptConstant.AES_KEYSIZE, random);
        SecretKey secretKey = keyGen.generateKey();
        return secretKey;
    }

    /**
     * 生成AES密钥
     * @return
     */
    public static String generateAESKey(){
        KeyGenerator generator = null;
        try {
            generator = KeyGenerator.getInstance("AES");
            //初始化密钥生成器，AES要求密钥长度为128位、192位、256位
            generator.init(128);
            SecretKey secretKey = generator.generateKey();
            return Base64.encodeBase64String(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            log.error("密钥生成异常,error:", e);
        }
        return "";
    }

    //加密
    public static String aesGcmEncrypt(String content, String keyStr) {
        try {
            if (StringUtils.isEmpty(content) || StringUtils.isEmpty(keyStr)) {
                throw new Exception("AESGCM128加密异常，请检查文本或密钥");
            }

            SecretKey secretKey = new SecretKeySpec(Base64.decodeBase64(keyStr), "AES");

            Cipher cipher = Cipher.getInstance(aesGcm2128Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] iv = cipher.getIV();
            assert iv.length == 12;// 偏移参数及长度要在解密的时候保持一致
            byte[] encryptData = cipher.doFinal(content.getBytes());
            assert encryptData.length == content.getBytes().length + 16;
            byte[] message = new byte[12 + content.getBytes().length + 16];
            System.arraycopy(iv, 0, message, 0, 12);
            System.arraycopy(encryptData, 0, message, 12, encryptData.length);
            return Base64.encodeBase64String(message);
        } catch (Exception e) {
            log.error("AESGCM128加密文本处理失败,error:{}", e);
        }
        return null;
    }

    //解密
    public static String aesGcmDecrypt(String content, String keyStr) {
        try {
            if (StringUtils.isEmpty(content) || StringUtils.isEmpty(keyStr)) {
                throw new Exception("AESGCM128解密异常,检查文本或密钥");
            }
            Cipher cipher = Cipher.getInstance(aesGcm2128Algorithm);
            SecretKey key = new SecretKeySpec(Base64.decodeBase64(keyStr), "AES");

            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] message = Base64.decodeBase64(content);
            // 这里的12和16是加密的时候设置的偏移参数及加密长度
            if (message.length < 12 + 16) throw new IllegalArgumentException();
            GCMParameterSpec params = new GCMParameterSpec(128, message, 0, 12);
            cipher.init(Cipher.DECRYPT_MODE, key, params);
            byte[] decryptData = cipher.doFinal(message, 12, message.length - 12);
            String decript = new String(decryptData);
            return decript;
        } catch (Exception e) {
            log.error("AESGCM128解密文本处理失败,error:{}", e);
        }
        return null;
    }

    public static void main(String[] args) {

        String s = AESUtil.generateAESKey();
        System.out.println(s);
        String sd="1234";
        String s1 = AESUtil.aesGcmEncrypt(sd, s);
        String s2 = AESUtil.aesGcmDecrypt(s1, s);
        System.out.println(s2);
//        KeyGenerator generator = null;
//        try {
//            generator = KeyGenerator.getInstance("AES");
//            //初始化密钥生成器，AES要求密钥长度为128位、192位、256位
//            generator.init(128);
//            SecretKey secretKey = generator.generateKey();
//            String gcmSecretKey = Base64.encodeBase64String(secretKey.getEncoded());
//            System.out.println("生成密钥key: "+gcmSecretKey +"\n");
//
//            String toDoStr = "canshuwenben?a=a&b=d文本加密测试";
//            System.out.println("明文文本："+toDoStr);
//            String encryptResult = aesGcmEncrypt(toDoStr,gcmSecretKey);
//            System.out.println("加密结果: "+encryptResult);
//
//            String decryptResult = aesGcmDecrypt(encryptResult,gcmSecretKey);
//            System.out.println("解密结果: "+decryptResult +"\n");
//
//
//        } catch (NoSuchAlgorithmException e) {
//            log.error("密钥生成异常,error:", e);
//        }
    }
}
