package com.egg.manager.common.annotation.test;

import com.egg.manager.common.scanner.XBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description: 在@Configuration或启动类 添加，
 * @ClassName: XBeanScan
 * @Author: zhoucj
 * @Date: 2020/9/21 16:59
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(XBeanDefinitionRegistrar.class)
public @interface XBeanScan {
    /**
     * 扫描包，可多个
     * @return
     */
    String[] basePackages();
}
