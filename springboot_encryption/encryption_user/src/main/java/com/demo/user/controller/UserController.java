package com.demo.user.controller;

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
@RequestMapping("user")
public class UserController {

    @Autowired
    RedisService redisService;

    @GetMapping("getPublicKey")
    public String testHttp(){
        Map<String, byte[]> user = redisService.getCacheMap("user");
        //自己的公钥
        String publicKey1 = Base64.encodeBase64String(user.get("publicKey"));
        //对方的公钥
        String oppoPublicKey = HttpRequest.get("localhost:1112/returnPublicKey").header("publicKey", publicKey1).execute().body();
//        byte[] secretKey = DHUtil.getSecretKey(Base64.decodeBase64(oppoPublicKey), user.get("privateKey"), "AES");
//        redisService.setCacheObject("userBDGet",Base64.encodeBase64String(secretKey));
        //对方的公钥保存起来
        redisService.setCacheObject("user:consumerPublicKey",oppoPublicKey);
        return "user---->"+oppoPublicKey;
    }

    @GetMapping("returnPublicKey")
    public String test(HttpServletRequest request){
        //取出所带的公钥 生成自己的本地密钥
        String oppoPublicKey = request.getHeader("publicKey");
        System.out.println("对方的公钥"+oppoPublicKey);
        Map<String, byte[]> publicKey = redisService.getCacheMap("user");
        //根据对方公钥和自己的私钥 生成双方发本地密钥对
//        byte[] secretKey = DHUtil.getSecretKey(Base64.decodeBase64(oppoPublicKey), publicKey.get("privateKey"), "AES");
//        redisService.setCacheObject("userBDReturn",Base64.encodeBase64String(secretKey));
        // 返回给对方自己的公钥
        return Base64.encodeBase64String(publicKey.get("publicKey"));
    }

    //密文方式返回
    @PostMapping("getToken")
    public String getToken(HttpServletRequest request) throws Exception {
        String userAesMIYao= AESUtil.generateAESKey();
        String appId="user";
        String app_secret="user:app_secret";
        String tenant_id="user_110";
        long apply_time=System.currentTimeMillis();
        String con=app_secret+"&"+appId+"&"+tenant_id+"&"+apply_time;
        System.out.println("user系统--miyao拼接：--"+con);
        String ConEncrypt = AESUtil.aesGcmEncrypt(con, userAesMIYao);
        System.out.println("user系统明文加密结果密文:"+ConEncrypt);
        Map<String, byte[]> userKey = redisService.getCacheMap("user");
        String publicKey = redisService.getCacheObject("user:consumerPublicKey");
        byte[] bytes = Base64.decodeBase64(publicKey);
        byte[] secretKey = DHUtil.getSecretKey(bytes, userKey.get("privateKey"), "AES");
        //使用A的密钥对Aes密钥进行加密
        byte[] UserEsencrypt = DHUtil.encrypt(userAesMIYao.getBytes(), secretKey);
        System.out.println("User系统---对Aes密钥加密后的结果:"+Base64.encodeBase64String(UserEsencrypt));
        //签名
        String Asignature = EncryptUtil.getInstance().getSignature(ConEncrypt);
        System.out.println("User系统---对明文文本加密结果签名:" + Asignature + "\n");
        //报文拼接
        // 密文 +使用DH算法加密的Aes密钥+生成的签名
        String ConsumerBestText= ConEncrypt+"&"+ Base64.encodeBase64String(UserEsencrypt)+"&"+Asignature;
        System.out.println("User最终生成报文:"+ConsumerBestText);

        //请求Consumer服务 获取token
        String message = HttpRequest.post("localhost:1112/generateToken").header("message", ConsumerBestText).execute().body();
        String[] split = message.split("&");
        boolean b = EncryptUtil.getInstance().verifySignature(split[0], split[1]);
        System.out.println("验签结果："+b);
        System.out.println("user系统得到的Token值："+split[0]);

        //获取返回的密文
//        request.setAttribute("message",ConsumerBestText);
//        String text = generateToken(request);
//        String[] split1 = text.split("&");
//        boolean b1 = EncryptUtil.getInstance().verifySignature(split1[0], split1[1]);
//        System.out.println("User系统获取的token:--"+split1[0]);
        return split[0];
    }

    @GetMapping("generateToken")
    public String generateToken(HttpServletRequest request) throws Exception {
        String message = request.getHeader("message");
        String[] split = message.split("&");

        boolean b = EncryptUtil.getInstance().verifySignature(split[0], split[2]);
        System.out.println("User系统验签:"+b);
        //DH解密AES密钥
        //获取自己的本地密钥
        String oppoPublicKey = redisService.getCacheObject("user:consumerPublicKey");
        Map<String, byte[]> consumer = redisService.getCacheMap("user");
        byte[] secretKey = DHUtil.getSecretKey(Base64.decodeBase64(oppoPublicKey), consumer.get("privateKey"), "AES");
        byte[] decrypt = DHUtil.decrypt(Base64.decodeBase64(split[1]), secretKey);
        String s = new String(decrypt);
        System.out.println("User系统--解密后AES密钥："+s);
        //AES解密报文
        String decryptResult = AESUtil.aesGcmDecrypt(split[0], s);
        System.out.println("User系统---解密结果："+decryptResult);

        String acctoken="123453343";
        //根据appId,app_secret,teanantId,当前时间戳 使用sha256算法生成access_token,并保存
        String sha256StrJava = SHAUtil.getSHA256StrJava(acctoken);
        System.out.println("B系统生成的token:--"+sha256StrJava);
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
