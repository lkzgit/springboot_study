package com.demo.versionOne;

import com.demo.api.IHelloService;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName App.java
 * @Description TODO
 * @createTime 2021年11月14日 11:03:00
 */
public class AppClient {


    public static void main( String[] args )
    {
        RpcProxyClient rpcProxyClient=new RpcProxyClient();

        IHelloService iHelloService=rpcProxyClient.clientProxy
                (IHelloService.class,"localhost",8080);

        String result=iHelloService.sayHello("Mic");
        System.out.println(result);
    }
}
