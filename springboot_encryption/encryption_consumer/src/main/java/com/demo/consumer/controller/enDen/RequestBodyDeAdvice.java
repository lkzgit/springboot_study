package com.demo.consumer.controller.enDen;//package com.demo.consumer.controller.enDen;
//
//import cn.hutool.core.util.StrUtil;
//import com.demo.consumer.annotation.EnDnParamter;
//import com.demo.token.util.AESUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.IOUtils;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpInputMessage;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.Type;
//
///**
// * @author lkz
// * @version 1.0.0
// * @ClassName RequestBodyDeAdvice.java
// * @Description TODO
// * @createTime 2021年12月08日 23:20:00
// */
//@ControllerAdvice
//@Slf4j
//public class RequestBodyDeAdvice implements RequestBodyAdvice {
//
//    private String aesKey="F6R7V5DfVF24SLk5rWqgjg==";
//
//    @Override
//    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
//        return true;
//    }
//
//    @Override
//    public Object handleEmptyBody(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
//        return body;
//    }
//
//    @Override
//    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
//        try {
//            boolean encode = false;
//            if (methodParameter.getMethod().isAnnotationPresent(EnDnParamter.class)) {
//                //获取注解配置的包含和去除字段
//                EnDnParamter serializedField = methodParameter.getMethodAnnotation(EnDnParamter.class);
//                //入参是否需要解密
//                encode = serializedField.inDecode();
//            }
//            if (encode) {
//                log.info("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密");
//                return new MyHttpInputMessage(inputMessage);
//            }else{
//                return inputMessage;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密出现异常："+e.getMessage());
//            return inputMessage;
//        }
//    }
//
//    @Override
//    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
//        return body;
//    }
//
//    class MyHttpInputMessage implements HttpInputMessage {
//        private HttpHeaders headers;
//
//        private InputStream body;
//
//        public MyHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
//            this.headers = inputMessage.getHeaders();
//            this.body = IOUtils.toInputStream(AESUtil.aesGcmDecrypt(easpString(IOUtils.toString(inputMessage.getBody(), "UTF-8")),aesKey), "UTF-8");
//        }
//
//        @Override
//        public InputStream getBody() throws IOException {
//            return body;
//        }
//
//        @Override
//        public HttpHeaders getHeaders() {
//            return headers;
//        }
//
//        public String easpString(String requestData){
//            log.info("接受的加密数据:{}",requestData);
//            if(requestData != null && !requestData.equals("")){
//                if(!requestData.startsWith("{\"requestData\":")){
//                    throw new RuntimeException("参数【requestData】缺失异常！");
//                }else{
//                    int closeLen = requestData.length()-2;
//                    int openLen = "{\"requestData\":".length()+1;
//                    return StrUtil.sub(requestData,openLen,closeLen);
//                }
//            }
//            return "";
//        }
//    }
//
//
//}
