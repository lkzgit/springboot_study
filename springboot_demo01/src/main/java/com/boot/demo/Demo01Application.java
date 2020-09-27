package com.boot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Demo01Application {
    public static void main(String[] args) {
        SpringApplication.run(Demo01Application.class,args);
    }
}
