package com.demo.nien;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lkz
 * @date 2021/11/18 19:51
 * @description 模板模式
 * 优点：把公共的部分放在基类算法骨架中里面 把 变化的部分通过钩子函数进行实现 让子类继承实现其钩子函数
 */
public class TemplateDemo {

    static abstract class AbstractAction{
        /**
         * 模板方法：算法骨架
         */
        public void template(){
            System.out.println("template执行开始");
           before();
           action();
           after();
            System.out.println("template执行结束");
        }
        //钩子方法
        public abstract void action();
        protected void before(){
            System.out.println("AbstractAction之前");
        }
        protected void after(){
            System.out.println("AbstractAction之后");
        }
    }
    //子类 A：提供了钩子方法实现
    static class Action extends AbstractAction{


        @Override
        public void action() {
            System.out.println("Action子类执行");
        }
    }
    static class ActionB extends AbstractAction{


        @Override
        public void action() {
            System.out.println("ActionB子类执行");
        }
    }

    public static void main(String[] args) {
        AbstractAction action=null;
        action=new Action();
        action.template();

        action=new ActionB();
        action.template();
    }

}
