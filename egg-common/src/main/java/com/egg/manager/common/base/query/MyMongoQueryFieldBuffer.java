package com.egg.manager.common.base.query;

import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * \* note: 数据库查询字段 追加(除Request取得外)
 * \* User: zhouchengjie
 * \* Date: 2020/8/8
 * \* Time: 19:06
 * \* Description:
 * \
 */
@Data
public class MyMongoQueryFieldBuffer {

    private List<QueryFormFieldBean> queryFormFieldBeanList = new ArrayList<>();

    /**
     * 批量添加 MyMongoCommonQueryFieldEnum 定义的项
     * @param enums
     */
    public MyMongoQueryFieldBuffer(MyMongoCommonQueryFieldEnum... enums){
        if(enums != null && enums.length > 0){
            List<QueryFormFieldBean> queryFormFieldBeans = QueryFormFieldBean.handleBatch_MyMongoCommonQueryFieldEnum_CopyTo_Self(enums);
            queryFormFieldBeanList.addAll(queryFormFieldBeans);
        }
    }

    /**
     * 添加 QueryFormFieldBean 到集合
     * @param fieldBean
     */
    public void addItem(QueryFormFieldBean fieldBean){
        if(fieldBean != null){
            queryFormFieldBeanList.add(fieldBean);
        }
    }

}
