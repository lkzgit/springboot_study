package com.model.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoTestController {

    @GetMapping("test")
    public String test(){
        return "ok";
    }
}
