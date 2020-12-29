package com.egg.manager.facade.persistence.commons.base.query;

import com.egg.manager.facade.persistence.commons.base.enums.db.QueryMatchingEnum;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.QueryField;

import java.io.Serializable;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class BaseQueryBean implements Serializable {
    private static final long serialVersionUID = -6749643403613166598L;


    public static QueryField gainEq(String fieldName, Object value) {
        QueryField bean = new QueryField();

        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        QueryMatchingEnum.EqualsMatch.dealSetToQueryFormFieldBean(bean);
        return bean;
    }

    public static QueryField gainNotEq(String fieldName, Object value) {
        QueryField bean = new QueryField();
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        QueryMatchingEnum.NotEqualsMatch.dealSetToQueryFormFieldBean(bean);
        return bean;
    }

    public static QueryField gainLike(String fieldName, Object value) {
        QueryField bean = new QueryField();
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        QueryMatchingEnum.LikeMatch.dealSetToQueryFormFieldBean(bean);
        return bean;
    }

    public static QueryField gainEq(String fieldName, Object value, String foreignName) {
        QueryField bean = new QueryField();
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        QueryMatchingEnum.EqualsMatch.dealSetToQueryFormFieldBean(bean);
        bean.setForeignName(foreignName);
        return bean;
    }

    public static QueryField gainNotEq(String fieldName, Object value, String foreignName) {
        QueryField bean = new QueryField();
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        QueryMatchingEnum.NotEqualsMatch.dealSetToQueryFormFieldBean(bean);
        bean.setForeignName(foreignName);
        return bean;
    }

    public static QueryField gainLike(String fieldName, Object value, String foreignName) {
        QueryField bean = new QueryField();
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        QueryMatchingEnum.LikeMatch.dealSetToQueryFormFieldBean(bean);
        bean.setForeignName(foreignName);
        return bean;
    }
}
