package com.demo.proxy;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName ProxyClient.java
 * @Description TODO
 * @createTime 2022年02月17日 23:05:00
 * 代理模式
 *  通过代理 控制对象的访问
 *  可以详细的控制访问某个类对象的方法在调用这个方法之前做前置处理 调用这个方法后做后置处理（aop实现）
 * 核心角色
 *    抽象角色 ；定义代理角色和真实角色的公共对外方法
 *    真实角色；实现抽象角色 ，定义真实角色所要实现的业务逻辑 ，供代理角色调用 关注真正的业务逻辑
 *    代理角色； 实现抽象角色，是真实角色的代理，通过真实角色的业务逻辑方法来实现抽象方法 并可以
 *      附加自己的操作
 *  -- 将统一的流程控制放到代理角色中处理
 *  应用场景:安全代理:屏蔽对真实角色的直接访问。
 *  远程代理:通过代理类处理远程方法调用(RMI)
 *  延迟加载:先加载轻量级的代理对象,真正需要再加载真实对象。
 *  比如你要开发一个大文档查看软件,大文档中有大的图片,有可能一个图片有100MB,在打开文件时不可能将所有的图片都显示出来,这样就可以使用代理模式,当需要查看图片时,用proxy来进行大图片的打开。
 *  静态代理
 *  动态代理
 */
public class ProxyClient {

    public static void main(String[] args) {

        RealStar star = new RealStar();
        ProxyStar proxyStar = new ProxyStar(star);
        proxyStar.confer();
        proxyStar.bookTicket();
        proxyStar.sign();
    }
}
