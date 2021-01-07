package com.demo.rule;

import com.demo.domian.PreparationList;
import com.demo.filter.AbstractFilterChain;
import com.demo.filter.StudyPrepareFilter;

public class WashHairFilter1 implements StudyPrepareFilter {

    @Override
    public void doFilter(PreparationList preparationList, AbstractFilterChain filterChain) {
        System.out.println("洗头发");
    }
}
