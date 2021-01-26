package com.demo.mangyThread;

public class Jerry {

    public void say(){
        System.out.println("jerry对barry说：不行，你要先请我吃饭");
    }
    public void eat(){
        System.out.println("barry请jerry吃饭");
    }
}
class Barry{
    public void say(){
        System.out.println("barry对jerry说：你请我吃饭，我就请你吃饭");
    }
    public void eat(){
        System.out.println("jerry请barry吃饭");
    }
}
 class MyDeadLock implements Runnable{
    public static Jerry jerry=new Jerry();
    public static Barry barry=new Barry();
    public  boolean flag=false;
    public void run() {
        if (flag){
            synchronized (jerry){
                jerry.say();
                try{
                    Thread.sleep(500);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                synchronized (barry){
                    jerry.eat();
                }
            }
        }
        else {
            synchronized (barry){
                barry.say();
                try{
                    Thread.sleep(500);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                synchronized (jerry){
                    barry.eat();
                }
            }
        }
    }

    public static void main(String[] args) {
        MyDeadLock t1=new MyDeadLock();
        MyDeadLock t2=new MyDeadLock();
        t1.flag=true;
        t2.flag=false;
        Thread tha=new Thread(t1);
        Thread thb=new Thread(t2);
        tha.start();
        thb.start();
    }
}
