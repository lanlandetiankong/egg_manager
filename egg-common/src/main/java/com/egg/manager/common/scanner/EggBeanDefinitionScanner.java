package com.egg.manager.common.scanner;

import com.egg.manager.common.annotation.teaegg.EggBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * @Description:
 * @ClassName: EggBeanDefinitionScanner
 * @Author: zhoucj
 * @Date: 2020/9/21 17:01
 */
public class EggBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
    public EggBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
        super.addIncludeFilter(new AnnotationTypeFilter(EggBean.class));
    }
}