package com.asimple.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * @ProjectName video
 * @Description 配置文件读取类设置
 * @Author Asimple
 * @date 2019/9/13 12:35
 */
public class WebConfig {

    public WebConfig(InternalResourceViewResolver resolver) {

        Resource resource = new ClassPathResource("user.properties");
        Properties props = null;
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resolver.setAttributes(props);
    }

}
