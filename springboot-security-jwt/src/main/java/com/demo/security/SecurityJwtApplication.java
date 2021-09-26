package com.demo.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lkz
 * @date 2021/9/26 9:37
 * @description
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.demo.security.mapper"})
public class SecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityJwtApplication.class,args);
    }
}
