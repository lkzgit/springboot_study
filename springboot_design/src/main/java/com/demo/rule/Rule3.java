package com.demo.rule;

import com.demo.annotation.EnableFilter;
import com.demo.domian.Alarm;
import com.demo.filter.Filter;
import com.demo.filter.FilterChain;

@EnableFilter
public class Rule3 implements Filter {
    @Override
    public void execute(Alarm alarm, FilterChain chain) {


        if(alarm.getDesc().contains("割接")){
            alarm.setAlarmType(5);
            System.out.println("执行规则3");
        }
        chain.doFilter(alarm,chain);
    }
}
