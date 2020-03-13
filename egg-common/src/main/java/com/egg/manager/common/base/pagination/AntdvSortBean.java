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
@NoArgsConstructor
@AllArgsConstructor
public class AntdvSortBean {
    private String field ;
    //正序：ascend,倒序:descend
    private String order ;
    private boolean isAsc ;



    public static final String ORDER_ASC = "ascend";
    public static final String ORDER_DESC = "descend";


    public static AntdvSortBean gainCreateTimeDescBean(){
        return new AntdvSortBean("create_time",ORDER_DESC,false) ;
    }

    public static AntdvSortBean gainOrderSortBean(boolean isAsc){       //排序字段
        return new AntdvSortBean("order",isAsc ? ORDER_ASC :ORDER_DESC,isAsc) ;
    }

    public boolean getOrderIsAsc(){
        return this.ORDER_ASC.equalsIgnoreCase(this.order);
    }
}