package com.egg.manager.persistence.enhance.annotation.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhoucj
 * @description: 在Controller方法参数上使用该注解将会在映射时注入当前登录的 DefineTenant,注解在方法参数上，运行时有效,required为true时会自动验证当前登录用户的有效性，默认true
 * @date 2020/10/21
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentLoginerBelongTenant {
    /**
     * 是否必须要登录
     * @return
     */
    boolean required() default true;
}
