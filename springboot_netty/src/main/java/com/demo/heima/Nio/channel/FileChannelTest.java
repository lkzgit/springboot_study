package com.demo.heima.Nio.channel;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel的常用方法:
 *      int read(ByteBuffer dst) 从 从  Channel 到 中读取数据到  ByteBuffer
 *      long  read(ByteBuffer[] dsts) 将 将  Channel 到 中的数据“分散”到  ByteBuffer[]
 *      int  write(ByteBuffer src) 将 将  ByteBuffer 到 中的数据写入到  Channel
 *      long write(ByteBuffer[] srcs) 将 将  ByteBuffer[] 到 中的数据“聚集”到  Channel
 *      long position() 返回此通道的文件位置
 *      FileChannel position(long p) 设置此通道的文件位置
 *      long size() 返回此通道的文件的当前大小
 *      FileChannel truncate(long s) 将此通道的文件截取为给定大小
 *      void force(boolean metaData) 强制将所有对此通道的文件更新写入到存储设备中
 *
 *
 *
 */
public class FileChannelTest {

    //使用 ByteBuffer(缓冲) 和 FileChannel(通道)， 将 "hello,Java程序员！" 写入到 data.txt 中.
   @Test
    public void write(){
       try {
           // 1、字节输出流通向目标文件
           FileOutputStream fos = new FileOutputStream("data01.txt");
           // 2、得到字节输出流对应的通道Channel
           FileChannel channel = fos.getChannel();
           // 3、分配缓冲区
           ByteBuffer buffer = ByteBuffer.allocate(1024);
           buffer.put("hello,Java程序员！".getBytes());
           // 4、把缓冲区切换成写出模式
           buffer.flip();
           channel.write(buffer);
           channel.close();
           System.out.println("写数据到文件中！");
       } catch (Exception e) {
           e.printStackTrace();
       }

   }
    // 将 data01.txt 中的数据读入到程序，并显示在控制台屏幕
    @Test
    public void read() throws Exception {
        // 1、定义一个文件字节输入流与源文件接通
        FileInputStream is = new FileInputStream("data01.txt");
        // 2、需要得到文件字节输入流的文件通道
        FileChannel channel = is.getChannel();
        // 3、定义一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 4、读取数据到缓冲区
        channel.read(buffer);
        buffer.flip();
        // 5、读取出缓冲区中的数据并输出即可
        String rs = new String(buffer.array(),0,buffer.remaining());
        System.out.println(rs);

    }

    //使用 FileChannel(通道) ，完成文件的拷贝。
    @Test
    public void copy() throws Exception {
        // 源文件
        File srcFile = new File("E:\\百度网盘\\咕泡\\黑马IO\\文件\\壁纸.jpg");
        File destFile = new File("E:\\百度网盘\\咕泡\\黑马IO\\文件\\壁纸new.jpg");
        // 得到一个字节字节输入流
        FileInputStream fis = new FileInputStream(srcFile);
        // 得到一个字节输出流
        FileOutputStream fos = new FileOutputStream(destFile);
        // 得到的是文件通道
        FileChannel isChannel = fis.getChannel();
        FileChannel osChannel = fos.getChannel();
        // 分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(true){
            // 必须先清空缓冲然后再写入数据到缓冲区
            buffer.clear();
            // 开始读取一次数据
            int flag = isChannel.read(buffer);
            if(flag == -1){
                break;
            }
            // 已经读取了数据 ，把缓冲区的模式切换成可读模式
            buffer.flip();
            // 把数据写出到
            osChannel.write(buffer);
        }
        isChannel.close();
        osChannel.close();
        System.out.println("复制完成！");
    }

    /**
     * 分散读取（Scatter ）:是指把Channel通道的数据读入到多个缓冲区中去
     *
     * 聚集写入（Gathering ）是指将多个 Buffer 中的数据“聚集”到 Channel。
     */
    @Test
    public void test() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("1.txt", "rw");
        //1. 获取通道
        FileChannel channel1 = raf1.getChannel();

        //2. 分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //3. 分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel1.read(bufs);

        for (ByteBuffer byteBuffer : bufs) {
            byteBuffer.flip();
        }

        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("-----------------");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        //4. 聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
        FileChannel channel2 = raf2.getChannel();

        channel2.write(bufs);
    }
   // 从目标通道中去复制原通道数据
    @Test
    public void testTransferFrom() throws Exception {
        // 1、字节输入管道
        FileInputStream is = new FileInputStream("data01.txt");
        FileChannel isChannel = is.getChannel();
        // 2、字节输出流管道
        FileOutputStream fos = new FileOutputStream("data03.txt");
        FileChannel osChannel = fos.getChannel();
        // 3、复制
        osChannel.transferFrom(isChannel,isChannel.position(),isChannel.size());
        isChannel.close();
        osChannel.close();
    }

    //把原通道数据复制到目标通道
    @Test
    public void testTransferTo() throws Exception {
        // 1、字节输入管道
        FileInputStream is = new FileInputStream("data01.txt");
        FileChannel isChannel = is.getChannel();
        // 2、字节输出流管道
        FileOutputStream fos = new FileOutputStream("data04.txt");
        FileChannel osChannel = fos.getChannel();
        // 3、复制
        isChannel.transferTo(isChannel.position() , isChannel.size() , osChannel);
        isChannel.close();
        osChannel.close();
    }


}
