package com.egg.manager.persistence.enhance.annotation.teaegg;

import com.egg.manager.persistence.enhance.scanner.EggBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhoucj
 * @description: 在@Configuration或启动类 添加，
 * @date 2020/10/21
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(EggBeanDefinitionRegistrar.class)
public @interface EnableEggBeanScan {
    /**
     * 扫描包，可多个
     * @return
     */
    String[] basePackages();
}
