package com.egg.manager.persistence.commons.util;

/**
 * @Description:
 * @ClassName: LongUtils
 * @Author: zhoucj
 * @Date: 2020/11/4 10:11
 */
public class LongUtils {


    public static boolean isNotBlank(Long val) {
        return val != null ;
    }

    public static boolean isBlank(Long val) {
        return val == null ;
    }
}