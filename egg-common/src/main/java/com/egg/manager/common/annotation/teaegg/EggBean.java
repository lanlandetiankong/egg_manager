package com.egg.manager.common.annotation.teaegg;

import java.lang.annotation.*;

/**
 * @Description:
 * @ClassName: EggBean
 * @Author: zhoucj
 * @Date: 2020/9/21 17:00
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EggBean {
}
