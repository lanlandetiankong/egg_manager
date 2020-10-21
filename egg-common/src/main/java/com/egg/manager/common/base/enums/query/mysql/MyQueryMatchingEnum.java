package com.egg.manager.common.base.enums.query.mysql;

import com.egg.manager.common.base.query.form.QueryFormFieldBean;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public enum MyQueryMatchingEnum {
    EqualsMatch("equals", "=", "相等查询"),
    NotEqualsMatch("notEquals", "!=", "字符串非等查询"),
    LikeMatch("like", "like", "字符串通配查询"),


    ;

    MyQueryMatchingEnum(String value, String sqlMatchingValue, String label) {
        this.value = value;
        this.sqlMatchingValue = sqlMatchingValue;
        this.label = label;
    }

    public void dealSetToQueryFormFieldBean(QueryFormFieldBean bean) {
        bean = bean != null ? bean : new QueryFormFieldBean();
        bean.setMatching(this.getValue());
        bean.setSqlMatching(this.getSqlMatchingValue());
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
