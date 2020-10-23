package com.egg.manager.common.annotation.log.pc.pservice;

import java.lang.annotation.*;

/**
 * @author zhoucj
 * @description: redis缓存，请在@Service层 实现类上使用
 * @date 2020/10/23
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EggRedisQueryCache {
    /**
     * 数据库缓存KEY
     * @return
     */
    String key() ;

    /**
     * 操作
     * @return
     */
    String action() default "";
    /**
     * 描述
     * @return
     */
    String description() default "";

    /**
     * 过期时间，默认 60*60*1000 毫秒,单位毫秒
     * @return
     */
    long expireTime() default 3600000 ;

    /**
     * 是否记录到 mongodb
     * @return
     */
    boolean flag() default true;

}
