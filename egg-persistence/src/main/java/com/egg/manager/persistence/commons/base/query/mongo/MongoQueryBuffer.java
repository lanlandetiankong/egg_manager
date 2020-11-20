package com.egg.manager.persistence.commons.base.query.mongo;

import com.egg.manager.persistence.commons.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import com.egg.manager.persistence.commons.base.enums.query.mongo.MyMongoCommonSortFieldEnum;
import com.egg.manager.persistence.commons.base.query.BaseQueryBean;
import com.egg.manager.persistence.commons.base.query.form.QueryField;
import com.egg.manager.persistence.commons.base.query.form.QueryFieldArr;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author zhoucj
 * @description数据库查询字段，用作dubbo各个service之间的传输对象
 * @date 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MongoQueryBuffer extends BaseQueryBean {

    /**
     * (除HttpServletRequest取得外)添加的查询字段
     */
    private QueryFieldArr queryFieldArr = new QueryFieldArr();
    /**
     * 添加到 HttpServletRequest取得的排序字段前
     */
    private List<MongoSortBean> frontSortList = new ArrayList<>();
    /**
     * 追加到 HttpServletRequest取得的排序字段后
     */
    private List<MongoSortBean> behindSortList = new ArrayList<>();
    /**
     * 可查询过滤字段白名单
     */
    private Set<String> blackQueryFieldSets = new HashSet<>();
    /**
     * 可查询过滤字段白名单
     */
    private Set<String> whiteQueryFieldSets = new HashSet<>();

    /**
     * 是否使用 白名单
     */
    private Boolean whiteSetsFlag = false;
    /**
     * 分页bean
     */
    private MongoQueryPageBean pageBean = null;

    public MongoQueryBuffer() {
    }

    /**
     * 批量添加 MyMongoCommonQueryFieldEnum 定义的项
     * @param enums
     */
    public MongoQueryBuffer(MyMongoCommonQueryFieldEnum... enums) {
        if (enums != null && enums.length > 0) {
            QueryFieldArr queryFields = new QueryFieldArr(enums);
            this.queryFieldArr.addAll(queryFields);
        }
    }

    /**
     * 添加 QueryField 到集合
     * @param fieldBean
     */
    public void addQueryFieldItem(QueryField fieldBean) {
        if (fieldBean != null) {
            this.queryFieldArr.add(fieldBean);
        }
    }

    public MongoQueryBuffer addFrontSortItem(MyMongoCommonSortFieldEnum... commonSortFieldEnumArr) {
        if (commonSortFieldEnumArr != null && commonSortFieldEnumArr.length > 0) {
            for (MyMongoCommonSortFieldEnum sortFieldEnum : commonSortFieldEnumArr) {
                this.frontSortList.add(0, new MongoSortBean(sortFieldEnum.getFieldName(), sortFieldEnum.getAscFlag()));
            }
        }
        return this;
    }

    public MongoQueryBuffer addBehindSortItem(MyMongoCommonSortFieldEnum... commonSortFieldEnumArr) {
        if (commonSortFieldEnumArr != null && commonSortFieldEnumArr.length > 0) {
            for (MyMongoCommonSortFieldEnum sortFieldEnum : commonSortFieldEnumArr) {
                this.behindSortList.add(new MongoSortBean(sortFieldEnum.getFieldName(), sortFieldEnum.getAscFlag()));
            }
        }
        return this;
    }

    public MongoQueryBuffer addFrontSortItem(MongoSortBean mongoSortBean) {
        if (mongoSortBean != null) {
            this.frontSortList.add(mongoSortBean);
        }
        return this;
    }

    public MongoQueryBuffer addFrontSortItem(List<MongoSortBean> mongoSortBeans) {
        if (CollectionUtils.isNotEmpty(mongoSortBeans)) {
            this.frontSortList.addAll(mongoSortBeans);
        }
        return this;
    }

    public MongoQueryBuffer addBehindSortItem(MongoSortBean mongoSortBean) {
        if (mongoSortBean != null) {
            this.behindSortList.add(mongoSortBean);
        }
        return this;
    }

    public MongoQueryBuffer addBlackQueryFieldItem(String... fieldNameArr) {
        if (fieldNameArr != null && fieldNameArr.length > 0) {
            for (String fieldName : fieldNameArr) {
                if (StringUtils.isNotBlank(fieldName)) {
                    this.blackQueryFieldSets.add(fieldName);
                }
            }
        }
        return this;
    }

    public MongoQueryBuffer addWhiteQueryFieldItem(String... fieldNameArr) {
        if (fieldNameArr != null && fieldNameArr.length > 0) {
            for (String fieldName : fieldNameArr) {
                if (StringUtils.isNotBlank(fieldName)) {
                    this.whiteQueryFieldSets.add(fieldName);
                }
            }
        }
        return this;
    }

    /**
     * 取得经过处理后的自身
     * @return
     */
    public MongoQueryBuffer getRefreshedSelf() {
        if (CollectionUtils.isNotEmpty(this.whiteQueryFieldSets) && CollectionUtils.isNotEmpty(this.blackQueryFieldSets)) {
            //取得黑名单与白名单的交集
            Sets.SetView<String> intersection = Sets.intersection(this.whiteQueryFieldSets, this.blackQueryFieldSets);
            //黑名单 优先于 白名单
            if (intersection != null) {
                Iterator<String> iterator = intersection.iterator();
                while (iterator.hasNext()) {
                    this.whiteQueryFieldSets.remove(iterator.next());
                }
            }
        }
        return this;
    }

}
