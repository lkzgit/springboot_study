package com.demo.server;

import com.demo.api.IHelloService;
import com.demo.api.User;

/**
 * 腾讯课堂搜索 咕泡学院
 */
public class HelloServiceImpl implements IHelloService {



    @Override
    public String sayHello(String content) {
        System.out.println("request in sayHello:"+content);
        return "Say Hello:"+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("request in saveUser:"+user);
        return "SUCCESS";
    }
}
