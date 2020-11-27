package com.model.demo.controller;

import com.model.demo.service.UserService;
import com.model.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class TestController {






    @Autowired
    private UserService userService;

//    private final UserService userService;
//
//    public UserController(final UserService userService) {
//        this.userService = userService;
//    }


    @GetMapping("/test")
    public Mono<String> test(){
        return Mono.just("hello");
    }


    /**
     * 功能描述：根据id找用户
     * @param id
     * @return
     */
    @GetMapping("find")
    public Mono<User> findByid(final String id){
        return userService.getById(id);
    }


    /**
     * 功能描述：删除用户
     * @param id
     * @return
     */
    @GetMapping("del")
    public Mono<User> del(final String id){
        return userService.del(id);
    }

    /**
     * 功能描述：列表
     * @return
     */
    @GetMapping(value="list",produces= MediaType.APPLICATION_STREAM_JSON_VALUE) //通过流的形式返回
    public Flux<User> list(){
        return userService.list().delayElements(Duration.ofSeconds(2));//延迟2秒返回
    }

}
