package com.demo.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticsearchApplication {


    /**
     * 官网地址： https://www.elastic.co/cn/downloads/?elektra=home&storm=hero
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchApplication.class,args);
    }
}
