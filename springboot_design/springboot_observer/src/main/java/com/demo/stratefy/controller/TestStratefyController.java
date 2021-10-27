package com.demo.stratefy.controller;

import com.demo.stratefy.service.FileTypeResolveEnum;
import com.demo.stratefy.service.StrategyUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lkz
 * @date 2021/10/27 9:30
 * @description
 */
@RestController
public class TestStratefyController {

    @Autowired
    StrategyUseService service;

    @GetMapping("testStratefy")
    public void test(){
        service.resolveFile(FileTypeResolveEnum.File_DEFAULT_RESOLVE
        ,"这是默认的数据");
    }

}
