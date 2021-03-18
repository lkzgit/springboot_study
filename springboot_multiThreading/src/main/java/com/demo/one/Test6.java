package com.demo.one;

import com.sun.org.apache.regexp.internal.RE;
//懒汉
public class Test6 {

    private static Test6 test6;

    private Test6(){

    }
    public static Test6 get(){
       if(test6==null){
           synchronized (Test6.class){
               if(test6==null){
                   test6=new Test6();
               }
           }

       }
        return test6;
    }
}
//饿汉
class Test61{
    private final static Test61 te=new Test61();

    private Test61(){

    }

    public static Test61 get61(){
        return te;
    }
}
// 静态代码块内部类
class Singleton6{
    /**
     * 1、内部类被加载和初始化时，才创建INSTANCE实例对象
     * 2、静态内部类不会自动创建,随着外部类的加载初始化而初始化，他是要单独去加载和实例化的
     * 3、因为是在内部类加载和初始化时，创建的，因此线程安全
     */
    private Singleton6(){}

    public static class Inner{
        private static final Singleton6 INSTANCE = new Singleton6();
    }
    public static Singleton6 getInstance() {
        return Inner.INSTANCE;
    }

}
//静态代码块饿汉式(适合复杂实例化)
class Singleton3{

    /**
     *  静态代码块
     */
    public static final Singleton3 INSTANCE;
    private String info;

    static {
        try {
            INSTANCE = new Singleton3("123");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private Singleton3(String info) {
        this.info = info;
    }


}