package com.demo.consumer.interceptor;//package com.demo.consumer.interceptor;
//
//import cn.hutool.http.HttpRequest;
//import cn.hutool.http.HttpResponse;
//import cn.hutool.http.HttpUtil;
//import com.demo.token.config.RedisService;
//import com.demo.token.util.PublicMethodUtils;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.util.WebUtils;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author lkz
// * @date 2021/12/6 18:29
// * @description token请求拦截器
// */
////@Configuration
//public class AskTokenInterceptor implements HandlerInterceptor {
//
//    @Value("${center.http}")
//    private String http;
//    @Value("${center.returnPublicKey}")
//    private String returnPublicKey;
//    @Value("${center.generateToken}")
//    private String generateToken;
//    @Value("${center.checkToken}")
//    private String checkToken;
//    @Value("${service.appId}")
//    private String appId;
//
//    @Autowired
//    RedisService redisService;
//
//    //请求之前拦截
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String sysTem = request.getHeader("sysTem");
//
//        if(sysTem.equals("sysTem")){//说明是系统间调用
//            String accessToken = request.getHeader("accessToken");
//            System.out.println("系统调用携带的accessToken--:"+accessToken);
//            //当前服务去获取token
//            Map<String, byte[]> user = redisService.getCacheMap("consumerKeyPair");
//            //对方的公钥
//            String publicKeyUrl=http+returnPublicKey;
//            String oppoPublicKey = PublicMethodUtils.getPublicKey(user, publicKeyUrl,appId);
//            //对方的公钥保存起来
//            redisService.setCacheObject("consumer:centerPublicKey",oppoPublicKey);
//            //生成token
//            String userAesSecretKey = redisService.getCacheObject("consumerAesSecretKey");
//            Map<String,Object> serverInfo=new ConcurrentHashMap<>();
//            serverInfo.put("appId","consumer");
//            serverInfo.put("app_secret","consumer:app_secret");
//            serverInfo.put("tenant_id","consumer_110");
//            long apply_time=System.currentTimeMillis();
//            serverInfo.put("apply_time",apply_time);
//            serverInfo.put("aesKey",userAesSecretKey);
//            serverInfo.put("oppoPublicKey",redisService.getCacheObject("consumer:centerPublicKey"));
//            Map<String, byte[]> userKey = redisService.getCacheMap("consumerKeyPair");
//            String tokenUrl=http+generateToken;
//            String access_token = PublicMethodUtils.getToken(userKey, tokenUrl, serverInfo, userAesSecretKey);
//            System.out.println("consumer系统获取的token:"+access_token);
//            String body = HttpRequest.get(http + checkToken).header("access_token", access_token)
//                    .body(accessToken).execute().body();
//            System.out.println(body);
//            if(body.equals("00000")){
//                return true;
//            }else{
//                response.setContentType("text/html;charset=UTF-8");
//                PrintWriter out = response.getWriter();
//                out.println("token验证不通过");
//                return false;
//            }
//        }
//        System.out.println("AskTokenInterceptor-----");
//        return true;
//    }
//    //请求之后拦截
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//    }
//    //
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//    }
//
//    public void falseResult(HttpServletResponse response,Map<String,String> map) throws IOException {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=utf-8");
//        ObjectMapper objectMapper = new ObjectMapper();
//        response.getWriter().println(objectMapper.writeValueAsString(map));
//        return;
//    }
//
//
//
//
//}
