package com.egg.manager.persistence.commons.util.basic.jvm.reflex;

import cn.hutool.core.util.ReflectUtil;
import com.egg.manager.persistence.commons.util.basic.jvm.reflex.config.EggPojoReflexFieldConfig;
import com.egg.manager.persistence.commons.util.data.str.MyStringUtil;

import java.lang.reflect.Method;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class EggReflexUtil {


    /**
     * 根据字段名设置pojo对应字段的值
     * @param obj
     * @param fieldConfig 字段相关配置
     * @param value
     */
    public static void handlePojoSetFieldValue(Object obj, EggPojoReflexFieldConfig fieldConfig, Object value) {
        handlePojoSetFieldValue(obj, fieldConfig.getFieldName(), fieldConfig.getClazz(), value);
    }

    /**
     * 根据字段名设置pojo对应字段的值
     * @param t
     * @param fieldName
     * @param valClz
     * @param value
     */
    public static <T> void handlePojoSetFieldValue(T t, String fieldName, Class valClz, Object value) {
        //取得setter方法名
        String methodName = "set" + MyStringUtil.captureFirstWord(fieldName);
        Method method = ReflectUtil.getMethod(t.getClass(), methodName, valClz);
        if (method == null) {
            return;
        }
        ReflectUtil.invoke(t, method, value);
    }


    /**
     * 根据字段名取得pojo对应字段的值
     * @param obj
     * @param fieldConfig
     */
    public static <T, K> K handlePojoGetFieldValue(T obj, EggPojoReflexFieldConfig<K> fieldConfig) {
        return handlePojoGetFieldValue(obj, fieldConfig.getFieldName(), fieldConfig.getClazz());
    }

    /**
     * 根据字段名取得pojo对应字段的值
     * @param obj
     * @param fieldName
     * @param valClz    返回值class
     */
    public static <T, K> K handlePojoGetFieldValue(T obj, String fieldName, Class<K> valClz) {
        //取得getter方法名
        String methodName = "get" + MyStringUtil.captureFirstWord(fieldName);
        Method method = ReflectUtil.getMethod(obj.getClass(), methodName);
        if (method == null) {
            return null;
        }
        Object valObj = ReflectUtil.invoke(obj, method);
        if (valObj == null) {
            return null;
        }
        return (K) valObj;
    }

    /**
     * (请确保pojo都能有无参构造)
     * @param clazz
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T handlePojoGetInstance(Class<T> clazz, Object... params) {
        return ReflectUtil.newInstance(clazz, params);
    }
}
