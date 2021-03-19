package com.demo.one;

import java.util.concurrent.atomic.AtomicReference;

/**
 * ABA问题怎么产生的
 *  此A非彼A
 * 比如说一个线程one从内存位置V中取出A，这时候另一个线程two也从内存中取出A，
 * 并且线程two进行了一些操作将值变成了B,然后线程two又将V位置的数据变成A，
 * 这时候线程one进行CAS操作发现内存中仍然是A，然后线程one操作成功。
 *
 * 尽管线程one的CAS操作成功，但是不代表这个过程就是没有问题的。
 *
 *
 */
public class User {

    String userName;

    int age;

    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("User [userName=%s, age=%s]", userName, age);
    }


}

class AtomicReferenceDemo {
    public static void main(String[] args){
        User z3 = new User( "z3",22);
        User li4 = new User("li4" ,25);
        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(z3);
        System.out.println(atomicReference.compareAndSet(z3, li4)+"\t"+atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3, li4)+"\t"+atomicReference.get().toString());
    }
}

