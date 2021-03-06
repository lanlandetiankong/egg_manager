package com.egg.manager.persistence.commons.util.basic.jvm.reflex.config;

import lombok.Data;

/**
 * @author zhoucj
 * @description 反射
 * @date 2020/9/18 16:04
 */
@Data
public class EggPojoReflexFieldConfig<K> {
    /**
     * pojo中的字段名
     */
    private String fieldName;
    /**
     * 字段class
     */
    private Class<K> clazz;
    /**
     * pojo对应的数据库字段名
     */
    private String columnName;

    public EggPojoReflexFieldConfig(String fieldName, Class<K> clazz) {
        this.fieldName = fieldName;
        this.clazz = clazz;
    }

    public EggPojoReflexFieldConfig(String fieldName, Class<K> clazz, String columnName) {
        this.fieldName = fieldName;
        this.clazz = clazz;
        this.columnName = columnName;
    }
}
