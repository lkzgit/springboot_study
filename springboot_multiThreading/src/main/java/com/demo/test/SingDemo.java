package com.demo.test;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for(int i=1;i<=100;i++)
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"名字:"+SingDemo.getSingDemo());
        }).start();

        System.out.println("异步线程");

        System.out.println("静态内部类方式");
        Constructor<StaticSing> declaredConstructor = StaticSing.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        StaticSing staticSing = declaredConstructor.newInstance();
        StaticSing sing = StaticSing.getSing();
        System.out.println(staticSing==sing);

        System.out.println("枚举类");
//        Constructor<StaSing> declaredConstructor1 = StaSing.class.getDeclaredConstructor(String.class,int.class);
//        declaredConstructor1.setAccessible(true);
//        StaSing staSing = declaredConstructor1.newInstance();
        StaSing staSing1 = StaSing.STA_SING;
        StaSing staSing = StaSing.STA_SING;
        System.out.println("枚举类staSing:"+staSing.hashCode());
        System.out.println("枚举类staSing1:"+staSing1.hashCode());
    }

}


 enum StaSing{
    STA_SING;

    public StaSing getInstance(){
        return STA_SING;
    }
}

//静态内部类
class StaticSing{

    /**
     * 1、内部类被加载和初始化时，才创建INSTANCE实例对象
     * 2、静态内部类不会自动创建,随着外部类的加载初始化而初始化，他是要单独去加载和实例化的
     * 3、因为是在内部类加载和初始化时，创建的，因此线程安全
     *  可以通过反射的方式获取  通过在构造方法中设置非空的判断
     * 4.静态内部类通过实现序列化 也会破坏单例
     */
    public void StaticSing(){
        //new ObjectInputStream(new FileInputStream());
        if(getInstance.sing!=null){
            throw new RuntimeException("不允许通过反射获取");
        }
    };

    public static class getInstance{
      private static final   StaticSing sing=new StaticSing();
    }

    public static StaticSing getSing(){
        return getInstance.sing;
    }




}
