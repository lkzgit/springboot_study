package com.demo.heima.BIO.file;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

public class ClientFile {

    public static void main(String[] args) {
        try(
                InputStream is = new FileInputStream("D:\\迅雷下载\\文件\\java.png");
        ){
            //  1、请求与服务端的Socket链接
            Socket socket = new Socket("127.0.0.1" , 8888);
            //  2、把字节输出流包装成一个数据输出流
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            //  3、先发送上传文件的后缀给服务端
            dos.writeUTF(".png");
            //  4、把文件数据发送给服务端进行接收
            byte[] buffer = new byte[1024];
            int len;
            while((len = is.read(buffer)) > 0 ){
                dos.write(buffer , 0 , len);
            }
            dos.flush();
            socket.shutdownOutput(); // 通知服务端这边的数据发送完毕了
//            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
