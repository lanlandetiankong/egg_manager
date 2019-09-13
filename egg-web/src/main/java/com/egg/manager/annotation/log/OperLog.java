package com.egg.manager.annotation.log;

import java.lang.annotation.*;

/**
 * 操作日志记录 注解
 * aop 对controller 切面 记录
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {

    /**
     * 模块名称
     * @return
     */
    String modelName() default  "" ;

    /**
     * 操作
     * @return
     */
    String action() default "" ;

    String description() default "" ;
}
