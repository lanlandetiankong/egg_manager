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
public class AntdvPaginationBean {
    private Integer current ;
    private Integer pageSize ;
    private int total ;


    public static AntdvPaginationBean gainLimitPaginationBean(Integer pageSize){
        return new AntdvPaginationBean(1,pageSize,0);
    }

    /**
     * 默认分页
     * @return
     */
    public static AntdvPaginationBean gainDefaultPaginationBean(){
        return new AntdvPaginationBean(1,10,0);
    }
}
