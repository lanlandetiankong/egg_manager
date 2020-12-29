package com.egg.manager.facade.persistence.enhance.scanner;

import com.egg.manager.facade.persistence.enhance.annotation.custom.EnableEggBeanScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * @author zhoucj
 * @description 注解->注册
 * @date 2020/10/21
 */
public class EggBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 不使用默认过滤器
        EggBeanDefinitionScanner eggBeanDefinitionScanner = new EggBeanDefinitionScanner(registry, false);
        eggBeanDefinitionScanner.setResourceLoader(resourceLoader);
        // 扫描XBeanScan注解指定的包
        eggBeanDefinitionScanner.scan(getBasePackagesToScan(importingClassMetadata));
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 获取{@link EnableEggBeanScan}中声明的扫描包路径
     * @param metadata the meta
     * @return 包路径数组
     */
    private String[] getBasePackagesToScan(AnnotationMetadata metadata) {
        String name = EnableEggBeanScan.class.getName();
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(name, true));
        Assert.notNull(attributes, () -> "No auto-configuration attributes found. Is " + metadata.getClassName()
                + " annotated with " + ClassUtils.getShortName(name) + "?");
        return attributes.getStringArray("basePackages");
    }
}
