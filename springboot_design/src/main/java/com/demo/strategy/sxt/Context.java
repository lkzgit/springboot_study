package com.demo.strategy.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Context.java
 * @Description TODO 上下文 负责和具体的类交互
 * @createTime 2022年02月20日 15:52:00
 */
public class Context {
    private Strategy strategy;// 当前采用的算法
    // 可以通过构造 set,配置文件注入 动态注入
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public  void pringPrice(double bu){
        System.out.println("报价为："+strategy.getPrice(bu));
    }


}
