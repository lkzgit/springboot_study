package com.demo.adapter.hm.demo;

/**
 * @author lkz
 * @ClassName: Computer
 * @description: TODO
 * @date 2022/3/15 13:58
 */
public class Computer {

    public String readSD(SDCard sdCard) {
        if(sdCard == null) {
            throw new NullPointerException("sd card null");
        }
        return sdCard.readSD();
    }

}
