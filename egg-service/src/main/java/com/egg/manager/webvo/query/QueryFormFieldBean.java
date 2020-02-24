package com.egg.manager.webvo.query;

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
    private Object value ;


    public static QueryFormFieldBean dealGetEqualsBean(String fieldName,Object value){
        QueryFormFieldBean bean = new QueryFormFieldBean() ;
        bean.setFieldName(fieldName);
        bean.setValue(value);
        bean.setMatching("equals");
        return bean ;
    }

    public static QueryFormFieldBean dealGetNotEqualsBean(String fieldName,Object value){
        QueryFormFieldBean bean = new QueryFormFieldBean() ;
        bean.setFieldName(fieldName);
        bean.setValue(value);
        bean.setMatching("notEquals");
        return bean ;
    }

    public static QueryFormFieldBean dealGetLikeBean(String fieldName,Object value){
        QueryFormFieldBean bean = new QueryFormFieldBean() ;
        bean.setFieldName(fieldName);
        bean.setValue(value);
        bean.setMatching("like");
        return bean ;
    }
}
