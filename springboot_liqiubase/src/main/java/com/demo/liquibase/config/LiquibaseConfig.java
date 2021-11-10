package com.demo.liquibase.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author lkz
 * @date 2021/11/9 19:05
 * @description bean方式配置信息
 * https://www.jianshu.com/p/ddffa71ab60a
 */
//@Configuration
//public class LiquibaseConfig {
//
//    @Bean
//    public SpringLiquibase liquibase(DataSource dataSource) {
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setDataSource(dataSource);
//        liquibase.setChangeLog("classpath:/config/master.xml"); //加载的文件位置
//        liquibase.setContexts("development,test,production"); //环境
//        liquibase.setShouldRun(true);
//
//        return liquibase;
//    }
//
//
//
//
//}
