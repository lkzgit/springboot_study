package com.demo.consumer.controller.enDen;//package com.demo.consumer.controller.enDen;
//
//import com.demo.consumer.annotation.EnDnParamter;
//import com.demo.token.util.AESUtil;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.mybatis.logging.Logger;
//import org.mybatis.logging.LoggerFactory;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
///**
// * @author lkz
// * @version 1.0.0
// * @ClassName ResponseEnBodyAdvice.java
// * @Description 返回数据加密 https://www.jianshu.com/p/760b284fc269
// * @createTime 2021年12月08日 23:08:00
// */
//@ControllerAdvice
//@Slf4j
//public class ResponseEnBodyAdvice  implements ResponseBodyAdvice {
//
//    private String aesKey="aesSecret: F6R7V5DfVF24SLk5rWqgjg==";
//
//
//    @Override
//    public boolean supports(MethodParameter methodParameter, Class aClass) {
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
//        boolean encode = false;
//        if (methodParameter.getMethod().isAnnotationPresent(EnDnParamter.class)) {
//            //获取注解配置的包含和去除字段
//            EnDnParamter serializedField = methodParameter.getMethodAnnotation(EnDnParamter.class);
//            //出参是否需要加密
//            encode = serializedField.outEncode();
//        }
//        if (encode) {
//            log.info("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行加密");
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
//                return AESUtil.aesGcmEncrypt(result,aesKey);
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密出现异常："+e.getMessage());
//            }
//        }
//        return body;
//    }
//
//}
