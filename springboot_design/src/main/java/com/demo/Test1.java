package com.demo;

import com.demo.domian.PreparationList;
import com.demo.filter.AbstractPrepareFilter;
import com.demo.filter.Study;
import com.demo.rule.HaveBreakfastFilter;
import com.demo.rule.WashFaceFilter;
import com.demo.rule.WashHairFilter;

public class Test1 {

    public static void main(String[] args) {

        PreparationList preparationList = new PreparationList();
             preparationList.setWashFace(true);
             preparationList.setWashHair(false);
             preparationList.setHavaBreakfast(true);
             Study study = new Study();
             AbstractPrepareFilter haveBreakfastFilter = new HaveBreakfastFilter(null);
             AbstractPrepareFilter washFaceFilter = new WashFaceFilter(haveBreakfastFilter);
             AbstractPrepareFilter washHairFilter = new WashHairFilter(washFaceFilter);

             washHairFilter.doFilter(preparationList, study);
    }
}
