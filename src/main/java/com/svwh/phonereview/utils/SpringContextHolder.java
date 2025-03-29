package com.svwh.phonereview.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/14 16:09
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 根据名称获取Bean
     */
    public static <T> T getBean(String name) {
        checkApplicationContext();
        try {
            return (T) applicationContext.getBean(name);
        } catch (BeansException e) {
            return null;
        }
    }

    /**
     * 根据类型获取Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        try {
            return applicationContext.getBean(clazz);
        } catch (BeansException e) {
            return null;
        }
    }

    /**
     * 根据名称和类型获取Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        checkApplicationContext();
        try {
            return applicationContext.getBean(name, clazz);
        } catch (BeansException e) {
            return null;
        }
    }

    /**
     * 检查上下文是否初始化
     */
    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext 未注入，请确保 ContextHolder 已被 Spring 管理");
        }
    }
}
