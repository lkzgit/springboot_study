package com.demo.iterator;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName MyIterator.java
 * @Description TODO 自定义迭代器接口
 * @createTime 2022年02月20日 00:03:00
 */
public interface MyIterator {
    void first();//第一个元素
    void next();
    boolean hasNext();

    boolean isFirst();
    boolean isLast();

    Object getCurrentObj();//获取当前游标指向对象


}
