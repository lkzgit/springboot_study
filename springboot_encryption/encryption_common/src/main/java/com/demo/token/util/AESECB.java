package com.demo.token.util;


import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.util.Map;
import java.util.Random;


/**
 * AES的加密/解密
 */
public class AESECB {
    //用于生成key
    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //算法
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";


    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }



    /**
     * 将byte[]转为各种进制的字符串
     * @param bytes byte[]
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    /**
     * base 64 encode
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes){
        return Base64.encodeBase64String(bytes);
    }

    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception{
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }


    /**
     * AES加密
     * @param content 明文
     * @param encryptKey 密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes("utf-8"), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }



    /**
     * AES解密
     * @param encryptBytes 明文byte[]
     * @param decryptKey 密钥
     * @return 明文String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes("utf-8"), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        //这句很重要，不设置“UTF-8”，会乱码
        return new String(decryptBytes,"UTF-8");
    }


    /**
     * fun:加密
     * @param content 明文
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }


    /**
     * fun:解密
     * @param encryptStr 明文base 64 code
     * @param decryptKey 密钥
     * @return 明文string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }


    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {

        Map<String, byte[]> key1 = DHUtil.getKey();
        Map<String, byte[]> key2 = DHUtil.getKey();
        String str="这是个什么东西呀";
        String sha256StrJava = SHAUtil.getSHA256StrJava(str);
        byte[] secretKey = DHUtil.getSecretKey(key2.get("publicKey"), key1.get("privateKey"), "AES");
        System.out.println("Key1的本地密钥:"+Base64.encodeBase64String(secretKey));
        byte[] secretKey2 = DHUtil.getSecretKey(key1.get("publicKey"), key2.get("privateKey"), "AES");
        System.out.println("Key2的本地密钥:"+Base64.encodeBase64String(secretKey2));
        String s = AESECB.aesEncrypt(sha256StrJava, Base64.encodeBase64String(secretKey));
        String s1 = AESECB.aesDecrypt(s, Base64.encodeBase64String(secretKey2));
        System.out.println(s1);
        //随机生成16位定长的key密钥
//        String key = generateString(16);
//        String key = "066Q2OmxsfappSoT";
//        //明文
//        String content = "13999887766";
//        System.out.println("加密前：" + content);
//        System.out.println("密钥：" + key);
//        String encrypt = aesEncrypt(content, key);
//        System.out.println("加密后：" + encrypt);
//        String decrypt = aesDecrypt(encrypt, key);
//        System.out.println("解密后：" + decrypt);
    }
}

