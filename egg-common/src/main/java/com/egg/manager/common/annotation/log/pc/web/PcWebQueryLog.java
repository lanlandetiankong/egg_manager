package com.egg.manager.common.annotation.log.pc.web;

import java.lang.annotation.*;

/**
 * 操作日志记录到数据库 注解
 * aop 对controller 切面 记录
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PcWebQueryLog {


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

    /**
     * 描述
     * @return
     */
    String description() default "" ;

    /**
     * 是否记录到 mongodb
     * @return
     */
    boolean flag()  default true ;
}
