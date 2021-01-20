package com.demo.sguigu;

import java.nio.ByteBuffer;

/**
 * 直接缓冲区：建立在操作系统的物理内存中
 * 非直接缓冲区：建立在jvm的内存中
 */
public class TestBuff {

    /**
     * position <= limit <= capacity
     * @param args
     */
    public static void main(String[] args) {
        String str = "abcde";
        //1.分配一个缓冲区的大小
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("-----------分配一个缓冲区的大小--------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //2.利用put()存入数据进入缓冲区
        buf.put(str.getBytes());
        System.out.println("-----------利用put()存入数据进入缓冲区--------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //3.切换读取数据的模式
        buf.flip();
        System.out.println("-----------flip()--------------------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //4.利用get()读取数据
        byte[] dist = new byte[buf.limit()];
        buf.get(dist);
        System.out.println(new String(dist,0,dist.length));
        System.out.println("-----------get()--------------------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //5.利用rewind():可重复读
        buf.rewind();
        System.out.println("-----------rewind()--------------------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //6.利用clear():清空缓冲区,数据依然存在，处于被遗忘的状态
        buf.clear();
        System.out.println("-----------clear()--------------------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
    }

}
