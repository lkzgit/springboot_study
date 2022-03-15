package com.demo.adapter.hm.demo;

/**
 * @author lkz
 * @ClassName: SDCard
 * @description: 适配器模式 Sd 内存卡
 * @date 2022/3/15 13:54
 */
public interface SDCard {

    //读取SD卡方法
    String readSD();
    //写入SD卡功能
    void writeSD(String msg);
}

//SD卡实现类
class SDCardImpl implements SDCard {
    public String readSD() {
        String msg = "sd card read a msg :hello word SD";
        return msg;
    }

    public void writeSD(String msg) {
        System.out.println("sd card write msg : " + msg);
    }
}

