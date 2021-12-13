package com.demo.token.util;


import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;

/**
 * @ClassName EncryptUtil
 * @Description 加密工具类
 * @Author wujh
 * @Date 2021/5/25 0025 下午 7:07
 * @Version 1.0
 */
public class EncryptUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptUtil.class);

    private static EncryptUtil instance = null;

    /**
     * 工作秘钥密文和私钥存储地址
     */
    private String filePath;

    /**
     * 子系统标识
     */
    private String suiteId;
//
//    /**
//     * 通过构造方法进行初始化
//     */
//    public EncryptUtil() {
//    }
//
//    public EncryptUtil(String filePath, String suiteId) {
//        this.suiteId = suiteId;
//        this.filePath = filePath;
//    }

    /**
     * 通过构造方法进行初始化
     */
    private EncryptUtil() {
    }

    public static EncryptUtil getInstance() {
        if (null == instance) {
            instance = new EncryptUtil();
        }
        return instance;
    }

    public void getObject(String filePath, String suiteId) {
        this.suiteId = suiteId;
        this.filePath = filePath;
    }

    /**
     * 获取文件路径，如果不配置路径，则设默认地址
     * @return String 返回存储路径
     */
    public String path () {

        if (StringUtils.isEmpty(this.filePath)) {

            Properties properties = System.getProperties();

            String path = properties.getProperty("user.dir");
            if (properties.getProperty("os.name").toLowerCase().contains("win")) {
                path += "\\";
            }else {
                path += "/";
            }
            this.filePath = path;
        }
        return this.filePath;
    }

    /**
     * 生成工作秘钥密文和私钥，并将工作秘钥明文和公钥存储在内存中
     */
    public void generateSecretFile () throws Exception {

        String filePath = path();

        /**
         * 工作秘钥密文地址
         */
        String workSecretPath = filePath + File.separator + this.suiteId + "_txt";

        /**
         * 私钥地址
         */
        String privateSecretPath = filePath + File.separator + this.suiteId + "_rsa";

        /**
         * 工作秘钥密文文件
         */
        File workSecretFile = new File(workSecretPath);

        /**
         * 私钥文件
         */
        File privateSecretFile = new File(privateSecretPath);

        if((workSecretFile.exists() && !privateSecretFile.exists()) ||
                (!workSecretFile.exists() && privateSecretFile.exists())) {
            throw new Exception("the working secret file or the private key file not exists");
        } else if (!workSecretFile.exists() && !privateSecretFile.exists()) {

            /**
             * 生成工作私钥文件
             */
            RSAKeyUtil.generateRsaKey(privateSecretPath);

            /**
             * 工作秘钥
             */
            String workSecret = getWorkSecret();
            LOGGER.info("加密后工作秘钥明文，{} ", workSecret);

            RSAKeyUtil.encryptByRsa(workSecret,
                    workSecretPath);

        } else {
            /**
             * 获取公钥
             */
            RSAKeyUtil.generateRsaKey(privateSecretPath);
            RSAKeyUtil.decryptFileByRsa(workSecretPath);
        }
    }

    /**
     * 获取工作秘钥明文
     * @return String
     */
    public String getWorkSecret () {

        String result = null;
        try {
            /**
             * 生成随机盐汁
             */
            String salt = PBKDF2Util.generateSalt();
            LOGGER.info("the random salt, salt:{}", salt);

            /**
             * 工作秘钥明文
             */
            result = PBKDF2Util.getEncryptedPassword(this.suiteId, salt);
        }catch (InvalidKeySpecException e2) {
            e2.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }

    /**
     * 对字符串进行加密
     * @param msg 明文
     * @return
     */
    public String encrypt (String msg) {
        return RSAKeyUtil.getInstance().encryptString(msg);
    }

    /**
     * 对字符串进行解密
     * @param msg 加密字符串
     * @return
     */
    public String decrypt (String msg) {
        return RSAKeyUtil.getInstance().decryptString(msg);
    }

    /**
     *  签名
     * @param data 待签名数据
     * @return String 签名结果
     */
    public String sign(String data) throws Exception {
        return RSAKeyUtil.getInstance().sign(data);
    }

    /**
     * 验证签名
     * @param srcData 原始字符串
     * @param sign 签名
     * @return boolean 返回验签的结果
     */
    public boolean verifySing(String srcData, String sign) throws Exception {
        return RSAKeyUtil.getInstance().verify(srcData, sign);
    }

    /**
     * 获取密文签名，使用sha256签名
     * @param secretData
     * @return
     */
    public String getSignature(String secretData){
        return SHAUtil.getSHA256StrJava(secretData);
    }

    /**
     * sha256对应的验签
     * @param secretData 密文
     * @param signatureData 接收到的签名
     * @return
     */
    public boolean verifySignature(String secretData, String signatureData){
        return SHAUtil.verify(secretData, signatureData);
    }

    /**
     * 使用DH加密
     * @param data 要加密的数据
     * @param dHKeyBytes DH的本地共享秘钥，根据自己的私钥和对方的公钥生成的
     * @return
     * @throws Exception
     */
    public String encryptDH(String data, byte[] dHKeyBytes)  throws Exception {
        byte[] encodeData = DHUtil.encrypt(data.getBytes(), dHKeyBytes);
        return Base64.encodeBase64String(encodeData);
    }

    /**
     *
     * @param data 要解密的数据
     * @param dHKeyBytes DH的本地共享秘钥，根据自己的私钥和对方的公钥生成的
     * @return
     * @throws Exception
     */
    public String decryptDH(String data, byte[] dHKeyBytes) throws Exception {
        byte[] decryptData = DHUtil.decrypt(Base64.decodeBase64(data.getBytes()), dHKeyBytes);
        return Base64.encodeBase64String(decryptData);
    }

//    public static void main(String[] args) {
//        Map<String, byte[]> key1 = DHUtil.getKey();
//        Map<String, byte[]> key2 = DHUtil.getKey();
//        String str="1234";
//        byte[] secretKey = DHUtil.getSecretKey(key2.get("publicKey"), key1.get("privateKey"), "AES");
//        System.out.println("Key1的本地密钥:"+Base64.encodeBase64String(secretKey));
//        byte[] secretKey2 = DHUtil.getSecretKey(key1.get("publicKey"), key2.get("privateKey"), "AES");
//        System.out.println("Key2的本地密钥:"+Base64.encodeBase64String(secretKey2));
//        try {
//            String s = EncryptUtil.getInstance().encryptDH(str, secretKey);
//            String s1 = EncryptUtil.getInstance().decryptDH(s, secretKey2);
//            System.out.println(s1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }


}
