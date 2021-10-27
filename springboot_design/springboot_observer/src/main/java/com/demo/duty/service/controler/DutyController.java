package com.demo.duty.service.controler;

import com.demo.duty.service.AbstractHandler;
import com.demo.duty.service.ChainPatternDemo;
import com.demo.duty.service.impl.CheckParamFilterObject;
import com.demo.duty.service.impl.CheckSecurityFilterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lkz
 * @date 2021/10/27 10:04
 * @description
 */
@RestController
public class DutyController {

    @Autowired
    ChainPatternDemo chainPatternDemo;

    @GetMapping("duty")
    public void duty(){

    }
}
