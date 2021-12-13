package com.demo.consumer.controller;

import cn.hutool.http.HttpRequest;
import com.demo.token.config.RedisService;
import com.demo.token.util.AESUtil;
import com.demo.token.util.DHUtil;
import com.demo.token.util.EncryptUtil;
import com.demo.token.util.SHAUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lkz
 * @date 2021/12/4 10:58
 * @description
 */
@RestController
@RequestMapping("con")
public class ComsumerController {

    @Autowired
    RedisService redisService;

    @GetMapping("test")
    public String test(){
        return "jjj";
    }

    @GetMapping("test2")
    public String test2(){
        return "jjj222222";
    }

    /**
     * 获取公钥
     * @return
     */
    @PostMapping("getPublicKey")
    public String getPublicKey(){

        Map<String, byte[]> consumer = redisService.getCacheMap("consumer");
        //自己的公钥
        String publicKey1 = Base64.encodeBase64String(consumer.get("publicKey"));
        //对方的公钥
        String oppoPublicKey = HttpRequest.get("localhost:1113/returnPublicKey").header("publicKey", publicKey1).execute().body();
//        byte[] secretKey = DHUtil.getSecretKey(Base64.decodeBase64(oppoPublicKey), consumer.get("privateKey"), "AES");
//        redisService.setCacheObject("consumerBDGet",Base64.encodeBase64String(secretKey));
        //对方的公钥保存起来
        redisService.setCacheObject("consumer:userPublicKey",oppoPublicKey);
        return "user---->"+oppoPublicKey;

    }

    @GetMapping("returnPublicKey")
    public String test(HttpServletRequest request){
        //取出所带的公钥 生成自己的本地密钥
        String oppoPublicKey = request.getHeader("publicKey");
        redisService.setCacheObject("consumer:userPublicKey",oppoPublicKey);
        System.out.println("对方的公钥"+oppoPublicKey);
        Map<String, byte[]> publicKey = redisService.getCacheMap("consumer");
        //根据对方公钥和自己的私钥 生成双方发本地密钥对
//        byte[] secretKey = DHUtil.getSecretKey(Base64.decodeBase64(oppoPublicKey), publicKey.get("privateKey"), "AES");
//        redisService.setCacheObject("consumerBDReturn",Base64.encodeBase64String(secretKey));
        // 返回给对方自己的公钥
        return Base64.encodeBase64String(publicKey.get("publicKey"));
    }

    //密文方式返回
    @PostMapping("getToken")
    public String getToken(HttpServletRequest request) throws Exception {
        String consumerAesMIYao= AESUtil.generateAESKey();
        String appId="consumer";
        String app_secret="consumer:app_secret";
        String tenant_id="110Consumer";
        long apply_time=System.currentTimeMillis();
        String con=app_secret+"&"+appId+"&"+tenant_id+"&"+apply_time;
        System.out.println("Consumer系统--miyao拼接：--"+con);
        String ConEncrypt = AESUtil.aesGcmEncrypt(con, consumerAesMIYao);
        System.out.println("Consumer系统明文加密结果密文:"+ConEncrypt);
        Map<String, byte[]> consumer = redisService.getCacheMap("consumer");
        String publicKey = redisService.getCacheObject("consumer:userPublicKey");
        byte[] bytes = Base64.decodeBase64(publicKey);
        byte[] secretKey = DHUtil.getSecretKey(bytes, consumer.get("privateKey"), "AES");
        //使用A的密钥对Aes密钥进行加密
        byte[] Aesencrypt = DHUtil.encrypt(consumerAesMIYao.getBytes(), secretKey);
        System.out.println("Consumer系统---对Aes密钥加密后的结果:"+Base64.encodeBase64String(Aesencrypt));
        //签名
        String Asignature = EncryptUtil.getInstance().getSignature(ConEncrypt);
        System.out.println("Consumer系统---对明文文本加密结果签名:" + Asignature + "\n");
        //报文拼接
        // 密文 +使用DH算法加密的Aes密钥+生成的签名
        String ConsumerBestText= ConEncrypt+"&"+ Base64.encodeBase64String(Aesencrypt)+"&"+Asignature;
        System.out.println("Consumer最终生成报文:"+ConsumerBestText);

        //获取返回的密文
        String message = HttpRequest.post("localhost:1113/generateToken").header("message", ConsumerBestText).execute().body();
        String[] split = message.split("&");
        boolean b = EncryptUtil.getInstance().verifySignature(split[0], split[1]);
        System.out.println("验签结果："+b);
        System.out.println("user系统得到的Token值："+split[0]);
        return ConsumerBestText;
    }

    @PostMapping("generateToken")
    public String generateToken(HttpServletRequest request) throws Exception {
        String message = request.getHeader("message");
        String[] split = message.split("&");

        boolean b = EncryptUtil.getInstance().verifySignature(split[0], split[2]);
        System.out.println("Conusmer系统验签:"+b);
        //DH解密AES密钥
        //获取自己的本地密钥
        String oppoPublicKey = redisService.getCacheObject("consumer:userPublicKey");
        Map<String, byte[]> consumer = redisService.getCacheMap("consumer");
        byte[] secretKey = DHUtil.getSecretKey(Base64.decodeBase64(oppoPublicKey), consumer.get("privateKey"), "AES");
        byte[] decrypt = DHUtil.decrypt(Base64.decodeBase64(split[1]), secretKey);
        String s = new String(decrypt);
        System.out.println("Consumer系统--解密后AES密钥："+s);
        //AES解密报文
        String decryptResult = AESUtil.aesGcmDecrypt(split[0], s);
        System.out.println("Consumer系统---解密结果："+decryptResult);

        String acctoken="123453343";
        //根据appId,app_secret,teanantId,当前时间戳 使用sha256算法生成access_token,并保存
        String sha256StrJava = SHAUtil.getSHA256StrJava(acctoken);
        System.out.println("Consumer系统生成的token--:"+sha256StrJava);
        //生成签名
        String signatureBack = EncryptUtil.getInstance().getSignature(sha256StrJava);
        // 拼接
        String text=sha256StrJava+"&"+signatureBack;
        redisService.setCacheObject("text",text);
        return text;
    }

    @PostMapping("验证token")
    public String checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        //截取
        String[] split = token.split("&");
        //验签
        boolean b1 = EncryptUtil.getInstance().verifySignature(split[0], split[1]);

        System.out.println("系统获取的token:--"+split[0]);


        return "0000";
    }

}
