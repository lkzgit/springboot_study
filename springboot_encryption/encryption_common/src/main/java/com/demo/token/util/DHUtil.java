package com.demo.token.util;

import org.apache.tomcat.util.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DH算法，该算法属于一种交换算法，发送方生成自己的密钥对，再将公钥传递到接收方，
 * 接收方再根据发送方的公钥生成自己的密钥对，接着将自己生成的密钥对中的公钥传递到发送方。
 * 这时候发送方根据对方的公钥和自己的私钥生成新的本地密钥。
 * 而接收方也根据对方之前发生的公钥和自己的私钥生成新的本地密钥。
 * 双方生成的本地密钥是相同。弥补了对称加密密钥传递不安全的问题。
 *
 * @author
 *
 */
public class DHUtil {

    //DH 发送方（客户端）秘钥长度
    private static final int KEY_SIZE_CLIENT = 1024;
    //DH 接收方（服务端）秘钥长度
    private static final int KEY_SIZE_SERVER = 2048;

    //默认的加密算法，DH生成密钥的时候需要一个对称加密算法来加密，与
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES";

    public static void main(String[] args) throws Exception {

        Map<String, byte[]> key1 = DHUtil.getKey();
        Map<String, byte[]> key2 = DHUtil.getKey();
        Map<String, byte[]> publicKey1 = DHUtil.getKey(key2.get("publicKey"));
        Map<String, byte[]> publicKey2 = DHUtil.getKey(key1.get("publicKey"));
        byte[] secretKey1 = DHUtil.getSecretKey(key2.get("publicKey"), key1.get("privateKey"), "AES");
        System.out.println("key1的本地密钥对:"+ Base64.encodeBase64String(secretKey1));
        byte[] secretKey2 = DHUtil.getSecretKey(key1.get("publicKey"), key2.get("privateKey"), "AES");
        System.out.println("key2的本地密钥对:"+ Base64.encodeBase64String(secretKey2));
        String str="这是什么东西呀";
        byte[] encrypt = DHUtil.encrypt(str.getBytes(), secretKey1);
        byte[] decrypt = DHUtil.decrypt(encrypt, secretKey2);
        String s=new String(decrypt);
        System.out.println(s);

        System.out.println("-----");
        String s1 = Base64.encodeBase64String(str.getBytes());
        byte[] encrypt1 = DHUtil.encrypt(s1.getBytes(), secretKey1);
        byte[] decrypt1 = DHUtil.decrypt(encrypt1, secretKey2);
        byte[] bytes = Base64.decodeBase64(decrypt1);
        System.out.println(new String(decrypt1));
        System.out.println(new String(bytes));

    }
    
    
    /**
     * 发送方：生成自己的密钥对
     *
     * @return 返回密钥对 失败则null
     */
    public static Map<String, byte[]> getKey() {
        Map<String, byte[]> map = null;
        try {
            // 实例化密钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
            // 初始化密钥生成器 密钥长度默认1024 长度只能为512~1024 并且必须时64的倍数
            keyPairGenerator.initialize(KEY_SIZE_CLIENT);
            // 生成密钥对
            KeyPair keypair = keyPairGenerator.generateKeyPair();
            // 获得公钥
            PublicKey publicKey = keypair.getPublic();
            // 获得私钥
            PrivateKey privateKey = keypair.getPrivate();

            map = new HashMap<String, byte[]>();
            // 存储密钥
            map.put("publicKey", publicKey.getEncoded());
            map.put("privateKey", privateKey.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 接收方：得到发送方的公钥，生成接收方自己的密钥对
     *
     * @param key
     * 发送方传递的公钥
     * @return 返回自己的密钥对 失败则null
     */
    public static Map<String, byte[]> getKey(byte[] key) {
        Map<String, byte[]> map = null;
        try {
            // 实例化密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance("DH");
            // 将对方公钥从数组转换为PublicKey
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
            // 产生对方公钥publicKey
            DHPublicKey dhpublicKey = (DHPublicKey) keyFactory
                    .generatePublic(keySpec);
            // 剖析对方key 获取其参数
            DHParameterSpec dhParameterSpec = dhpublicKey.getParams();
            // 实例化密钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance("DH");
            keyPairGenerator.initialize(KEY_SIZE_SERVER);
            // 使用对方公钥参数初始化生成器
            keyPairGenerator.initialize(dhParameterSpec);
            // 生成密钥对
            KeyPair keypair = keyPairGenerator.generateKeyPair();
            // 获得公钥
            PublicKey publicKey = keypair.getPublic();
            // 获得私钥
            PrivateKey privateKey = keypair.getPrivate();

            map = new HashMap<String, byte[]>();
            // 存储密钥
            map.put("publicKey", publicKey.getEncoded());
            map.put("privateKey", privateKey.getEncoded());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将对方公钥 结合自己的私钥 生成一个双方都相同的密钥， 方便进行对称加密操作。
     *
     * @param publicKey
     * 对方传递的公钥
     * @param privateKey
     * 自己的私钥
     * @param algorithm
     * 生成哪个对称加密算法的密钥
     * @return 返回对称算法的共同密钥 失败则null
     */
    public static byte[] getSecretKey(byte[] publicKey, byte[] privateKey,
                                      String algorithm) {
        byte[] result = null;
        try {
            // 实例化密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance("DH");
            // 将publicKey转换为Key对象
            PublicKey publicKey2 = keyFactory
                    .generatePublic(new X509EncodedKeySpec(publicKey));
            // 将privateKey转换为Key对象
            PrivateKey privateKey2 = keyFactory
                    .generatePrivate(new PKCS8EncodedKeySpec(privateKey));

            // 根据以上私钥和公钥生成本地密钥SecretKey

            // 先实例化KeyAgreement
            KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
            // 用自己的私钥初始化keyAgreement
            keyAgreement.init(privateKey2);
            // 结合对方公钥进行运算
            keyAgreement.doPhase(publicKey2, true);
            // 开始生成本地密钥SecretKey 密钥算法为对称算法
            SecretKey secretKey = keyAgreement.generateSecret(algorithm);
            result = secretKey.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    private static SecretKeySpec getSecretKey(final byte[] key) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
//            kg = KeyGenerator.getInstance("AES");
            kg = KeyGenerator.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //AES 要求密钥长度为 128
            kg.init(128, new SecureRandom(key));
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), "AES");// 转换为AES专用密钥
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {

        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器

            byte[] byteContent = data;

            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));// 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);// 加密
            return result;
//            return org.apache.commons.codec.binary.Base64.encodeBase64String(result);//通过Base64转码返回

        } catch (Exception ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;


    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {

        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));

            //执行操作
            byte[] result = cipher.doFinal(data);

            return result;

        } catch (Exception ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * BASE64解密
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

}
