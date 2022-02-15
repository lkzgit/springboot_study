package com.demo.single;

import java.io.*;
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
        StaticSing sing = StaticSing.getSing(); //反射的方式 破坏单例模式
        System.out.println(staticSing==sing);

        System.out.println("枚举类");

        StaSing staSing1 = StaSing.STA_SING;
        StaSing staSing = StaSing.STA_SING;
        System.out.println("枚举类staSing:"+staSing.hashCode());
        System.out.println("枚举类staSing1:"+staSing1.hashCode());

        System.out.println("-------反序列化与反射方式破坏单例-------");
        System.out.println("反射方式破坏单例模式");
        Constructor<StaticSingTwo> twoConstructor = StaticSingTwo.class.getDeclaredConstructor();
        twoConstructor.setAccessible(true);
        StaticSingTwo c1 = twoConstructor.newInstance();
        StaticSingTwo c2 = twoConstructor.newInstance();
        System.out.println("c1:"+c1.hashCode());
        System.out.println("c2:"+c2.hashCode());
        System.out.println("反序列化方式破坏单例模式");
        Constructor<StaticSingThree> c = StaticSingThree.class.getDeclaredConstructor();
        c.setAccessible(true);
        StaticSingThree three = c.newInstance();
        System.out.println("反序列化three:"+three.hashCode());
        try{
            FileOutputStream fos=new FileOutputStream("d:/a.txt");
            ObjectOutputStream outputStream = new ObjectOutputStream(fos);
            outputStream.writeObject(three);
            outputStream.close();
            fos.close();
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream("d:/a.txt"));
            StaticSingThree o =(StaticSingThree) ois.readObject();
            System.out.println("反序列化:"+o.hashCode());


        }catch (Exception e){

        }



    }

}

// 枚举方式
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
    // 无参的构造方法
    public void StaticSing(){
        //new ObjectInputStream(new FileInputStream());
        if(getInstance.sing!=null){
            throw new RuntimeException("不允许通过反射获取");
        }
    };

    public static class getInstance{
      private static final   StaticSing sing=new StaticSing();
    }
    // 该方法效率高 实现了延时加载
    public static StaticSing getSing(){
        return getInstance.sing;
    }


}
class StaticSingTwo{

    private static StaticSingTwo singDemo;

    private void StaticSingTwo(){
        if(singDemo!=null){
            throw new RuntimeException();// 防止反射方式破坏单例
        }
    }
    //双重判定锁
    public static StaticSingTwo getSingDemo(){
        if(singDemo==null){
            synchronized (StaticSingTwo.class){
                if(singDemo==null){
                    singDemo=new StaticSingTwo();
                }
            }

        }
        return singDemo;
    }


}
// 反序列化需要实现接口
class StaticSingThree implements Serializable {

    private static StaticSingThree singDemo;

    private void StaticSingThree(){
        if(singDemo!=null){
            throw new RuntimeException();// 防止反射方式破坏单例
        }
    }
    //双重判定锁
    public static StaticSingThree getSingDemo(){
        if(singDemo==null){
            synchronized (StaticSingThree.class){
                if(singDemo==null){
                    singDemo=new StaticSingThree();
                }
            }

        }
        return singDemo;
    }

    // 解决反序列化方式破解单列
    // 反序列化是如果定义了readResolve() 则直接返回方法的指参数 而不需要单独的创建对象
    private Object readResolve() throws ObjectStreamException{
        return singDemo;
    }


}