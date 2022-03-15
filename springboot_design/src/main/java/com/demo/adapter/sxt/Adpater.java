package com.demo.adapter.sxt;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Adpater.java
 * @Description TODO 适配器
 * @createTime 2022年02月17日 22:36:00
 */
public class Adpater implements Target{

    // 建立与被适配器之间的关系 一种是继承使自己本身成为被适配对象 一种是添加
    private Adpatee adpatee;

    public Adpater(Adpatee adpatee) {
        this.adpatee = adpatee;
    }

    @Override
    public void target() {
        adpatee.test();
        System.out.println("适配器");
    }
}
