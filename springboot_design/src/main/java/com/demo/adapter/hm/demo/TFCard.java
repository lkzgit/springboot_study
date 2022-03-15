package com.demo.adapter.hm.demo;

/**
 * @author lkz
 * @ClassName: TFCard
 * @description:
 * @date 2022/3/15 13:59
 */
public interface TFCard {

    //读取TF卡方法
    String readTF();
    //写入TF卡功能
    void writeTF(String msg);

}

//TF卡实现类
class TFCardImpl implements TFCard {

    public String readTF() {
        String msg ="tf card read msg : hello word tf card";
        return msg;
    }

    public void writeTF(String msg) {
        System.out.println("tf card write a msg : " + msg);
    }
}
