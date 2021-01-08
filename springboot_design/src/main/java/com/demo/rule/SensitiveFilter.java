package com.demo.rule;

import com.demo.domian.Work;
import com.demo.filter.Filter2;
import com.demo.filter.FilterChain2;

public class SensitiveFilter  implements Filter2 {
    @Override
    public void doFilter(Work work, FilterChain2 chain2) {
        System.out.println("SenstiveFilter");
        chain2.doFilter(work,chain2);
    }
}
