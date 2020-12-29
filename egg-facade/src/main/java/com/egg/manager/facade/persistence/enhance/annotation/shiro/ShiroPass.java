package com.egg.manager.facade.persistence.enhance.annotation.shiro;

import java.lang.annotation.*;

/**
 * @author zhoucj
 * @description shiro 跳过 注解
 * @date 2020/10/21
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShiroPass {

}
