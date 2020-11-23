package com.egg.manager.persistence.commons.base.query.pagination.antdv;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;
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

    public QueryFieldArr(){}
    public QueryFieldArr(MyMongoCommonQueryFieldEnum fieldEnum){
        this.addByEnum(fieldEnum);
    }
    public QueryFieldArr(MyMongoCommonQueryFieldEnum... fieldEnums){
        this.addBatchByEnum(fieldEnums);
    }


    public QueryFieldArr addEq(String fieldName, Object value){
        QueryField item = QueryField.gainEq(fieldName,value);
        this.add(item);
        return this ;
    }

    public QueryFieldArr addNotEq(String fieldName, Object value){
        QueryField item = QueryField.gainNotEq(fieldName,value);
        this.add(item);
        return this ;
    }

    public QueryFieldArr addByEnum(MyMongoCommonQueryFieldEnum fieldEnum){
        if (fieldEnum == null) {
            return null;
        }
        QueryField item = QueryField.builder().fieldName(fieldEnum.getFieldName())
                .foreignName(fieldEnum.getForeignName())
                .matching(fieldEnum.getMatching())
                .sqlMatching(fieldEnum.getSqlMatching())
                .value(fieldEnum.getValue())
                .build();
        this.add(item);
        return this ;
    }

    public QueryFieldArr addBatchByEnum(MyMongoCommonQueryFieldEnum... fieldEnums) {
        if (fieldEnums == null || fieldEnums.length == 0) {
            return this;
        }
        return addBatchByEnum(Lists.newArrayList(fieldEnums));
    }

    public QueryFieldArr addBatch(List<QueryField> fields) {
        if (CollectionUtil.isEmpty(fields)) {
            return this;
        }
        this.addAll(fields);
        return this;
    }

    /**
     * 批量-通用查询字段 转QueryFormFieldBean集合
     * @param queryFieldEnumList
     * @return
     */
    public QueryFieldArr addBatchByEnum(List<MyMongoCommonQueryFieldEnum> queryFieldEnumList) {
        if (CollectionUtils.isEmpty(queryFieldEnumList)) {
            return this;
        }
        for (MyMongoCommonQueryFieldEnum queryFieldEnum : queryFieldEnumList) {
            addByEnum(queryFieldEnum);
        }
        return this;
    }

    /**
     * 解析json并构造返回
     * @param json
     * @return
     */
    public static QueryFieldArr parseFromJson(String json) {
        if(StringUtils.isBlank(json)){
            return new QueryFieldArr() ;
        }
        List<QueryField> queryFields = JSONArray.parseArray(json, QueryField.class);
        return new QueryFieldArr().addBatch(queryFields);
    }





}
