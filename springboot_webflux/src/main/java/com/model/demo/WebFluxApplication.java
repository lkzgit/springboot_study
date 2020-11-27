package com.model.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class WebFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxApplication.class, args);
    }

    /**
     *   2、Flux和Mono  User List<User>
     * 		1）简单业务而言：和其他普通对象差别不大，复杂请求业务，就可以提升性能
     * 		2）通俗理解：
     *     Mono 表示的是包含 0 或者 1 个元素的异步序列
     *     mono->单一对象 User     redis->用户ID-》唯一的用户Mono<User>
     *
     *     Flux 表示的是包含 0 到 N 个元素的异步序列
     *     flux->数组列表对象 List<User>   redis->男性用户->Flux<User>
     *     Flux 和 Mono 之间可以进行转换
     *   3、Spring WebFlux有两种风格：基于功能和基于注解的。基于注解非常接近Spring MVC模型，如以下示例所示：
     * 		第一种：
     *                        @RestController
     *            @RequestMapping（“/ users”）
     * 			 public  class MyRestController {
     *
     *                @GetMapping（“/ {user}”）
     * 				 public Mono <User> getUser（ @PathVariable Long user）{
     * 					 // ...
     *                }
     *
     *                @GetMapping（“/ {user} / customers”）
     * 				 public Flux <Customer> getUserCustomers（ @PathVariable Long user）{
     * 					 // ...
     *                }
     *
     *                @DeleteMapping（“/ {user}”）
     * 				 public Mono <User> deleteUser（ @PathVariable Long user）{
     * 					 // ...
     *                }
     *
     *            }
     * 		第二种： 路由配置与请求的实际处理分开
     *            @Configuration
     *             public  class RoutingConfiguration {
     *
     *                @Bean
     *                 public RouterFunction <ServerResponse> monoRouterFunction（UserHandler userHandler）{
     * 					 return route（GET（ “/ {user}”）.and（accept（APPLICATION_JSON）），userHandler :: getUser）
     * 							.andRoute（GET（“/ {user} / customers”）.and（accept（APPLICATION_JSON）），userHandler :: getUserCustomers）
     * 							.andRoute（DELETE（“/ {user}”）.and（accept（APPLICATION_JSON）），userHandler :: deleteUser）;
     *                }
     *
     *            }
     *            @Component
     *            public class UserHandler {
     *
     * 				公共 Mono <ServerResponse> getUser（ServerRequest请求）{
     * 					 // ...
     *                }
     *
     * 				public Mono <ServerResponse> getUserCustomers（ServerRequest request）{
     * 					 // ...
     *                }
     *
     * 				公共 Mono <ServerResponse> deleteUser（ServerRequest请求）{
     * 					 // ...
     *                }
     *            }
     *
     *
     *
     * 	4、Spring WebFlux应用程序不严格依赖于Servlet API，因此它们不能作为war文件部署，也不能使用src/main/webapp目录
     *
     * 	5、可以整合多个模板引擎
     * 		除了REST Web服务外，您还可以使用Spring WebFlux提供动态HTML内容。Spring WebFlux支持各种模板技术，包括Thymeleaf，FreeMarker
     *

     * 3、SpringBoot2.x webflux实战
     * 	简介:webflux响应式编程实战
     *
     * 	1、WebFlux中，请求和响应不再是WebMVC中的ServletRequest和ServletResponse，而是ServerRequest和ServerResponse
     *
     * 	2、加入依赖，如果同时存在spring-boot-starter-web，则会优先用spring-boot-starter-web
     * 		<dependency>
     * 				<groupId>org.springframework.boot</groupId>
     * 				<artifactId>spring-boot-starter-webflux</artifactId>
     * 		</dependency>
     *
     * 		测试
     * 		localhost:8080/api/v1/user/test
     *
     * 	3、启动方式默认是Netty,8080端口
     *
     *
     *
     * 	4、参考：https://spring.io/blog/2016/04/19/understanding-reactive-types
     *
     *
     *
     *
     *
     */


}
