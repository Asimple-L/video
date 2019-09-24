package com.asimple;

import com.asimple.config.WebConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @version 2.0
 * @ProjectName video
 * @description SpringBoot启动类
 * @author Asimple
 * @date 2019/8/30 21:05
 */
@SpringBootApplication
@MapperScan("com.asimple.mapper")
public class App {

    @Autowired
    InternalResourceViewResolver resolver;

    @Bean
    public WebConfig webConfig() {
        return new WebConfig(resolver);
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
