package com.egg.manager.common.base.query.mongo;

import com.egg.manager.common.base.pagination.MyBasePagination;
import lombok.Data;

/**
 * \* note:
 * @author: zhouchengjie
 * \* Date: 2020/2/4
 * \* Time: 14:51
 * \* Description:
 * \p
 */
@Data
public class MyMongoSortBean extends MyBasePagination {
    /**
     * 字段名
     */
    private String field ;
    /**
     * 正序：ascend,倒序:descend
     */
    private Boolean ascFlag ;
    /**
     * 排序值
     */
    private String order ;


    public MyMongoSortBean(){}
    public MyMongoSortBean(String field, boolean ascFlag) {
        this.field = field;
        this.ascFlag = ascFlag;
        this.order = (ascFlag) ? ORDER_ASC : ORDER_DESC ;
    }

    public static final String ORDER_ASC = "ascend";
    public static final String ORDER_DESC = "descend";


    public static MyMongoSortBean gainCreateTimeDescBean(){
        return new MyMongoSortBean("create_time",false) ;
    }

    public static MyMongoSortBean gainOrderSortBean(boolean ascFlag){       //排序字段
        return new MyMongoSortBean("order",ascFlag) ;
    }

    public boolean getOrderIsAsc(){
        return MyMongoSortBean.ORDER_ASC.equalsIgnoreCase(this.order);
    }
}
