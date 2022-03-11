package com.demo;

import com.demo.flowable.FlowableOneApplication;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lkz
 * @ClassName: Test
 * @description: TODO
 * @date 2022/3/10 9:15
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowableOneApplication.class)
public class Test {

    @Autowired
    StringEncryptor stringEncryptor;

    @org.junit.Test
    public void test(){
        String str="119.29.120.60";
        String str2="123456";
        System.out.println("加密数据:"+stringEncryptor.encrypt(str));
        System.out.println("加密数据2:"+stringEncryptor.encrypt(str2));

    }
}
