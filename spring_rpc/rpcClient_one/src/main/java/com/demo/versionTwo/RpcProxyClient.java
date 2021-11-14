package com.demo.versionTwo;

import java.lang.reflect.Proxy;

/**
 * 动态代理
 */
public class RpcProxyClient {

    public <T> T clientProxy(final Class<T> interfaceCls,final String host,final int port){

        return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls},new RemoteInvocationHandler(host,port));
    }
}
