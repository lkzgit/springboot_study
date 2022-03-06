
package com.demo.flowable.config;

import org.flowable.engine.ProcessEngine;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述：工作流配置类
 *
 * @author lkz
 * @date 2022-03-08 22:41
 */
@Configuration
public class  FlowableConfig {
    /**
     * 解决flowable图片中的中文乱码
     * @param configuration
     * @return
     */
    @Bean
    public ProcessEngine processEngine(SpringProcessEngineConfiguration configuration) {
        configuration.setActivityFontName("宋体");
        configuration.setLabelFontName("宋体");
        configuration.setAnnotationFontName("宋体");
        return configuration.buildProcessEngine();
    }
}
