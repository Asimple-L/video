package com.asimple.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Asimple
 * @description 配置项工具类
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
            LogUtil.error("加载配置项失败！" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取配置项的值
     *
     * @param key 配置项名称
     * @return 配置项对应的值
     */
    public String getProperties(String key) {
        try {
            return properties.getProperty(key);
        } catch (Exception e) {
            LogUtil.info("getProperties error : " + e.getMessage());
            return null;
        }
    }

    /**
     * 获取配置项的值，若无此配置项，返回默认值
     *
     * @param key          配置项名称
     * @param defaultValue 默认值
     * @return 配置项对应的值
     */
    public String getProperties(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (StringUtils.isEmpty(value)) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * 判断配置项值是否和传入的值相等
     *
     * @param key        配置项
     * @param openSwitch 预想值
     * @return 是否相等
     */
    public boolean isOn(String key, String openSwitch) {
        String switchKey = getProperties(key);
        return switchKey != null && switchKey.equals(openSwitch);
    }

}
