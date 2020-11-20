package com.egg.manager.persistence.commons.util.reflex;

import com.google.common.collect.Lists;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
public class MyReflexUtil {


    public static Update getMoUpdateByObjectWithIgnores(Object object, boolean skipNull, String... args) {
        List list = (args == null) ? new ArrayList() : Lists.newArrayList(args);
        return getMoUpdateByObject(object, skipNull, list);
    }

    /**
     * 将查询条件对象转换为update
     * @param object
     * @return
     * @author Jason
     */
    public static Update getMoUpdateByObject(Object object, boolean skipNull, List<String> ignoreKeys) {
        Update update = new Update();
        ignoreKeys = ignoreKeys != null ? ignoreKeys : new ArrayList<>();
        //是否取得继承的上一级baseMO的字段
        boolean isWithSuper = true;
        Set<String> filedSet = getFiledName(object, isWithSuper);
        for (String filedName : filedSet) {
            if (ignoreKeys.contains(filedName)) {
                //该字段将不会被设置到Update
                continue;
            }
            Object filedValue = getFieldValueByName(filedName, object);
            if (filedValue == null && skipNull == true) {
                continue;
            }
            update.set(filedName, filedValue);
        }
        return update;
    }

    /***
     * 获取对象属性返回字符串数组
     * @param o
     * @param isWithSuper 是否也要继承
     * @return
     */
    private static Set<String> getFiledName(Object o, boolean isWithSuper) {
        //pojo定义的字段
        Field[] fields = o.getClass().getDeclaredFields();
        Set<String> fieldNames = new HashSet<>();
        for (int i = 0; i < fields.length; ++i) {
            fieldNames.add(fields[i].getName());
        }
        if (isWithSuper) {
            //pojo继承的实体类
            Class superClazz = o.getClass().getSuperclass();
            if (superClazz != null) {
                Field[] superFields = superClazz.getDeclaredFields();
                if (superFields != null && superFields.length > 0) {
                    for (Field superFieldItem : superFields) {
                        fieldNames.add(superFieldItem.getName());
                    }
                }
            }
        }
        return fieldNames;
    }

    /***
     * 根据属性获取对象属性值
     * @param fieldName
     * @param o
     * @return
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String e = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + e + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception var6) {
            return null;
        }
    }
}
