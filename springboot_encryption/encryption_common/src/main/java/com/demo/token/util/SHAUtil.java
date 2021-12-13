package com.demo.token.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256算法
 */
public class SHAUtil {

    public static void main(String[] args) throws Exception {
        String shaString = SHAUtil.getSHA256StrJava("asd");
        System.out.println("sha256签名：" +shaString);
        boolean verify = SHAUtil.verify("122", shaString);
        System.out.println(verify);
    }

    /**
     * 利用java原生的摘要实现SHA256算法
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256StrJava(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * sha256验签
     * @param srcData
     * @param signatureString
     * @return
     * @throws Exception
     */
    public static boolean verify(String srcData, String signatureString) {
        // 根据密文获取签名
        String newSignature = getSHA256StrJava(srcData);
        //生成的签名与传过来的签名比对
        return signatureString.equals(newSignature);
    }


    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
