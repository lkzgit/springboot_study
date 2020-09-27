package com.boot.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("test")
    public String getTest(){
        return "Hello world!";
    }

    @GetMapping("/api/test")
    public String getTest1(){
        return "Hello filter!";
    }
    @GetMapping("/vi/test")
    public String getTest2(){
        return "Hello Servlet!";
    }
    @GetMapping("/api/test3")
    public String getTest3(){
        return "Hello Intercepter!";
    }
}
