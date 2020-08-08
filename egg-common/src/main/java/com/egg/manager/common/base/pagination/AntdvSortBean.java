package com.egg.manager.common.base.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/4
 * \* Time: 14:51
 * \* Description:
 * \p
 */
@Data
@AllArgsConstructor
public class AntdvSortBean {
    private String field ;
    private Boolean ascFlag ;
    //正序：ascend,倒序:descend
    private String order ;

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
        return this.ORDER_ASC.equalsIgnoreCase(this.order);
    }
}
