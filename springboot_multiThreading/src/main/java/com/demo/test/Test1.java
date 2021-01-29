package com.demo.test;

public class Test1 {

    private  static volatile boolean flag=false;
    private Object object=new Object();



    public  void A(){
        this.flag=true;
        System.out.println("改变量设置为true");
    }

    public  void B(){
        int i=0;
        while (!flag){
//            System.out.println(i);
//            synchronized (object){
//                i++;
//            }
        }
        System.out.println("flag已经被修改");
    }


    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(() -> {
            new Test1().A();
        }, "A");

        new Thread(()->{
            new Test1().B();
        }).start();
        Thread.sleep(200);
        a.start();
    }
}
