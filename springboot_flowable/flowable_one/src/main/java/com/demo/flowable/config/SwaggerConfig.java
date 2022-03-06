
package com.demo.flowable.config;


import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 功能描述：
 *
 * @author lkz
 * @date 2022-02-06 7:38
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig{
    //配置swagger2核心配置
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2) //指定api类型位swagger2
                .apiInfo(apiInfo())            //用于定义api文档汇总信息
                .select().apis(RequestHandlerSelectors
                        .basePackage("com.song.flowable.web")) //指定生成文档的controller
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("工作流API") //文档标题
                .contact(new Contact("SteveCode", //作者
                        "关注公众号SteveCode回复：flowable","1965569785@qq.com")) //联系人
                .description("工作流api接口")//详细信息
                .version("1.0.0")//文档版本号
                .termsOfServiceUrl("https://gitee.com/lkz/yqmm-boot.git")//网站地址
                .build();
    }
}
