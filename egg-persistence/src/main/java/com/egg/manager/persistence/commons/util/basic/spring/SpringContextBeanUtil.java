package com.egg.manager.persistence.commons.util.basic.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description 获取spring上下文
 * @date 2020/10/20
 */
@Component
public class SpringContextBeanUtil implements ApplicationContextAware {
    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }


    public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

}
