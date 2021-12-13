package com.demo.token.util;

import com.demo.token.config.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lkz
 * @date 2021/12/10 16:18
 * @description
 */
@Component
@Slf4j
public class AskTokenUtil {

        private static final ThreadLocal<Map<String,byte[]>> threadLocal=new ThreadLocal<>();

        private static  AskTokenUtil  askTokenUtils=new AskTokenUtil();

        private static final String rbacUrl = "http://rbac.tshlms.com:10210";

        private static final String publicKeyUrl = "/hlms/api/rbac/security/getDHPublicKey";

        private static final String getTokenUrl = "/hlms/api/rbac/security/getToken";

        private static final String aesEncryptorPassword = "fbwMRN1oj1WxPjcSRX+cdw==";

        private static final String suiteId = "10210";

        private static final String appSecret = "1563a9b2048c84db76a146ba3af07c521d53000c";



        private static final Lock lock = new ReentrantLock();


        @Autowired
        private RedisService redisService;

        @PostConstruct
        public void init(){
            askTokenUtils.redisService=redisService;
        }

        private AskTokenUtil() {
        }

        /**
         * 取得单例实现
         *
         * @return
         */
        public static AskTokenUtil getInstance() {
            return askTokenUtils;
        }

        public String getRedis(){
            System.out.println("nij");
            askTokenUtils.redisService.setCacheObject("key",273388);
            return "nij";
        }


//        //获取token 并放入headers
//        public  HttpHeaders getHeaderToken(String suiteId,String tenantId,String appSecret) {
//            String redisKey = RedisKeyGenUtils.key(suiteId, tenantId);
//            String accessToken = redisService.getCacheObject(redisKey);
//            HttpHeaders httpHeaders = AskTokenUtils.getHeaders();
//
//            try{
//                if(lock.tryLock(5, TimeUnit.SECONDS)){
//                    if(StrUtil.isEmptyOrUndefined(accessToken)){
//                        Result resultToken = AskTokenUtils.getPublicKey(suiteId, appSecret, tenantId, publicKeyUrl, getTokenUrl, rbacUrl, aesEncryptorPassword);
//                        String errcode = resultToken.getErrcode();
//                        if(errcode.equals("0")){
//                            Map<String,String> data = (Map<String, String>) resultToken.getData();
//                            accessToken = data.get("accessToken");
//                            String expiresIn = data.get("expiresIn");
//                            httpHeaders.set("access_token",accessToken);
//                            askTokenUtils.redisService.setCacheObject(redisKey,accessToken,Long.parseLong(expiresIn), TimeUnit.SECONDS);
//                        }else if(errcode.equals("20085")|| errcode.equals("200103")
//                                ||errcode.equals("200102")||errcode.equals("200803")){
//                            //重新获取token
//                            Result publicKey = AskTokenUtils.getPublicKey(suiteId, appSecret, tenantId, publicKeyUrl, getTokenUrl, rbacUrl, aesEncryptorPassword);
//                            Map<String,String> data=(Map<String, String>) publicKey.getData();
//                            String token = data.get("accessToken");
//                            String time = data.get("expiresIn");
//                            httpHeaders.set("access_token",accessToken);
//
//                            askTokenUtils.redisService.setCacheObject(redisKey,token,Long.parseLong(time), TimeUnit.SECONDS);
//                        }else{
//                            throw new Exception();
//                        }
//                    }else{
//                        httpHeaders.set("access_token",accessToken);
//                    }
//                }
//            }catch (Exception e){
//                log.info("获取token失败:{}",e.getMessage());
//            }finally {
//                lock.unlock();
//            }
//            return httpHeaders;
//
//
//        }
//
//        /**
//         * 获取token
//         * @param suiteId suitId 唯一标识
//         * @param appSecret 线下约定appSecret
//         * @param tenantId 租户Id
//         * @param publicKeyUrl 请求rbc公钥的请求地址
//         * @param genTokenUrl 请求rbc获取token的地址
//         * @param hlmsRbcUrl rbc服务地址
//         * @param aesEncryptorPassword aes密钥
//         * @return
//         */
//        public static Result getPublicKey(String suiteId, String appSecret, String tenantId, String publicKeyUrl,
//                                          String genTokenUrl, String hlmsRbcUrl, String aesEncryptorPassword) {
//
//            Map<String, byte[]> key = DHUtil.getKey();// 本地的公私密钥
//            threadLocal.set(key);
//            String publicKey = Base64.encodeBase64String(key.get("publicKey"));
//            System.out.println("本地的公私密钥---:"+publicKey);
//            //拿到header信息
//            HttpHeaders requestHeaders = new HttpHeaders();
//            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//            requestHeaders.set("suiteId", suiteId);
//            requestHeaders.set("tenantId", tenantId);
//            requestHeaders.set("publicKey", publicKey);
//            //得到对方的公钥
//            String  oppoPublicKey="";
//            try {
//                oppoPublicKey = HttpRequest.get(hlmsRbcUrl + publicKeyUrl).header("suiteId", suiteId).
//                        header("tenantId", tenantId).header("publicKey", publicKey).execute().body();
//
//            }catch (Exception e){
//                log.info("获取公钥失败信息:",e.getMessage());
//                return ResultUtil.error(AesEnum.AES_ACCESS_PUBLICKEY.getCode(), AesEnum.AES_ACCESS_PUBLICKEY.getMsg());
//            }
//
//            if(StrUtil.isEmptyOrUndefined(oppoPublicKey)){
//                System.out.println("oppoPublicKey---获取公钥失败:");
//                return ResultUtil.error(AesEnum.AES_ACCESS_PUBLICKEY.getCode(),AesEnum.AES_ACCESS_PUBLICKEY.getMsg());
//            }
//
//            Map<String, String> stringStringMap = JSON.parseObject(oppoPublicKey,
//                    new TypeReference<Map<String, String>>() {
//                    });
//            String code= stringStringMap.get("errcode");
//            if(!code.equals("0")){
//                return ResultUtil.error(AesEnum.AES_ACCESS_PUBLICKEY.getCode(),AesEnum.AES_ACCESS_PUBLICKEY.getMsg());
//            };
//
//            String data = stringStringMap.get("data");
//            Map<String,String> pubKeyMap= JSON.parseObject(data,
//                    new TypeReference<Map<String,String>>() {
//                    });
//            String oppoKey= pubKeyMap.get("publicKey");
//            System.out.println("成功获取对方公钥---:"+oppoKey);
//            //根据对方的公钥是自己的私钥生成本地密钥
//            byte[] bytes = Base64.decodeBase64(oppoKey);
//            //生成自己的本地密钥
//            byte[] secretKey = DHUtil.getSecretKey(bytes, key.get("privateKey"), "AES");
//            System.out.println("生成和对方服务相同的本地密钥:" + Base64.encodeBase64String(secretKey));
//            //得到公钥加密信息
//            //获取Aes密钥
//            String str = "suiteId=" + suiteId + "&appSecret=" + appSecret + "&tenantId=" + tenantId + "&applyTime=" + System.currentTimeMillis();
//            //明文通过Aes加密
//            String aesGcmEncrypt = AESUtil.aesGcmEncrypt(str, aesEncryptorPassword);
//            System.out.println("原文加密结果:" + aesGcmEncrypt);
//            //对Aes密钥进行加密使用DH
//            try {
//                byte[] encrypt = DHUtil.encrypt(aesEncryptorPassword.getBytes(), secretKey);
//                System.out.println("Aes密钥加密后结果:" + encrypt);
//                //生成签名
//                String signature = EncryptUtil.getInstance().getSignature(aesGcmEncrypt);
//                System.out.println("根据明文加密的密文生成的签名:" + signature);
//                //报文拼接
//                //密文+使用DH算法加密的Aes密钥+生成的签名
//                String askMessage = aesGcmEncrypt + "." + Base64.encodeBase64String(encrypt) + "." + signature;
//                System.out.println("Authorization--密文+使用DH算法加密的Aes密钥+生成的签名---:"+askMessage);
//                //请求rbc服务生成token
//                String url=hlmsRbcUrl + genTokenUrl+"?tenantId="+tenantId+"&suiteId="+suiteId;
//                HttpHeaders headers=AskTokenUtils.getHeaders();
//                headers.set("Authorization",askMessage);
//                String returnMessage= RestUtil.getForEntity(url,null,headers,String.class);
//                //String returnMessage2= HttpRequest.get(url).header("Authorization",askMessage).execute().body();
//                if(StrUtil.isEmptyOrUndefined(returnMessage)){
//                    System.out.println("returnMessage为空------获取token失败");
//                    return ResultUtil.error(AesEnum.AES_ACCESS_ERR.getCode(),AesEnum.AES_ACCESS_ERR.getMsg());
//                }
//                System.out.println("请求响应的数据:" + returnMessage);
//                Map<String, String> tokenMap = JSON.parseObject(returnMessage,
//                        new TypeReference<Map<String, String>>() {
//                        });
//                String errcode = tokenMap.get("errcode");
//                if(errcode.equals("0")){
//                    String dataTokenMap=  tokenMap.get("data");
//                    Map<String,String> tokenKey= JSON.parseObject(dataTokenMap,
//                            new TypeReference<Map<String, String>>() {
//                            });
//                    String accessToken = tokenKey.get("accessToken");
//                    String expiresIn = tokenKey.get("expiresIn");
//                    Map<String,String> map=new ConcurrentHashMap<>();
//                    map.put("accessToken",accessToken);
//                    map.put("expiresIn",expiresIn);
//                    return ResultUtil.success(map);
//                }else if(errcode.equals("20085")){
//                    return ResultUtil.error("20085","签名校验失败");
//                }else if(errcode.equals("200103")){
//                    return ResultUtil.error("200103","参数格式不符");
//                }else if(errcode.equals("200102")){
//                    return ResultUtil.error("200102","参数错误");
//                }else if(errcode.equals("200803")){
//                    return ResultUtil.error(tokenMap.get("errcode"),tokenMap.get("errmsg"));
//                }else if(errcode.equals("200804")){
//                    return ResultUtil.error(tokenMap.get("errcode"),tokenMap.get("errmsg"));
//                }else if(errcode.equals("200100")){
//                    return ResultUtil.error(tokenMap.get("errcode"),tokenMap.get("errmsg"));
//                }else{
//                    return ResultUtil.error(AesEnum.AES_ACCESS_ERR.getCode(),AesEnum.AES_ACCESS_ERR.getMsg());
//                }
//
//
//            } catch (Exception e) {
//                log.info("Aes加密失败:", e.getCause());
//
//                return ResultUtil.error(AesEnum.AES_ENCRY.getCode(),AesEnum.AES_ENCRY.getMsg());
//
//            }finally {
//                threadLocal.remove();
//            }
//
//
//        }
//
//        /**
//         * 子系统处理token
//         *
//         * @return header
//         */
//        public static HttpHeaders getHeaders() {
//            HttpHeaders headers = new HttpHeaders();
//            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//            headers.setContentType(type);
//            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//            headers.set("Connection", "Close");
//            headers.add("X-Auth-Token", UUID.randomUUID().toString());
//            return headers;
//        }
//
//        /**
//         * 返回application/json 的请求头（带access_token）
//         * @return
//         */
//        public HttpHeaders getFormHeaders() {
//            //初始化header
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Type", "application/x-www-form-urlencoded");
//            return headers;
//        }
//
//



}

