package com.egg.manager.common.util.reflex;

import com.google.common.collect.Lists;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @ClassName: MyReflexUtil
 * @Author: zhoucj
 * @Date: 2020/7/27 11:56
 */
public class MyReflexUtil {


    public static Update getMOUpdateByObjectWithIgnores(Object object, boolean skipNull, String ... args){
        List list = (args == null) ? new ArrayList() : Lists.newArrayList(args);
        return getMOUpdateByObject(object,skipNull,list);
    }
    /**
     * 将查询条件对象转换为update
     *
     * @param object
     * @return
     * @author Jason
     */
    public static Update getMOUpdateByObject(Object object, boolean skipNull, List<String> ignoreKeys) {
        Update update = new Update();
        ignoreKeys = ignoreKeys != null ? ignoreKeys : new ArrayList<>() ;
        String[] fileds = getFiledName(object);
        for (int i = 0; i < fileds.length; i++) {
            String filedName = fileds[i];
            if (ignoreKeys.contains(filedName)){
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
     * @return
     */
    private static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            fieldNames[i] = fields[i].getName();
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
            Method method = o.getClass().getMethod(getter, new Class[0]);
            return method.invoke(o, new Object[0]);
        } catch (Exception var6) {
            return null;
        }
    }
}
