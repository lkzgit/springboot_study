package com.demo.heima.Nio.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 *Buffer常见方法:
 *  Buffer clear() 清空缓冲区并返回对缓冲区的引用
 * Buffer flip() 为 将缓冲区的界限设置为当前位置，并将当前位置充值为 0
 * int capacity() 返回 Buffer 的 capacity 大小
 * boolean hasRemaining() 判断缓冲区中是否还有元素
 * int limit() 返回 Buffer 的界限(limit) 的位置
 * Buffer limit(int n) 将设置缓冲区界限为 n, 并返回一个具有新 limit 的缓冲区对象
 * Buffer mark() 对缓冲区设置标记
 * int position() 返回缓冲区的当前位置 position
 * Buffer position(int n) 将设置缓冲区的当前位置为 n , 并返回修改后的 Buffer 对象
 * int remaining() 返回 position 和 limit 之间的元素个数
 * Buffer reset() 将位置 position 转到以前设置的 mark 所在的位置
 * Buffer rewind() 将位置设为为 0， 取消设置的 mark
 *
 */
public class TestBuffer {

    @Test
    public void test3(){
        //分配直接缓冲区
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        System.out.println(buf.isDirect());//true 为直接内存 false为非直接内存
    }

    @Test
    public void test2(){
        String str = "abcdefj";

        ByteBuffer buf = ByteBuffer.allocate(1024);

        buf.put(str.getBytes());

        buf.flip();

        byte[] dst = new byte[buf.limit()];

        buf.get(dst, 0, 2);
        System.out.println(buf.limit());
        System.out.println(new String(dst, 0, 2));
        System.out.println(buf.position());

        //mark() : 标记
        buf.mark();

        buf.get(dst, 2, 2);
        System.out.println(new String(dst, 2, 2));
        System.out.println(buf.position());

        //reset() : 恢复到 mark 的位置
        buf.reset();
        System.out.println(buf.position());

        //判断缓冲区中是否还有剩余数据
        if(buf.hasRemaining()){
            //获取缓冲区中可以操作的数量
            System.out.println(buf.remaining());
        }
    }

    @Test
    public void test1(){
        String str = "allocate";
        //1. 分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("-----------------allocate()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //2. 利用 put() 存入数据到缓冲区中
        buf.put(str.getBytes());
        System.out.println("-----------------put()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //3. 切换读取数据模式
        buf.flip();
        System.out.println("-----------------flip()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //4. 利用 get() 读取缓冲区中的数据
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst, 0, dst.length));

        System.out.println("-----------------get()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        //5. rewind() : 可重复读
        buf.rewind();
        System.out.println("-----------------rewind()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //6. clear() : 清空缓冲区. 但是缓冲区中的数据依然存在，但是处于“被遗忘”状态
        buf.clear();
        System.out.println("-----------------clear()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        System.out.println((char)buf.get());

    }

}
