package com.egg.manager.persistence.enhance.annotation.log.obl;

import com.egg.manager.persistence.commons.base.constant.basic.AspectTypeConstant;

import java.lang.annotation.*;


/**
 * @author zhoucj
 * @description OolongBlog-查询接口日志记录到数据库 注解
 * aop 对controller 切面 记录
 * 1、当action为空时，会改为取用@ApiOperation的value
 * 2、当description为空时，会改为取用@ApiOperation的notes
 * @date 2020/12/03
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OblPcWebQueryLog {


    /**
     * 操作
     * @return
     */
    String action() default "";

    /**
     * 相对于项目的 全路径
     * @return
     */
    String fullPath();

    /**
     * 描述
     * @return
     */
    String description() default "";

    /**
     * 是否记录到 mongodb
     * @return
     */
    boolean flag() default true;

    /**
     * 是否打印出 计时器日志
     * @return
     */
    boolean printWatchFlag() default false;

    /**
     * 操作类型
     * @return
     */
    String type() default AspectTypeConstant.QUERY_API;
}
