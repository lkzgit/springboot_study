package com.easyexcle.demo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.easyexcle.demo.pojo.User;
import org.springframework.stereotype.Component;

@Component
public class UserListener extends AnalysisEventListener<User> {
    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        System.out.println(user);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
