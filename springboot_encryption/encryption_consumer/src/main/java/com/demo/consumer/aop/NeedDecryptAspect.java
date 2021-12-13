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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName NeedDecryptAspect.java
 * @Description 需要解密的切面
 * @createTime 2021年12月12日 00:40:00
 */
@Component
@Slf4j
@Aspect
public class NeedDecryptAspect {



    @Pointcut("@annotation(com.demo.consumer.annotation.NeedDecryptMethod)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //解密
        Object result = decrypt(joinPoint);
        return result;
    }

    public Object decrypt(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            Object obj = joinPoint.proceed();
            if (obj != null) {
                //可自行扩展其他类型字段的判断
                if (obj instanceof String) {
                    decryptValue(obj);
                } else {
                    result = decryptData(obj);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }
    /*
    解密。 因为查询出来的数据有可能是单个实体，也可能是List
     （其实甚至是Map或者Set，又或者是 分页数据类）
    所以本文将会以 最常用的 单个实体 、 List<实体> 为例子，去做解密。
     */
    private Object decryptData(Object obj) throws IllegalAccessException, NoSuchFieldException {
        Field data = obj.getClass().getDeclaredField("data");
        List<?> ability = getClassAbility(obj);
        if (Objects.isNull(obj)) {
            return null;
        }
        if (obj instanceof ArrayList) {
            decryptList(obj);
        }else if(ability instanceof ArrayList){
            decryptList(ability);
        } else {
            decryptObj(obj);
        }


        return obj;
    }

    /**
     * 针对单个实体类进行 解密
     * @param obj
     * @throws IllegalAccessException
     */
    private void decryptObj(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean hasSecureField = field.isAnnotationPresent(NeedEncryptField.class);
            if (hasSecureField) {
                field.setAccessible(true);
                String realValue = (String) field.get(obj);
                String value = AESUtil.aesGcmDecrypt(realValue,AesInfoService.AesKey);
                field.set(obj, value);
            }
        }
    }

    /**
     * 针对list<实体来> 进行反射、解密
     * @param obj
     * @throws IllegalAccessException
     */
    private void decryptList(Object obj) throws IllegalAccessException {
        List<Object> result = new ArrayList<>();
        if (obj instanceof ArrayList) {
            for (Object o : (List<?>) obj) {
                result.add(o);
            }
        }
        for (Object object : result) {
            decryptObj(object);
        }
    }


    public String decryptValue(Object realValue) {
        try {
            realValue = AESUtil.aesGcmDecrypt(String.valueOf(realValue), AesInfoService.AesKey);
        } catch (Exception e) {
            log.info("解密异常={}", e.getMessage());
        }
        return String.valueOf(realValue);
    }

    //反射获取类的属性名，和值
    private List<?> getClassAbility(Object obj){
        List<?> resultList = new ArrayList<>();
        Class<?> aClass = obj.getClass();
        //得到属性
        Field field=null;
        Field field2=null;
        Field field3=null;
        Field field4=null;

        try {
           field= aClass.getDeclaredField("data");
           field2= aClass.getDeclaredField("status");
           field3= aClass.getDeclaredField("msg");
           field4= aClass.getDeclaredField("total");
           //打开私有访问
            field.setAccessible(true);
            field2.setAccessible(true);
            field3.setAccessible(true);
            field4.setAccessible(true);
            //获取属性

            String name = field.getName();
            String name2 = field2.getName();
            String name3 = field3.getName();
            String name4 = field4.getName();
            try {
                //属性.get(类名) 获取响应的参数值
                resultList =(List) field.get(obj);
                Object o2 = field2.get(obj);
                Object o3 = field3.get(obj);
                Object o4 = field4.get(obj);

                System.out.println(resultList);
                System.out.println(o2);
                System.out.println(o3);
                System.out.println(o4);

                return resultList;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return resultList;
    }


}
