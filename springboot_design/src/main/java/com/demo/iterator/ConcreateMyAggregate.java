package com.demo.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName ConcreateMyAggregate.java
 * @Description TODO 自定义聚合类
 * @createTime 2022年02月20日 00:06:00
 */
public class ConcreateMyAggregate {

    private List<Object> list=new ArrayList<>();

    public  void add(Object o){
        this.list.add(o);
    }

    public void removeObj(Object o){
        this.list.remove(o);
    }

    //获取迭代器
    public MyIterator createIterator(){
        return new ConcreteIntertor();
    }

    // 使用内部类定义迭代器可是直接使用外部类的属性
    private class ConcreteIntertor implements MyIterator{

        private int cursor;//定义游标用于记录遍历时的位置

        @Override
        public void first() {
            this.cursor=0;
        }

        @Override
        public void next() {
            if(cursor<list.size()){
                cursor++;
            }
        }

        @Override
        public boolean hasNext() {
            if(cursor<list.size()){
                return true;
            }
            return false;
        }

        @Override
        public boolean isFirst() {

            return cursor==0?true:false;
        }

        @Override
        public boolean isLast() {

             return cursor==(list.size()-1)?true:false;
        }

        @Override
        public Object getCurrentObj() {
            return list.get(cursor);
        }
    }

}
