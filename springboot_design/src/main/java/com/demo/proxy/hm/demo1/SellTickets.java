package com.demo.proxy.hm.demo1;

/**
 * @author lkz
 * @ClassName: SellTickets
 * @description: 静态代理
 * @date 2022/3/15 11:10
 */
public interface SellTickets {

    void sell();
}

//火车站  火车站具有卖票功能，所以需要实现SellTickets接口
 class TrainStation implements SellTickets {

    public void sell() {
        System.out.println("火车站卖票");
    }
}

//代售点
 class ProxyPoint implements SellTickets {

    private TrainStation station = new TrainStation();

    public void sell() {
        System.out.println("代理点收取一些服务费用");
        station.sell();
    }
}

//测试类
class Client {
    public static void main(String[] args) {
        ProxyPoint pp = new ProxyPoint();
        pp.sell();
    }
}