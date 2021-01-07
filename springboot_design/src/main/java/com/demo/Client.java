package com.demo;

import com.demo.domian.Alarm;
import com.demo.factory.FilterFactory;
import com.demo.filter.FilterChain;
import com.demo.rule.Rule1;
import com.demo.rule.Rule2;

import java.util.Date;

public class Client {

//    public static void main(String[] args)
//    {
//        //构造告警事件
//        Alarm alarm = new Alarm(1, 10, "光功率衰耗", "省政府23号楼",
//                1, 1, 1, new Date(), "割接");
//
//        //将规则加入责任链
//        FilterChain filterChain = new FilterChain();
//        filterChain.addFilter(new Rule1()).addFilter(new Rule2());
//
//        //执行责任链
//        filterChain.doFilter(alarm, filterChain);
//
//        //输出结果
//        System.out.println(alarm.toString());
//    }
    // 通过注解的方式
    public static void main(String[] args)
    {
        //构造告警事件
        Alarm alarm = new Alarm(1, 10, "光功率衰耗",
                "省政府23号楼", 1, 1, 1, new Date(), "割接");

        //将规则加入责任链中,通过注解扫描指定的包，此处无需指定执行哪个规则（FIlter的实现类）
        FilterChain filterChain = new FilterChain();
        /**
         * 这个时候，如果想增加一个规则，那么就只需要在 com.demo.rule的包下面新增一个规则
         * ，然后加上注解@EnableFilter即可将该规则加入到责任链中。
         *
         * 客户端的责任链并没有手动添加规则过滤器的实现类，
         * 通过FilterFactory自动扫描指定的包下面的被EnableFilter注解修饰的类，
         * 这样达到了动态添加规则，又不影响主体代码的效果。
         */
        filterChain.addFilters(FilterFactory.getFilters("com.demo.rule"));

        //执行责任链
        filterChain.doFilter(alarm, filterChain);

        //输出结果
        System.out.println(alarm);
    }

}
