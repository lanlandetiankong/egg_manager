package com.egg.manager.common.annotation.teaegg;

import com.egg.manager.common.scanner.EggBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description: 在@Configuration或启动类 添加，
 * @ClassName: EnableEggBeanScan
 * @Author: zhoucj
 * @Date: 2020/9/21 16:59
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
