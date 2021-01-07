package com.demo.rule;

import com.demo.domian.PreparationList;
import com.demo.filter.AbstractPrepareFilter;

public class WashHairFilter extends AbstractPrepareFilter {

    public WashHairFilter(AbstractPrepareFilter nextPrepareFilter) {
        super(nextPrepareFilter);
    }

    @Override
    public void prepare(PreparationList preparationList) {
        if (preparationList.isWashHair()) {
            System.out.println("洗头");
        }

    }

}