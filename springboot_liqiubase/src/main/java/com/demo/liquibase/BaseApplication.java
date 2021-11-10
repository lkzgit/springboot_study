package com.demo.liquibase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lkz
 * @date 2021/11/9 18:59
 * @description
 * https://blog.csdn.net/NathanniuBee/article/details/90079840 lqiubase
 * https://wyl.im/archives/77
 * https://blog.csdn.net/weixin_43069862/article/details/107065462
 */
@SpringBootApplication
@MapperScan(basePackages = "com.demo.liquibase.mapper")
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }
}
