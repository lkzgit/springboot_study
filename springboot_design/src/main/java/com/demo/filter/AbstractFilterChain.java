package com.demo.filter;

import com.demo.domian.PreparationList;

import java.util.ArrayList;
import java.util.List;

public class AbstractFilterChain {

    private int pos=0;

//    private Study study;

    private List<StudyPrepareFilter> studyFilters=new ArrayList<>();

    /**
     * 向责任链中添加过滤器
     * @param
     */
    public void addFilter(StudyPrepareFilter studyPrepareFilter){
        if (studyPrepareFilter == null) {
            studyFilters = new ArrayList<StudyPrepareFilter>();
        }

        studyFilters.add(studyPrepareFilter);
    }


    //处理事件（alarm）从FilterChain中获取过滤器，进行处理，处理完成之后过滤器会再调用该方法，
    //继续执行下一个filter.这就需要在每个Filter接口的实现类中最后一句需要回调FilterChain的doFilter方法。
    public void doFilter(PreparationList thingList, AbstractFilterChain filterChain) {
                 // 所有过滤器执行完毕
                 if (pos == studyFilters.size()) {
                        return;
                   }
                studyFilters.get(pos++).doFilter(thingList, filterChain);
            }

}
