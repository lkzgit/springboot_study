package com.demo.iterator;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Client.java
 * @Description TODO 迭代器模式
 * @createTime 2022年02月20日 00:02:00
 */
public class Client {

    public static void main(String[] args) {
        ConcreateMyAggregate aggregate = new ConcreateMyAggregate();
        aggregate.add("aa");
        aggregate.add("ab");
        aggregate.add("ac");
        MyIterator iterator = aggregate.createIterator();
        while (iterator.hasNext()){
            System.out.println(iterator.getCurrentObj());
            iterator.next();
        }
    }
}
