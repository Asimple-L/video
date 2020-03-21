package com.asimple;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
