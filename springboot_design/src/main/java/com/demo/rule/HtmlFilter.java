package com.demo.rule;

import com.demo.domian.Work;
import com.demo.filter.Filter2;
import com.demo.filter.FilterChain2;

public class HtmlFilter implements Filter2 {
    @Override
    public void doFilter(Work work, FilterChain2 chain2) {
        System.out.println("执行html");
        chain2.doFilter(work,chain2);
    }
}
