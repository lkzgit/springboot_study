package com.easyexcle.demo.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.util.List;

public class DemoTest {

    /**
     * 糊涂工具包测试 导入
     * @param args
     */
    public static void main(String[] args) {

        ExcelReader reader = ExcelUtil.getReader("D://moban.xlsx");
        List<List<Object>> readAll = reader.read();
       for(List tt:readAll){
           System.out.println(tt);
       }
    }
}
