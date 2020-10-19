package com.egg.manager.common.annotation.log.pc.web;

import java.lang.annotation.*;

/**
 * 操作日志记录到数据库 注解
 * aop 对controller 切面 记录
 * 1、当action为空时，会改为取用@ApiOperation的value
 * 2、当description为空时，会改为取用@ApiOperation的notes
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PcWebOperationLog {


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
