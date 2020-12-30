package com.egg.manager.persistence.commons.base.enums.db;

import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryField;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Getter
@AllArgsConstructor
public enum QueryMatchingEnum {
    EqualsMatch("equals", "=", "相等查询"),
    NotEqualsMatch("notEquals", "!=", "字符串非等查询"),
    LikeMatch("like", "like", "字符串通配查询"),


    ;


    public void dealSetToQueryFormFieldBean(QueryField bean) {
        bean = bean != null ? bean : new QueryField();
        bean.setMatching(this.getValue());
        bean.setSqlMatching(this.getSqlMatchingValue());
    }

    private String value;
    private String sqlMatchingValue;
    private String label;

    public boolean equalsValue(String value) {
        return this.value.equals(value);
    }


}
