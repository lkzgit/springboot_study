package com.model.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sse")
public class SSEController {


    @GetMapping(value = "/get_data", produces = "text/event-stream;charset=UTF-8")
    public String push() {

        try {
            Thread.sleep(1000);
            //第三方数据源调用
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "data:xdclass 行情" + Math.random() + "\n\n";
    }

}
