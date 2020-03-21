package com.asimple.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description 配置项工具类
 * @Author Asimple
 * @date 2020/3/21 22:58
 */

@Component
public class PropertiesUtil {

    private Properties properties;

    PropertiesUtil() {
        properties = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("user.properties");
        try { // 加载本地存贮路径
            properties.load(inputStream);
        } catch (IOException e) {
            LogUtil.error("加载配置项失败！"+ e.getMessage());
            e.printStackTrace();
        }
    }

    public String getProperties(String key) {
        try {
            return properties.getProperty(key);
        } catch (Exception e) {
            LogUtil.info("getProperties error : "+e.getMessage());
            return null;
        }
    }

    public String getProperties(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if(StringUtils.isEmpty(value) ) {
            value = defaultValue;
        }
        return value;
    }
}
