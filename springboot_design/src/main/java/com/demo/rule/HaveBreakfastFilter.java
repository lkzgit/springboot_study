package com.demo.rule;

import com.demo.domian.PreparationList;
import com.demo.filter.AbstractPrepareFilter;

public class HaveBreakfastFilter extends AbstractPrepareFilter {

    public HaveBreakfastFilter(AbstractPrepareFilter nextPrepareFilter) {
        super(nextPrepareFilter);
    }

    @Override
    public void prepare(PreparationList preparationList) {
        if (preparationList.isHavaBreakfast()) {
            System.out.println("吃早餐");
        }

    }

}