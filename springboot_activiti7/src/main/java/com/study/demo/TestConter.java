package com.study.demo;

import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lkz
 * @ClassName: TestConter
 * @description: TODO
 * @date 2022/3/3 17:01
 */
public class TestConter {



    @GetMapping("test1")
    public String test(){
        return "om";

    }
}
