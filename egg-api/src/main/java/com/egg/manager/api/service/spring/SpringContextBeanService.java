package com.egg.manager.api.service.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring上下文
 */
@Component
public class SpringContextBeanService implements ApplicationContextAware{
    private static ApplicationContext context = null ;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext ;
    }


    public static <T> T getBean(String name) {
        return (T) context.getBean(name) ;
    }
    public static <T> T getBean(Class<T> clazz) {
        return (T) context.getBean(clazz) ;
    }

}