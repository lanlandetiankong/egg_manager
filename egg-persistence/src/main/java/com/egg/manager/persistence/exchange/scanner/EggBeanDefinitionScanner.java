package com.egg.manager.persistence.exchange.scanner;

import com.egg.manager.persistence.exchange.annotation.teaegg.EggBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
public class EggBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
    public EggBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
        super.addIncludeFilter(new AnnotationTypeFilter(EggBean.class));
    }
}
