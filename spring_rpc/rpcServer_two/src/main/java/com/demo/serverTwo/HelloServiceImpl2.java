package com.demo.serverTwo;

import com.demo.api.IHelloService;
import com.demo.api.User;

/**
 * 版本设置2
 */
@RpcService(value = IHelloService.class,version = "v2.0")
public class HelloServiceImpl2 implements IHelloService{

    @Override
    public String sayHello(String content) {
        System.out.println("【v2.0】request in sayHello:"+content);
        return "【v2.0】Say Hello:"+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("【V1.0】request in saveUser:"+user);
        return "【v2.0】SUCCESS";
    }
}
