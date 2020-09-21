package com.egg.manager.common.scanner;

import com.egg.manager.common.annotation.test.XBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * @Description:
 * @ClassName: XBeanDefinitionScanner
 * @Author: zhoucj
 * @Date: 2020/9/21 17:01
 */
public class XBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
    public XBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
        super.addIncludeFilter(new AnnotationTypeFilter(XBean.class));
    }
}
