package com.demo.heima.BIO;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伪异步IO
 */
public class ServerThree {


    public static void main(String[] args) {
        try {
            System.out.println("----------服务端启动成功------------");
            ServerSocket ss = new ServerSocket(9999);

            // 一个服务端只需要对应一个线程池
            HandlerSocketThreadPoolThree handlerSocketThreadPool =
                    new HandlerSocketThreadPoolThree(3, 1000);

            // 客户端可能有很多个
            while(true){
                Socket socket = ss.accept() ; // 阻塞式的！
                System.out.println("有人上线了！！");
                // 每次收到一个客户端的socket请求，都需要为这个客户端分配一个
                // 独立的线程 专门负责对这个客户端的通信！！
                handlerSocketThreadPool.execute(new ReaderClientRunnable(socket));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
class ReaderClientRunnable implements Runnable{

    private Socket socket ;

    public ReaderClientRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 读取一行数据
            InputStream is = socket.getInputStream() ;
            // 转成一个缓冲字符流
            Reader fr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(fr);
            // 一行一行的读取数据
            String line = null ;
            while((line = br.readLine())!=null){ // 阻塞式的！！
                System.out.println("服务端收到了数据："+line);
            }
        } catch (Exception e) {
            System.out.println("有人下线了");
        }

    }

}
