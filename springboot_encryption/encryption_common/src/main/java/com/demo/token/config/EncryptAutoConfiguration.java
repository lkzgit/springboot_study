package com.demo.token.config;

import com.demo.token.properties.EncryptProperties;
import com.demo.token.util.EncryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName EncryptAutoConfiguration
 * @Description 配置类
 * @Author wujh
 * @Date 2021/5/25 0025 下午 2:52
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(EncryptProperties.class)
public class EncryptAutoConfiguration {

    @Autowired
    private EncryptProperties encryptProperties;



    @Bean
    @ConditionalOnMissingBean()
    public EncryptService encryptService (EncryptProperties encryptProperties) {
        return new EncryptService(encryptProperties.getFilePath(), encryptProperties.getSuiteId(),encryptProperties.getEnabled());
    }
}
