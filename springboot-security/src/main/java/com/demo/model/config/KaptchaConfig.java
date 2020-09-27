package com.demo.model.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getDefaultKaptcha()
    {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        /*是否使用边框*/
        properties.setProperty("kaptcha.border","no");
        /*验证码 边框颜色*/
        //properties.setProperty("kaptcha.border.color","black");
        /*验证码干扰线 颜色*/
        properties.setProperty("kaptcha.noise.color","black");
        /*验证码宽度*/
        properties.setProperty("kaptcha.image.width","110");
        /*验证码高度*/
        properties.setProperty("kaptcha.image.height","40");
        //properties.setProperty("kaptcha.session.key","code");
        /*验证码颜色*/
        properties.setProperty("kaptcha.textproducer.font.color","204,128,255");
        /*验证码大小*/
        properties.setProperty("kaptcha.textproducer.font.size","30");
        properties.setProperty("kaptcha.textproducer.char.space","3");
        /*验证码字数*/
        properties.setProperty("kaptcha.textproducer.char.length","4");
        /*验证码 背景渐变色 开始*/
        properties.setProperty("kaptcha.background.clear.from","240,240,240");
        /*验证码渐变色 结束*/
        properties.setProperty("kaptcha.background.clear.to","240,240,240");
        /*验证码字体*/
        properties.setProperty("kaptcha.textproducer.font.names", "Arial,微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
