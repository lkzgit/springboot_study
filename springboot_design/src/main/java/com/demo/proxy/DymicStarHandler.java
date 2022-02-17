package com.demo.proxy;

import org.omg.PortableInterceptor.INACTIVE;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName DymicStarHandler.java
 * @Description TODO 动态代理需要的处理器接口
 * @createTime 2022年02月17日 23:34:00
 */
public class DymicStarHandler implements InvocationHandler {

    DynamicStar realStar;

    public DymicStarHandler(DynamicStar realStar) {
        super();
        this.realStar = realStar;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("-------");
      method.invoke(realStar,args);
        return null;
    }

    public static void main(String[] args) {
        DynamicStar star=new DynamicRealStar();
        DymicStarHandler handler = new DymicStarHandler(star);
        DynamicStar instance = (DynamicStar)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{DynamicStar.class}, handler);
        instance.bookTicket();
        instance.sign();

    }
}
