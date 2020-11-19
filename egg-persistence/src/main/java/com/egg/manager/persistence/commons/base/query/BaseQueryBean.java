package com.egg.manager.persistence.commons.base.query;

import com.egg.manager.persistence.commons.base.enums.query.mysql.MyQueryMatchingEnum;
import com.egg.manager.persistence.commons.base.query.form.QueryField;

import java.io.Serializable;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class BaseQueryBean implements Serializable {
    public static QueryField gainEq(String fieldName, Object value) {
        QueryField bean = new QueryField();

        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        MyQueryMatchingEnum.EqualsMatch.dealSetToQueryFormFieldBean(bean);
        return bean;
    }

    public static QueryField gainNotEq(String fieldName, Object value) {
        QueryField bean = new QueryField();
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        MyQueryMatchingEnum.NotEqualsMatch.dealSetToQueryFormFieldBean(bean);
        return bean;
    }

    public static QueryField gainLike(String fieldName, Object value) {
        QueryField bean = new QueryField();
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        MyQueryMatchingEnum.LikeMatch.dealSetToQueryFormFieldBean(bean);
        return bean;
    }

    public static QueryField gainEq(String fieldName, Object value, String foreignName) {
        QueryField bean = new QueryField();
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        MyQueryMatchingEnum.EqualsMatch.dealSetToQueryFormFieldBean(bean);
        bean.setForeignName(foreignName);
        return bean;
    }

    public static QueryField gainNotEq(String fieldName, Object value, String foreignName) {
        QueryField bean = new QueryField();
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        MyQueryMatchingEnum.NotEqualsMatch.dealSetToQueryFormFieldBean(bean);
        bean.setForeignName(foreignName);
        return bean;
    }

    public static QueryField gainLike(String fieldName, Object value, String foreignName) {
        QueryField bean = new QueryField();
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        MyQueryMatchingEnum.LikeMatch.dealSetToQueryFormFieldBean(bean);
        bean.setForeignName(foreignName);
        return bean;
    }
}
