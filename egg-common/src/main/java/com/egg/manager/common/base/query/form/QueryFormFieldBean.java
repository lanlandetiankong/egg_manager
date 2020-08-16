package com.egg.manager.common.base.query.form;

import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import com.egg.manager.common.base.enums.query.mysql.MyQueryMatchingEnum;
import com.egg.manager.common.base.query.MyBaseQueryBean;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/7
 * \* Time: 20:30
 * \* Description:
 * \
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryFormFieldBean  extends MyBaseQueryBean {
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


    /**
     * 通用查询字段 转QueryFormFieldBean集合
     * @param fieldEnum
     * @return
     */
    public static QueryFormFieldBean handle_MyMongoCommonQueryFieldEnum_CopyTo_Self(MyMongoCommonQueryFieldEnum fieldEnum){
        if(fieldEnum == null){
            return null ;
        }
        return QueryFormFieldBean.builder().fieldName(fieldEnum.getFieldName())
                .foreignName(fieldEnum.getForeignName())
                .matching(fieldEnum.getMatching())
                .sqlMatching(fieldEnum.getSqlMatching())
                .value(fieldEnum.getValue())
                .build();
    }

    public static List<QueryFormFieldBean> handleBatch_MyMongoCommonQueryFieldEnum_CopyTo_Self(@NotNull MyMongoCommonQueryFieldEnum ... fieldEnum){
        List list = new ArrayList<QueryFormFieldBean>();
        if(fieldEnum == null || fieldEnum.length == 0){
            return list;
        }
        return handleBatch_MyMongoCommonQueryFieldEnum_CopyTo_Self(Lists.newArrayList(fieldEnum));
    }

    /**
     * 批量-通用查询字段 转QueryFormFieldBean集合
     * @param queryFieldEnumList
     * @return
     */
    public static List<QueryFormFieldBean> handleBatch_MyMongoCommonQueryFieldEnum_CopyTo_Self(List<MyMongoCommonQueryFieldEnum> queryFieldEnumList){
        List list = new ArrayList<QueryFormFieldBean>();
        if(CollectionUtils.isEmpty(queryFieldEnumList)){
            return list;
        }
        for(MyMongoCommonQueryFieldEnum queryFieldEnum : queryFieldEnumList){
            list.add(handle_MyMongoCommonQueryFieldEnum_CopyTo_Self(queryFieldEnum));
        }
        return list ;
    }

}