package com.egg.manager.common.base.pagination.antdv;

import com.egg.manager.common.base.pagination.MyBasePagination;
import lombok.Data;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/4
 * \* Time: 14:51
 * \* Description:
 * \p
 */
@Data
public class AntdvSortBean extends MyBasePagination {
    /**
     * 字段名
     */
    private String field ;
    /**
     * 正序：ascend,倒序:descend
     */
    private Boolean ascFlag ;
    /**
     *  序号
     */
    private String order ;
    public AntdvSortBean(){}
    public AntdvSortBean(String field, boolean ascFlag) {
        this.field = field;
        this.ascFlag = ascFlag;
        this.order = (ascFlag) ? ORDER_ASC : ORDER_DESC ;
    }

    public static final String ORDER_ASC = "ascend";
    public static final String ORDER_DESC = "descend";


    public static AntdvSortBean gainCreateTimeDescBean(){
        return new AntdvSortBean("create_time",false) ;
    }

    public static AntdvSortBean gainOrderSortBean(boolean ascFlag){       //排序字段
        return new AntdvSortBean("order",ascFlag) ;
    }

    public boolean getOrderIsAsc(){
        return AntdvSortBean.ORDER_ASC.equalsIgnoreCase(this.order);
    }
}
