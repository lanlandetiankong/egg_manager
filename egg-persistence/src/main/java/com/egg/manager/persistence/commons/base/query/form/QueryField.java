package com.egg.manager.persistence.commons.base.query.form;

import com.egg.manager.persistence.commons.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import com.egg.manager.persistence.commons.base.query.BaseQueryBean;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryField extends BaseQueryBean {
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 匹配方式
     */
    private String matching;
    /**
     * 外键名
     */
    private String foreignName;
    /**
     * 数据库匹配方式
     */
    private String sqlMatching;
    /**
     * 匹配的值
     */
    private Object value;





    /**
     * 通用查询字段 转QueryFormFieldBean集合
     * @param fieldEnum
     * @return
     */
    public static QueryField handle_MyMongoCommonQueryFieldEnum_CopyTo_Self(MyMongoCommonQueryFieldEnum fieldEnum) {
        if (fieldEnum == null) {
            return null;
        }
        return QueryField.builder().fieldName(fieldEnum.getFieldName())
                .foreignName(fieldEnum.getForeignName())
                .matching(fieldEnum.getMatching())
                .sqlMatching(fieldEnum.getSqlMatching())
                .value(fieldEnum.getValue())
                .build();
    }

    public static List<QueryField> handleBatch_MyMongoCommonQueryFieldEnum_CopyTo_Self(MyMongoCommonQueryFieldEnum... fieldEnum) {
        List list = new ArrayList<QueryField>();
        if (fieldEnum == null || fieldEnum.length == 0) {
            return list;
        }
        return handleBatch_MyMongoCommonQueryFieldEnum_CopyTo_Self(Lists.newArrayList(fieldEnum));
    }

    /**
     * 批量-通用查询字段 转QueryFormFieldBean集合
     * @param queryFieldEnumList
     * @return
     */
    public static List<QueryField> handleBatch_MyMongoCommonQueryFieldEnum_CopyTo_Self(List<MyMongoCommonQueryFieldEnum> queryFieldEnumList) {
        List list = new ArrayList<QueryField>();
        if (CollectionUtils.isEmpty(queryFieldEnumList)) {
            return list;
        }
        for (MyMongoCommonQueryFieldEnum queryFieldEnum : queryFieldEnumList) {
            list.add(handle_MyMongoCommonQueryFieldEnum_CopyTo_Self(queryFieldEnum));
        }
        return list;
    }

}
