package com.egg.manager.common.base.query;

import com.egg.manager.common.base.enums.query.mysql.MyQueryMatchingEnum;
import lombok.Data;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/7
 * \* Time: 20:30
 * \* Description:
 * \
 */
@Data
public class QueryFormFieldBean {
    private String fieldName;
    private String matching ;
    private String foreignName ;
    private String sqlMatching ;
    private Object value ;



    public static QueryFormFieldBean dealGetEqualsBean(String fieldName,Object value){
        QueryFormFieldBean bean = new QueryFormFieldBean() ;
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        MyQueryMatchingEnum.EqualsMatch.dealSetToQueryFormFieldBean(bean);
        return bean ;
    }

    public static QueryFormFieldBean dealGetNotEqualsBean(String fieldName,Object value){
        QueryFormFieldBean bean = new QueryFormFieldBean() ;
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        MyQueryMatchingEnum.NotEqualsMatch.dealSetToQueryFormFieldBean(bean);
        return bean ;
    }

    public static QueryFormFieldBean dealGetLikeBean(String fieldName,Object value){
        QueryFormFieldBean bean = new QueryFormFieldBean() ;
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        MyQueryMatchingEnum.LikeMatch.dealSetToQueryFormFieldBean(bean);
        return bean ;
    }

    public static QueryFormFieldBean dealGetEqualsBean(String fieldName,Object value,String foreignName){
        QueryFormFieldBean bean = new QueryFormFieldBean() ;
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        MyQueryMatchingEnum.EqualsMatch.dealSetToQueryFormFieldBean(bean);
        bean.setForeignName(foreignName);
        return bean ;
    }

    public static QueryFormFieldBean dealGetNotEqualsBean(String fieldName,Object value,String foreignName){
        QueryFormFieldBean bean = new QueryFormFieldBean() ;
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        MyQueryMatchingEnum.NotEqualsMatch.dealSetToQueryFormFieldBean(bean);
        bean.setForeignName(foreignName);
        return bean ;
    }

    public static QueryFormFieldBean dealGetLikeBean(String fieldName,Object value,String foreignName){
        QueryFormFieldBean bean = new QueryFormFieldBean() ;
        bean.setFieldName(fieldName);
        bean.setValue(value);
        //枚举值参数设置到bean
        MyQueryMatchingEnum.LikeMatch.dealSetToQueryFormFieldBean(bean);
        bean.setForeignName(foreignName);
        return bean ;
    }
}
