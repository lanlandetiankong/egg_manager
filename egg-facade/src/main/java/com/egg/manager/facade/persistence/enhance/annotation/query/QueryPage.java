package com.egg.manager.facade.persistence.enhance.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhoucj
 * @description: 查询分页 配置
 * @date 2020/11/23
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryPage {
    Class tClass() default Object.class;

    /**
     * 添加->时间倒序
     * @return
     */
    boolean withCreateTimeDesc() default true;
}
