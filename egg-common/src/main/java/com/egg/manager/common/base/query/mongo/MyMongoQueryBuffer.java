package com.egg.manager.common.base.query.mongo;

import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonSortFieldEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.MyBaseQueryBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.google.common.collect.Sets;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * \* note: 数据库查询字段 ，用作dubbo各个service之间的传输对象
 * \* User: zhouchengjie
 * \* Date: 2020/8/8
 * \* Time: 19:06
 * \* Description:
 * \
 */
@Data
public class MyMongoQueryBuffer extends MyBaseQueryBean {

    /**
     * (除HttpServletRequest取得外)添加的查询字段
     */
    private List<QueryFormFieldBean> queryFormFieldBeanList = new ArrayList<>();
    /**
     * 添加到 HttpServletRequest取得的排序字段前
     */
    private List<AntdvSortBean> frontSortList = new ArrayList<>();
    /**
     * 追加到 HttpServletRequest取得的排序字段后
     */
    private List<AntdvSortBean> behindSortList = new ArrayList<>();
    /**
     * 可查询过滤字段白名单
     */
    private Set<String> blackQueryFieldSets = new HashSet<>();
    /**
     * 可查询过滤字段白名单
     */
    private Set<String> whiteQueryFieldSets = new HashSet<>();


    private Boolean whiteSetsFlag = false ;

    public MyMongoQueryBuffer() {
    }

    /**
     * 批量添加 MyMongoCommonQueryFieldEnum 定义的项
     * @param enums
     */
    public MyMongoQueryBuffer(MyMongoCommonQueryFieldEnum... enums){
        if(enums != null && enums.length > 0){
            List<QueryFormFieldBean> queryFormFieldBeans = QueryFormFieldBean.handleBatch_MyMongoCommonQueryFieldEnum_CopyTo_Self(enums);
            this.queryFormFieldBeanList.addAll(queryFormFieldBeans);
        }
    }

    /**
     * 添加 QueryFormFieldBean 到集合
     * @param fieldBean
     */
    public void addQueryFieldItem(QueryFormFieldBean fieldBean){
        if(fieldBean != null){
            this.queryFormFieldBeanList.add(fieldBean);
        }
    }

    public MyMongoQueryBuffer addFrontSortItem(MyMongoCommonSortFieldEnum ... commonSortFieldEnumArr){
        if(commonSortFieldEnumArr != null && commonSortFieldEnumArr.length > 0){
            for(MyMongoCommonSortFieldEnum sortFieldEnum : commonSortFieldEnumArr){
                this.frontSortList.add(0,new AntdvSortBean(sortFieldEnum.getFieldName(),sortFieldEnum.getAscFlag()));
            }
        }
        return this ;
    }

    public MyMongoQueryBuffer addBehindSortItem(MyMongoCommonSortFieldEnum ... commonSortFieldEnumArr){
        if(commonSortFieldEnumArr != null && commonSortFieldEnumArr.length > 0){
            for(MyMongoCommonSortFieldEnum sortFieldEnum : commonSortFieldEnumArr){
                this.behindSortList.add(new AntdvSortBean(sortFieldEnum.getFieldName(),sortFieldEnum.getAscFlag()));
            }
        }
        return this ;
    }

    public MyMongoQueryBuffer addFrontSortItem(AntdvSortBean antdvSortBean){
        if(antdvSortBean != null){
            this.frontSortList.add(antdvSortBean) ;
        }
        return this ;
    }
    public MyMongoQueryBuffer addBehindSortItem(AntdvSortBean antdvSortBean){
        if(antdvSortBean != null){
            this.behindSortList.add(antdvSortBean) ;
        }
        return this ;
    }
    public MyMongoQueryBuffer addBlackQueryFieldItem(String ... fieldNameArr){
        if (fieldNameArr != null && fieldNameArr.length > 0){
            for (String fieldName : fieldNameArr){
                if(StringUtils.isNotBlank(fieldName)){
                    this.blackQueryFieldSets.add(fieldName) ;
                }
            }
        }
        return this ;
    }
    public MyMongoQueryBuffer addWhiteQueryFieldItem(String ... fieldNameArr){
        if (fieldNameArr != null && fieldNameArr.length > 0){
            for (String fieldName : fieldNameArr){
                if(StringUtils.isNotBlank(fieldName)){
                    this.whiteQueryFieldSets.add(fieldName) ;
                }
            }
        }
        return this ;
    }

    /**
     * 取得经过处理后的自身
     * @return
     */
    public MyMongoQueryBuffer getRefreshedSelf(){
        if(CollectionUtils.isNotEmpty(this.whiteQueryFieldSets) && CollectionUtils.isNotEmpty(this.blackQueryFieldSets)){
            //取得黑名单与白名单的交集
            Sets.SetView<String> intersection  = Sets.intersection(this.whiteQueryFieldSets, this.blackQueryFieldSets);
            //黑名单 优先于 白名单
            if(intersection != null ){
                Iterator<String> iterator = intersection.iterator();
                while(iterator.hasNext()){
                    this.whiteQueryFieldSets.remove(iterator.next());
                }
            }
        }
        return this ;
    }

}
