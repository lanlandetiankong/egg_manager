package com.egg.manager.persistence.commons.util.basic.dubbo;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:  Dubbo 工具类
 * @author zhoucj
 * @date 2020/12/29
 */
@Component
public class DubboUtils implements ApplicationContextAware {
    private static ApplicationContext context = null;
    /**
     * bean-list转map，仅用于查询，请勿对这个map进行增删改等。
     */
    private static final Map<Class<?>,ReferenceBean<?>> referenceBeanMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        ReferenceAnnotationBeanPostProcessor dubboContext = context.getBean(ReferenceAnnotationBeanPostProcessor.class);
        Collection<ReferenceBean<?>> referenceBeans = dubboContext.getReferenceBeans();
        for (ReferenceBean<?> referenceBean : referenceBeans) {
            Class<?> objectType = referenceBean.getObjectType();
            referenceBeanMap.put(objectType,referenceBean);
        }
    }

    public static <T> T getRefBean(Class<T> requiredType){
        try{
            ReferenceBean<?> referenceBean = referenceBeanMap.get(requiredType);
            if(referenceBean != null){
                return (T)referenceBean.getObject();
            }
        }   catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getRefBean2(Class<T> requiredType){
        try{
            ReferenceAnnotationBeanPostProcessor dubboContext = context.getBean(ReferenceAnnotationBeanPostProcessor.class);
            Collection<ReferenceBean<?>> referenceBeans = dubboContext.getReferenceBeans();
            for (ReferenceBean<?> referenceBean : referenceBeans) {
                Class<?> objectType = referenceBean.getObjectType();
                if(objectType == requiredType){
                    return (T)referenceBean.getObject();
                }
            }
        }   catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}