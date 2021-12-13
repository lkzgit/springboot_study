package com.demo.token.util;


import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Map;

public class Test {

    public static void main(String[] args) throws Exception {

        //AES加解密数据：
        //生成aes密钥
        String gcmSecretKey = AESUtil.generateAESKey();
        System.out.println("A系统---生成AES密钥原文: "+gcmSecretKey);
        //参数
        String paramsText = "canshuwenben?a=a&b=d文本明文";
        System.out.println("A系统---明文文本："+paramsText);
        String encryptResult = AESUtil.aesGcmEncrypt(paramsText,gcmSecretKey);
        System.out.println("A系统---明文文本加密结果: "+encryptResult);

        //DH加解密AES密钥：
        // 发送方生成自己的密钥对并保存
        Map<String, byte[]> map = DHUtil.getKey();
        // 接收方根据发送方的公钥生成自己的密钥对并保存
        Map<String, byte[]> map1 = DHUtil.getKey(map.get("publicKey"));
        // 根据发送方的公钥和接收方私钥生成 接收方的本地密钥
        byte[] bytes =DHUtil.getSecretKey(map.get("publicKey"), map1.get("privateKey"), "AES");
        //使用甲方本地DH密钥对AES密钥进行加密
        byte[] encodeMsgA2B = DHUtil.encrypt(gcmSecretKey.getBytes(), bytes);
        System.out.println("A系统---对AES密钥加密后结果:" + Base64.encodeBase64String(encodeMsgA2B));

        //签名：
        String signature = EncryptUtil.getInstance().getSignature(encryptResult);

        //报文拼接：密文.DH加密后的秘钥.签名
        String finalText = encryptResult + "." + Base64.encodeBase64String(encodeMsgA2B) + "." + signature;
        System.out.println("A系统---报文:" + finalText + "\n");

        //B系统先截取报文
        //截取代码

        //验签：
        boolean signatureVerify = EncryptUtil.getInstance().verifySignature(encryptResult, signature);
        System.out.println("B系统---验签:" + signatureVerify);

        //DH解密aes密钥：
        bytes = DHUtil.getSecretKey(map1.get("publicKey"), map.get("privateKey"), "AES");
        byte[] msgB2A = DHUtil.decrypt(encodeMsgA2B, bytes);
        String dhAesKey = new String(msgB2A);
        System.out.println("B系统---解密后AES秘钥:" + dhAesKey);

        //AES解密报文：
        String decryptResult = AESUtil.aesGcmDecrypt(encryptResult,dhAesKey);
        System.out.println("B系统---解密报文结果: "+decryptResult +"\n");

    }
}
