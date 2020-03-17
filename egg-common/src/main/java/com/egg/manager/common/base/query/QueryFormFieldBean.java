package com.egg.manager.common.base.query;

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
        bean.setMatching("equals");
        bean.setSqlMatching("=");
        return bean ;
    }

    public static QueryFormFieldBean dealGetNotEqualsBean(String fieldName,Object value){
        QueryFormFieldBean bean = new QueryFormFieldBean() ;
        bean.setFieldName(fieldName);
        bean.setValue(value);
        bean.setMatching("notEquals");
        bean.setSqlMatching("!=");
        return bean ;
    }

    public static QueryFormFieldBean dealGetLikeBean(String fieldName,Object value){
        QueryFormFieldBean bean = new QueryFormFieldBean() ;
        bean.setFieldName(fieldName);
        bean.setValue(value);
        bean.setMatching("like");
        bean.setSqlMatching("like");
        return bean ;
    }

    public static QueryFormFieldBean dealGetEqualsBean(String fieldName,Object value,String foreignName){
        QueryFormFieldBean bean = new QueryFormFieldBean() ;
        bean.setFieldName(fieldName);
        bean.setValue(value);
        bean.setMatching("equals");
        bean.setSqlMatching("=");
        bean.setForeignName(foreignName);
        return bean ;
    }

    public static QueryFormFieldBean dealGetNotEqualsBean(String fieldName,Object value,String foreignName){
        QueryFormFieldBean bean = new QueryFormFieldBean() ;
        bean.setFieldName(fieldName);
        bean.setValue(value);
        bean.setMatching("notEquals");
        bean.setSqlMatching("!=");
        bean.setForeignName(foreignName);
        return bean ;
    }

    public static QueryFormFieldBean dealGetLikeBean(String fieldName,Object value,String foreignName){
        QueryFormFieldBean bean = new QueryFormFieldBean() ;
        bean.setFieldName(fieldName);
        bean.setValue(value);
        bean.setMatching("like");
        bean.setSqlMatching("like");
        bean.setForeignName(foreignName);
        return bean ;
    }
}
