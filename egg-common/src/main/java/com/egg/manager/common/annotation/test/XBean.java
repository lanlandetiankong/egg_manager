package com.egg.manager.common.annotation.test;

import java.lang.annotation.*;

/**
 * @Description:
 * @ClassName: XBean
 * @Author: zhoucj
 * @Date: 2020/9/21 17:00
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface XBean {
}
