package com.egg.manager.common.annotation.log;

import java.lang.annotation.*;

/**
 * 操作日志记录到数据库 注解
 * aop 对controller 切面 记录
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {


    /**
     * 操作
     * @return
     */
    String action() default "" ;

    /**
     * 相对于项目的 全路径
     * @return
     */
    String fullPath() ;

    String description() default "" ;

    boolean flag()  default true ;
}
