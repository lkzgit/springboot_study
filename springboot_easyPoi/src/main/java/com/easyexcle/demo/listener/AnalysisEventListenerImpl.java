package com.easyexcle.demo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.easyexcle.demo.pojo.User;
import org.springframework.stereotype.Component;

import java.util.List;


/**

 * @Description: 使用EasyExcel读excel文件，需要使用一个监听器，
 * <p>
 * 在读的时候，每读一行，就会自动调用监听器的invoke方法，并且把读取的内容自动封装成了一个对象
 */
@Component
public class AnalysisEventListenerImpl<T> extends AnalysisEventListener<User> {

    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /***
     * 每读一样会自动调用这个方法
     */
    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        System.out.println(user);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
