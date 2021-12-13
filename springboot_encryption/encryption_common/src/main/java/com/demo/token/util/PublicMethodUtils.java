package com.demo.token.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lkz
 * @date 2021/12/4 16:29
 * @description  操作获取token工具类
 */
public class PublicMethodUtils {

    private static PublicMethodUtils instance = null;


    /**
     * 通过构造方法进行初始化
     */
    private PublicMethodUtils() {
    }

    public static PublicMethodUtils getInstance() {
        if (null == instance) {
            instance = new PublicMethodUtils();
        }
        return instance;
    }


    /*
    获取公钥
    puPrKeyPair :自己服务的公私密钥
    url:对方请求服务的地址
     */
    public  static String getPublicKey(Map<String,byte[]> puPrKeyPair,String url,String service){
        // 获取自己的公钥
        byte[] publicKeys = puPrKeyPair.get("publicKey");
        String base64String = Base64.encodeBase64String(publicKeys);
        //对方的公钥
        String oppoPublicKey = HttpRequest.get(url).header("publicKey", base64String).header("service",service).execute().body();
        return oppoPublicKey;

    }

    /**
     * 返回请求需要的公钥
     * @param puPrKeyPair 当前服务的公私钥对
     * @param
     * @return
     */
    public static String returnPublic(Map<String,byte[]> puPrKeyPair){
        byte[] publicKeys = puPrKeyPair.get("publicKey");
        String base64String = Base64.encodeBase64String(publicKeys);
        return base64String;

    }

    /**
     *
     * @param puPrKeyPair 服务自身的公私钥
     * @param url 请求的地址
     * @param serveInfo 服务本身的参数信息 用来生成token
     * @param aesKey 当前服务生成的Aes密钥
     * @return
     */
    public static String getToken(Map<String,byte[]>puPrKeyPair,String url,Map<String,Object> serveInfo,String aesKey ){
        // 服务信息拼接
        String appId = (String)serveInfo.get("appId");
        String app_secret =(String) serveInfo.get("app_secret");
        String tenantId =(String) serveInfo.get("tenant_id");
        long app_time = (long)serveInfo.get("apply_time");
        //对方的公钥
        String oppoPublicKey =(String) serveInfo.get("oppoPublicKey");
        String str=appId+"&"+app_secret+"&"+tenantId+"&"+app_time;
        //原文加密
        String aesGcmDecrypt = AESUtil.aesGcmEncrypt(str, aesKey);
        System.out.println("原文加密结果:"+aesGcmDecrypt);
        //对方的公钥与自己的私钥生成本地密钥 保证双方拥有同一个本地密钥
        byte[] bytes = Base64.decodeBase64(oppoPublicKey);
        byte[] secretKey = DHUtil.getSecretKey(bytes, puPrKeyPair.get("privateKey"), "AES");
        System.out.println("生成的本地密钥："+Base64.encodeBase64String(secretKey));
        //根据当前服务的本地密钥对Aes的密钥加密
        try {
            byte[] encrypt = DHUtil.encrypt(aesKey.getBytes(), secretKey);
            System.out.println("Aes密钥加密后结果："+Base64.encodeBase64String(encrypt));
            //生成签名 根据原文的密文生成签名
            String signature = EncryptUtil.getInstance().getSignature(aesGcmDecrypt);
            System.out.println("根据原文的密文生成签名:"+signature);
            //报文拼接
            // 密文+使用DH算法加密的Aes密钥+生成的签名
            String askMessage=aesGcmDecrypt+"&"+Base64.encodeBase64String(encrypt)+"&"+signature;
            //请求对方服务获取token
            String returnMessage = HttpRequest.post(url).header("message", askMessage)
                    .header("sysTem","0000").header("service",appId).execute().body();
            Map<String, String> stringStringMap = JSON.parseObject(returnMessage,
                    new TypeReference<Map<String, String>>() {
                    });
            String tokenMessage = stringStringMap.get("tokenMessage");
            System.out.println("请求响应数据:"+returnMessage);
            //处理结果得到token
            String[] split = tokenMessage.split("&");
            boolean b = EncryptUtil.getInstance().verifySignature(split[0], split[1]);
      //      boolean b = EncryptUtil.getInstance().verifySignature(split[0], split[1]);
            System.out.println("验签结果查看签名是否更改:"+b);
            if(b){
                //使用DH解密 得到加密的token
                byte[] bytes1 = Base64.decodeBase64(split[0]);
                byte[] tokenEncrypt = DHUtil.decrypt(bytes1, secretKey);

                String access_token=new String(tokenEncrypt);
                System.out.println("系统得到的access_token:"+access_token);
                return access_token;
            }else{
                return "签名已被篡改";
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("信息加密失败");
            return "加密过程失败";
        }

    }

    /**
     * 具体生成token的方法
     * message: 请求的报文信息
     * puPrKeyPair: 服务的秘钥对
     * oppoPublicKey: 对方的密钥
     * @return
     */
    public Map generateToken(String message,Map<String,byte[]>puPrKeyPair,String oppoPublicKey){
        Map<String,Object>map=new ConcurrentHashMap<>();
        String[] split = message.split("&");
        boolean b = EncryptUtil.getInstance().verifySignature(split[0], split[2]);
        System.out.println("系统验签结果:"+b);
        //对方公钥与自己的私钥生成本地的密钥对
        byte[] secretKey = DHUtil.getSecretKey(Base64.decodeBase64(oppoPublicKey), puPrKeyPair.get("privateKey"), "AES");
        try {
            byte[] decrypt = DHUtil.decrypt(Base64.decodeBase64(split[1]), secretKey);
            String s=new String(decrypt);
            System.out.println("系统解密后的AES密钥:"+s);
            //AES解密报文
            String gcmDecrypt = AESUtil.aesGcmDecrypt(split[0], s);
            System.out.println("系统解密结果:"+gcmDecrypt);
            //生成token
            String access_token = SHAUtil.getSHA256StrJava(gcmDecrypt);
            System.out.println("当前系统生成的access_token:"+access_token);
            //根据DH算法使用本地密钥加密
            byte[] encrypt = DHUtil.encrypt(access_token.getBytes(), secretKey);
            //使用Base64.encodeBase64String编码 解密是先使用Base64.decodeBase64()转码
            String encodeBase64String = Base64.encodeBase64String(encrypt);

            System.out.println("token经过本地密钥加密结果:"+encodeBase64String);
           //生成签名
            String signature = EncryptUtil.getInstance().getSignature(encodeBase64String);

            map.put("access_token",access_token);
            map.put("tokenMessage",encodeBase64String+"&"+signature);
            return map;

        } catch (Exception e) {
            e.printStackTrace();
            map.put("err","生成token失败");
            return map;
        }

    }

    public static void main(String[] args) {
        String appId = "1";
        String app_secret ="23";
        String tenantId ="45";
        long app_time = 8888;
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(appId).append("&").append(app_secret).append("&").append(tenantId).append("&").append(app_time);
        System.out.println(stringBuffer);
    }

}
