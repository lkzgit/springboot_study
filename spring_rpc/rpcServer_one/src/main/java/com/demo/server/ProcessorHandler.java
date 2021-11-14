package com.demo.server;

import com.demo.api.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 *  咕泡学院
 * 处理客户端请求
 */
public class ProcessorHandler implements Runnable{

    private Socket socket;
    private Object service;

    public ProcessorHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream=null;
        ObjectOutputStream objectOutputStream=null;

        try {
            objectInputStream=new ObjectInputStream(socket.getInputStream());

            //输入流中应该有什么东西？
            //请求哪个类，方法名称、参数
            RpcRequest rpcRequest=(RpcRequest)objectInputStream.readObject();
            Object result=invoke(rpcRequest); //反射调用本地服务

            objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }finally {
            if(objectInputStream!=null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(objectOutputStream!=null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //反射调用
        Object[] args=request.getParameters(); //拿到客户端请求的参数
        Class<?>[] types=new Class[args.length]; //获得每个参数的类型
        for(int i=0;i<args.length;i++){
            types[i]=args[i].getClass();
        }
        Class clazz=Class.forName(request.getClassName()); //跟去请求的类进行加载
        Method method=clazz.getMethod(request.getMethodName(),types); //sayHello, saveUser找到这个类中的方法
        Object result=method.invoke(service,args);//HelloServiceImpl 进行反射调用
        return result;
    }
}
