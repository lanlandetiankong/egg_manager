package com.egg.manager.service.annotation.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * \* note: 在Controller方法参数上使用该注解将会在映射时注入当前登录的 UserAccountXlsModel,同时表明该方法是需要
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 14:47
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 注解在方法参数上，运行时有效,required为true时会自动验证当前登录用户的有效性，默认true
 * \
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentLoginUser {
    boolean required() default true;
}
