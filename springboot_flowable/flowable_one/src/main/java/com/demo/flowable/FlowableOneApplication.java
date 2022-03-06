package com.demo.flowable;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.demo.flowable.mapper")
public class FlowableOneApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(FlowableOneApplication.class, args);
    }
    
}
