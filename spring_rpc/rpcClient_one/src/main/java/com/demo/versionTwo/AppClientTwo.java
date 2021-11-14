package com.demo.versionTwo;


import com.demo.api.IPaymentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class AppClientTwo
{
    public static void main( String[] args )
    {
        /*RpcProxyClient rpcProxyClient=new RpcProxyClient();
        //动态代理实现
        IHelloService iHelloService=rpcProxyClient.clientProxy
                (IHelloService.class,"localhost",8080);

        String result=iHelloService.sayHello("Mic");
        System.out.println(result);*/

        ApplicationContext context=new
                AnnotationConfigApplicationContext(SpringConfig.class);
        RpcProxyClient rpcProxyClient=context.getBean(RpcProxyClient.class);

        /*IHelloService iHelloService=rpcProxyClient.clientProxy
                (IHelloService.class,"localhost",8080);*/

        IPaymentService iPaymentService=rpcProxyClient.clientProxy(IPaymentService.class,
                "localhost",8080);

        iPaymentService.doPay();
    }
}
