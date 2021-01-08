package com.demo.rule;

import com.demo.annotation.EnableFilter;
import com.demo.domian.Alarm;
import com.demo.filter.Filter;
import com.demo.filter.FilterChain;
import org.springframework.core.annotation.Order;

@EnableFilter
public class Rule1 implements Filter {

    @Override
    public void execute(Alarm alarm, FilterChain chain)
    {
        //规则内容：如果是政府发生告警。告警等级设为最高
        if (alarm.getAlarmAddress().contains("政府"))
        {
            alarm.setAlarmLevel(4);
            System.out.println("执行规则1");
        }

        //注意回调FilterChain的doFilter方法，让FilterChain继续执行下一个Filter
        chain.doFilter(alarm, chain);
    }
}
