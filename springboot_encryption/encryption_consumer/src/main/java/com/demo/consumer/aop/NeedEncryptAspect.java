package com.demo.consumer.aop;

import com.demo.consumer.annotation.NeedEncryptField;
import com.demo.token.constant.AesInfoService;
import com.demo.token.util.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName NeedEncryptAspect.java
 * @Description TODO
 * @createTime 2021年12月12日 00:09:00
 */
@Component
@Aspect
@Slf4j
public class NeedEncryptAspect {


    @Pointcut("@annotation(com.demo.consumer.annotation.NeedEncryptMethod)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //加密
        encrypt(joinPoint);

        return joinPoint.proceed();
    }

    public void encrypt(ProceedingJoinPoint joinPoint)  {
        Object[] objects=null;
        try {
            objects = joinPoint.getArgs();
            if (objects.length != 0) {
                for (int i = 0; i < objects.length; i++) {
                    //抛砖引玉 ，可自行扩展其他类型字段的判断
                    if (objects[i] instanceof String) {
                        objects[i] = encryptValue(objects[i]);
                    } else {
                        encryptObject(objects[i]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密对象
     * @param obj
     * @throws IllegalAccessException
     */
    private void encryptObject(Object obj) throws IllegalAccessException {

        if (Objects.isNull(obj)) {
            log.info("当前需要加密的object为null");
            return;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean containEncryptField = field.isAnnotationPresent(NeedEncryptField.class);
            if (containEncryptField) {
                //获取访问权
                field.setAccessible(true);
                String value = AESUtil.aesGcmEncrypt(String.valueOf(field.get(obj)), AesInfoService.AesKey);
                field.set(obj, value);
            }
        }
    }

    /**
     * 加密单个值
     * @param realValue
     * @return
     */
    public String encryptValue(Object realValue) {
        try {
            realValue = AESUtil.aesGcmDecrypt(String.valueOf(realValue),AesInfoService.AesKey);
        } catch (Exception e) {
            log.info("加密异常={}",e.getMessage());
        }
        return String.valueOf(realValue);
    }



}
