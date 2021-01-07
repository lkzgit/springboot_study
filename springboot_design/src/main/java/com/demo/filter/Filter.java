package com.demo.filter;

import com.demo.domian.Alarm;

/**
 * 过滤器接口
 */
public interface Filter {


    void execute(Alarm alarm, FilterChain chain);

}
