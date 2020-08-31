package com.egg.manager.common.annotation.shiro;

import java.lang.annotation.*;

/**
 * shiro 跳过 注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShiroPass {

}
