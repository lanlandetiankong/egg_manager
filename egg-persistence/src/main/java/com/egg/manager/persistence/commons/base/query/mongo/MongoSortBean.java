package com.egg.manager.persistence.commons.base.query.mongo;

import com.egg.manager.persistence.commons.base.pagination.IBasePagination;
import lombok.Data;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
public class MongoSortBean extends IBasePagination {
    /**
     * 字段名
     */
    private String field;
    /**
     * 正序：ascend,倒序:descend
     */
    private Boolean ascFlag;


    public MongoSortBean() {
    }

    public MongoSortBean(String field, boolean ascFlag) {
        this.field = field;
        this.ascFlag = ascFlag;
    }

    public static final String ORDER_ASC = "ascend";
    public static final String ORDER_DESC = "descend";


    public static MongoSortBean gainCreateTimeDescBean() {
        return new MongoSortBean("create_time", false);
    }

    public static MongoSortBean gainOrderSortBean(boolean ascFlag) {       //排序字段
        return new MongoSortBean("order", ascFlag);
    }

    public boolean getOrderIsAsc() {
        return Boolean.TRUE.equals(this.ascFlag);
    }
}
