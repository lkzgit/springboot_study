package com.boot.guigu;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapDemo {

    public static void main(String[] args) throws InterruptedException {
        myHashMap();
        System.out.println("==========");
        myWeakHashMap();
    }

    private static void myHashMap() {
        Map<Integer, String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "HashMap";

        map.put(key, value);
        System.out.println(map);

        key = null;

        System.gc();

        System.out.println(map);
    }

    /**
     * 弱引用 只要被gc 就会被 回收
     */
    private static void myWeakHashMap() throws InterruptedException {
        Map<Integer, String> map = new WeakHashMap<>();
        Integer key = new Integer(1);
        String value = "WeakHashMap";

        map.put(key, value);
        System.out.println(map);

        key = null;
        Thread.sleep(1000);
        System.gc();

        System.out.println(map);
    }


}
