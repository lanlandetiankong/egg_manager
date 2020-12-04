package com.egg.manager.persistence.commons.base.query;

import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.query.QueryMatchingEnum;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryField;

import java.io.Serializable;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class BaseQueryBean implements Serializable {
    private static final long serialVersionUID = -6749643403613166598L;

    public final static int DEFAULT_PAGE = 0;
    public final static int DEFAULT_SIZE = 10;
    public final static String PARAMETER_PAGINATION_OBJ = WebApiConstant.FIELDNAME_PAGINATION_OBJ;
    public final static String PARAMETER_QUERY_OBJ = WebApiConstant.FIELDNAME_QUERY_OBJ;
    public final static String PARAMETER_SORT_OBJ = WebApiConstant.FIELDNAME_SORT_OBJ;



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
