package com.egg.manager.persistence.utils.reflex.config;

import lombok.Data;

/**
 * @Description: 反射
 * @ClassName: EggPojoReflexFieldConfig
 * @Author: zhoucj
 * @Date: 2020/9/18 16:04
 */
@Data
public class EggPojoReflexFieldConfig<K> {
    /**
     * pojo中的字段名
     */
    private String fieldName ;
    /**
     * 字段class
     */
    private Class<K> clazz ;
    /**
     * pojo对应的数据库字段名
     */
    private String columnName ;

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
