package com.egg.manager.persistence.commons.base.enums.query.mongo;

import com.egg.manager.persistence.commons.base.query.form.QueryField;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public enum MyMongoQueryMatchingEnum {
    EqualsMatch("equals", "=", "相等查询"),
    NotEqualsMatch("notEquals", "!=", "字符串非等查询"),
    LikeMatch("like", "like", "字符串通配查询"),


    ;

    MyMongoQueryMatchingEnum(String value, String sqlMatchingValue, String label) {
        this.value = value;
        this.sqlMatchingValue = sqlMatchingValue;
        this.label = label;
    }

    /**
     * 将枚举的参数设置到QueryFormFieldBean
     * @param bean
     */
    public void dealSetToQueryFormFieldBean(QueryField bean) {
        bean = bean != null ? bean : new QueryField();
        bean.setMatching(this.getValue());
        bean.setSqlMatching(this.getSqlMatchingValue());
    }

    public boolean equalsValue(String value) {
        return this.value.equals(value);
    }

    private String value;
    private String sqlMatchingValue;
    private String label;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSqlMatchingValue() {
        return sqlMatchingValue;
    }

    public void setSqlMatchingValue(String sqlMatchingValue) {
        this.sqlMatchingValue = sqlMatchingValue;
    }
}
