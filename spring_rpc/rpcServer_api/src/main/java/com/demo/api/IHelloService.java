package com.demo.api;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface IHelloService {

    //
    String sayHello(String content);

    /**
     * 保存用户
     * @param user
     * @return
     */
    String saveUser(User user);

}
