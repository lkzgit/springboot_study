package com.demo.consumer.aop;//package com.demo.consumer.aop;
//
//import com.demo.consumer.annotation.EncryptField;
//import com.demo.token.util.AESUtil;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.util.Objects;
//
///**
// * @author lkz
// * @version 1.0.0
// * @ClassName EncryptFieldAop.java
// * @Description TODO
// * @createTime 2021年12月09日 00:43:00
// */
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Aspect
//@Component
//public class EncryptFieldAop {
//
//
//
//    Logger log = LoggerFactory.getLogger(this.getClass());
//    @Value("${service.aesSecret}")
//    private String secretKey;
//
//    @Pointcut("@annotation(com.demo.consumer.annotation.EncryptMethod)")
//    public void annotationPointCut() {
//    }
//
//    @Around("annotationPointCut()")
//    public Object around(ProceedingJoinPoint joinPoint) {
//        Object responseObj = null;
//        try {
//            Object requestObj = joinPoint.getArgs()[0];
//            handleEncrypt(requestObj);
//            responseObj = joinPoint.proceed();
//            handleDecrypt(responseObj);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//            log.error("SecureFieldAop处理出现异常{}", e);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//            log.error("SecureFieldAop处理出现异常{}", throwable);
//        }
//        return responseObj;
//    }
//
//    /**
//     * 处理加密
//     *
//     * @param requestObj
//     */
//    private void handleEncrypt(Object requestObj) throws IllegalAccessException {
//        if (Objects.isNull(requestObj)) {
//            return;
//        }
//        Field[] fields = requestObj.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            boolean hasSecureField = field.isAnnotationPresent(EncryptField.class);
//            if (hasSecureField) {
//                field.setAccessible(true);
//                String plaintextValue = (String) field.get(requestObj);
//                String encryptValue = AESUtil.aesGcmEncrypt(plaintextValue, secretKey);
//                field.set(requestObj, encryptValue);
//            }
//        }
//    }
//
//
//    /**
//     * 处理解密
//     *
//     * @param responseObj
//     */
//    private Object handleDecrypt(Object responseObj) throws IllegalAccessException {
//        if (Objects.isNull(responseObj)) {
//            return null;
//        }
//
//        Field[] fields = responseObj.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            boolean hasSecureField = field.isAnnotationPresent(EncryptField.class);
//            if (hasSecureField) {
//                field.setAccessible(true);
//                String encryptValue = (String) field.get(responseObj);
//                String plaintextValue = AESUtil.aesGcmDecrypt(encryptValue, secretKey);
//                field.set(responseObj, plaintextValue);
//            }
//        }
//        return responseObj;
//    }
//
//}
