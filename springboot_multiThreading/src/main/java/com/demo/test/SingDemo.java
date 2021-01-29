package com.demo.test;

public class SingDemo {

    private static SingDemo singDemo;

    private void SingDemo(){

    }

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
