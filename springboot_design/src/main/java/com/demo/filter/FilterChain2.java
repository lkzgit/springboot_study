package com.demo.filter;

import com.demo.domian.Work;

import java.util.ArrayList;
import java.util.List;

public class FilterChain2 implements Filter2 {

    // 用List集合来存储过滤规则
    List<Filter2> filters = new ArrayList<Filter2>();
    // 用于标记规则的引用顺序
    int index = 0;

    // 往规则链条中添加规则
    public FilterChain2 addFilter(Filter2 f) {
        filters.add(f);
        // 代码的设计技巧:Chain链添加过滤规则结束后返回添加后的Chain，方便我们下面doFilter函数的操作
        return this;
    }

    @Override
    public void doFilter(Work response, FilterChain2 chain) {
        // index初始化为0,filters.size()为3，不会执行return操作
        if (index == filters.size()) {
            return ;
        }
        // 每添加一个过滤规则，index自增1
        Filter2 f = filters.get(index);
        index++;
        // 根据索引值获取对应的规律规则对字符串进行处理
        f.doFilter(response, chain);
    }
}
