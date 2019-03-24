package com.asimple.util;

import org.slf4j.LoggerFactory;

/**
 * @ProjectName video
 * @Description: 日志工具类
 * @author: Asimple
 */
public class LogUtil {

    public static void trace(Class<?> clazz, String message) {
        LoggerFactory.getLogger(clazz).trace("--> "+message);
    }

    public static void trace(String name, String message) {
        LoggerFactory.getLogger(name).trace("--> "+message);
    }

    public static void trace(String message) {
        LoggerFactory.getLogger("LogUtil traceLogger").trace("--> "+message);
    }

    public static void debug(Class<?> clazz, String message) {
        LoggerFactory.getLogger(clazz).debug("--> "+message);
    }

    public static void debug(String name, String message) {
        LoggerFactory.getLogger(name).debug("--> "+message);
    }

    public static void debug(String message) {
        LoggerFactory.getLogger("LogUtil debugLogger").debug("--> "+message);
    }

    public static void info(Class<?> clazz, String message) {
        LoggerFactory.getLogger(clazz).info("--> "+message);
    }

    public static void info(String name, String message) {
        LoggerFactory.getLogger(name).info("--> "+message);
    }

    public static void info(String message) {
        LoggerFactory.getLogger("LogUtil infoLogger").info("--> "+message);
    }

    public static void warn(Class<?> clazz, String message) {
        LoggerFactory.getLogger(clazz).warn("--> "+message);
    }

    public static void warn(String name, String message) {
        LoggerFactory.getLogger(name).warn("--> "+message);
    }

    public static void warn(String message) {
        LoggerFactory.getLogger("LogUtil warnLogger").warn("--> "+message);
    }

    public static void error(Class<?> clazz, String message) {
        LoggerFactory.getLogger(clazz).error("--> "+message);
    }

    public static void error(String name, String message) {
        LoggerFactory.getLogger(name).error("--> "+message);
    }

    public static void error(String message) {
        LoggerFactory.getLogger("LogUtil errorLogger").error("--> "+message);
    }


}
