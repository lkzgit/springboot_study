package com.demo.token.controller;

import com.demo.token.config.RedisService;
import com.demo.token.util.AESUtil;
import com.demo.token.util.DHUtil;
import com.demo.token.util.EncryptUtil;
import com.demo.token.util.SHAUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lkz
 * @date 2021/12/2 16:54
 * @description
 */
@RestController
public class TokenController {

    @Autowired
    RedisService redisService;

    @GetMapping("test")
    public String test(){
        return "token------";
    }

    @GetMapping("getDHEncrypt")
    public String getDHEncrypt(HttpServletRequest request){
        Map<String, byte[]> token = redisService.getCacheMap("token");

        byte[] privateKeys = token.get("privateKey");
        byte[] publicKey = token.get("publicKey");
        System.out.println("publicKey:--"+Base64.encodeBase64String(publicKey));
        System.out.println("privateKeys:--"+Base64.encodeBase64String(privateKeys));
        return "ok";
    }

    @GetMapping("getToken")
    public String getToken() throws Exception {
        //AEs密钥
        System.out.println("-----------");
        String AtokenAesMiyao=AESUtil.generateAESKey();
        String BtokenAesMiyao=AESUtil.generateAESKey();
        System.out.println("A--Aes密钥:"+AtokenAesMiyao);
        System.out.println("B--Aes密钥:"+BtokenAesMiyao);
        System.out.println("-----------");

       String appId="Atoken";
       String app_secret="Atoken:app_secret";
       String tenant_id="A1";
       long apply_time=System.currentTimeMillis();
       String Amiyao=app_secret+"&"+appId+"&"+tenant_id+"&"+apply_time;
        System.out.println("A系统--miyao拼接：--"+Amiyao);
        String Aencrypt = AESUtil.aesGcmEncrypt(Amiyao, AtokenAesMiyao);
        System.out.println("A系统明文加密结果密文:"+Aencrypt);

        String BappId="Btoken";
        String Bapp_secret="Btoken:app_secret";
        String Btenant_id="B1";
        long Bapply_time=System.currentTimeMillis();
        String Bmiyao=Bapp_secret+"&"+BappId+"&"+Btenant_id+"&"+Bapply_time;
        System.out.println("B系统--miyao拼接：--"+Bmiyao);
        String Bencrypt = AESUtil.aesGcmEncrypt(Bmiyao, BtokenAesMiyao);
        System.out.println("B系统明文加密结果密文:"+Bencrypt);
        Map<String, byte[]> token1 = redisService.getCacheMap("token1");

        Map<String, byte[]> token2 = redisService.getCacheMap("token2");

        System.out.println("生产token1的密钥对");
        //根据A的私钥 和B的公钥生成A的本地密钥
        byte[] AsecretKey = DHUtil.getSecretKey(token2.get("publicKey"), token1.get("privateKey"), "AES");
        System.out.println("A的本地密钥:"+Base64.encodeBase64String(AsecretKey));
        System.out.println("生产token2的密钥对");
        //根据B的私钥和 A的公钥 生成B的密钥
        byte[] BsecretKey = DHUtil.getSecretKey(token1.get("publicKey"), token2.get("privateKey"), "AES");
        System.out.println("B的本地密钥:"+Base64.encodeBase64String(BsecretKey));
       //使用A的密钥对Aes密钥进行加密
        byte[] Aesencrypt = DHUtil.encrypt(AtokenAesMiyao.getBytes(), AsecretKey);
        System.out.println("A系统---对Aes密钥加密后的结果:"+Base64.encodeBase64String(Aesencrypt));
        //使用B的密钥对Aes密钥进行加密
        byte[] Besencrypt = DHUtil.encrypt(BtokenAesMiyao.getBytes(), BsecretKey);
        System.out.println("B系统---对Aes密钥加密后的结果:"+Base64.encodeBase64String(Besencrypt));
        //签名
        String Asignature = EncryptUtil.getInstance().getSignature(Aencrypt);
        System.out.println("A系统---对明文文本加密结果签名:" + Asignature + "\n");
        String Bsignature = EncryptUtil.getInstance().getSignature(Bencrypt);
        System.out.println("B系统---对明文文本加密结果签名:" + Bsignature + "\n");
        //报文拼接
        // 密文 +使用DH算法加密的Aes密钥+生成的签名
       String ABestText= Aencrypt+"&"+ Base64.encodeBase64String(Aesencrypt)+"&"+Asignature;
        System.out.println("a最终生成报文:"+ABestText);
        String BBestText= Bencrypt+"&"+ Base64.encodeBase64String(Besencrypt)+"&"+Bsignature;
        System.out.println("b最终生成报文:"+BBestText);

        System.out.println("开始生成token-----");
        //B系统开始生成token
        String[] split = ABestText.split("&");
        boolean b = EncryptUtil.getInstance().verifySignature(split[0], split[2]);
        System.out.println("B系统验签:"+b);
        //DH解密AES密钥
        //根据B本地的密钥对
        byte[] decrypt = DHUtil.decrypt(Base64.decodeBase64(split[1]), BsecretKey);
        String s = new String(decrypt);
        System.out.println("B系统--解密后AES密钥："+s);
        //AES解密报文
        String decryptResult = AESUtil.aesGcmDecrypt(split[0], s);
        System.out.println("B系统---解密结果："+decryptResult);

        String acctoken="123453343";
        //根据appId,app_secret,teanantId,当前时间戳 使用sha256算法生成access_token,并保存
        String sha256StrJava = SHAUtil.getSHA256StrJava(acctoken);
        System.out.println("B系统生成的token:--"+sha256StrJava);
        //生成签名
        String signatureBack = EncryptUtil.getInstance().getSignature(sha256StrJava);
        // 拼接
        String text=sha256StrJava+"&"+signatureBack;
         // A系统截取
        String[] split1 = text.split("&");
        boolean b1 = EncryptUtil.getInstance().verifySignature(split1[0], split1[1]);
        System.out.println("A系统获取的token:--"+split1[0]);


//        //access_token 加密
//        String secreatTextBack = EncryptUtil.getInstance().encryptDH(sha256StrJava,BsecretKey);
//
//        System.out.println("B系统生成的token经过DH加密：--"+secreatTextBack);
//
//        //生成签名返回A系统
//        String signatureBack = EncryptUtil.getInstance().getSignature(secreatTextBack);
//
//        //拼接成报文返回
//        String text=secreatTextBack+"&"+signatureBack;
//
//        System.out.println("B系统生成token拼接报文返回:"+text);
//
//        System.out.println("A系统获取报文开始——---------");
//        //截取报文
//        String[] splitA = text.split("&");
//
//        //验签
//        boolean b1A = EncryptUtil.getInstance().verifySignature(splitA[0], splitA[1]);
//        System.out.println("A系统收到签名验签结果:"+b1A);
//
//        String s1A = EncryptUtil.getInstance().decryptDH(splitA[0],BsecretKey);
//        System.out.println("A系统Aes解密获取token:"+s1A);

        return "ok";
    }

    @GetMapping("checkToken")
    public String checkToken(@RequestParam("token")String token){
        String btoken = (String)redisService.getCacheObject("Btoken");
        boolean verify = SHAUtil.verify(token, btoken);
        System.out.println(verify);
        return btoken;
    }

}
