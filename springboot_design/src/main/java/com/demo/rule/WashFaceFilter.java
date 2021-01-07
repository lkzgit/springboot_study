package com.demo.rule;

import com.demo.domian.PreparationList;
import com.demo.filter.AbstractPrepareFilter;

public class WashFaceFilter extends AbstractPrepareFilter {

    public WashFaceFilter(AbstractPrepareFilter nextPrepareFilter) {
        super(nextPrepareFilter);
    }

    @Override
    public void prepare(PreparationList preparationList) {
        if (preparationList.isWashFace()) {
            System.out.println("洗脸");
        }

    }

}
