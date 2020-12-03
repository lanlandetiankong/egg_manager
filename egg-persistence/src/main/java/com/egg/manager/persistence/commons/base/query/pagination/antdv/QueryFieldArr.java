package com.egg.manager.persistence.commons.base.query.pagination.antdv;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class QueryFieldArr extends ArrayList<QueryField> implements FieldConst {

    public QueryFieldArr() {
    }

    /**
     * 添加 字段相等条件
     * @param fieldName db字段名
     * @param value     匹配的值
     * @return
     */
    public QueryFieldArr addEq(String fieldName, Object value) {
        return addEq(true, fieldName, value);
    }

    /**
     * 添加 字段相等条件
     * @param flag      是否添加
     * @param fieldName db字段名
     * @param value     匹配的值
     * @return
     */
    public QueryFieldArr addEq(Boolean flag, String fieldName, Object value) {
        if (!Boolean.TRUE.equals(flag)) {
            return this;
        }
        QueryField item = QueryField.gainEq(fieldName, value);
        this.add(item);
        return this;
    }

    /**
     * 添加 字段不相等条件
     * @param fieldName db字段名
     * @param value     匹配的值
     * @return
     */
    public QueryFieldArr addNotEq(String fieldName, Object value) {
        return addNotEq(true, fieldName, value);
    }

    /**
     * 添加 字段不相等条件
     * @param flag      是否添加
     * @param fieldName db字段名
     * @param value     匹配的值
     * @return
     */
    public QueryFieldArr addNotEq(Boolean flag, String fieldName, Object value) {
        if (!Boolean.TRUE.equals(flag)) {
            return this;
        }
        QueryField item = QueryField.gainNotEq(fieldName, value);
        this.add(item);
        return this;
    }

    public QueryFieldArr addBatch(List<QueryField> fields) {
        if (CollectionUtil.isEmpty(fields)) {
            return this;
        }
        this.addAll(fields);
        return this;
    }

    /**
     * 解析json并构造返回
     * @param json
     * @return
     */
    public static QueryFieldArr parseFromJson(String json) {
        if (StringUtils.isBlank(json)) {
            return new QueryFieldArr();
        }
        List<QueryField> queryFields = JSONArray.parseArray(json, QueryField.class);
        return new QueryFieldArr().addBatch(queryFields);
    }


}
