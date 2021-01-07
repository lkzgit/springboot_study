package com.demo;

import com.demo.domian.PreparationList;
import com.demo.filter.AbstractFilterChain;
import com.demo.filter.FilterChain;
import com.demo.filter.Study;
import com.demo.filter.StudyPrepareFilter;
import com.demo.rule.*;

public class Test {

    @org.junit.Test
    public void test1(){
        PreparationList preparationList = new PreparationList();
           preparationList.setWashFace(true);
             preparationList.setWashHair(true);
           preparationList.setHavaBreakfast(true);

             Study study = new Study();

            StudyPrepareFilter washFaceFilter = new WashFaceFilter1();
           StudyPrepareFilter washHairFilter = new WashHairFilter1();
           StudyPrepareFilter haveBreakfastFilter = new HaveBreakfastFilter1();

        AbstractFilterChain filterChain = new AbstractFilterChain();
//            filterChain.addChain(washFaceFilter);
//            filterChain.addFilter(washHairFilter);
//            filterChain.addFilter(haveBreakfastFilter);
            filterChain.addFilter(washFaceFilter);
            filterChain.addFilter(washHairFilter);
            filterChain.addFilter(haveBreakfastFilter);
            filterChain.doFilter(preparationList, filterChain);
    }
}
