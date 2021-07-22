package com.demo.test;

public class SingDemo {

    private static SingDemo singDemo;

    private void SingDemo(){

    }
    //双重判定锁
    public static SingDemo getSingDemo(){
            if(singDemo==null){
                synchronized (SingDemo.class){
                    if(singDemo==null){
                        singDemo=new SingDemo();
                    }
                }

            }
            return singDemo;
        }

    public static void main(String[] args) {
        for(int i=1;i<=100;i++)
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"名字:"+SingDemo.getSingDemo());
        }).start();

        System.out.println("异步线程");
    }

}

//静态内部类
class StaticSing{

    /**
     * 1、内部类被加载和初始化时，才创建INSTANCE实例对象
     * 2、静态内部类不会自动创建,随着外部类的加载初始化而初始化，他是要单独去加载和实例化的
     * 3、因为是在内部类加载和初始化时，创建的，因此线程安全
     */
    public void StaticSing(){};

    public static class getInstance{
      private static final   StaticSing sing=new StaticSing();
    }

    public static StaticSing getSing(){
        return getInstance.sing;
    }

}
