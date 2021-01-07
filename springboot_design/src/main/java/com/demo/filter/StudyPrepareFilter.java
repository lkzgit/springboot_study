package com.demo.filter;

import com.demo.domian.PreparationList;

/**
 * 过滤器接口
 */
public interface StudyPrepareFilter {

     void doFilter(PreparationList preparationList, AbstractFilterChain filterChain);
}
