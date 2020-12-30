package com.egg.manager.persistence.enhance.annotation.custom;

import java.lang.annotation.*;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EggBean {
}
