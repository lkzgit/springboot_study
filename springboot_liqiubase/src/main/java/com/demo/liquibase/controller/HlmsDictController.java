package com.demo.liquibase.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.liquibase.bean.TestDTO;
import com.demo.liquibase.entity.HlmsDict;
import com.demo.liquibase.mapper.HlmsDictDao;
import com.demo.liquibase.service.HlmsDictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;
import java.util.List;

/**
 * <p>
 * 系统字典表 前端控制器
 * </p>
 *
 * @author lkz
 * @since 2021-11-10
 */
@RestController
@RequestMapping("/hlmsdict")
public class HlmsDictController {

    @Autowired
    HlmsDictDao hlmsDictDao;
    @Autowired
    HlmsDictService hlmsDictService;

    @GetMapping
    public String test(){
        QueryWrapper<HlmsDict> queryWrapper=new QueryWrapper<>();
//        List<HlmsDict> hlmsDicts = hlmsDictService.list(null);
//        System.out.println("service:"+hlmsDicts.toString());
//        System.out.println("------Mapper-------");
//        List<HlmsDict> hlmsDicts1 = hlmsDictDao.selectList(null);
//        System.out.println("mapper:"+hlmsDicts1.toString());
        //测试自定义对象
        queryWrapper.like("dict_name","ss");
        List<HlmsDict> hlmsDicts = hlmsDictDao.selectList(queryWrapper);
        System.out.println(hlmsDicts.toString());




        return "1";
    }
}
