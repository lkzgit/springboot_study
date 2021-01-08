package com.demo;

import com.demo.domian.Work;
import com.demo.filter.Filter2;
import com.demo.filter.FilterChain2;
import com.demo.rule.*;

public class Test {


    @org.junit.Test
    public void test(){
        Work w=new Work();
        w.setId(1);
        w.setName("测试");

        FilterChain2 ww=new FilterChain2();
        ww.addFilter(new HtmlFilter());
        ww.addFilter(new SensitiveFilter());
        ww.addFilter(new FaceFilter());

        ww.doFilter(w,ww);

        System.out.println(w);
    }

}
