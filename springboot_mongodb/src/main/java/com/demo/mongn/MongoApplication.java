package com.demo.mongn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**docker run -p 27017:27017 --name mongodb -v /dockerInstall/mongo/db:/data/db -d mongo:latest --auth
 * 尚硅谷安装mongodb
 */
@SpringBootApplication
public class MongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class);
    }
}
